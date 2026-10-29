/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author User
 */
public class ConexionDB {

    // Conexion de la base de datos original del proyecto
    private static final String URL =
        "jdbc:mysql://localhost:3306/imlvt_bd?useSSL=false&serverTimezone=UTC";

    // Conexion de tu base de datos de doctor
    private static final String URL_DOCTOR =
        "jdbc:mysql://localhost:3306/doctor_bd?useSSL=false&serverTimezone=UTC";

    // Usuario y contraseña de MySQL
    private static final String USUARIO = "root";
<<<<<<< HEAD
    private static final String PASSWORD = "1234"; 
    
=======
    private static final String PASSWORD = "";

    // Metodo para conectar con la base de datos original
>>>>>>> 5abf8f4 (Actualizar modulo medico y recetas)
    public static Connection obtenerConexion() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(
                URL, USUARIO, PASSWORD
            );
        } catch (SQLException e) {
            System.out.println(
                "Error de conexion a la base de datos: " + e.getMessage()
            );
        }

        return conexion;
    }

    // Metodo para conectar con tu base de datos de doctor
    public static Connection obtenerConexionDoctor() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(
                URL_DOCTOR, USUARIO, PASSWORD
            );
        } catch (SQLException e) {
            System.out.println(
                "Error en la base de doctor: " + e.getMessage()
            );
        }

        return conexion;
    }
    
    
}
