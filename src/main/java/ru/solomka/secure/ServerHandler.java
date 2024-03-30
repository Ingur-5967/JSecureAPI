package ru.solomka.secure;

import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

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

    public Object send(String secKey) throws IOException {

        HttpURLConnection connection = getConnection();

        try {
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
        } catch (ProtocolException ex) {
            throw new RuntimeException(ex);
        }

        String request = STR."{jwt: \{secKey}";

        OutputStream output = connection.getOutputStream();
        output.write(request.getBytes());
        output.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }

        connection.disconnect();

        return response.toString().isEmpty() ? "None" : response;
    }

    public Object get() {

        HttpURLConnection conn = getConnection();

        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String inputLine;
        StringBuffer response = new StringBuffer();

        while (true) {
            try {
                if ((inputLine = in.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response.append(inputLine);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    public HttpURLConnection getConnection() {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}