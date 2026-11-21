/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.imlvt.presentacion.secretaria;

/**
 *
 * @author User
 */
public class PanelProgramarCita extends javax.swing.JPanel {
private final com.mycompany.imlvt.servicios.ServiciosSecretaria serviciosSecretaria =
            new com.mycompany.imlvt.servicios.ServiciosSecretaria();
    private final javax.swing.DefaultListModel<String> modeloSugerencias = new javax.swing.DefaultListModel<>();
    private String cedulaPacienteSeleccionada = null;
    /**
     * Creates new form PanelProgramarCita
     */
    public PanelProgramarCita() {
       initComponents();
       cargarEspecialidadesDesdeBD();
        actualizarDoctoresPorEspecialidad();
        actualizarHorariosDisponibles();
        configurarBuscadorPacientes();
        jButton1.addActionListener(e -> registrarCitaDesdeFormulario());
        jDateChooser1.addPropertyChangeListener("date", evt -> actualizarHorariosDisponibles());
        cmbEspecialidad.addActionListener(e -> {
            actualizarDoctoresPorEspecialidad();
            actualizarHorariosDisponibles();
            
        });
    }
    private void registrarCitaDesdeFormulario() {
    // 1. Obtener y validar cédula del paciente registrado
    String cedulaPaciente = this.cedulaPacienteSeleccionada;
    if (cedulaPaciente == null || cedulaPaciente.trim().isEmpty()) {
        String textoCampo = jTextField1.getText().trim();
        if (textoCampo.contains(" - ")) {
            cedulaPaciente = textoCampo.split(" - ", 2)[0].trim();
        } else if (!textoCampo.isEmpty()) {
            String nombreEncontrado = serviciosSecretaria.buscarNombrePaciente(textoCampo);
            if (nombreEncontrado != null) {
                cedulaPaciente = textoCampo;
            }
        }
    }

    if (cedulaPaciente == null || cedulaPaciente.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Seleccione un paciente registrado de las sugerencias.",
                "Paciente no seleccionado",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // 2. Validar especialidad
    String especialidad = (cmbEspecialidad.getSelectedItem() != null)
            ? cmbEspecialidad.getSelectedItem().toString().trim()
            : "";
    if (especialidad.isEmpty() || especialidad.contains("Seleccione")) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Seleccione una especialidad.",
                "Dato faltante",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // 3. Validar médico
    String medicoSeleccionado = (cmbMedico.getSelectedItem() != null)
            ? cmbMedico.getSelectedItem().toString().trim()
            : "";
    if (medicoSeleccionado.isEmpty()
            || medicoSeleccionado.contains("Seleccione")
            || medicoSeleccionado.contains("Primero selecciona")
            || medicoSeleccionado.contains("No hay médicos")) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Seleccione un médico válido.",
                "Dato faltante",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // 4. Validar fecha (y que no sea domingo)
    java.util.Date fecha = jDateChooser1.getDate();
    if (fecha == null) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Seleccione la fecha de la cita.",
                "Dato faltante",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    java.time.DayOfWeek dia = fecha.toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate()
            .getDayOfWeek();
    if (dia == java.time.DayOfWeek.SUNDAY) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Los domingos no se atienden citas.",
                "Día no laborable",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // 5. Validar hora
    String hora = (cmbHorarios.getSelectedItem() != null)
            ? cmbHorarios.getSelectedItem().toString().trim()
            : "";
    if (!cmbHorarios.isEnabled() || hora.isEmpty() || hora.contains("Elija") || hora.contains("Seleccione") || hora.contains("No hay")) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Seleccione un horario disponible.",
                "Dato faltante",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // 6. Obtener síntomas del paciente
    String sintomas = (txtSintomasPaciente.getText() != null)
            ? txtSintomasPaciente.getText().trim()
            : "";

    // 7. Registrar cita mediante ServiciosSecretaria
    String resultado = serviciosSecretaria.registrarCita(
            cedulaPaciente, medicoSeleccionado, especialidad, fecha, hora, sintomas
    );

    if ("OK".equals(resultado)) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Cita registrada correctamente.",
                "Éxito",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
        limpiarFormularioCita();
    } else {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                resultado,
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}

private void limpiarFormularioCita() {
    cedulaPacienteSeleccionada = null;
    jTextField1.setText("");
    jPopupMenu1.setVisible(false);
    if (cmbEspecialidad.getItemCount() > 0) {
        cmbEspecialidad.setSelectedIndex(0);
    }
    jDateChooser1.setDate(null);
    txtSintomasPaciente.setText("");
    actualizarDoctoresPorEspecialidad();
    actualizarHorariosDisponibles();
}
    private void cargarEspecialidadesDesdeBD() {
        javax.swing.DefaultComboBoxModel<String> modeloEsp = new javax.swing.DefaultComboBoxModel<>();
        modeloEsp.addElement("---Seleccione una especialidad---");

        for (String esp : serviciosSecretaria.listarEspecialidades()) {
            modeloEsp.addElement(esp);
        }

        cmbEspecialidad.setModel(modeloEsp);
    }

    private void configurarBuscadorPacientes() {
        jList1.setModel(modeloSugerencias);
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPopupMenu1.removeAll();
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(jList1);
        scroll.setPreferredSize(new java.awt.Dimension(Math.max(jTextField1.getWidth(), 320), 130));
        jPopupMenu1.add(scroll);
        jPopupMenu1.setFocusable(false);
        jList1.setFocusable(false);

        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void onChange() {
                cedulaPacienteSeleccionada = null;
                actualizarSugerencias();
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                onChange();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                onChange();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                onChange();
            }
        });

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String sel = jList1.getSelectedValue();
                if (sel != null) {
                    String[] partes = sel.split(" - ", 2);
                    cedulaPacienteSeleccionada = partes[0].trim();
                    jTextField1.setText(sel);
                    jPopupMenu1.setVisible(false);
                }
            }
        });
    }

    private void actualizarSugerencias() {
        String texto = jTextField1.getText();
        java.util.List<String> resultados = serviciosSecretaria.obtenerSugerenciasPacientes(texto);

        modeloSugerencias.clear();
        if (resultados == null || resultados.isEmpty()) {
            jPopupMenu1.setVisible(false);
            return;
        }

        for (String r : resultados) {
            modeloSugerencias.addElement(r);
        }

        jList1.setSelectedIndex(0);
        jPopupMenu1.pack();
        jPopupMenu1.show(jTextField1, 0, jTextField1.getHeight());
        jTextField1.requestFocusInWindow();
    }
