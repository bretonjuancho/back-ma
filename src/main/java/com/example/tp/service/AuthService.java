package com.example.tp.service;

import com.example.tp.controllers.AuthRequest;
import com.example.tp.controllers.RegisterRequest;
import com.example.tp.controllers.TokenResponse;
import com.example.tp.modelo.Token;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.TokenRepository;
import com.example.tp.repository.UsuarioRepository;
import com.example.tp.controllers.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository bddUsuario;
    private final TokenRepository bddToken;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final com.example.tp.repository.TokenRepository tokenRepository;

    public TokenResponse register(RegisterRequest request){
        Usuario usuario = new Usuario(request.dni(),
                request.nombre(),
                request.apellido(),
                request.email(),
                passwordEncoder.encode(request.contrasenia()));
        Usuario savedUsuario = bddUsuario.save(usuario);
        String jwtToken = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);
        saveUserToken(savedUsuario,jwtToken);
        return new TokenResponse(jwtToken,refreshToken,"administrativo");
    }

    private void saveUserToken(Usuario usuario, String jwtToken){
        Token token = Token.builder()
                .user(usuario)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();
        bddToken.save(token);
    }

    public TokenResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.dni(),
                        request.password()
                )
        );
        Usuario user = bddUsuario.findByDni(request.dni());
        if(user==null){throw new UsernameNotFoundException("Usuario no encontrado");}
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);
        return new TokenResponse(jwtToken,refreshToken,"administrativo");
    }

    private void revokeAllUserTokens(final Usuario user){
        final List<Token> validUserToken = tokenRepository
                .findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());
        if(!validUserToken.isEmpty()){
            for(final Token token : validUserToken){
                token.setIsExpired(true);
                token.setIsRevoked(true);
            }
            tokenRepository.saveAll(validUserToken);
        }
    }

    public TokenResponse authenticate(final AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.dni(),
                        request.password()
                )
        );
        final Usuario user = (Usuario) bddUsuario.findByDni(request.dni()).orElseThrow(new UsernameNotFoundException("Usuario no encontrado"));
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken,"administrativo");
    }


    public TokenResponse refreshToken(@NotNull final String authentication) {

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = authentication.substring(7);
        final String userDNI = jwtService.extractUsername(refreshToken);
        if (userDNI == null) {
            return null;
        }

        final Usuario user = (Usuario) this.bddUsuario.findByDni(userDNI).orElseThrow(new UsernameNotFoundException("Usuario no encontrado"));
        final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
        if (!isTokenValid) {
            return null;
        }

        final String accessToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken,"administrativo");
    }

}
