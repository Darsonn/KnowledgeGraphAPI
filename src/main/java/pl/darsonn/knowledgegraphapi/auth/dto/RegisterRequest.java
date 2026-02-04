package pl.darsonn.knowledgegraphapi.auth.dto;

public record RegisterRequest(String email, String password, String displayName) {}
