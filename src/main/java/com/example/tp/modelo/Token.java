package com.example.tp.modelo;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity (name = "tokens")

public final class Token {


    public enum TokenType{
        BEARER
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TokenType tokenType = TokenType.BEARER;

    @Column(nullable = false)
    private Boolean isRevoked;

    @Column(nullable = false)
    private Boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Usuario user;


}
