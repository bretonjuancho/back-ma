package com.example.tp.controllers;

public record RegisterRequest(
        String dni,
        String contrasenia,
        String nombre,
        String apellido,
        String email
) {
}
