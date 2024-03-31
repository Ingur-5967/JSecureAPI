package ru.solomka.jwt.secure.crypt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import java.util.Optional;

public class EncryptTool {

    public static @NotNull Optional<Claims> getDecryptKey(@NotNull String secKey, @NotNull String cryptKey) {
        SecretKey key = Keys.hmacShaKeyFor(secKey.getBytes());
        return Optional.of(Jwts.parser().verifyWith(key).build().parseSignedClaims(cryptKey).getPayload());
    }
}