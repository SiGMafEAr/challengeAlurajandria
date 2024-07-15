package com.SiGMafEAr.Alurandria.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.SiGMafEAr.Alurandria.models.*;
import com.SiGMafEAr.Alurandria.repository.AutorRepository;
import com.SiGMafEAr.Alurandria.repository.LibroRepository;
import com.SiGMafEAr.Alurandria.services.ConsumeAPI;
import com.SiGMafEAr.Alurandria.services.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void showMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    ╔══════════════════════════════════════════════════╗
                    ║                    Alurandria                    ║
                    ╠═════════════════ Menú Principal ═════════════════╣
                    ║                                                  ║
                    ║ 1 - Buscar Libro por Título.                     ║
                    ║ 2 - Mostrar Libros registrados.                  ║
                    ║ 3 - Mostrar Autores registrados.                 ║
                    ║ 4 - Mostrar Autores que de un determinado año.   ║
                    ║ 5 - Mostrar Libros por Idioma.                   ║
                    ║ 6 - Top 10 Libros más descargados.               ║
                    ║                                                  ║
                    ║ 0 - Salir.                                       ║
                    ║                                                  ║
                    ╚══════════════════════════════════════════════════╝
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    buscarLibroRegistrado();
                    break;
                case 3:
                    buscarAutoresRegistrados();
                    break;
                case 4:
                    autoresPorYear();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 6:
                    topLibrosDescargados();
                    break;
                case 0:
                    System.out.println("Saliendo");
                    break;

                default:
                    System.out.println("Opción no valida! \nIntente de nuevo por favor: ");

            }
        }
    }

    private void topLibrosDescargados() {

        System.out.println("El Top de 10 Libros mas descargados es: \n");
        var json = consumeAPI.obtenerDatos(URL_BASE);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println("Salgo del conversor de datos");/////////////////////////////////////////////////////////////
        List<Libro> librosArray = new ArrayList<>();

        for (DatosLibro datosLibros : datos.resultados()) {
            Autor autor = new Autor(datosLibros.autor().get(0));
            Libro libro = new Libro(datosLibros, autor);
            librosArray.add(libro);
        }
        librosArray.stream()
                .sorted(Comparator.comparing(Libro::getNumeroDeDescargas).reversed())
                .limit(10)
                .forEach(System.out::println);
    }

    private void librosPorIdioma() {
        System.out.println("Indique las siglas del idioma que deseas buscar: ");
        String menuIdiomas = """
                    ╔══════════════════════════════════════════════════╗
                    ║                    Alurandria                    ║
                    ╠════════════════════ sub-menú ════════════════════╣
                    ║                                                  ║
                    ║ es - Español.                                    ║
                    ║ en - Inglés.                                     ║
                    ║ fr - Francés.                                    ║
                    ║ pt - Portugués.                                  ║
                    ║                                                  ║
                    ╚══════════════════════════════════════════════════╝                       
                """;
        System.out.println(menuIdiomas);
        var idioma = teclado.nextLine();
        if (!idioma.equals("es") && !idioma.equals("en") && !idioma.equals("fr") && !idioma.equals("pt")) {
            System.out.println("Idioma no válido, Intente de nuevo. Por favor: ");
            return;
        }
        List<Libro> librosXIdioma = libroRepository.findByIdiomaContaining(idioma);
        if (librosXIdioma.isEmpty()) {
            System.out.println("No hay libros registrados en el idioma seleccionado: ");
            return;
        }
        System.out.println("Libros Seleccionados en el idioma " + idioma + "\n");
        librosXIdioma.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void autoresPorYear() {
        System.out.println("Proporcione el año en el que desea buscar al/los Autor(es). Por favor: ");
        var anio = teclado.nextInt();
        teclado.nextLine();
        if (anio <= 0) {
            System.out.println("El año ingresado no es correcto.  Por favor intenta de nuevo");
            return;
        }
        List<Autor> autoresYear = autorRepository.findByNacimientoYearLessThanEqualAndFallecimientoYearGreaterThanEqual(anio, anio);
        if (autoresYear.isEmpty()) {
            System.out.println("No Existen Autores registrados para este año");
            return;
        }
        System.out.println("Autores vivos en el  " + anio + " : \n");
        autoresYear.stream()
                .sorted(Comparator.comparing(Autor::getAutor))
                .forEach(System.out::println);
    }

    private void buscarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No Exsiten autores registrados, Realice la busqueda de algún Libro, por favor:");
            return;
        }
        System.out.println("Los Autores Registrados son: \n");
        autores.stream()
                .sorted(Comparator.comparing(Autor::getAutor))
                .forEach(System.out::println);

    }

    private void buscarLibroRegistrado() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No Existen Libros registrados, Busque un Libro de su interes. Por favor:  ");
            return;
        }
        System.out.println("Los Libros Registrados son:  \n");
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);

    }

    private void buscarLibro() {
        System.out.println("Proporcione el Título del Libro que desea buscar: ");
        Datos datos = getDatosLibros();
        if (!datos.resultados().isEmpty()) {
            DatosLibro datosLibro = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Libro libro = null;
            Libro libroRepositorio = libroRepository.findByTituloIgnoreCase(datosLibro.titulo());
            if (libroRepositorio != null) {
                System.out.println("ERROR: Libro ya registrado en la base de datos. Intente otro Título, por favor: ");
                System.out.println(libroRepositorio.toString());
            } else {
                Autor autorRepositorio = autorRepository.findByAutorIgnoreCase(datosLibro.autor().get(0).autor());
                if (autorRepositorio != null) {
                    libro = crearLibro(datosLibro, autorRepositorio);
                    libroRepository.save(libro);
                    System.out.println("Libro agregado Exitosamente!!!  \n");
                    System.out.println(libro);
                } else {
                    Autor autor = new Autor(datosAutor);
                    autor = autorRepository.save(autor);
                    libro = crearLibro(datosLibro, autor);
                    libroRepository.save(libro);
                    System.out.println(" Libro y Autor Agregados Exitosamente!!!\n");
                    System.out.println(libro);
                }
            }
        } else {
            System.out.println("El libro NO Encontrado en Base de Datos Externa, Proporcione otro Título, por favor");
        }
    }

    private Libro crearLibro(DatosLibro datosLibro, Autor autor) {
        if (autor != null) {
            return new Libro(datosLibro, autor);
        } else {
            System.out.println("Autor desconocido, NO es posible dar de alta el libro");
            return null;
        }
    }

    private Datos getDatosLibros() {
        var nombreLibro = teclado.nextLine();
        var json = consumeAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        Datos datosLibros = conversor.obtenerDatos(json, Datos.class);
        return datosLibros;
    }
}