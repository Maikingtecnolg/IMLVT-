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

/**
 *
 * @author User
 */
public class SecretariaDAO {

    public List<String> obtenerDoctores() throws Exception {
        List<String> doctores = new ArrayList<>();
        String sql = "SELECT cedula, nombres FROM usuarios WHERE rol = 'Medico'";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                doctores.add(rs.getString("nombres") + " - " + rs.getString("cedula"));
            }
        }
        return doctores;
    }

    public String obtenerNombrePaciente(String cedula) {
        String sql = "SELECT nombres FROM usuarios WHERE cedula = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombres");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar paciente: " + e.getMessage());
        }
        return null;
    }

    public boolean agendarCita(String cedPaciente, String cedDoctor, String especialidad, String fecha, String hora) {
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
            System.out.println("Error al agendar: " + e.getMessage());
            return false;
        }
    }

    public java.util.List<Object[]> obtenerCitasProgramadas() {
        java.util.List<Object[]> listaCitas = new java.util.ArrayList<>();
        String sql = "SELECT c.id_cita, c.fecha, c.hora, p.nombres AS paciente, d.nombres AS doctor, c.estado " +
                     "FROM citas_medicas c " +
                     "INNER JOIN usuarios p ON c.cedula_paciente = p.cedula " +
                     "INNER JOIN usuarios d ON c.cedula_doctor = d.cedula " +
                     "ORDER BY c.fecha ASC, c.hora ASC"; 
                     
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id_cita");
                fila[1] = rs.getDate("fecha");
                fila[2] = rs.getTime("hora");
                fila[3] = rs.getString("paciente");
                fila[4] = rs.getString("doctor");
                fila[5] = rs.getString("estado");
                
                listaCitas.add(fila);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla de citas: " + e.getMessage());
        }
        return listaCitas;
    }

    public boolean cancelarCita(int idCita) {
        String sql = "UPDATE citas_medicas SET estado = 'Cancelada' WHERE id_cita = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, idCita);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al cancelar: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarFechaHoraCita(int idCita, String nuevaFecha, String nuevaHora) {
        String sql = "UPDATE citas_medicas SET fecha = ?, hora = ? WHERE id_cita = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, nuevaFecha);
            ps.setString(2, nuevaHora);
            ps.setInt(3, idCita);
            
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al reprogramar: " + e.getMessage());
            return false;
        }
    }
}