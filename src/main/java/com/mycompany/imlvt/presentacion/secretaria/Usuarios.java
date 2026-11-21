/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.imlvt.presentacion.secretaria;

/**
 *
 * @author User
 */
public class Usuarios extends javax.swing.JPanel {
private final com.mycompany.imlvt.servicios.ServiciosSecretaria serviciosSecretaria =
        new com.mycompany.imlvt.servicios.ServiciosSecretaria();

private void configurarModuloUsuarios() {
    // 1. ID no editable
    id.setEditable(false);

    // 2. Poblar combos de Rol y Especialidad
    cmbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
        "Secretaria", "Enfermera", "Medico", "Paciente"
    }));

    cmbEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
        "---Seleccione especialidad---",
        "Medicina General",
        "Neurología",
        "Ginecología",
        "Traumatología",
        "Dermatología",
        "Oftalmología",
        "Odontología",
        "Psiquiatría",
        "Cardiología",
        "Pediatría"
    }));

    // 3. Modelo de tabla no editable directamente en celdas
    tableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Cedula", "Nombres", "Especialidad", "Correo", "Rol"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    });

    // 4. Color e indicador lateral según el rol en la columna Rol (índice 5)
    tableDatos.getColumnModel().getColumn(5).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            java.awt.Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            String rolTexto = (value != null) ? value.toString().trim() : "";
            java.awt.Color colorRol = obtenerColorPorRol(rolTexto);

            if (!isSelected) {
                c.setBackground(java.awt.Color.WHITE);
                c.setForeground(colorRol);
            } else {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            }

            if (c instanceof javax.swing.JComponent) {
                ((javax.swing.JComponent) c).setBorder(
                        javax.swing.BorderFactory.createMatteBorder(0, 4, 0, 0, colorRol)
                );
            }
            return c;
        }
    });

    // 5. Listeners
    cmbRol.addActionListener(e -> actualizarEstadoEspecialidadEdicion());
    cmbFiltroUsuarios.addActionListener(e -> cargarTablaUsuarios());
    btnBuscar.addActionListener(e -> cargarTablaUsuarios());
    txtUsuario.addActionListener(e -> cargarTablaUsuarios());
    btnLimpiar.addActionListener(e -> limpiarFormularioEdicion());
    btnActualizar.addActionListener(e -> actualizarUsuarioSeleccionado());
    btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());

    tableDatos.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            cargarFilaSeleccionadaEnFormulario();
        }
    });

    // 6. Estado inicial
    actualizarEstadoEspecialidadEdicion();
    cargarTablaUsuarios();
}

private java.awt.Color obtenerColorPorRol(String rol) {
    if (rol == null) return new java.awt.Color(71, 85, 105);
    switch (rol.trim().toLowerCase()) {
        case "medico":
        case "médico":
        case "doctor":
            return new java.awt.Color(2, 132, 199); // Azul
        case "secretaria":
        case "secretario":
            return new java.awt.Color(126, 34, 206); // Morado
        case "enfermera":
        case "enfermero":
            return new java.awt.Color(21, 128, 61); // Verde
        case "paciente":
            return new java.awt.Color(194, 65, 12); // Ámbar/Naranja
        default:
            return new java.awt.Color(71, 85, 105);
    }
}

private void actualizarEstadoEspecialidadEdicion() {
    Object rolObj = cmbRol.getSelectedItem();
    String rol = (rolObj != null) ? rolObj.toString().trim() : "";

    boolean esMedico = rol.equalsIgnoreCase("Medico")
            || rol.equalsIgnoreCase("Médico")
            || rol.equalsIgnoreCase("Doctor");

    cmbEspecialidad.setEnabled(esMedico);
    if (!esMedico && cmbEspecialidad.getItemCount() > 0) {
        cmbEspecialidad.setSelectedIndex(0);
    }
}

private void cargarTablaUsuarios() {
    String filtro = (cmbFiltroUsuarios.getSelectedItem() != null)
            ? cmbFiltroUsuarios.getSelectedItem().toString()
            : "Todos";
    String busqueda = txtUsuario.getText();

    javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tableDatos.getModel();
    modelo.setRowCount(0);

    java.util.List<Object[]> filas = serviciosSecretaria.consultarUsuarios(filtro, busqueda);
    for (Object[] fila : filas) {
        modelo.addRow(fila);
    }
}

