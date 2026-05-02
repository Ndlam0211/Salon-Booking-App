package com.lamnd.dto.request;

import com.lamnd.annotation.UniqueEmail;
import com.lamnd.annotation.UniquePhone;
import com.lamnd.annotation.UniqueUsername;
import com.lamnd.annotation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequest {
    @NotBlank(message = "Username is required")
    @UniqueUsername
    private String username;

    @NotBlank(message = "Email is required")
    @ValidEmail
    @UniqueEmail
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,message= "Password must be at least 8 characters")
    private String password;

    @Pattern(regexp = "^\\+?\\d{10}$", message = "Phone number must be 10 digits")
    @UniquePhone
    private String phoneNumber;

    private String fullName;
    private String role;
}
