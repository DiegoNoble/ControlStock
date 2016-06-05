package com.view;

import com.DAO.DAOGenerico;
import Utilidades.Utilidades;
import com.Beans.Cotizacion;
import com.Renderers.MeDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class FrameRegistraCotizacion extends javax.swing.JInternalFrame {

    MaskFormatter formatoftxtFechaIngreso;
    private ListSelectionModel listModelCotizacion;
    private List<Cotizacion> listCotizacion;
    private DAOGenerico dao;
    private Cotizacion cotizacion;
    private String modo;
    private DefaultTableModel tableModel;

    public FrameRegistraCotizacion() {
        initComponents();
        cargaDatos();
        listeners();
    }

    private void cargaDatos() {
        try {
            dao = new DAOGenerico();
            listCotizacion = dao.BuscaTodos(Cotizacion.class);
            ((DefaultTableCellRenderer) tblCotizacion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            tableModel = (DefaultTableModel) tblCotizacion.getModel();
            for (Cotizacion contenido : listCotizacion) {

                tableModel.addRow(new Object[]{
                    contenido.getFecha(), 
                    contenido.getPesos(), 
                    contenido.getDolares(), 
                    contenido.getReales()});

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void listeners() {

        listModelCotizacion = tblCotizacion.getSelectionModel();
        listModelCotizacion.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    detallesCotizacion();
                }
            }
        });

    }

    private void nuevaCotizacion() {

        if (txtPesos.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del Usuario!", "Error", JOptionPane.ERROR_MESSAGE);
            txtPesos.requestFocus();
        } else {
            try {
                cotizacion = new Cotizacion();
                cotizacion.setPesos(Float.valueOf(txtPesos.getText()));
                cotizacion.setDolares(Float.valueOf(txtDolares.getText()));
                cotizacion.setReales(Float.valueOf(txtReales.getText()));

                Date hoy = Utilidades.RecibeDateRetornaDateFormatoBD(new Date());
                cotizacion.setFecha(hoy);
                dao = new DAOGenerico(cotizacion);
                dao.guardar();

                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
            }



        }
    }

    private void editaCotizacion() {


        if (tblCotizacion.getSelectedRow() != -1) {
            cotizacion = new Cotizacion();
            cotizacion.setId(listCotizacion.get(tblCotizacion.getSelectedRow()).getId());
            cotizacion.setPesos(Float.valueOf(txtPesos.getText()));
            cotizacion.setDolares(Float.valueOf(txtDolares.getText()));
            cotizacion.setReales(Float.valueOf(txtReales.getText()));
            cotizacion.setFecha(listCotizacion.get(tblCotizacion.getSelectedRow()).getFecha());

            dao = new DAOGenerico(cotizacion);
            dao.actualiza();

            JOptionPane.showMessageDialog(null, "Usuario editado correctamente!");


        } else {

            JOptionPane.showMessageDialog(null, "Seleccione un registro en la tabla");

        }

    }

    private void limpiaTbl() {

        tableModel.setNumRows(0);
    }

    private void eliminaCotizacion() {

        cotizacion = new Cotizacion();
        cotizacion.setId(listCotizacion.get(tblCotizacion.getSelectedRow()).getId());

        dao = new DAOGenerico(cotizacion);
        dao.elimina();

        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente!");

    }

    private void detallesCotizacion() {

        limpiaCampos();

        try {

            int filaSeleccionada = tblCotizacion.getSelectedRow();

            txtPesos.setText(tblCotizacion.getValueAt(filaSeleccionada, 1).toString());
            txtDolares.setText(tblCotizacion.getValueAt(filaSeleccionada, 2).toString());
            txtReales.setText(tblCotizacion.getValueAt(filaSeleccionada, 3).toString());
        } catch (Exception error) {
        }

    }

    private void habilitaCampos() {
        txtPesos.setEditable(true);
        txtDolares.setEditable(true);
        txtReales.setEditable(true);
        tblCotizacion.setEnabled(false);
        //tblCotizacion.setVisible(false);
    }

    private void desabilitaCampos() {
        txtPesos.setEditable(false);
        txtDolares.setEditable(false);
        txtReales.setEditable(false);
        tblCotizacion.setEnabled(true);
        tblCotizacion.setVisible(true);
    }

    private void desabilitaBotones() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnAlterar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void habilitaBotones() {

        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void limpiaCampos() {
        txtPesos.setText("");
        txtDolares.setText("");
        txtReales.setText("");

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCotizacion = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtPesos = new javax.swing.JTextField();
        txtReales = new javax.swing.JTextField();
        txtDolares = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-"); // NOI18N
        setPreferredSize(new java.awt.Dimension(400, 500));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cotización"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblCotizacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Pesos", "Dólares", "Reales"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCotizacion.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblCotizacion);
        tblCotizacion.getColumnModel().getColumn(0).setCellRenderer(new MeDateCellRenderer());
        tblCotizacion.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblCotizacion.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblCotizacion.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnNuevo.setMnemonic('N');
        btnNuevo.setText("Nuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNuevo);

        btnAlterar.setMnemonic('E');
        btnAlterar.setText("Editar"); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAlterar);

        btnEliminar.setMnemonic('X');
        btnEliminar.setText("Excluir"); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminar);

        btnSalvar.setMnemonic('S');
        btnSalvar.setText("Salvar"); // NOI18N
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("Reales"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel4, gridBagConstraints);

        jLabel14.setText("Pesos"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel14, gridBagConstraints);

        txtPesos.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtPesos, gridBagConstraints);

        txtReales.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtReales, gridBagConstraints);

        txtDolares.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtDolares, gridBagConstraints);

        jLabel5.setText("Dólares"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel5, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        modo = "nuevo";
        limpiaCampos();
        habilitaCampos();
        habilitaBotones();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (modo.equals("nuevo")) {
            nuevaCotizacion();
        } else if (modo.equals("edicion")) {
            editaCotizacion();
        }

        modo = "";
        desabilitaBotones();
        desabilitaCampos();
        limpiaTbl();
        cargaDatos();


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        modo = "";
        desabilitaBotones();
        desabilitaCampos();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        modo = "edicion";

        habilitaBotones();
        habilitaCampos();
        limpiaTbl();
        cargaDatos();

    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        modo = "";
        if (tblCotizacion.getSelectedRow() != -1) {

            int respuesta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                eliminaCotizacion();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una cotización de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        limpiaCampos();
        limpiaTbl();
        cargaDatos();

    }//GEN-LAST:event_btnEliminarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCotizacion;
    private javax.swing.JTextField txtDolares;
    private javax.swing.JTextField txtPesos;
    private javax.swing.JTextField txtReales;
    // End of variables declaration//GEN-END:variables
}
