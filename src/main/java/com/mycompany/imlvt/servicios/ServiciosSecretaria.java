/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.servicios;

import com.mycompany.imlvt.datos.SecretariaDAO;
import java.util.List;

/**
 *
 * @author User
 */
public class ServiciosSecretaria {
    private SecretariaDAO datos = new SecretariaDAO();
    
    public List<String> listarDoctores() {
        try {
            return datos.obtenerDoctores();
        } catch (Exception e) {
            System.out.println("Error en base de datos: " + e.getMessage());
            return null;
        }
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

    public String registrarCita(String cedPaciente, String docSeleccionado, String especialidad, java.util.Date fecha, String hora) {
        if (cedPaciente.isEmpty() || docSeleccionado.contains("Seleccione") || fecha == null) {
            return "Por favor, complete todos los datos.";
        }

        try {
            String[] partes = docSeleccionado.split(" - ");
            String cedDoctor = partes[1];

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String fechaMySQL = sdf.format(fecha);

            boolean exito = datos.agendarCita(cedPaciente, cedDoctor, especialidad, fechaMySQL, hora);
            
            if (exito) {
                return "OK";
            } else {
                return "Error al guardar en la base de datos.";
            }
        } catch (Exception e) {
            return "Error al procesar la cita. Verifique los datos.";
        }
    }

    public java.util.List<Object[]> listarCitas() {
        return datos.obtenerCitasProgramadas();
    }

    public String anularCita(int idCita) {
        if (datos.cancelarCita(idCita)) {
            return "OK";
        }
        return "Error al cancelar la cita en la base de datos.";
    }

    public String reprogramarCita(int idCita, java.util.Date nuevaFecha, String nuevaHora) {
        if (nuevaFecha == null || nuevaHora.isEmpty()) {
            return "Por favor, seleccione la nueva fecha y hora en el formulario izquierdo.";
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