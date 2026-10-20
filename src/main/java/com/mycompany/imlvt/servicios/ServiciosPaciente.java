/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.servicios;

/**
 *
 * @author User
 */
import com.mycompany.imlvt.datos.PacienteDAO;
import java.util.List;

public class ServiciosPaciente {
    private PacienteDAO datos = new PacienteDAO();
    
    public List<Object[]> listarMisCitas(String miCedula) {
        // En el futuro aquí puedes agregar validaciones, por ahora solo pedimos los datos
        return datos.obtenerMisCitas(miCedula);
    }
}
