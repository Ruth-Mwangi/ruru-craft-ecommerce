package com.ruth.rurucraftsecommerce.authentication;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Entity
@Table(name = "users")
public class User {
}
