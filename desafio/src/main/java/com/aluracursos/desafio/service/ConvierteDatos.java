package com.aluracursos.desafio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// Clase encargada de convertir datos JSON en objetos Java
public class ConvierteDatos implements IConvierteDatos{

    // Objeto ObjectMapper para realizar la conversión JSON a objetos Java
    private ObjectMapper objectMapper = new ObjectMapper();

    // Método para obtener datos de tipo T a partir de un JSON y una clase T
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {

            // Realiza la conversión del JSON a un objeto de la clase especificada
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {

            // Captura y relanza excepciones de procesamiento de JSON como RuntimeException
            throw new RuntimeException(e);
        }
    }

}
