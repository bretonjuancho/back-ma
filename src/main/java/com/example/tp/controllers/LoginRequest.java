package com.example.tp.controllers;

public record LoginRequest(
        String dni,
        String password
) {
}
