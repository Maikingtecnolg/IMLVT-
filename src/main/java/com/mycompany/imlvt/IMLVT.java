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
import com.mycompany.imlvt.presentacion.comunes.LoginIMLVT1;

public class IMLVT {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo inicializar FlatLaf");
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginIMLVT1().setVisible(true); 
            }
        });
    }
    }
