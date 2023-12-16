package com.ruth.rurucraftsecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

@Component
//@ConfigurationProperties(prefix = "rsa")
public class RsaKeyConfigProperties {
    @Value("${rsa.public-key}")
    private String publicKeyPath;

    @Value("${rsa.private-key}")
    private String privateKeyPath;


    public RSAPublicKey getPublicKey() {
        try {
            // Use ClassLoader to load the file from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(publicKeyPath);

            if (inputStream == null) {
                throw new RuntimeException("Public key file not found: " + publicKeyPath);
            }

            // Read the content of the input stream into a string
            String keyContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Remove "-----BEGIN PUBLIC KEY-----" and "-----END PUBLIC KEY-----"
            keyContent = keyContent.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

            // Remove whitespaces and newlines
            keyContent = keyContent.replaceAll("\\s", "");


            // Generate the public key
            byte[] decodedKey = Base64.getDecoder().decode(keyContent);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error reading public key", e);
        }
    }


    public RSAPrivateKey getPrivateKey() {
        try {
            // Use ClassLoader to load the file from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(privateKeyPath);

            if (inputStream == null) {
                throw new RuntimeException("Private key file not found: " + privateKeyPath);
            }

            // Read the content of the input stream into a string
            String keyContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Remove "-----BEGIN PRIVATE KEY-----" and "-----END PRIVATE KEY-----"
            keyContent = keyContent.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");

            // Remove whitespaces and newlines
            keyContent = keyContent.replaceAll("\\s", "");


            // Generate the private key
            byte[] decodedKey = Base64.getDecoder().decode(keyContent);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error reading private key", e);
        }
    }


}
