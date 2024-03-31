package ru.solomka.jwt;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ServerHandler {

    private final URL url;

    public ServerHandler(String url) {
        try {
            this.url = URI.create(url).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerHandler(URL url) {
        this.url = url;
    }

    public Optional<Object> sendWithBody(String value) {
        HttpRequest request = getRequestBuilder().POST(HttpRequest.BodyPublishers.ofString(value)).build();
        return getResponse(request, HttpResponse.BodyHandlers.ofString());
    }

    public Optional<Object> getOfNullBody() {
        HttpRequest request = getRequestBuilder().GET().build();
        return getResponse(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest.Builder getRequestBuilder() {
        try {
            return HttpRequest.newBuilder().uri(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private Optional<Object> getResponse(HttpRequest request, HttpResponse.BodyHandler<?> body) {
        HttpResponse<?> response;
        try {
            try {
                response = HttpClient.newHttpClient().send(request, body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(response.body());
    }
}