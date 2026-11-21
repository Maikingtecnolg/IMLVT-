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
    public String crearUsuarioDesdeSecretaria(String cedula, String nombres, String correo, String password, String rol, String especialidad) {
    if (cedula == null || cedula.trim().isEmpty()
            || nombres == null || nombres.trim().isEmpty()
            || correo == null || correo.trim().isEmpty()
            || password == null || password.trim().isEmpty()
            || rol == null || rol.trim().isEmpty()) {
        return "Por favor, complete todos los campos obligatorios.";
    }

    String rolLimpio = rol.trim();
    boolean esMedico = rolLimpio.equalsIgnoreCase("Medico")
            || rolLimpio.equalsIgnoreCase("Médico")
            || rolLimpio.equalsIgnoreCase("Doctor");

    String especialidadFinal = null;
    if (esMedico) {
        if (especialidad == null || especialidad.trim().isEmpty() || especialidad.contains("Seleccione")) {
            return "Seleccione una especialidad para el rol de Médico.";
        }
        especialidadFinal = especialidad.trim();
    }

    boolean guardado = datos.registrarNuevoUsuario(
            cedula.trim(),
            nombres.trim(),
            correo.trim(),
            password.trim(),
            rolLimpio,
            especialidadFinal
    );

    return guardado ? "OK" : "No se pudo registrar el usuario (verifique si la cédula o correo ya existen).";
}
    public java.util.List<Object[]> consultarUsuarios(String filtroCombo, String busqueda) {
    String rolBD = normalizarFiltroRol(filtroCombo);
    return datos.listarUsuariosFiltrados(rolBD, busqueda);
}

private String normalizarFiltroRol(String filtro) {
    if (filtro == null || filtro.isBlank() || filtro.equalsIgnoreCase("Todos")) {
        return "Todos";
    }
    switch (filtro.trim().toLowerCase()) {
        case "medicos":
        case "médicos":
        case "medico":
        case "médico":
        case "doctor":
            return "Medico";
        case "enfermeros":
        case "enfermeras":
        case "enfermero":
        case "enfermera":
            return "Enfermera";
        case "secretarias":
        case "secretarios":
        case "secretaria":
        case "secretario":
            return "Secretaria";
        case "pacientes":
        case "paciente":
            return "Paciente";
        default:
            return filtro.trim();
    }
}

public String editarUsuario(String idTexto, String cedula, String nombres, String correo, String rol, String especialidad) {
    if (idTexto == null || idTexto.trim().isEmpty()) {
        return "Seleccione un usuario de la tabla para actualizar.";
    }
    if (cedula == null || cedula.trim().isEmpty()
            || nombres == null || nombres.trim().isEmpty()
            || correo == null || correo.trim().isEmpty()
            || rol == null || rol.trim().isEmpty()) {
        return "Complete cédula, nombres, correo y rol.";
    }

    int idUsuario;
    try {
        idUsuario = Integer.parseInt(idTexto.trim());
    } catch (NumberFormatException e) {
        return "El ID del usuario no es válido.";
    }

    String rolLimpio = rol.trim();
    boolean esMedico = rolLimpio.equalsIgnoreCase("Medico")
            || rolLimpio.equalsIgnoreCase("Médico")
            || rolLimpio.equalsIgnoreCase("Doctor");

    String especialidadFinal = null;
    if (esMedico) {
        if (especialidad == null || especialidad.trim().isEmpty() || especialidad.contains("Seleccione")) {
            return "Seleccione una especialidad para el rol de Medico.";
        }
        especialidadFinal = especialidad.trim();
    }

    boolean actualizado = datos.actualizarUsuario(
            idUsuario,
            cedula.trim(),
            nombres.trim(),
            especialidadFinal,
            correo.trim(),
            rolLimpio
    );

    return actualizado ? "OK" : "No se pudieron actualizar los datos del usuario.";
}

public String eliminarUsuario(String idTexto) {
    if (idTexto == null || idTexto.trim().isEmpty()) {
        return "Seleccione un usuario de la tabla para eliminar.";
    }

    int idUsuario;
    try {
        idUsuario = Integer.parseInt(idTexto.trim());
    } catch (NumberFormatException e) {
        return "El ID del usuario no es válido.";
    }

    boolean eliminado = datos.eliminarUsuarioPorId(idUsuario);
    return eliminado ? "OK" : "No se pudo eliminar el usuario (puede tener registros asociados).";
}
}