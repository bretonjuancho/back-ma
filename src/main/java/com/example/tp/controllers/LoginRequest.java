package com.example.tp.controllers;

public record LoginRequest(
        String email,
        String password
) {
}
