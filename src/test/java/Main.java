import ru.solomka.secure.SecureBoot;
import ru.solomka.secure.ServerHandler;
import ru.solomka.secure.secure.SecureEntity;
import ru.solomka.secure.secure.SecureManager;

import java.io.IOException;
import java.util.Map;

public class Main implements SecureBoot {

    public static void main(String[] args) throws IOException {

        ServerHandler serverHandler = new ServerHandler(STR."http://localhost:8080/api/token");
        System.out.println(serverHandler.getOfNullBody());
    }

    @Override
    public String getSpecificKey() {
        return "iahfa9f78sy98GS867DTF79yashu67TDFS8YDINDFGS807UmkhngfgjlpoO0U8GSDHNFMLC";
    }
}