package com.aluracursos.desafio.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    //Modificador de acceso
    public String obtenerDatos(String url){
        // Crear un cliente HTTP para enviar la solicitud
        HttpClient client = HttpClient.newHttpClient();

        // Crear una solicitud HTTP GET con la URL proporcionada
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Objeto para almacenar la respuesta HTTP
        HttpResponse<String> response = null;

        try {
            // Enviar la solicitud y obtener la respuesta
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            // Capturar cualquier error de E/S (IOException) y lanzarlo como una RuntimeException
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // Capturar cualquier interrupción (InterruptedException) y lanzarlo como una RuntimeException
            throw new RuntimeException(e);
        }

        // Obtener el cuerpo de la respuesta (en este caso, se asume que es JSON)
        String json = response.body();

        // Devolver el JSON obtenido como una cadena
        return json;
    }

}
