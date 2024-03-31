import ru.solomka.jwt.SecureBoot;
import ru.solomka.jwt.ServerHandler;

import java.io.IOException;

public class Main implements SecureBoot {

    public static void main(String[] args) throws IOException {

        ServerHandler serverHandler = new ServerHandler(STR."http://localhost:8080/api/token");
        System.out.println(serverHandler.sendWithBody("KrytoiKluchBlyat"));
    }

    @Override
    public String getSpecificKey() {
        return "iahfa9f78sy98GS867DTF79yashu67TDFS8YDINDFGS807UmkhngfgjlpoO0U8GSDHNFMLC";
    }
}