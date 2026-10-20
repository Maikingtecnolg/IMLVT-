/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imlvt.servicios;
import com.mycompany.imlvt.datos.EnfermeriaDAO;
/**
 *
 * @author User
 */
public class ServiciosEnfermeria {
    // Instanciamos la capa de datos
    private EnfermeriaDAO datos = new EnfermeriaDAO();
    
    public String procesarYGuardarSignos(String cedula, String presion, String tempTxt, String pesoTxt, String estTxt, String frecTxt) {
        // 1. Lógica de negocio y validaciones (Capa Servicios)
        if (cedula.isEmpty() || presion.isEmpty()) {
            return "Faltan datos importantes.";
        }
        
        try {
            // Transformamos los textos a números aquí, para que la capa de Datos reciba datos limpios
            double temperatura = Double.parseDouble(tempTxt.replace(",", "."));
            double peso = Double.parseDouble(pesoTxt.replace(",", "."));
            int estatura = Integer.parseInt(estTxt);
            int frecuencia = Integer.parseInt(frecTxt);
            
            // 2. Si todo está correcto, llamamos a la capa de DATOS
            boolean exito = datos.insertarSignos(cedula, presion, temperatura, peso, estatura, frecuencia);
            
            if (exito) {
                return "OK";
            } else {
                return "Error al guardar en la base de datos.";
            }
            
        } catch (NumberFormatException e) {
            return "Por favor, ingrese solo números válidos.";
        }
    }
}
