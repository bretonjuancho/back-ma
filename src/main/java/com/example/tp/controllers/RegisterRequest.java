package com.example.tp.controllers;

import lombok.Getter;


public record RegisterRequest(
        String dni,
        String contrasenia,
        String nombre,
        String apellido,
        String email
) {
}
