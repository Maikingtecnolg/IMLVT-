/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.servicios;
import com.mycompany.imlvt.datos.MedicoDAO;
/**
 *
 * @author Lenovo
 */
public class ServiciosMedico {
    private MedicoDAO datos = new MedicoDAO();

    // Validamos los datos antes de enviarlos al DAO
    public String registrarCita(String cedPaciente, String cedDoctor, String especialidad, java.util.Date fecha, String hora) {
        
        if (cedPaciente.isEmpty() || fecha == null || hora.isEmpty()) {
            return "Por favor, complete la cédula, fecha y hora del paciente.";
        }

        try {
            
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String fechaMySQL = sdf.format(fecha);

            
            boolean exito = datos.agendarCitaMedico(cedPaciente, cedDoctor, especialidad, fechaMySQL, hora);
            
            if (exito) {
                return "OK";
            } else {
                return "Error: Verifique que la cédula del paciente exista en el sistema.";
            }
        } catch (Exception e) {
            return "Error al procesar la cita.";
        }
    }
}
