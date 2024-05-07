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

    // Constante
    private static final String URL_BASE = "https://gutendex.com/books/" ;

    // Instancias de servicios
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    // Colores ANSI
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_PURPLE = "\u001B[35m";




    // Método principal para mostrar el menú y realizar las operaciones
    public void muestraElMenu(){

        // Consumir y mostrar JSON
        System.out.println(ANSI_RED + "*******************************************************************" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + " Consumiendo JSON " + ANSI_RESET);
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(ANSI_GREEN + "JSON obtenido: " + json + ANSI_RESET);

        // Convertir y mostrar datos
        System.out.println(ANSI_PURPLE + "Convirtiendo JSON..." + ANSI_RESET);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(ANSI_GREEN + "Datos obtenidos: " + datos + ANSI_RESET);


        // Mostrar top 10 libros más descargados
        System.out.println(ANSI_RED + "*******************************************************************" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + " Top 10 libros más descargados " + ANSI_RESET);
        AtomicInteger contador = new AtomicInteger(1);
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l-> l.titulo().toUpperCase())
                .forEach(libro -> System.out.println(ANSI_GREEN + contador.getAndIncrement() + ".- " + libro + ANSI_RESET ));



        // Buscar libros por nombre
        System.out.println(ANSI_RED + "*******************************************************************" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Ingrese el nombre del libro que desea buscar" + ANSI_RESET);
        var tituloLibro = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> librosBuscando = datosBusqueda.resultados().stream()
                .filter(l-> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (librosBuscando.isPresent()){
            System.out.println(ANSI_GREEN + "El libro fue encontrado" + ANSI_RESET);
            System.out.println(librosBuscando.get());
        }else {
            System.out.println(ANSI_GREEN + "Libro no encontrado" + ANSI_RESET);
        }

        // Generar y mostrar estadísticas
        System.out.println(ANSI_RED + "*******************************************************************" + ANSI_RESET);
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d->d.numeroDeDescargas() >0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println(ANSI_GREEN + "Cantidad Maxima de Descargas: "+ est.getMax() + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Cantidad Media de Descargas: " + est.getAverage() + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Cantidad Minima de Decargas: "+ est.getMin() + ANSI_RESET);
        System.out.println(ANSI_YELLOW  + "Cantidad de Registros Evaluados para Calcular las Estadisticas: "+est.getCount()+ ANSI_RESET);

        System.out.println(ANSI_RED + "*******************************************************************" + ANSI_RESET);


    }
}
