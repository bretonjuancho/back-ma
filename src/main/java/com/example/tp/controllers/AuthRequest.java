package com.example.tp.controllers;

public record AuthRequest (
        String email,
        String password
){
}
