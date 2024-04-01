package ru.solomka.jwt.secure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import java.util.Optional;

public class SecureValidator {

    private final String certificate;

    private SecureValidator(String certificate) {
        this.certificate = certificate;
    }

    public Optional<Jws<Claims>> validateKey(@NotNull String encryptKey) {
        Jws<Claims> jws;

        if(encryptKey.isEmpty()) return Optional.empty();

        try {
            SecretKey key = Keys.hmacShaKeyFor(certificate.getBytes());
            jws = Jwts.parser().verifyWith(key).build().parseSignedClaims(encryptKey);
        } catch(Exception e){ throw new RuntimeException("Token is invalid! Please, retry later"); }
        return Optional.of(jws);
    }

    @Contract(value = "_ -> new", pure = true)
    protected static @NotNull SecureValidator of(String certificate) {
        return new SecureValidator(certificate);
    }
}