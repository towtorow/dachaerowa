package com.dachaerowa.dachaerowa.internal.api.request;

import com.sun.istack.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String email;

}