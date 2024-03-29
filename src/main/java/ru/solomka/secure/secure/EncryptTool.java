package ru.solomka.secure.secure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;

public class EncryptTool {
    public static Claims getDecryptKey(@NotNull String secKey, String cryptKey) {
        SecretKey key = Keys.hmacShaKeyFor(secKey.getBytes());
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(cryptKey).getPayload();
    }
}