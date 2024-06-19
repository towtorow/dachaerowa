package com.dachaerowa.dachaerowa.internal.api.response;

import lombok.Data;
import org.springframework.stereotype.Component;
@Data
public class AuthResponse {
    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

}