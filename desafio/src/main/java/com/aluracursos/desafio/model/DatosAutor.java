package com.aluracursos.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        //Continua 3

        @JsonAlias( "name" ) String nombre,

        @JsonAlias( "birth_year" ) String fechaDeNaciemineto,

        @JsonAlias( "death_year" ) String FechaDeMuerte



) {
}
