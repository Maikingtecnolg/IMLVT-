/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author User
 */
public class UsuarioDAO {
 
    
    // Método que verifica el correo y contraseña, y devuelve los datos si son correctos
    public String[] validarLogin(String correo, String contrasena) {
        String sql = "SELECT cedula, nombres, rol FROM usuarios WHERE correo = ? AND password = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Si el usuario existe, devolvemos un arreglo con sus 3 datos clave
                return new String[]{
                    rs.getString("cedula"), 
                    rs.getString("nombres"), 
                    rs.getString("rol")
                };
            }
        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null; // Si la contraseña es incorrecta o no existe, devuelve null
    }
}
    

