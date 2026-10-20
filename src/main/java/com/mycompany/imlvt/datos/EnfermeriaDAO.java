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
    // Este método SOLO inserta en la base de datos, no hace nada más.
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
}
