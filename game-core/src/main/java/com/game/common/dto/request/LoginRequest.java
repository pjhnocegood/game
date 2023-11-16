package com.game.common.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull
    @Size(min = 3, max = 50)
    private String emailAddress;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}
