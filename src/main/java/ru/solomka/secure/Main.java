package ru.solomka.secure;

import io.jsonwebtoken.Claims;
import ru.solomka.secure.secure.EncryptTool;
import ru.solomka.secure.secure.SecureEntity;
import ru.solomka.secure.secure.SecureManager;

import java.util.Date;
import java.util.Map;

public class Main implements SecureBoot {

    public void onEnable() {
        SecureManager secureManager = new SecureManager(this);

        String cryptJWT = secureManager.generateSecureKey(new SecureEntity() {
            @Override
            public String getId() {
                return "Anatoliy";
            }

            @Override
            public Map<String, Object> getParameters() {
                return Map.of("name", "Local", "email", "local@mail.ru");
            }
        }, 8600000);

        Claims decryptJWT = EncryptTool.getDecryptKey(getRootKey(), cryptJWT);

        Object name = decryptJWT.get("name"); // Local
        Object email = decryptJWT.get("email"); // local@mail.ru

        Date nowTime = new Date();

        if(!secureManager.checkSecureKey(decryptJWT, nowTime)) {
            System.out.println("Token is not a valid!");
            return;
        }

        // ...
    }

    @Override
    public String getRootKey() {
        return "uhasdyuigxcsihjwertyui237912t34678UIGYUFDAS78TY78678t3rghuasdsjgurhjndftdyuig"; // (length * 8) >= 256 or (length * 8) >= 384 or (length * 8) >= 512
    }
}