private void cargarFilaSeleccionadaEnFormulario() {
    int fila = tableDatos.getSelectedRow();
    if (fila < 0) return;

    javax.swing.table.TableModel modelo = tableDatos.getModel();
    id.setText(valorSeguro(modelo.getValueAt(fila, 0)));
    txtCedula.setText(valorSeguro(modelo.getValueAt(fila, 1)));
    txtNombre.setText(valorSeguro(modelo.getValueAt(fila, 2)));
    String esp = valorSeguro(modelo.getValueAt(fila, 3));
    txtCorreo.setText(valorSeguro(modelo.getValueAt(fila, 4)));
    String rol = valorSeguro(modelo.getValueAt(fila, 5));

    seleccionarEnCombo(cmbRol, rol);
    actualizarEstadoEspecialidadEdicion();

    if (cmbEspecialidad.isEnabled()) {
        if (esp.isBlank()) {
            cmbEspecialidad.setSelectedIndex(0);
        } else {
            seleccionarEnCombo(cmbEspecialidad, esp);
        }
    }
}

private void seleccionarEnCombo(javax.swing.JComboBox<String> combo, String valor) {
    for (int i = 0; i < combo.getItemCount(); i++) {
        String item = combo.getItemAt(i);
        if (item != null && item.equalsIgnoreCase(valor)) {
            combo.setSelectedIndex(i);
            return;
        }
    }
    if (combo.getItemCount() > 0) {
        combo.setSelectedIndex(0);
    }
}

private String valorSeguro(Object obj) {
    return (obj != null) ? obj.toString() : "";
}

private void actualizarUsuarioSeleccionado() {
    String esp = (cmbEspecialidad.getSelectedItem() != null)
            ? cmbEspecialidad.getSelectedItem().toString()
            : "";
    String rol = (cmbRol.getSelectedItem() != null)
            ? cmbRol.getSelectedItem().toString()
            : "";

    String res = serviciosSecretaria.editarUsuario(
            id.getText(),
            txtCedula.getText(),
            txtNombre.getText(),
            txtCorreo.getText(),
            rol,
            esp
    );

    if ("OK".equals(res)) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Usuario actualizado correctamente.",
                "Éxito",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
        cargarTablaUsuarios();
        limpiarFormularioEdicion();
    } else {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                res,
                "Aviso",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
    }
}

private void eliminarUsuarioSeleccionado() {
    String idActual = id.getText().trim();
    if (idActual.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Seleccione un usuario de la tabla para eliminar.",
                "Sin selección",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que deseas eliminar al usuario con ID " + idActual + "?",
            "Confirmar eliminación",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE
    );

    if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
        String res = serviciosSecretaria.eliminarUsuario(idActual);
        if ("OK".equals(res)) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Usuario eliminado correctamente.",
                    "Éxito",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
            cargarTablaUsuarios();
            limpiarFormularioEdicion();
        } else {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    res,
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

private void limpiarFormularioEdicion() {
    tableDatos.clearSelection();
    id.setText("");
    txtCedula.setText("");
    txtNombre.setText("");
    txtCorreo.setText("");
    if (cmbRol.getItemCount() > 0) {
        cmbRol.setSelectedIndex(0);
    }
    actualizarEstadoEspecialidadEdicion();
}
    /**
     * Creates new form Usuarios
     */
    public Usuarios() {
        initComponents();
        configurarModuloUsuarios();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableDatos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        cmbFiltroUsuarios = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        cmbEspecialidad = new javax.swing.JComboBox<>();
        cmbRol = new javax.swing.JComboBox<>();
        btnActualizar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        tableDatos.setBackground(new java.awt.Color(227, 227, 243));
        tableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Cedula", "Nombres", "Especialidad", "Correo", "Rol"
            }
        ));
        jScrollPane1.setViewportView(tableDatos);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("TABLA DE USUARIOS");

        btnBuscar.setText("Buscar");

        cmbFiltroUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Medicos", "Enfermeros", "Secretarias", "Pacientes" }));

        jLabel2.setText("Filtro de usuarios");

        btnEliminar.setBackground(new java.awt.Color(255, 204, 204));
        btnEliminar.setForeground(new java.awt.Color(153, 0, 0));
        btnEliminar.setText("Eliminar");

        jLabel3.setText("ID:");

        jLabel4.setText("Cedula:");

        jLabel5.setText("Nombres:");

        jLabel6.setText("Especialidad:");

        jLabel7.setText("Correo:");

        jLabel8.setText("Rol:");

        cmbEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnActualizar.setBackground(new java.awt.Color(204, 255, 204));
        btnActualizar.setForeground(new java.awt.Color(0, 102, 0));
        btnActualizar.setText("Actualizar Datos");

        btnLimpiar.setBackground(new java.awt.Color(204, 204, 255));
        btnLimpiar.setForeground(new java.awt.Color(0, 0, 102));
        btnLimpiar.setText("Limpiar Datos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(cmbFiltroUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(137, 137, 137))))
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(jLabel8)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(167, 167, 167)
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(79, 79, 79))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap())))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(193, 193, 193))))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(cmbFiltroUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnActualizar)
                    .addComponent(btnLimpiar))
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbEspecialidad;
    private javax.swing.JComboBox<String> cmbFiltroUsuarios;
    private javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableDatos;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
