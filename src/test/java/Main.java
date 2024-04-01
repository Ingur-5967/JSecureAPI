import ru.solomka.jwt.server.ServerConnection;
import ru.solomka.jwt.server.ServerHandler;

public class Main {
    public static void main(String[] args) {
        ServerHandler serverHandler = new ServerHandler(new ServerConnection("http://localhost:8080/api"));
        System.out.println(serverHandler.http().sendWithBody("TestValueForServer"));
    }
}