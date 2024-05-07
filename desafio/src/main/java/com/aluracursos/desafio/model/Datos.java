package com.aluracursos.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Inicia

// Indica que esta clase representa los datos obtenidos de la API

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(

        @JsonAlias( "results" ) List< DatosLibros > resultados
) {
}
