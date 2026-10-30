/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.servicios;

/**
 *
 * @author User
 */
import com.mycompany.imlvt.datos.PacienteDAO;
import java.util.ArrayList;
import java.util.List;



public class ServiciosPaciente {
 private PacienteDAO datos = new PacienteDAO();
   
    public List<Object[]> listarMisCitas(String miCedula) {
        return datos.obtenerMisCitas(miCedula);
    }
    
  
    public List<String> listarMedicosPorEspecialidad(String especialidad) {
        
        if (especialidad == null || especialidad.isEmpty() || especialidad.contains("Seleccione")) {
            return new ArrayList<>(); 
        }
         return datos.obtenerMedicosPorEspecialidad(especialidad);
    }
    // Método para pedirle las especialidades al DAO
    public java.util.List<String> listarEspecialidades() {
        return datos.obtenerEspecialidades();
    }
    public String registrarNuevaCita(String cedulaPaciente, String especialidad, String nombreMedico, java.util.Date fecha, String hora, String sintomas) {
        if (especialidad == null || especialidad.contains("Seleccione")) return "Por favor, seleccione una especialidad.";
        if (nombreMedico == null || nombreMedico.contains("Seleccione")) return "Por favor, seleccione un médico.";
        if (hora == null || hora.contains("Elija")) return "Por favor, elija un horario.";
        if (fecha == null) return "Por favor, seleccione una fecha.";
        if (sintomas == null || sintomas.trim().isEmpty()) return "Por favor, describa sus síntomas.";
        
        boolean exito = datos.agendarCita(cedulaPaciente, especialidad, nombreMedico, fecha, hora, sintomas);
        return exito ? "OK" : "Error: No se pudo guardar la cita.";
    }
    }
   

