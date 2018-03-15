package com.Unidades;

import com.Beans.Unidad;
import com.DAO.DAOGenerico;
import com.Articulos.ArticulosFrame;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class UnidadFrame extends javax.swing.JInternalFrame {

    private DefaultTableModel tableModelUnidades;
    private ListSelectionModel listModelUnidades;
    private ArticulosFrame articulosFrame;

    public UnidadFrame() {
        initComponents();

        btnSeleccionaUnidadNormal.setVisible(false);
        defineModelo();
        actualizaTable();
    }

    public UnidadFrame(ArticulosFrame articulosFrame) {
        initComponents();

        defineModelo();
        actualizaTable();

        btnSeleccionaUnidadNormal.setVisible(true);
        this.articulosFrame = articulosFrame;
    }

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void defineModelo() {

        tableModelUnidades = (DefaultTableModel) tblUnidades.getModel();

        listModelUnidades = tblUnidades.getSelectionModel();
        listModelUnidades.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    detallesUnidad();
                }
            }
        });

        TableCellRenderer centerRenderer = new CenterRenderer();
        ((DefaultTableCellRenderer) tblUnidades.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        TableColumn column = tblUnidades.getColumnModel().getColumn(1); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable

        column.setCellRenderer(centerRenderer);

        DefaultTableModel modelo = (DefaultTableModel) tblUnidades.getModel();
        modelo.setNumRows(0);

        tblUnidades.getColumn("Descripción").setPreferredWidth(50); //------> Ajusta el tamaño de las columnas

    }

    private void actualizaTable() {

        try {

            DAOGenerico dao = new DAOGenerico();
            List<Unidad> lista_unidades = dao.BuscaTodos(Unidad.class);

            int tamano_lista = lista_unidades.size();

            tableModelUnidades.setNumRows(0);

            for (int i = 0; i < tamano_lista; i++) {

                Unidad unidades = lista_unidades.get(i);

                Object[] linea = new Object[2];
                linea[0] = unidades.getId();
                linea[1] = unidades.getDescripcion();

                tableModelUnidades.addRow(linea);
            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error);
        }

    }

    private void NuevoUnidad() {

        if (txtDescripcion.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe la descripción de la Unidad ", "Error", JOptionPane.ERROR_MESSAGE);
            txtDescripcion.requestFocus();
        } else {
            try {
                Unidad unidad = new Unidad();
                unidad.setDescripcion(txtDescripcion.getText());

                DAOGenerico dao = new DAOGenerico(unidad);
                dao.guardar();

                JOptionPane.showMessageDialog(null, "Unidad registrada correctamente!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
            }

        }
    }

    private void EditarUnidad() {

        if (txtDescripcion.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe la descripción de la Unidad!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtDescripcion.requestFocus();

        } else {

            Unidad usuarios = new Unidad();
            usuarios.setId(Integer.parseInt(jlbCodigo.getText()));
            usuarios.setDescripcion(txtDescripcion.getText());

            DAOGenerico dao = new DAOGenerico(usuarios);
            dao.actualiza();

            JOptionPane.showMessageDialog(null, "Unidad editada correctamente!");

        }

    }

    private void eliminaUnidad() throws Exception {

        Unidad unidad = new Unidad();
        unidad.setId(Integer.parseInt(jlbCodigo.getText()));

        DAOGenerico dao = new DAOGenerico(unidad);
        dao.elimina();

        JOptionPane.showMessageDialog(null, "Unidad eliminada correctamente!");

    }

    private void detallesUnidad() {

        limpiaCampos();

        try {

            int filaSeleccionada = tblUnidades.getSelectedRow();

            jlbCodigo.setText(tblUnidades.getValueAt(filaSeleccionada, 0).toString());
            txtDescripcion.setText(tblUnidades.getValueAt(filaSeleccionada, 1).toString());
        } catch (Exception error) {
        }

    }

    private void habilitaCampos() {
        txtDescripcion.setEditable(true);
        tblUnidades.setEnabled(false);
        tblUnidades.setVisible(false);
    }

    private void desabilitaCampos() {
        txtDescripcion.setEditable(false);
        tblUnidades.setEnabled(true);
        tblUnidades.setVisible(true);
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
        txtDescripcion.setText("");
        jlbCodigo.setText("");

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUnidades = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jlbCodigo = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnSeleccionaUnidadNormal = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-"); // NOI18N
        setPreferredSize(new java.awt.Dimension(550, 350));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Unidades"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblUnidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblUnidades.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUnidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUnidadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUnidades);
        if (tblUnidades.getColumnModel().getColumnCount() > 0) {
            tblUnidades.getColumnModel().getColumn(0).setMinWidth(0);
            tblUnidades.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblUnidades.getColumnModel().getColumn(0).setMaxWidth(0);
        }

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

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Descripción"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jlbCodigo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtDescripcion, gridBagConstraints);

        jTabbedPane1.addTab("Datos", jPanel5);

        jPanel3.add(jTabbedPane1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSeleccionaUnidadNormal.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSeleccionaUnidadNormal.setMnemonic('U');
        btnSeleccionaUnidadNormal.setText("Selecciona Unidad");
        btnSeleccionaUnidadNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaUnidadNormalActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaUnidadNormal);

        btnNuevo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnNuevo.setMnemonic('N');
        btnNuevo.setText("Nuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNuevo);

        btnAlterar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnAlterar.setMnemonic('E');
        btnAlterar.setText("Editar"); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAlterar);

        btnEliminar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnEliminar.setText("Excluir"); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminar);

        btnSalvar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSalvar.setMnemonic('S');
        btnSalvar.setText("Salvar"); // NOI18N
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        limpiaCampos();
        habilitaCampos();
        habilitaBotones();
        txtDescripcion.requestFocus();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (jlbCodigo.getText().equals("")) {
            NuevoUnidad();
        } else {
            EditarUnidad();
        }

        actualizaTable();
        desabilitaBotones();
        desabilitaCampos();


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        desabilitaBotones();
        desabilitaCampos();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        if (tblUnidades.getSelectedRow() != -1) {
            habilitaBotones();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una Unidad o en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }


    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        try {

            if (tblUnidades.getSelectedRow() != -1) {
                String Nombre = txtDescripcion.getText();
                int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación de la Unidad " + Nombre + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    eliminaUnidad();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una Unidad de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No es posible eliminar el articulo seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
        }
        actualizaTable();
        limpiaCampos();

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblUnidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUnidadesMouseClicked
    }//GEN-LAST:event_tblUnidadesMouseClicked

    private void btnSeleccionaUnidadNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaUnidadNormalActionPerformed

        try {

            int filaSeleccionada = tblUnidades.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una categoría en la tabla!", "Error", JOptionPane.ERROR_MESSAGE);

            } else {

                int unidadSeleccionada = (int) tblUnidades.getValueAt(filaSeleccionada, 0);
                DAOGenerico dao = new DAOGenerico();
                Unidad unidad = (Unidad) dao.buscarPorID(Unidad.class, unidadSeleccionada);

                articulosFrame.setUnidad(unidad);
                this.dispose();
                articulosFrame.toFront();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnSeleccionaUnidadNormalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaUnidadNormal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlbCodigo;
    private javax.swing.JTable tblUnidades;
    private javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables
}
