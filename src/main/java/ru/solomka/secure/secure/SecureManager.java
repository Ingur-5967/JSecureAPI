package ru.solomka.secure.secure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import ru.solomka.secure.SecureBoot;

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
        if(boot.getRootKey().isEmpty() || (boot.getRootKey().length() * 8) < 256)
            throw new IllegalArgumentException("Secure key is empty OR size < 256 symbols");

        SecretKey key = Keys.hmacShaKeyFor(boot.getRootKey().getBytes());

        Date now = new Date();

        return Jwts.builder().subject(entity.getId()).claims(entity.getParameters())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + activeTime))
                .signWith(key).compact();
    }

    public boolean checkSecureKey(Claims encryptKey, Date time) {
        try {
            return encryptKey.getExpiration().before(time);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
