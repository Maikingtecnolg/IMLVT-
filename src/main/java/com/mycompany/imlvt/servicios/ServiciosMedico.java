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
}

    

