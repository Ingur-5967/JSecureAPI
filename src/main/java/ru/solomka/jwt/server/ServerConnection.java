package ru.solomka.jwt.server;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class ServerConnection {

    private final String url;

    public ServerConnection(String url) {
        this.url = url;
    }

    @Contract(pure = true)
    public ServerConnection(@NotNull URL url) {
        this.url = url.toString();
    }

    public HttpClient newClient() {
        return HttpClient.newHttpClient();
    }

    public HttpRequest.Builder newRequest() {
        return HttpRequest.newBuilder();
    }

    public URI getAbsoluteAddress() {
        return URI.create(url);
    }
}