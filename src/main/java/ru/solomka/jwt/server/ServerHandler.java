package ru.solomka.jwt.server;

import org.apache.http.ssl.SSLContexts;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;

public class ServerHandler {

    private final ServerConnection connection;

    public ServerHandler(ServerConnection connection) {
        this.connection = connection;
    }

    @NotNull
    public Optional<Object> getResponse(HttpRequest request, HttpResponse.BodyHandler<?> body) {
        HttpResponse<?> response;
        try {
            response = connection.newClient().send(request, body);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Connection refused");
        }
        return Optional.of(response.body());
    }

    public HttpRequest.Builder getRequestBuilder() {
        return connection.newRequest().uri(connection.getAbsoluteAddress());
    }

    public @NotNull HttpsHandler https(String sslPath) {
        return new HttpsHandler(sslPath);
    }

    public @NotNull HttpHandler http() {
        return new HttpHandler();
    }

    public class HttpHandler {
        public Optional<Object> sendWithBody(String value) {
            HttpRequest request = getRequestBuilder().POST(HttpRequest.BodyPublishers.ofString(value)).build();
            return getResponse(request, HttpResponse.BodyHandlers.ofString());
        }

        public Optional<Object> getOfNullBody() {
            HttpRequest request = getRequestBuilder().GET().build();
            return getResponse(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    public class HttpsHandler {

        private final String sslPath;

        private HttpsHandler(String sslPath) {
            this.sslPath = sslPath;
        }

        public Optional<Object> sendWithSecure(String value) {

            SSLContext sslContext = buildSSLContext();
            HttpClient httpClient = HttpClient.newBuilder().sslContext(sslContext).build();

            HttpResponse<String> response;
            try {
                response = httpClient.send(getRequestBuilder().POST(HttpRequest.BodyPublishers.ofString(value)).build(), HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            return Optional.of(response);
        }

        public Optional<Object> getWithSecure() {

            SSLContext sslContext = buildSSLContext();
            HttpClient httpClient = HttpClient.newBuilder().sslContext(sslContext).build();

            HttpResponse<String> response;
            try {
                response = httpClient.send(getRequestBuilder().GET().build(), HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            return Optional.of(response);
        }

        private SSLContext buildSSLContext() {
            SSLContext sslContext;
            try {
                sslContext = SSLContexts.custom().loadKeyMaterial(loadKeyForSSL(), null).build();
            } catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException | KeyStoreException e) {
                throw new RuntimeException(e);
            }
            return sslContext;
        }

        private @NotNull KeyStore loadKeyForSSL() throws KeyStoreException {
            String keyPassphrase = "";

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try {
                keyStore.load(new FileInputStream(sslPath), keyPassphrase.toCharArray());
            } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
                throw new RuntimeException(e);
            }
            return keyStore;
        }
    }
}