package ru.solomka.jwt.secure;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class SecureManager {
    private final String certificate;

    public SecureManager(String certificate) {
        this.certificate = certificate;
    }

    public <T extends SecureEntity> String generateSecureKey(@NotNull T entity, long activeTime) {
        if(certificate.isEmpty() || (certificate.length() * 8) < 256)
            throw new IllegalArgumentException("Secure key is empty OR (size * 8) < 256 symbols");

        if(activeTime <= 0)
            throw new IllegalArgumentException("Active time for a JWT token cannot be negative!");

        if(entity.getId() == null || entity.getParameters() == null)
            throw new NullPointerException("Entity for a JWT token cannot be null!");

        SecretKey key = Keys.hmacShaKeyFor(certificate.getBytes());

        Date now = new Date();

        return Jwts.builder().subject(entity.getId()).claims(entity.getParameters())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + activeTime))
                .signWith(key).compact();
    }

    public SecureValidator validator() {
        return SecureValidator.of(certificate);
    }
}
