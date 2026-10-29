/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.imlvt;

/**
 *
 * @author User
 */
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UIManager;
import com.mycompany.imlvt.presentacion.comunes.LoginIMLVT;

public class IMLVT {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        // 1. Activar el diseño moderno ANTES de abrir cualquier ventana
        try {
            // Usamos el tema claro estilo Mac que es muy limpio para sistemas médicos
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo inicializar FlatLaf");
        }

        // 2. Aquí ya llamas a tu ventana inicial (Ejemplo: Login o Menú)
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginIMLVT().setVisible(true); // Cambia "MenuPrincipal" por el nombre de tu JFrame
            }
        });
    }
    }
