package com.example.tp.controllers;

public record RegisterRequest(
        String email,
        String contrasenia,
        String nombre,
        String apellido,
        String dni
) {
}
