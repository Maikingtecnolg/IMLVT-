/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
/**
 *
 * @author User
 */
public class EnfermeriaDAO {
    public boolean insertarSignos(String cedula, String presion, double temp, double peso, int estatura, int frec) {
        String sql = "INSERT INTO signos_vitales (cedula_paciente, presion_arterial, temperatura, peso, estatura, frecuencia_cardiaca) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionDB.obtenerConexion();
           PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.setString(2, presion);
            ps.setDouble(3, temp);
            ps.setDouble(4, peso);
            ps.setInt(5, estatura);
            ps.setInt(6, frec);
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en Datos: " + e.getMessage());
            return false;
        }
    }
    public java.util.List<String[]> listarPacientesCitaPendiente() {
    java.util.List<String[]> lista = new java.util.ArrayList<>();

    String sql = "SELECT u.cedula, u.nombres, TIME_FORMAT(c.hora, '%H:%i') AS hora_ingreso, c.especialidad "
               + "FROM citas_medicas c "
               + "INNER JOIN usuarios u ON u.cedula = c.cedula_paciente "
               + "WHERE LOWER(TRIM(c.estado)) = 'pendiente' "
               + "AND LOWER(TRIM(u.rol)) = 'paciente' "
               + "ORDER BY c.fecha ASC, c.hora ASC";

    try (Connection con = ConexionDB.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            String cedula = rs.getString("cedula");
            String nombre = rs.getString("nombres");
            String hora = rs.getString("hora_ingreso");
            String esp = rs.getString("especialidad");

            String ingreso = (hora != null && !hora.isBlank())
                    ? (esp != null && !esp.isBlank() ? hora + " - " + esp : hora)
                    : "Pendiente";

            lista.add(new String[]{
                cedula != null ? cedula : "",
                nombre != null ? nombre : "",
                ingreso
            });
        }
    } catch (SQLException e) {
        System.out.println("Error al listar pacientes con cita pendiente: " + e.getMessage());
    }

    return lista;
}
}
