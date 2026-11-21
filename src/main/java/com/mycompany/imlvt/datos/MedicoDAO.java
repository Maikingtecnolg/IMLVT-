/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;





/**
 *
 * @author Lenovo
 */
public class MedicoDAO {


public DefaultTableModel listarProximasCitas(String cedulaDoctor) {

    DefaultTableModel modelo = new DefaultTableModel(
        new String[]{"Cédula", "Paciente", "Hora", "Especialidad", "Síntomas", "Estado"}, 0
    );

    String sql = "SELECT c.cedula_paciente, u.nombres AS paciente, "
            + "c.hora, c.especialidad, c.sintomas, c.estado "
            + "FROM citas_medicas c "
            + "INNER JOIN usuarios u "
            + "ON c.cedula_paciente = u.cedula "
            + "WHERE c.cedula_doctor = ? "
            + "AND c.estado = 'Pendiente' "
            + "ORDER BY c.fecha ASC, c.hora ASC";

    try (Connection con = ConexionDB.obtenerConexion()) {

        if (con == null) {
            return modelo;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedulaDoctor);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    modelo.addRow(new Object[]{
                        rs.getString("cedula_paciente"),
                        rs.getString("paciente"),
                        rs.getString("hora"),
                        rs.getString("especialidad"),
                        rs.getString("sintomas"),
                        rs.getString("estado")
                    });
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al consultar citas: " + e.getMessage());
        e.printStackTrace();
    }

    return modelo;
}


    
     public boolean agendarCita(
        String cedulaPaciente,
        String cedulaDoctor,
        String especialidad,
        java.sql.Date fecha,
        java.sql.Time hora,
        String sintomas) {

    String sql = "INSERT INTO citas_medicas "
            + "(cedula_paciente, cedula_doctor, especialidad, "
            + "fecha, hora, sintomas) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection con = ConexionDB.obtenerConexion()) {

        if (con == null) {
            System.out.println("No hay conexión con la base de datos.");
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedulaPaciente);
            ps.setString(2, cedulaDoctor);
            ps.setString(3, especialidad);
            ps.setDate(4, fecha);
            ps.setTime(5, hora);
            ps.setString(6, sintomas);

            return ps.executeUpdate() > 0;
        }

    } catch (SQLException e) {
        System.out.println("Error al agendar la cita: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

    
    
    public boolean guardarMedicamento(
            String medicamento, String dosis, String duracion) {

        String sql = "INSERT INTO medicamentos_recetados "
                   + "(medicamento, dosis, duracion) "
                   + "VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.obtenerConexionDoctor();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, medicamento);
            consulta.setString(2, dosis);
            consulta.setString(3, duracion);

            consulta.executeUpdate();
            return true;

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al guardar medicamento: "
                    + e.getMessage());
            return false;
        }
    }
    
    public DefaultTableModel listarMedicamentos() {
    DefaultTableModel modelo = new DefaultTableModel(
        new Object[]{"ID", "Medicamento", "Dosis", "Duracion"}, 0
    );

    String sql = "SELECT * FROM medicamentos_recetados";

    try (Connection conexion = ConexionDB.obtenerConexionDoctor();
         PreparedStatement consulta = conexion.prepareStatement(sql);
         ResultSet resultado = consulta.executeQuery()) {

        while (resultado.next()) {
            modelo.addRow(new Object[]{
                resultado.getInt("id_medicamento"),
                resultado.getString("medicamento"),
                resultado.getString("dosis"),
                resultado.getString("duracion")
            });
        }

    } catch (SQLException e) {
        System.out.println("Error al listar medicamentos: " + e.getMessage());
    }

    return modelo;
}
    public boolean eliminarMedicamento(int id) {
    String sql = "DELETE FROM medicamentos_recetados WHERE id_medicamento = ?";

    try (Connection conexion = ConexionDB.obtenerConexionDoctor();
         PreparedStatement consulta = conexion.prepareStatement(sql)) {

        consulta.setInt(1, id);

        return consulta.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("Error al eliminar medicamento: " + e.getMessage());
        return false;
    }
}
    //DESDE AQUI IMPLEMENTE LO NUEVO LILI
    // Método para obtener los datos del paciente y su consulta actual
   
    public java.util.Map<String, String> obtenerDatosParaAtencion(String cedulaPaciente) {
        java.util.Map<String, String> datosPaciente = new java.util.HashMap<>();
        
        String sql = "SELECT u.nombres, u.edad, u.sexo, u.peso, c.sintomas, c.motivo " 
                   + "FROM usuarios u "
                   + "LEFT JOIN citas_medicas c ON u.cedula = c.cedula_paciente "
                   + "WHERE u.cedula = ? AND c.estado = 'Pendiente' LIMIT 1";
                   
        try (Connection con = ConexionDB.obtenerConexion(); // O usa obtenerConexionDoctor() según tu configuración general
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            if (con == null) {
                return datosPaciente;
            }
            
            ps.setString(1, cedulaPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    datosPaciente.put("nombres", rs.getString("nombres") != null ? rs.getString("nombres") : "");
                    datosPaciente.put("edad", rs.getString("edad") != null ? rs.getString("edad") : "");
                    datosPaciente.put("sexo", rs.getString("sexo") != null ? rs.getString("sexo") : "");
                    datosPaciente.put("peso", rs.getString("peso") != null ? rs.getString("peso") : "");
                    datosPaciente.put("motivo", rs.getString("motivo") != null ? rs.getString("motivo") : "");
                    datosPaciente.put("sintomas", rs.getString("sintomas") != null ? rs.getString("sintomas") : "");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en DAO al obtener datos de atención: " + e.getMessage());
        }
        
        return datosPaciente;
    }
    
    
public DefaultTableModel buscarPacientes(String busqueda) {

    DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Cedula", "Especialidad"}, 0
    );

    String sql = "SELECT DISTINCT u.id_usuario, u.nombres, "
            + "u.cedula, c.especialidad "
            + "FROM citas_medicas c "
            + "INNER JOIN usuarios u "
            + "ON c.cedula_paciente = u.cedula "
            + "WHERE CAST(u.cedula AS CHAR) LIKE ? "
            + "OR u.nombres LIKE ? "
            + "ORDER BY u.nombres";

    try (Connection conexion = ConexionDB.obtenerConexion();
         PreparedStatement consulta = conexion.prepareStatement(sql)) {

        consulta.setString(1, "%" + busqueda.trim() + "%");
        consulta.setString(2, "%" + busqueda.trim() + "%");

        try (ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {

                modelo.addRow(new Object[]{
                    resultado.getInt("id_usuario"),
                    resultado.getString("nombres"),
                    resultado.getString("cedula"),
                    resultado.getString("especialidad")
                });
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al buscar pacientes: " + e.getMessage());
        e.printStackTrace();
    }

    return modelo;
}

   
public boolean actualizarPaciente(
        int id,
        String nombre,
        String cedula,
        String especialidad) {

    String sqlUsuario = "UPDATE usuarios SET nombres = ?, cedula = ? "
            + "WHERE id_usuario = ?";

    String sqlCita = "UPDATE citas_medicas SET especialidad = ? "
            + "WHERE cedula_paciente = ?";

    try (Connection conexion = ConexionDB.obtenerConexion()) {

        conexion.setAutoCommit(false);

        try {
            // Obtener la cédula anterior del paciente
            String cedulaAnterior = "";

            String sqlBuscar = "SELECT cedula FROM usuarios WHERE id_usuario = ?";

            try (PreparedStatement buscar = conexion.prepareStatement(sqlBuscar)) {
                buscar.setInt(1, id);

                try (ResultSet resultado = buscar.executeQuery()) {
                    if (resultado.next()) {
                        cedulaAnterior = resultado.getString("cedula");
                    } else {
                        conexion.rollback();
                        return false;
                    }
                }
            }

            // Actualizar nombre y cédula
            try (PreparedStatement actualizar = conexion.prepareStatement(sqlUsuario)) {
                actualizar.setString(1, nombre);
                actualizar.setString(2, cedula);
                actualizar.setInt(3, id);
                actualizar.executeUpdate();
            }

            // Actualizar especialidad de sus citas
            try (PreparedStatement actualizar = conexion.prepareStatement(sqlCita)) {
                actualizar.setString(1, especialidad);
                actualizar.setString(2, cedulaAnterior);
                actualizar.executeUpdate();
            }

            conexion.commit();
            return true;

        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(true);
        }

    } catch (SQLException e) {
        System.out.println("Error al actualizar paciente: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

}
    
    
    
    
    
    
    

