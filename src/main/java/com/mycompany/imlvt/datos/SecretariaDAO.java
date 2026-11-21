package com.mycompany.imlvt.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SecretariaDAO {

    public List<String> obtenerDoctores() throws Exception {
        List<String> doctores = new ArrayList<>();
        String sql = "SELECT cedula, nombres FROM usuarios WHERE rol = 'Medico'";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                doctores.add(rs.getString("nombres") + " - " + rs.getString("cedula"));
            }
        }
        return doctores;
    }

    public List<String> obtenerEspecialidades() {
        List<String> especialidades = new ArrayList<>();
        String sql = "SELECT DISTINCT TRIM(especialidad) AS especialidad "
                   + "FROM usuarios "
                   + "WHERE rol = 'Medico' "
                   + "AND especialidad IS NOT NULL "
                   + "AND TRIM(especialidad) <> '' "
                   + "ORDER BY especialidad ASC";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                especialidades.add(rs.getString("especialidad"));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar especialidades: " + e.getMessage());
        }
        return especialidades;
    }

    public List<String> obtenerDoctoresPorEspecialidad(String especialidad) {
        List<String> doctores = new ArrayList<>();
        String sql = "SELECT cedula, nombres FROM usuarios "
                   + "WHERE rol = 'Medico' AND TRIM(especialidad) = ? "
                   + "ORDER BY nombres ASC";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, especialidad.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    doctores.add(rs.getString("nombres") + " - " + rs.getString("cedula"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar doctores por especialidad: " + e.getMessage());
        }
        return doctores;
    }

    public List<String> buscarSugerenciasPacientes(String texto) {
        List<String> sugerencias = new ArrayList<>();
        String sql = "SELECT cedula, nombres FROM usuarios "
                   + "WHERE cedula LIKE ? OR nombres LIKE ? "
                   + "ORDER BY nombres ASC LIMIT 10";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String patron = "%" + texto.trim() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sugerencias.add(rs.getString("cedula") + " - " + rs.getString("nombres"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar sugerencias: " + e.getMessage());
        }
        return sugerencias;
    }

    public String obtenerNombrePaciente(String cedula) {
        String sql = "SELECT nombres FROM usuarios WHERE cedula = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombres");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar paciente: " + e.getMessage());
        }
        return null;
    }

   public boolean agendarCita(String cedPaciente, String cedDoctor, String especialidad, String fecha, String hora, String sintomas) {
    String sql = "INSERT INTO citas_medicas (cedula_paciente, cedula_doctor, especialidad, fecha, hora, sintomas, estado) VALUES (?, ?, ?, ?, ?, ?, 'Pendiente')";

    try (Connection con = ConexionDB.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, cedPaciente);
        ps.setString(2, cedDoctor);
        ps.setString(3, especialidad);
        ps.setString(4, fecha);
        ps.setString(5, hora);
        ps.setString(6, sintomas);

        ps.executeUpdate();
        return true;
    } catch (Exception e) {
        System.out.println("Error al agendar: " + e.getMessage());
        return false;
    }
}

    public List<Object[]> obtenerCitasProgramadas() {
        List<Object[]> listaCitas = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.fecha, c.hora, p.nombres AS paciente, d.nombres AS doctor, c.estado "
                   + "FROM citas_medicas c "
                   + "INNER JOIN usuarios p ON c.cedula_paciente = p.cedula "
                   + "INNER JOIN usuarios d ON c.cedula_doctor = d.cedula "
                   + "ORDER BY c.fecha ASC, c.hora ASC";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id_cita");
                fila[1] = rs.getDate("fecha");
                fila[2] = rs.getTime("hora");
                fila[3] = rs.getString("paciente");
                fila[4] = rs.getString("doctor");
                fila[5] = rs.getString("estado");
                listaCitas.add(fila);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla de citas: " + e.getMessage());
        }
        return listaCitas;
    }

    public int contarCitasHoy() {
        String sql = "SELECT COUNT(*) FROM citas_medicas WHERE fecha = CURDATE()";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error al contar citas de hoy: " + e.getMessage());
        }
        return 0;
    }

    public int contarPendientesHoy() {
        String sql = "SELECT COUNT(*) FROM citas_medicas WHERE fecha = CURDATE() AND estado = 'Pendiente'";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error al contar pendientes de hoy: " + e.getMessage());
        }
        return 0;
    }

    public int contarMedicosDisponibles() {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE rol = 'Medico'";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error al contar médicos: " + e.getMessage());
        }
        return 0;
    }

    public List<Object[]> filtrarCitasMenuSecre(String filtroTiempo, String filtroEstado, String busqueda) {
        List<Object[]> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT c.id_cita, c.cedula_paciente, "
            + "COALESCE(p.nombres, 'No registrado') AS paciente, "
            + "c.fecha, c.hora, c.especialidad, c.estado "
            + "FROM citas_medicas c "
            + "LEFT JOIN usuarios p ON c.cedula_paciente = p.cedula "
            + "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (filtroTiempo != null) {
            switch (filtroTiempo) {
                case "Hoy":
                    sql.append("AND c.fecha = CURDATE() ");
                    break;
                case "Ayer":
                    sql.append("AND c.fecha = DATE_SUB(CURDATE(), INTERVAL 1 DAY) ");
                    break;
                case "Mañana":
                    sql.append("AND c.fecha = DATE_ADD(CURDATE(), INTERVAL 1 DAY) ");
                    break;
                case "Todas las del mes":
                    sql.append("AND YEAR(c.fecha) = YEAR(CURDATE()) AND MONTH(c.fecha) = MONTH(CURDATE()) ");
                    break;
                case "Todas las citas":
                default:
                    break;
            }
        }

        if (filtroEstado != null && !filtroEstado.equalsIgnoreCase("Todos") && !filtroEstado.equalsIgnoreCase("Todas")) {
            if (filtroEstado.equalsIgnoreCase("Atendidos") || filtroEstado.equalsIgnoreCase("Atendida")) {
                sql.append("AND c.estado IN ('Atendidos', 'Atendida', 'Atendido') ");
            } else {
                sql.append("AND TRIM(c.estado) = ? ");
                params.add(filtroEstado.trim());
            }
        }

        if (busqueda != null && !busqueda.trim().isEmpty()) {
            sql.append("AND (c.cedula_paciente LIKE ? OR p.nombres LIKE ?) ");
            String patron = "%" + busqueda.trim() + "%";
            params.add(patron);
            params.add(patron);
        }

        sql.append("ORDER BY c.fecha DESC, c.hora ASC");

        try (Connection con = ConexionDB.obtenerConexion()) {
            if (con == null) {
                return lista;
            }
            try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Object[] fila = new Object[7];
                        fila[0] = rs.getInt("id_cita");
                        fila[1] = rs.getString("cedula_paciente");
                        fila[2] = rs.getString("paciente");
                        fila[3] = rs.getDate("fecha");
                        fila[4] = rs.getTime("hora");
                        fila[5] = rs.getString("especialidad");
                        fila[6] = rs.getString("estado");
                        lista.add(fila);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar citas en MenuSecre: " + e.getMessage());
        }

        return lista;
    }

    public boolean cancelarCita(int idCita) {
        String sql = "UPDATE citas_medicas SET estado = 'Cancelada' WHERE id_cita = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCita);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al cancelar: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarFechaHoraCita(int idCita, String nuevaFecha, String nuevaHora) {
        String sql = "UPDATE citas_medicas SET fecha = ?, hora = ? WHERE id_cita = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevaFecha);
            ps.setString(2, nuevaHora);
            ps.setInt(3, idCita);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al reprogramar: " + e.getMessage());
            return false;
        }
    }
    public boolean registrarNuevoUsuario(String cedula, String nombres, String correo, String password, String rol, String especialidad) {
    String sql = "INSERT INTO usuarios (cedula, nombres, especialidad, correo, password, rol) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection con = ConexionDB.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, cedula);
        ps.setString(2, nombres);
        ps.setString(3, especialidad); // puede ser null si no es Médico
        ps.setString(4, correo);
        ps.setString(5, password);
        ps.setString(6, rol);

        ps.executeUpdate();
        return true;
    } catch (Exception e) {
        System.out.println("Error al registrar usuario: " + e.getMessage());
        return false;
    }
}
    public java.util.List<Object[]> listarUsuariosFiltrados(String rolFiltro, String textoBusqueda) {
    java.util.List<Object[]> lista = new java.util.ArrayList<>();

    StringBuilder sql = new StringBuilder(
            "SELECT id_usuario, cedula, nombres, especialidad, correo, rol FROM usuarios WHERE 1=1"
    );
    java.util.List<Object> params = new java.util.ArrayList<>();

    if (rolFiltro != null && !rolFiltro.isBlank() && !rolFiltro.equalsIgnoreCase("Todos")) {
        sql.append(" AND LOWER(rol) = LOWER(?)");
        params.add(rolFiltro.trim());
    }

    if (textoBusqueda != null && !textoBusqueda.isBlank()) {
        sql.append(" AND (LOWER(cedula) LIKE LOWER(?) OR LOWER(nombres) LIKE LOWER(?) OR LOWER(correo) LIKE LOWER(?))");
        String patron = "%" + textoBusqueda.trim() + "%";
        params.add(patron);
        params.add(patron);
        params.add(patron);
    }

    sql.append(" ORDER BY id_usuario ASC");

    try (Connection con = ConexionDB.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_usuario"),
                    rs.getString("cedula"),
                    rs.getString("nombres"),
                    rs.getString("especialidad"),
                    rs.getString("correo"),
                    rs.getString("rol")
                });
            }
        }
    } catch (Exception e) {
        System.out.println("Error al listar usuarios: " + e.getMessage());
    }

    return lista;
}

public boolean actualizarUsuario(int idUsuario, String cedula, String nombres, String especialidad, String correo, String rol) {
    String sql = "UPDATE usuarios SET cedula = ?, nombres = ?, especialidad = ?, correo = ?, rol = ? WHERE id_usuario = ?";

    try (Connection con = ConexionDB.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, cedula);
        ps.setString(2, nombres);
        ps.setString(3, especialidad);
        ps.setString(4, correo);
        ps.setString(5, rol);
        ps.setInt(6, idUsuario);

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        System.out.println("Error al actualizar usuario: " + e.getMessage());
        return false;
    }
}

public boolean eliminarUsuarioPorId(int idUsuario) {
    String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

    try (Connection con = ConexionDB.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        System.out.println("Error al eliminar usuario: " + e.getMessage());
        return false;
    }
}
}