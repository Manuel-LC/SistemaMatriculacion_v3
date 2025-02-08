package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Vista {

    private static Controlador controlador;

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        Vista.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion = Opcion.SALIR;

        do {
            try {
                Consola.mostrarMenu();
                System.out.println();
                opcion = Consola.elegirOpcion();
                ejecutarOpcion(opcion);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != Opcion.SALIR);

        controlador.terminar();
    }

    public void terminar() {
        System.out.println("Hasta luego!!!!");
        System.out.println();
    }

    private void ejecutarOpcion(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_ALUMNO: insertarAlumno(); break;
            case BUSCAR_ALUMNO: buscarAlumno(); break;
            case BORRAR_ALUMNO: borrarAlumno(); break;
            case MOSTRAR_ALUMNOS: mostrarAlumnos(); break;
            case INSERTAR_ASIGNATURA: insertarAsignatura(); break;
            case BUSCAR_ASIGNATURA: buscarAsignatura(); break;
            case BORRAR_ASIGNATURA: borrarAsignatura(); break;
            case MOSTRAR_ASIGNATURAS: mostrarAsignaturas(); break;
            case INSERTAR_CICLO_FORMATIVO: insertarCicloFormativo(); break;
            case BUSCAR_CICLO_FORMATIVO: buscarCicloFormativo(); break;
            case BORRAR_CICLO_FORMATIVO: borrarCicloFormativo(); break;
            case MOSTRAR_CICLOS_FORMATIVOS: mostrarCiclosFormativos(); break;
            case INSERTAR_MATRICULA: insertarMatricula(); break;
            case BUSCAR_MATRICULA: buscarMatricula(); break;
            case ANULAR_MATRICULA: anularMatricula(); break;
            case MOSTRAR_MATRICULAS: mostrarMatriculas(); break;
            case MOSTRAR_MATRICULAS_ALUMNO: mostrarMatriculasPorAlumno(); break;
            case MOSTRAR_MATRICULAS_CICLO_FORMATIVO: mostrarMatriculasPorCicloFormativo(); break;
            case MOSTRAR_MATRICULAS_CURSO_ACADEMICO: mostrarMatriculasPorCursoAcademico(); break;
        }
    }

    private static void insertarAlumno() {
        try {
            System.out.println();
            System.out.println("Datos del alumno");
            System.out.println("=============================================================================================");
            Alumno alumno = Consola.leerAlumno();
            controlador.insertar(alumno);
            System.out.println();
            System.out.println("Alumno insertado correctamente.");
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void buscarAlumno() {
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            alumno = controlador.buscar(alumno);

            if (alumno != null) {
                System.out.println("Alumno encontrado:");
                System.out.println(alumno);
            } else {
                System.out.println("Alumno no encontrado.");
            }
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void borrarAlumno() {
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            controlador.borrar(alumno);
            System.out.println("Alumno borrado correctamente.");
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void mostrarAlumnos() {
        List<Alumno> listaAlumnos = controlador.getAlumnos();

        listaAlumnos = listaAlumnos.stream().sorted(Comparator.comparing(Alumno::getNombre)).toList();

        if (listaAlumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        } else {
            System.out.println("Alumnos registrados:");
            listaAlumnos.forEach(System.out::println);
        }
    }

    private static void insertarAsignatura() {
        try {
            System.out.println("Inserción de una asignatura:");
            System.out.println("=============================================================================================");
            System.out.println("-- Ciclo formativo de la asignatura --");
            mostrarCiclosFormativos();
            System.out.println();
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            cicloFormativo = controlador.buscar(cicloFormativo);

            System.out.println();
            System.out.println("-- Datos de la asignatura --");

            Asignatura asignatura = Consola.leerAsignatura(new CicloFormativo(cicloFormativo));
            controlador.insertar(asignatura);
            System.out.println("Asignatura insertada correctamente.");
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void buscarAsignatura() {
        try {
            Asignatura asignatura = Consola.getAsignaturaPorCodigo();
            asignatura = controlador.buscar(asignatura);
            System.out.println();

            if (asignatura != null) {
                System.out.println("Asignatura encontrada:");
                System.out.println(asignatura);
            } else {
                System.out.println("Asignatura no encontrada.");
            }
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void borrarAsignatura() {
        try {
            Asignatura asignatura = Consola.getAsignaturaPorCodigo();
            controlador.borrar(asignatura);
            System.out.println();
            System.out.println("Asignatura borrada correctamente.");
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void mostrarAsignaturas() {
        List<Asignatura> listaAsignaturas = controlador.getAsignaturas();

        listaAsignaturas = listaAsignaturas.stream().sorted(Comparator.comparing(Asignatura::getNombre)).toList();

        if (listaAsignaturas.isEmpty()) {
            System.out.println("No hay asignaturas registradas.");
        } else {
            System.out.println("Asignaturas registradas:");
            listaAsignaturas.forEach(System.out::println);
        }
    }

    private static void insertarCicloFormativo() {
        try {
            System.out.println("Datos del ciclo formativo");
            System.out.println("=============================================================================================");
            CicloFormativo ciclo = Consola.leerCicloFormativo();
            controlador.insertar(ciclo);
            System.out.println("Ciclo formativo insertado correctamente.");
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void buscarCicloFormativo() {
        try {
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            cicloFormativo = controlador.buscar(cicloFormativo);
            System.out.println();

            if (cicloFormativo != null) {
                System.out.println("Ciclo formativo encontrado:");
                System.out.println(cicloFormativo);
            } else {
                System.out.println("Ciclo formativo no encontrado.");
            }
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void borrarCicloFormativo() {
        try {
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            controlador.borrar(cicloFormativo);
            System.out.println();
            System.out.println("Ciclo formativo borrado correctamente.");
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void mostrarCiclosFormativos() {
        List<CicloFormativo> listaCiclos = controlador.getCiclosFormativos();

        listaCiclos = listaCiclos.stream().sorted(Comparator.comparing(CicloFormativo::getNombre)).toList();

        if (listaCiclos.isEmpty()) {
            System.out.println("No hay ciclos formativos registrados.");
        } else {
            System.out.println("Ciclos formativos registrados:");
            listaCiclos.forEach(System.out::println);
        }
    }

    private static void insertarMatricula() {
        try {
            System.out.println("Inserción de una matrícula");
            System.out.println("=============================================================================================");

            System.out.println("-- Alumno --");
            Alumno alumno = Consola.getAlumnoPorDni();
            alumno = controlador.buscar(alumno);

            System.out.println("-- Asignaturas de la matrícula --");
            List<Asignatura> asignaturasMatricula = Consola.elegirAsignaturasMatricula(controlador.getAsignaturas());
            System.out.println();

            System.out.println("-- Datos restantes --");
            Matricula matricula = Consola.leerMatricula(alumno, asignaturasMatricula);
            controlador.insertar(matricula);
            System.out.println("Matrícula insertada correctamente.");
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void buscarMatricula() {
        try {
            Matricula matricula = Consola.getMatriculaPorIdentificador();
            matricula = controlador.buscar(matricula);
            System.out.println();

            if (matricula != null) {
                System.out.println("Matrícula encontrada:");
                System.out.println(matricula);
            } else {
                System.out.println("No existe ninguna matrícula con ese identificador.");
            }
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void anularMatricula() {
        try {
            if (controlador.getMatriculas().isEmpty()) {
                System.out.println("No hay matrículas registradas para anular.");
                return;
            }

            mostrarMatriculas();
            System.out.println();
            Matricula matricula = Consola.getMatriculaPorIdentificador();
            matricula = controlador.buscar(matricula);

            if (matricula != null) {
                LocalDate fechaAnulacion = Consola.leerFecha("Introduce la fecha de anulación");
                matricula.setFechaAnulacion(fechaAnulacion);
                System.out.println();

                Locale spanishLocale = new Locale("es", "ES");
                String dateInSpanish = fechaAnulacion.format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy",spanishLocale));
                System.out.println("Matrícula anulada correctamente el " + dateInSpanish + ".");
            } else {
                System.out.println("No existe ninguna matrícula con ese identificador.");
            }
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void mostrarMatriculas() {
        List<Matricula> listaMatriculas = controlador.getMatriculas();

        listaMatriculas = listaMatriculas.stream().sorted(Comparator.comparing(Matricula::getFechaMatriculacion)
                            .reversed()
                            .thenComparing(matricula -> matricula.getAlumno().getNombre()))
                            .toList();

        if (listaMatriculas.isEmpty()) {
            System.out.println("No hay matrículas registradas.");
        } else {
            System.out.println("Matrículas registradas:");
            listaMatriculas.forEach(System.out::println);
        }

    }

    private static void mostrarMatriculasPorAlumno() {
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            alumno = controlador.buscar(alumno);

            if (alumno == null) {
                System.out.println("El alumno no está registrado.");
                return;
            }

            List<Matricula> matriculasAlumno = controlador.getMatriculas(alumno);

            matriculasAlumno = matriculasAlumno.stream()
                                .sorted(Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                                .thenComparing(m -> m.getAlumno().getNombre()))
                                .toList();

            if (matriculasAlumno.isEmpty()) {
                System.out.println("El alumno no tiene matrículas registradas.");
            } else {
                System.out.println("Matrículas del alumno " + alumno.getNombre() + ":");
                matriculasAlumno.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void mostrarMatriculasPorCicloFormativo() {
        try {
            mostrarCiclosFormativos();
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            cicloFormativo = controlador.buscar(cicloFormativo);

            if (cicloFormativo == null) {
                System.out.println("El ciclo formativo no está registrado.");
                return;
            }

            List<Matricula> matriculasCiclo = controlador.getMatriculas(cicloFormativo);

            matriculasCiclo = matriculasCiclo.stream()
                                .sorted(Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                                .thenComparing(matricula -> matricula.getAlumno().getNombre()))
                                .toList();

            if (matriculasCiclo.isEmpty()) {
                System.out.println("El ciclo formativo no tiene matrículas registradas.");
            } else {
                System.out.println();
                System.out.println("Matrículas del ciclo formativo " + cicloFormativo.getNombre() + ":");
                matriculasCiclo.forEach(System.out::println);
            }
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

    private static void mostrarMatriculasPorCursoAcademico() {
        try {
            System.out.println("Introduce el curso academico:");
            String cursoAcademico = Entrada.cadena();

            List<Matricula> matriculasCurso = controlador.getMatriculas(cursoAcademico);

            matriculasCurso = matriculasCurso.stream()
                                .sorted(Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                                .thenComparing(matricula -> matricula.getAlumno().getNombre()))
                                .toList();

            if (matriculasCurso.isEmpty()) {
                System.out.println("No hay matrículas registradas para el curso académico " + cursoAcademico + ".");
            } else {
                System.out.println();
                System.out.println("Matrículas registradas para el curso académico " + cursoAcademico + ":");
                matriculasCurso.forEach(System.out::println);
            }
        }
        catch (Exception e) {
            System.out.println("*" + e.getMessage());
        }
    }

}
