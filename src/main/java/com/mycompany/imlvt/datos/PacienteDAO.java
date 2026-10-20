/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;

/**
 *
 * @author User
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    public List<Object[]> obtenerMisCitas(String miCedula) {
        List<Object[]> misCitas = new ArrayList<>();
        
        // Buscamos las citas y traemos el nombre del doctor. Le asignamos un "Lugar" por defecto.
        String sql = "SELECT c.especialidad, d.nombres AS doctor, c.fecha, c.hora, 'Consultorio Principal' AS lugar, c.estado " +
                     "FROM citas_medicas c " +
                     "INNER JOIN usuarios d ON c.cedula_doctor = d.cedula " +
                     "WHERE c.cedula_paciente = ? " +
                     "ORDER BY c.fecha ASC, c.hora ASC";
                     
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            // Le pasamos tu cédula al filtro de MySQL
            ps.setString(1, miCedula);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[6];
                    fila[0] = rs.getString("especialidad");
                    fila[1] = rs.getString("doctor");
                    fila[2] = rs.getDate("fecha");
                    fila[3] = rs.getTime("hora");
                    fila[4] = rs.getString("lugar");
                    fila[5] = rs.getString("estado");
                    
                    misCitas.add(fila);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las citas del paciente: " + e.getMessage());
        }
        return misCitas;
    }

}
