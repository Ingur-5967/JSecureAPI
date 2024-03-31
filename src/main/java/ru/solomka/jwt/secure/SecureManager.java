package ru.solomka.jwt.secure;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import ru.solomka.jwt.SecureBoot;

import javax.crypto.SecretKey;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class SecureManager {
    private final SecureBoot boot;

    public SecureManager(SecureBoot boot) {
        this.boot = boot;
    }

    public SecureManager(@NotNull Class<? extends SecureBoot> boot) {
        try {
            this.boot = boot.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends SecureEntity> String generateSecureKey(@NotNull T entity, long activeTime) {
        if(boot.getSpecificKey().isEmpty() || (boot.getSpecificKey().length() * 8) < 256)
            throw new IllegalArgumentException("Secure key is empty OR (size * 8) < 256 symbols");

        if(activeTime <= 0)
            throw new IllegalArgumentException("Active time for a JWT token cannot be negative!");

        if(entity.getId() == null || entity.getParameters() == null)
            throw new NullPointerException("Entity for a JWT token cannot be null!");

        SecretKey key = Keys.hmacShaKeyFor(boot.getSpecificKey().getBytes());

        Date now = new Date();

        return Jwts.builder().subject(entity.getId()).claims(entity.getParameters())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + activeTime))
                .signWith(key).compact();
    }

    public SecureValidator validator() {
        return SecureValidator.of(boot);
    }
}
