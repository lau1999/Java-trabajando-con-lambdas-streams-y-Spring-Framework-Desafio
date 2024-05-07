package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.Datos;
import com.aluracursos.desafio.model.DatosLibros;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Principal {
    //Constante
    private static final String URL_BASE = "https://gutendex.com/books/" ;

    //instancia
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private Scanner teclado = new Scanner(System.in);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";


    //Metodo
    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        //Prueba si Realmente consume el Json

        System.out.println(json);

        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        // Top 10 libros más descargados
        System.out.println("************************************");
        System.out.println(" Top 10 libros más descargados");
        AtomicInteger contador = new AtomicInteger(1);
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l-> l.titulo().toUpperCase())
                .forEach(libro -> System.out.println(contador.getAndIncrement() + ".- " + libro));
                //.forEach(System.out::println);


        // Buscando libros por nombre
        System.out.println("************************************");
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> librosBuscando = datosBusqueda.resultados().stream()
                .filter(l-> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (librosBuscando.isPresent()){
            System.out.println("El libro fue encontrado");
            System.out.println(librosBuscando.get());
        }else {
            System.out.println("Libro no encontrado");
        }

        //Generando estadísticas
        System.out.println("************************************");
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d->d.numeroDeDescargas() >0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("Cantidad Maxima de Descargas: "+ est.getMax());
        System.out.println("Cantidad Media de Descargas: " + est.getAverage());
        System.out.println("Cantidad Minima de Decargas: "+ est.getMin());
        System.out.println("Cantidad de Registros Evaluados para Calcular las Estadisticas: "+est.getCount());


    }
}
