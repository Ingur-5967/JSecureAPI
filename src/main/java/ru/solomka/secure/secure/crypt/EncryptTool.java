package ru.solomka.secure.secure.crypt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import ru.solomka.secure.SecureBoot;

import javax.crypto.SecretKey;

public class EncryptTool {

    public static Claims getDecryptKey(@NotNull String secKey, String cryptKey) {
        SecretKey key = Keys.hmacShaKeyFor(secKey.getBytes());
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(cryptKey).getPayload();
    }
}