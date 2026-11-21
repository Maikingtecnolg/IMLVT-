package com.mycompany.imlvt.servicios;

import com.mycompany.imlvt.datos.SecretariaDAO;
import java.util.Collections;
import java.util.List;

public class ServiciosSecretaria {

    private final SecretariaDAO datos = new SecretariaDAO();

    public List<String> listarDoctores() {
        try {
            return datos.obtenerDoctores();
        } catch (Exception e) {
            System.out.println("Error en base de datos: " + e.getMessage());
            return null;
        }
    }

    public List<String> listarEspecialidades() {
        return datos.obtenerEspecialidades();
    }

    public List<String> listarDoctoresPorEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty() || especialidad.contains("Seleccione")) {
            return Collections.emptyList();
        }
        return datos.obtenerDoctoresPorEspecialidad(especialidad);
    }

    public List<String> obtenerSugerenciasPacientes(String texto) {
        if (texto == null || texto.trim().length() < 2) {
            return Collections.emptyList();
        }
        return datos.buscarSugerenciasPacientes(texto);
    }

    public String buscarNombrePaciente(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return null;
        }
        try {
            return datos.obtenerNombrePaciente(cedula.trim());
        } catch (Exception e) {
            System.out.println("Error al buscar paciente: " + e.getMessage());
            return null;
        }
    }

    public String registrarCita(String cedPaciente, String docSeleccionado, String especialidad, java.util.Date fecha, String hora, String sintomas) {
    if (cedPaciente == null || cedPaciente.isEmpty() || docSeleccionado == null || docSeleccionado.contains("Seleccione") || fecha == null) {
        return "Por favor, complete todos los datos.";
    }

    try {
        String[] partes = docSeleccionado.split(" - ");
        String cedDoctor = partes[1].trim();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String fechaMySQL = sdf.format(fecha);

        String sintomasLimpios = (sintomas != null) ? sintomas.trim() : "";

        boolean exito = datos.agendarCita(cedPaciente.trim(), cedDoctor, especialidad, fechaMySQL, hora, sintomasLimpios);

        if (exito) {
            return "OK";
        } else {
            return "Error al guardar en la base de datos.";
        }
    } catch (Exception e) {
        return "Error al procesar la cita. Verifique los datos.";
    }
}

    public List<Object[]> listarCitas() {
        return datos.obtenerCitasProgramadas();
    }

    public int obtenerTotalCitasHoy() {
        return datos.contarCitasHoy();
    }

    public int obtenerTotalPendientesHoy() {
        return datos.contarPendientesHoy();
    }

    public int obtenerTotalMedicosDisponibles() {
        return datos.contarMedicosDisponibles();
    }

    public List<Object[]> buscarCitasMenuSecre(String filtroTiempo, String filtroEstado, String busqueda) {
        return datos.filtrarCitasMenuSecre(filtroTiempo, filtroEstado, busqueda);
    }

    public String anularCita(int idCita) {
        if (datos.cancelarCita(idCita)) {
            return "OK";
        }
        return "Error al cancelar la cita en la base de datos.";
    }

    public String reprogramarCita(int idCita, java.util.Date nuevaFecha, String nuevaHora) {
        if (nuevaFecha == null || nuevaHora == null || nuevaHora.isEmpty()) {
            return "Por favor, seleccione la nueva fecha y hora.";
        }

        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String fechaMySQL = sdf.format(nuevaFecha);

            if (datos.actualizarFechaHoraCita(idCita, fechaMySQL, nuevaHora)) {
                return "OK";
            }
            return "Error al actualizar la cita en la base de datos.";
        } catch (Exception e) {
            return "Error al procesar la nueva fecha.";
        }
    }
}