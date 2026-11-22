package com.mycompany.imlvt.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class MedicamentoDAO {

    public boolean guardarMedicamento(String medicamento, String dosis, String duracion, String sintomas, String diagnostico) {
        String sql = "INSERT INTO medicamentos_recetados (medicamento, dosis, duracion, sintomas, diagnostico) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            if (conexion == null) {
                System.out.println("No se pudo conectar con imlvt_bd.");
                return false;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setString(1, medicamento);
                consulta.setString(2, dosis);
                consulta.setString(3, duracion);
                consulta.setString(4, sintomas);
                consulta.setString(5, diagnostico);

                consulta.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar medicamento: " + e.getMessage());
            return false;
        }
    }

    public DefaultTableModel listarMedicamentos() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Medicamento", "Dosis", "Duracion", "Sintomas", "Diagnostico"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = "SELECT medicamento, dosis, duracion, sintomas, diagnostico FROM medicamentos_recetados ORDER BY id_medicamento ASC";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("medicamento"),
                    rs.getString("dosis"),
                    rs.getString("duracion"),
                    rs.getString("sintomas"),
                    rs.getString("diagnostico")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error al listar medicamentos: " + e.getMessage());
        }

        return modelo;
    }

    public ArrayList<Integer> obtenerIdsMedicamentos() {
        ArrayList<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_medicamento FROM medicamentos_recetados ORDER BY id_medicamento ASC";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("id_medicamento"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener IDs de medicamentos: " + e.getMessage());
        }

        return ids;
    }

    public boolean eliminarMedicamento(int idMedicamento) {
        String sql = "DELETE FROM medicamentos_recetados WHERE id_medicamento = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idMedicamento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar medicamento: " + e.getMessage());
            return false;
        }
    }
}