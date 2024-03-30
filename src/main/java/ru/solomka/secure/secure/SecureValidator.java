package ru.solomka.secure.secure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.solomka.secure.SecureBoot;

import javax.crypto.SecretKey;

public class SecureValidator {

    private final SecureBoot secureBoot;

    private SecureValidator(SecureBoot secureBoot) {
        this.secureBoot = secureBoot;
    }

    public Jws<Claims> validateKey(String encryptKey) {
        Jws<Claims> jws;
        try {
            SecretKey key = Keys.hmacShaKeyFor(secureBoot.getSpecificKey().getBytes());
            jws = Jwts.parser().verifyWith(key).build().parseSignedClaims(encryptKey);
        } catch(Exception e){ throw new RuntimeException("Token is invalid! Please, retry later"); }
        return jws;
    }

    @Contract(value = "_ -> new", pure = true)
    protected static @NotNull SecureValidator of(SecureBoot boot) {
        return new SecureValidator(boot);
    }
}