import ru.solomka.secure.ServerHandler;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerHandler serverHandler = new ServerHandler("http://localhost:8080");
        System.out.println(serverHandler.send("123"));
    }
}