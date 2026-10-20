/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.SQLException;

/**
 *
 * @author Lenovo
 */
public class MedicoDAO {
     public DefaultTableModel listarProximasCitas(String cedulaDoctor) {

        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Paciente", "Hora", "Motivo", "Estado"}, 0
        );

        String sql = "SELECT u.nombres AS paciente, c.hora, "
                + "c.especialidad AS motivo, c.estado "
                + "FROM citas_medicas c "
                + "INNER JOIN usuarios u "
                + "ON c.cedula_paciente = u.cedula "
                + "WHERE c.cedula_doctor = ? "
                + "AND c.fecha = CURDATE() "
                + "ORDER BY c.hora ASC";

        try {
            Connection con = ConexionDB.obtenerConexion();

            if (con == null) {
                return modelo;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedulaDoctor);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("paciente"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getString("estado")
                });
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error al consultar citas: "
                    + e.getMessage());
        }

        return modelo;
    }
    
     public boolean agendarCita(
        String cedulaPaciente,
        String cedulaDoctor,
        String especialidad,
        java.sql.Date fecha,
        java.sql.Time hora) {

    String sql = "INSERT INTO citas_medicas "
            + "(cedula_paciente, cedula_doctor, especialidad, "
            + "fecha, hora, estado) "
            + "VALUES (?, ?, ?, ?, ?, 'Pendiente')";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                return false;
            }

            ps.setString(1, cedulaPaciente);
            ps.setString(2, cedulaDoctor);
            ps.setString(3, especialidad);
            ps.setDate(4, fecha);
            ps.setTime(5, hora);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agendar la cita: "
                    + e.getMessage());
            return false;
        }
    }
     
     
     
     
}
    
    
    
    
    
    
    

