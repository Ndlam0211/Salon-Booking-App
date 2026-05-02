package com.lamnd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Auditable {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String role;
}
