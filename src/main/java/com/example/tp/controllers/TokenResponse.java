package com.example.tp.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse (
    @JsonProperty("acccess_token")
    String accessToken,
    @JsonProperty("refresh_token")
    String refreshToken,
    @JsonProperty("rol")
    String rol
){

}
