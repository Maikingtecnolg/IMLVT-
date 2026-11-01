package com.mycompany.imlvt.presentacion.medico;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import com.mycompany.imlvt.datos.ConexionDB;
import com.mycompany.imlvt.datos.MedicoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PanelReceta extends javax.swing.JPanel {
    private final MedicoDAO medicamentoDAO = new MedicoDAO();

    public PanelReceta() {
        initComponents();
        cargarMedicamentos();        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jTextField5 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btn_Buscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_duracion = new javax.swing.JTextField();
        txt_medicamento = new javax.swing.JTextField();
        txt_dosis = new javax.swing.JTextField();
        btn_AgregaReceta = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_eliminarReceta = new javax.swing.JButton();
        btn_finalizaConsulta = new javax.swing.JButton();
        btn_borrador = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField5.setForeground(new java.awt.Color(102, 204, 255));
        jTextField5.addActionListener(this::jTextField5ActionPerformed);
        add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 32, 290, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/magnifying-glass-search-button-microphone-260nw-2120105102.png"))); 
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 74));

        btn_Buscar.setFont(new java.awt.Font("Serif", 2, 12)); 
        btn_Buscar.setText("Buscar");
        btn_Buscar.addActionListener(this::btn_BuscarActionPerformed);
        add(btn_Buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 80, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/images (4).png"))); 
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel14.setFont(new java.awt.Font("Serif", 2, 24)); 
        jLabel14.setForeground(new java.awt.Color(51, 153, 255));
        jLabel14.setText("Paciente en Consulta");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setForeground(new java.awt.Color(204, 204, 255));

        jLabel5.setText("Nombre del paciente:");
        jLabel6.setText("Motivo de consulta:");
        jLabel7.setText("Alergia a medicamento:");
        jLabel8.setText("Edad:");
        jTextField2.addActionListener(this::jTextField2ActionPerformed);
        jLabel9.setText("Sexo:");
        
        jLabel10.setText("Peso:");
        jTextField3.addActionListener(this::jTextField3ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(68, 68, 68))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(70, 70, 70))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(102, 102, 102)))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(9, Short.MAX_VALUE))))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 420, 230));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icono-del-logotipo-hospital-135146804.png"))); 
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, -1));

        jLabel16.setFont(new java.awt.Font("Serif", 2, 24)); 
        jLabel16.setForeground(new java.awt.Color(51, 153, 255));
        jLabel16.setText("Evaluacion Clinica");
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, -1, -1));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setForeground(new java.awt.Color(204, 204, 255));

        jLabel12.setText("Sintomas referidos por el paciente:");
        jLabel15.setText("Diagnostico:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel12)
                .addGap(1, 1, 1)
                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, 410, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/images (5).png"))); 
        add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        jLabel13.setFont(new java.awt.Font("Serif", 2, 24)); 
        jLabel13.setForeground(new java.awt.Color(51, 153, 255));
        jLabel13.setText("Receta Medica");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, -1, -1));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel18.setText("Nombre del medicamento:");
        jLabel19.setText("Dosis:");
        jLabel20.setText("Duracion:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_medicamento, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addComponent(txt_duracion)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(txt_dosis))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_medicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_dosis, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_duracion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 420, -1));

        btn_AgregaReceta.setBackground(new java.awt.Color(102, 153, 255));
        btn_AgregaReceta.setFont(new java.awt.Font("Serif", 2, 12)); 
        btn_AgregaReceta.setText("Agregar a la Receta");
        btn_AgregaReceta.addActionListener(this::btn_AgregaRecetaActionPerformed);
        add(btn_AgregaReceta, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 450, 130, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Medicamento", "Dosis", "Duracion"
            }
        ));
        jScrollPane8.setViewportView(jTable1);

        add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 510, 380, 110));

        btn_eliminarReceta.setFont(new java.awt.Font("Serif", 2, 14)); 
        btn_eliminarReceta.setText("Eliminar");
        btn_eliminarReceta.addActionListener(this::btn_eliminarRecetaActionPerformed);
        add(btn_eliminarReceta, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 650, -1, -1));

        btn_finalizaConsulta.setFont(new java.awt.Font("Serif", 2, 14)); 
        btn_finalizaConsulta.setText("Finalizar consulta");
        btn_finalizaConsulta.addActionListener(this::btn_finalizaConsultaActionPerformed);
        add(btn_finalizaConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 650, -1, -1));

        btn_borrador.setFont(new java.awt.Font("Serif", 2, 14)); 
        btn_borrador.setText("Guardar ");
        btn_borrador.addActionListener(this::btn_borradorActionPerformed);
        add(btn_borrador, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 650, -1, -1));
    }// </editor-fold>                        

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void btn_AgregaRecetaActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        String medicamento = txt_medicamento.getText().trim();
        String dosis = txt_dosis.getText().trim();
        String duracion = txt_duracion.getText().trim();

        if (medicamento.isEmpty() || dosis.isEmpty() || duracion.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Completa los tres campos.");
            return;
        }

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable1.getModel();

        // Eliminar las filas vacías que aparecen inicialmente
        for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
            boolean filaVacia = true;
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                Object valor = modelo.getValueAt(i, j);
                if (valor != null && !valor.toString().trim().isEmpty()) {
                    filaVacia = false;
                    break;
                }
            }
            if (filaVacia) {
                modelo.removeRow(i);
            }
        }

        modelo.addRow(new Object[]{medicamento, dosis, duracion});

        // Limpiar los cuadros
        txt_medicamento.setText("");
        txt_dosis.setText("");
        txt_duracion.setText("");
    }                                                

    private void btn_eliminarRecetaActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        int fila = jTable1.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un medicamento.");
            return;
        }

        int id = (int) jTable1.getValueAt(fila, 0);

        if (medicamentoDAO.eliminarMedicamento(id)) {
            cargarMedicamentos();
            javax.swing.JOptionPane.showMessageDialog(this, "Medicamento eliminado correctamente.");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se pudo eliminar el medicamento.");
        }
    }                                                  

    private void btn_finalizaConsultaActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // TODO add your handling code here:
    }                                                    

    private void btn_BuscarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void btn_borradorActionPerformed(java.awt.event.ActionEvent evt) {                                             
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        int guardados = 0;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object med = modelo.getValueAt(i, 0);
            Object dos = modelo.getValueAt(i, 1);
            Object dur = modelo.getValueAt(i, 2);

            if (med == null || dos == null || dur == null) {
                continue;
            }

            String medicamento = med.toString().trim();
            String dosis = dos.toString().trim();
            String duracion = dur.toString().trim();

            if (medicamento.isEmpty() || dosis.isEmpty() || duracion.isEmpty()) {
                continue;
            }

            if (medicamentoDAO.guardarMedicamento(medicamento, dosis, duracion)) {
                guardados++;
            }
        }

        if (guardados > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Se guardaron " + guardados + " medicamentos.");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se guardaron medicamentos. Revisa la tabla y la conexión.");
        }
    }                                            

    private void cargarMedicamentos() {
        jTable1.setModel(medicamentoDAO.listarMedicamentos());
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btn_AgregaReceta;
    private javax.swing.JButton btn_Buscar;
    private javax.swing.JButton btn_borrador;
    private javax.swing.JButton btn_eliminarReceta;
    private javax.swing.JButton btn_finalizaConsulta;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField txt_dosis;
    private javax.swing.JTextField txt_duracion;
    private javax.swing.JTextField txt_medicamento;
    // End of variables declaration                   
}