private void actualizarHorariosDisponibles() {
    javax.swing.DefaultComboBoxModel<String> modeloHoras = new javax.swing.DefaultComboBoxModel<>();
    java.util.Date fechaSeleccionada = jDateChooser1.getDate();

    if (fechaSeleccionada == null) {
        modeloHoras.addElement("---Seleccione una fecha---");
        cmbHorarios.setModel(modeloHoras);
        cmbHorarios.setEnabled(false);
        return;
    }

    java.time.LocalDate fechaLocal = fechaSeleccionada.toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();

    java.time.DayOfWeek diaSemana = fechaLocal.getDayOfWeek();

    // 1. Domingo: no hay atención
    if (diaSemana == java.time.DayOfWeek.SUNDAY) {
        modeloHoras.addElement("---No hay atención los domingos---");
        cmbHorarios.setModel(modeloHoras);
        cmbHorarios.setEnabled(false);
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Los domingos no se atienden citas médicas. Seleccione de lunes a sábado.",
                "Día no laborable",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
        return;
    }

    java.time.LocalTime horaInicio = java.time.LocalTime.of(8, 0);
    java.time.LocalTime horaFin = (diaSemana == java.time.DayOfWeek.SATURDAY)
            ? java.time.LocalTime.of(14, 0)
            : java.time.LocalTime.of(17, 0);

    String especialidad = (cmbEspecialidad.getSelectedItem() != null)
            ? cmbEspecialidad.getSelectedItem().toString()
            : "";
    int intervaloMinutos = especialidad.equalsIgnoreCase("Medicina General") ? 30 : 30;

    modeloHoras.addElement("---Elija un horario---");
    java.time.LocalTime actual = horaInicio;
    java.time.format.DateTimeFormatter formatoHora = java.time.format.DateTimeFormatter.ofPattern("HH:mm");

    while (!actual.isAfter(horaFin)) {
        modeloHoras.addElement(actual.format(formatoHora));
        actual = actual.plusMinutes(intervaloMinutos);
    }

    cmbHorarios.setModel(modeloHoras);
    cmbHorarios.setEnabled(true);
}
private void actualizarDoctoresPorEspecialidad() {
        javax.swing.DefaultComboBoxModel<String> modeloMedicos = new javax.swing.DefaultComboBoxModel<>();

        Object espObj = cmbEspecialidad.getSelectedItem();
        String especialidad = (espObj != null) ? espObj.toString() : "";

        if (especialidad.isBlank() || especialidad.contains("Seleccione")) {
            modeloMedicos.addElement("Primero selecciona especialidad");
            cmbMedico.setModel(modeloMedicos);
            cmbMedico.setEnabled(false);
            return;
        }

        java.util.List<String> lista = serviciosSecretaria.listarDoctoresPorEspecialidad(especialidad);
        if (lista.isEmpty()) {
            modeloMedicos.addElement("No hay médicos para esta especialidad");
            cmbMedico.setModel(modeloMedicos);
            cmbMedico.setEnabled(false);
            return;
        }

        modeloMedicos.addElement("---Seleccione un médico---");
        for (String doc : lista) {
            modeloMedicos.addElement(doc);
        }
        cmbMedico.setModel(modeloMedicos);
        cmbMedico.setEnabled(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbEspecialidad = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cmbMedico = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        cmbHorarios = new javax.swing.JComboBox<>();
        txtSintomasPaciente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(248, 242, 242));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/registrarse - copia.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 102, 0));
        jLabel2.setText("AGENDA MÉDICA / NUEVA CITA");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Programar Nueva Cita");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Información de la cita");

        jLabel6.setText("Nombre o Cédula del Paciente *");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("DATOS DE LA CONSULTA");

        jLabel8.setText("Especialidad *");

        cmbEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Médico *");

        cmbMedico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Fecha de la cita *");

        jLabel11.setText("Horario disponible *");

        cmbHorarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Sintomas del paciente");

        jButton1.setBackground(new java.awt.Color(102, 255, 102));
        jButton1.setText("Confirmar Cita");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSintomasPaciente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel10)
                                        .addGap(367, 367, 367)
                                        .addComponent(jLabel11))
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jLabel7))))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(211, 211, 211)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cmbHorarios, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cmbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(243, 243, 243)
                                                .addComponent(jLabel9)))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(45, 45, 45)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbHorarios, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSintomasPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(97, 97, 97))
        );

        jLabel4.setText("Busque un paciente registrado y complete los datos de la consulta médica.");

        jLabel13.setText("jLabel13");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(436, 436, 436)
                        .addComponent(jLabel13))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))))))
                .addContainerGap(240, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 243, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(39, 39, 39))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 961, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
jButton1.addActionListener(e -> registrarCitaDesdeFormulario());        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbEspecialidad;
    private javax.swing.JComboBox<String> cmbHorarios;
    private javax.swing.JComboBox<String> cmbMedico;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtSintomasPaciente;
    // End of variables declaration//GEN-END:variables
}
