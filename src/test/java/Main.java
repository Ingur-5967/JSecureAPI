import ru.solomka.secure.SecureBoot;
import ru.solomka.secure.ServerHandler;
import ru.solomka.secure.secure.SecureEntity;
import ru.solomka.secure.secure.SecureManager;
import ru.solomka.secure.secure.SecureValidator;
import ru.solomka.secure.secure.crypt.EncryptTool;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class Main implements SecureBoot {

    public static void main(String[] args) throws IOException {

        String jwt = new SecureManager(Main.class)
                .generateSecureKey(new SecureEntity() {
                    @Override
                    public String getId() {
                        return "Local";
                    }

                    @Override
                    public Map<String, Object> getParameters() {
                        return Map.of("name", "Naming", "age", 12);
                    }
                }, 9000000);

        ServerHandler serverHandler = new ServerHandler(STR."http://localhost:8080/api/token?sec_jwt=\{
                }");

        System.out.println(STR."Answer: \{serverHandler.get()}");

        SecureManager secureManager = new SecureManager(Main.class);
        secureManager.validator().validateKey("eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzc3MiLCJuYW1lIjoiTmFtZSIsImlhdCI6MTcxMTc5ODg0OCwiZXhwIjoxNzExNzk5ODA4fQ.udk2WhP6me5k51uprEP8AXg16DPWsrYnK8XzGaCXEyKBZ0Cc2c4C4mn2J51v_t6k");
    }

    @Override
    public String getSpecificKey() {
        return "iahfa9f78sy98GS867DTF79yashu67TDFS8YDINDFGS807UmkhngfgjlpoO0U8GSDHNFMLC";
    }
}