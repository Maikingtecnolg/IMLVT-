/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.servicios;
import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.mycompany.imlvt.datos.MedicoDAO;
import javax.swing.table.DefaultTableModel;
import com.mycompany.imlvt.datos.MedicamentoDAO;
/**
 *
 * @author Lenovo
 */



public class ServiciosMedico {

    private MedicoDAO medicoDAO = new MedicoDAO();

    public Map<String, String> cargarInformacionAtencion(String cedula) {
        return medicoDAO.obtenerDatosParaAtencion(cedula);
    }
    public DefaultTableModel listarProximasCitas(String cedulaDoctor) {
        return medicoDAO.listarProximasCitas(cedulaDoctor);
    }
    public java.util.List<String[]> listarPacientesConCita(String filtro) {
    return medicoDAO.listarPacientesConCita(filtro);
}
    public boolean finalizarAtencionPaciente(String cedulaPaciente) {
    return medicoDAO.marcarCitaComoAtendida(cedulaPaciente);
}
    private final MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
    public boolean guardarMedicamentoConPaciente(
            String cedulaPaciente,
            String medicamento,
            String dosis,
            String duracion,
            String sintomas,
            String diagnostico) {
        return medicamentoDAO.guardarMedicamentoConPaciente(
                cedulaPaciente, medicamento, dosis, duracion, sintomas, diagnostico);
    }
}

    

