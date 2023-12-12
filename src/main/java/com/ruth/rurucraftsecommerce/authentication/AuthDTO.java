package com.ruth.rurucraftsecommerce.authentication;

public class AuthDTO {
    public static class LoginRequest {
        private String username;
        private String password;

        // Constructors, getters, and setters

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
    public static class Response {
        private String message;
        private String token;

        // Constructors, getters, and setters

        public Response(String message, String token) {
            this.message = message;
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class RegistrationResponse {
        private String message;
        private String user;

        // Constructors, getters, and setters

        public RegistrationResponse(String message, String user) {
            this.message = message;
            this.user = user;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

}
