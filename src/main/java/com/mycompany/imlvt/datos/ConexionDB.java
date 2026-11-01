package com.mycompany.imlvt.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Apuntamos a una ÚNICA base de datos donde están todas tus tablas
    private static final String URL = "jdbc:mysql://localhost:3306/imlvt_bd?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; 

    // Método original
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conexion;
    }

    // Método del doctor que ahora apunta a la misma base unificada
    public static Connection obtenerConexionDoctor() {
        // Redirigimos esta conexión para que use los mismos datos de arriba
        return obtenerConexion();
    }
}