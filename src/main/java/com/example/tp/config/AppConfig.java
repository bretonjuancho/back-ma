package com.example.tp.config;

import com.example.tp.modelo.Administrador;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.AdministradorRepository;
import com.example.tp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.BeanProperty;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UsuarioRepository usuarioRepository;
    private final AdministradorRepository adminRepository;

   @Bean
   public UserDetailsService userDetailsService() {
       return username -> {
           final Usuario user = usuarioRepository.findByDni(username);
           if (user != null) {
               return org.springframework.security.core.userdetails.User.builder()
                       .username(user.getDni())
                       .password(user.getPassword())
                       //.roles("administrativo") // o user.getRol()
                       .build();
           }

           final Administrador admin = adminRepository.findByDni(username);
           if (admin != null) {
               System.out.println(" el administrador " + admin.getDni()  + "se consiguio de manera exitosa");
               return org.springframework.security.core.userdetails.User.builder()
                       .username(admin.getDni())
                       .password(admin.getPassword())
                     //  .roles("admin") // o admin.getRol()
                       .build();
           }

           throw new UsernameNotFoundException("DNI no encontrado en usuarios ni administradores");
       };

   }

   @Bean
   public AuthenticationProvider authenticationProvider() {
       DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
       daoAuthenticationProvider.setUserDetailsService(userDetailsService());
       daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
       return daoAuthenticationProvider;
   }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)  throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
