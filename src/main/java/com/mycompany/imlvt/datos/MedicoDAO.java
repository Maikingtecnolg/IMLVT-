/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Lenovo
 */
public class MedicoDAO {
    // Método para guardar la cita desde el perfil del médico
    public boolean agendarCitaMedico(String cedPaciente, String cedDoctor, String especialidad, String fecha, String hora) {
        // Hacemos el INSERT. Nota que el estado va como 'Pendiente' por defecto.
        String sql = "INSERT INTO citas_medicas (cedula_paciente, cedula_doctor, especialidad, fecha, hora, estado) VALUES (?, ?, ?, ?, ?, 'Pendiente')";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cedPaciente);
            ps.setString(2, cedDoctor);
            ps.setString(3, especialidad);
            ps.setString(4, fecha);
            ps.setString(5, hora);
            
            ps.executeUpdate();
            return true; 
            
        } catch (Exception e) {
            System.out.println("Error al agendar cita (Médico): " + e.getMessage());
            return false;
        }
    }
    
    
    
    
}
