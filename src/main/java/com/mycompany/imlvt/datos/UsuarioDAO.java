/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
   package com.mycompany.imlvt.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public String[] validarLogin(String correo, String contrasena) {
        String sql = "SELECT cedula, nombres, rol FROM usuarios WHERE correo = ? AND password = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new String[]{
                    rs.getString("cedula"),
                    rs.getString("nombres"),
                    rs.getString("rol")
                };
            }
        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null;
    }
} 

