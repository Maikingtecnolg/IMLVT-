/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;

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
    
    public List <String> obtenerMedicosPorEspecialidad(String especialidad){
        List<String>listaMedicos = new ArrayList<>();
        String sql = "SELECT nombres FROM usuarios WHERE rol= 'Medico' AND especialidad=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, especialidad);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                listaMedicos.add(rs.getString("nombres"));
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar médicos: " + e.getMessage());
        }
        return listaMedicos;
    }
    
    // Método para obtener las especialidades disponibles
    public java.util.List<String> obtenerEspecialidades() {
        java.util.List<String> especialidades = new java.util.ArrayList<>();
        // Busca las especialidades sin repetirlas (DISTINCT) solo de los médicos
        String sql = "SELECT DISTINCT especialidad FROM usuarios WHERE rol = 'Medico' AND especialidad IS NOT NULL";
        
        try (java.sql.Connection con = ConexionDB.obtenerConexion();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                especialidades.add(rs.getString("especialidad"));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar especialidades: " + e.getMessage());
        }
        return especialidades;
    }

    // NUEVO: Método para guardar la cita en la base de datos
    public boolean agendarCita(String cedulaPaciente, String especialidad, String nombreMedico, java.util.Date fecha, String hora, String sintomas) {
        String sql = "INSERT INTO citas_medicas (cedula_paciente, cedula_doctor, especialidad, fecha, hora, sintomas, estado) " +
                     "VALUES (?, (SELECT cedula FROM usuarios WHERE nombres = ? LIMIT 1), ?, ?, ?, ?, 'Pendiente')";
                     
        try (java.sql.Connection con = ConexionDB.obtenerConexion();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, cedulaPaciente);
            ps.setString(2, nombreMedico);
            ps.setString(3, especialidad);
            ps.setDate(4, new java.sql.Date(fecha.getTime()));
            ps.setString(5, hora);
            ps.setString(6, sintomas);
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            System.out.println("Error al guardar cita: " + e.getMessage());
            return false;
        }
    }
    public String obtenerFechaProximaCita(String cedulaPaciente) {
        String resultado = "Sin citas próximas";
        String sql = "SELECT fecha, hora FROM citas_medicas " +
                     "WHERE cedula_paciente = ? AND estado = 'Pendiente' AND fecha >= CURDATE() " +
                     "ORDER BY fecha ASC, hora ASC LIMIT 1";
                     
        try (java.sql.Connection con = ConexionDB.obtenerConexion(); 
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedulaPaciente);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getString("fecha") + " a las " + rs.getString("hora");
                }
            }
        } catch (Exception e) {
            System.out.println("Error Cita: " + e.getMessage());
        }
        return resultado;
    }

   public String obtenerUltimoChequeo(String cedulaPaciente) {
        String resultado = "No hay registros de chequeos anteriores.";
        
       
        String sql = "SELECT peso, presion_arterial, temperatura FROM signos_vitales " +
                     "WHERE cedula_paciente = ? ORDER BY id_triaje DESC LIMIT 1";
                     
        try (java.sql.Connection con = ConexionDB.obtenerConexion();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, cedulaPaciente);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = "Últimos Signos - Peso: " + rs.getString("peso") + " kg" +
                                " | Presión: " + rs.getString("presion_arterial") + 
                                " | Temp: " + rs.getString("temperatura") + "°C";
                }
            }
        } catch (Exception e) {
            System.out.println("Error Chequeo: " + e.getMessage());
        }
        return resultado;
    }
}