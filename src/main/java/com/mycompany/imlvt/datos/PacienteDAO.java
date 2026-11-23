/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class PacienteDAO {
   public List<Object[]> obtenerMisCitas(String miCedula) {
        List<Object[]> misCitas = new ArrayList<>();
        String sqlUpdate = "UPDATE citas_medicas SET estado = 'Cancelada' " +
                           "WHERE cedula_paciente = ? AND estado = 'Pendiente' AND fecha < CURDATE()";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
            psUpdate.setString(1, miCedula);
            psUpdate.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al cancelar citas caducadas: " + e.getMessage());
        }
        String sql = "SELECT c.especialidad, d.nombres AS doctor, c.fecha, c.hora, 'Consultorio Principal' AS lugar, c.estado " +
                     "FROM citas_medicas c " +
                     "INNER JOIN usuarios d ON c.cedula_doctor = d.cedula " +
                     "WHERE c.cedula_paciente = ? " +
                     "ORDER BY c.fecha ASC, c.hora ASC";
                     
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
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
    
   
    public java.util.List<String> obtenerEspecialidades() {
        java.util.List<String> especialidades = new java.util.ArrayList<>();
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

    public DefaultTableModel listarMiHistorialYRecetas(String miCedula) {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Fecha", "Especialidad", "Doctor", "Síntomas / Detalles"}, 0
        );

        String sql = "SELECT c.fecha, c.especialidad, u.nombres AS doctor, c.sintomas "
                   + "FROM citas_medicas c "
                   + "INNER JOIN usuarios u ON c.cedula_doctor = u.cedula "
                   + "WHERE c.cedula_paciente = ? AND c.estado = 'Atendida' "
                   + "ORDER BY c.fecha DESC";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, miCedula);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getDate("fecha"),
                    rs.getString("especialidad"),
                    rs.getString("doctor"),
                    rs.getString("sintomas") != null ? rs.getString("sintomas") : "Sin detalles registrados"
                });
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar el historial: " + e.getMessage());
        }
        
        return modelo;
    } 
    public DefaultTableModel listarHistorialPrescripcionesPaciente(String cedulaPaciente) {
    DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Fecha", "Profesional", "Motivo de Consulta", "Estado"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    String sql = "SELECT COALESCE(CAST(c.fecha AS CHAR), '') AS fecha, "
               + "COALESCE(u.nombres, c.cedula_doctor, 'Médico') AS profesional, "
               + "COALESCE(c.sintomas, c.especialidad, '') AS motivo, "
               + "COALESCE(c.estado, 'Pendiente') AS estado "
               + "FROM citas_medicas c "
               + "LEFT JOIN usuarios u ON c.cedula_doctor = u.cedula "
               + "WHERE c.cedula_paciente = ? "
               + "ORDER BY c.id_cita DESC";

    try (java.sql.Connection con = ConexionDB.obtenerConexion();
         java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

        if (con == null) {
            return modelo;
        }

        ps.setString(1, cedulaPaciente);

        try (java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("fecha"),
                    rs.getString("profesional"),
                    rs.getString("motivo"),
                    rs.getString("estado")
                });
            }
        }
    } catch (java.sql.SQLException e) {
        System.out.println("Error al listar historial de prescripciones: " + e.getMessage());
    }

    return modelo;
}

public java.util.List<String[]> obtenerRecetasPaciente(String cedulaPaciente) {
    java.util.List<String[]> lista = new java.util.ArrayList<>();

    String sql = "SELECT m.medicamento, m.dosis, m.duracion, m.sintomas, m.diagnostico, "
               + "COALESCE(u.nombres, c.cedula_doctor, 'Médico') AS doctor, "
               + "COALESCE(u.especialidad, c.especialidad, 'Medicina General') AS especialidad "
               + "FROM medicamentos_recetados m "
               + "LEFT JOIN citas_medicas c ON c.cedula_paciente = m.cedula_paciente "
               + "LEFT JOIN usuarios u ON c.cedula_doctor = u.cedula "
               + "WHERE m.cedula_paciente = ? "
               + "ORDER BY m.id_medicamento DESC LIMIT 20";

    try (java.sql.Connection con = ConexionDB.obtenerConexion();
         java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

        if (con == null) {
            return lista;
        }

        ps.setString(1, cedulaPaciente);

        try (java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String diag = rs.getString("diagnostico") != null ? rs.getString("diagnostico").trim() : "";
                String sint = rs.getString("sintomas") != null ? rs.getString("sintomas").trim() : "";
                String instrucciones = !diag.isEmpty() && !sint.isEmpty()
                        ? "Diagnóstico: " + diag + " | Síntomas: " + sint
                        : (!diag.isEmpty() ? "Diagnóstico: " + diag : sint);

                lista.add(new String[]{
                    rs.getString("medicamento") != null ? rs.getString("medicamento") : "",
                    rs.getString("dosis") != null ? rs.getString("dosis") : "",
                    rs.getString("duracion") != null ? rs.getString("duracion") : "",
                    instrucciones,
                    rs.getString("doctor") != null ? rs.getString("doctor") : "",
                    rs.getString("especialidad") != null ? rs.getString("especialidad") : ""
                });
            }
        }
    } catch (java.sql.SQLException e) {
        System.out.println("Error al obtener recetas del paciente: " + e.getMessage());
    }

    return lista;
}
}