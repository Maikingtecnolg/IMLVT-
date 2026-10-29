/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.TableModel;

/**
 *
 * @author Lenovo
 */
public class MedicamentoDAO {
 public boolean guardarMedicamento(String medicamento, String dosis, String duracion) {

        String sql = "INSERT INTO medicamentos_recetados "
                   + "(medicamento, dosis, duracion) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.obtenerConexionDoctor()) {

            if (conexion == null) {
                System.out.println("No se pudo conectar con doctor_bd.");
                return false;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

                consulta.setString(1, medicamento);
                consulta.setString(2, dosis);
                consulta.setString(3, duracion);

                consulta.executeUpdate();
                System.out.println("Medicamento guardado correctamente.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar medicamento: " + e.getMessage());
            return false;
        }
    }

    public TableModel listarMedicamentos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
