package com.example.tp.modelo;


import com.example.tp.DTO.UsuarioDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    private int id;

    @Column(name = "dni", unique = true, nullable = false)
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<GestionUsuario> gestionesCreador;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<GestionLicencia> licenciasEmitidas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<GestionTitular> titularesCreados;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;


    public Usuario() {
    }

    public Usuario(String dni,String nombre,String apellido, String email, String password) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.gestionesCreador = new ArrayList<>();
        this.licenciasEmitidas = new ArrayList<>();
        this.titularesCreados = new ArrayList<>();
    }

    public Usuario(UsuarioDTO usuario) {
        this.dni = usuario.getDni();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
        this.gestionesCreador = new ArrayList<>();
        this.licenciasEmitidas = new ArrayList<>();
        this.titularesCreados = new ArrayList<>();
    }

    public void addGestionUsuario(GestionUsuario gestionUsuario){this.gestionesCreador.add(gestionUsuario);}

    public Object orElseThrow(Object o) {
        return null;
    }
}
