package com.Articulos;

import com.Beans.Articulo;
import com.Beans.EquivalenciaUnidades;
import com.Beans.Unidad;
import com.DAO.ArticuloDAO;
import com.DAO.EquivalenciaUnidadesDAO;
import com.DAO.UnidadesDAO;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

public class EquivalenciasFrame extends javax.swing.JInternalFrame {

    private EquivalenciaUnidadesTableModel tableModel;
    private ListSelectionModel listModelArticulos;
    Articulo articuloSeleccionado;
    EquivalenciaUnidades equivalenciaUnidadesSeleccionado;
    EquivalenciaUnidadesDAO equivalenciaDAO;
    ArticuloDAO articuloDAO;
    List<EquivalenciaUnidades> listEquivalencias;

    public EquivalenciasFrame(Articulo articuloSeleccionado) {
        initComponents();
        this.articuloSeleccionado = articuloSeleccionado;
        defineModelo();

    }
    
    

    public class ComboBoxUnidades extends JComboBox<Object> {

        UnidadesDAO unidadesDAO;

        public ComboBoxUnidades() {
            try {
                AutoCompleteDecorator.decorate(this);
                unidadesDAO = new UnidadesDAO();
                for (Unidad uni : (List<Unidad>) unidadesDAO.BuscaTodos(Unidad.class)) {

                    this.addItem(uni);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
            }
        }

    }

    private void defineModelo() {
        listEquivalencias = articuloSeleccionado.getListEquivalencias();
        tableModel = new EquivalenciaUnidadesTableModel(listEquivalencias);
        tblEquivalencias.setModel(tableModel);
        tblEquivalencias.getColumn("Unidad").setCellEditor(new ComboBoxCellEditor(new ComboBoxUnidades()));

        listModelArticulos = tblEquivalencias.getSelectionModel();
        listModelArticulos.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tblEquivalencias.getSelectedRow() != -1) {
                        equivalenciaUnidadesSeleccionado = listEquivalencias.get(tblEquivalencias.getSelectedRow());
                        btnExcluir.setEnabled(true);

                    } else {
                        equivalenciaUnidadesSeleccionado = null;
                        btnExcluir.setEnabled(false);
                    }
                }
            }
        });

    }

    private void NuevoProducto() {

        try {

            tableModel.agregar(new EquivalenciaUnidades(articuloSeleccionado));

            desabilitaBotoes();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error " + ex);
        }
    }

    void buscarTodos() {

        tableModel.agregar(articuloSeleccionado.getListEquivalencias());
    }

    private void eliminaProducto() {

        equivalenciaDAO = new EquivalenciaUnidadesDAO(equivalenciaUnidadesSeleccionado);
        equivalenciaDAO.elimina();
        articuloDAO = new ArticuloDAO();
        articuloSeleccionado = (Articulo) articuloDAO.buscarPorIDString(Articulo.class, articuloSeleccionado.getId()).get(0);
        buscarTodos();

        JOptionPane.showMessageDialog(null, "Equivalencia eliminada correctamente!");

    }

    private void desabilitaBotoes() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnExcluir.setEnabled(true);
        btnNovo.setEnabled(true);
    }

    private void habilitaBotoes() {
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnExcluir.setEnabled(false);
        btnNovo.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEquivalencias = new javax.swing.JTable();

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(600, 500));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Equivalencia unidades");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        btnNovo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnNovo.setMnemonic('N');
        btnNovo.setText("Nuevo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnNovo, gridBagConstraints);

        btnExcluir.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnExcluir, gridBagConstraints);

        btnSalvar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSalvar.setMnemonic('S');
        btnSalvar.setText("Salvar");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnSalvar, gridBagConstraints);

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        tblEquivalencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEquivalencias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblEquivalencias);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        for (EquivalenciaUnidades equivalencia : listEquivalencias) {
            equivalenciaDAO = new EquivalenciaUnidadesDAO(equivalencia);
            equivalenciaDAO.actualiza();

        }
        JOptionPane.showMessageDialog(this, "Datos registrados correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        articuloDAO = new ArticuloDAO();
        articuloSeleccionado = (Articulo) articuloDAO.buscarPorIDString(Articulo.class, articuloSeleccionado.getId()).get(0);
        desabilitaBotoes();
        buscarTodos();

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaBotoes();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación de la equivalencia ?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            eliminaProducto();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        NuevoProducto();
        habilitaBotoes();

    }//GEN-LAST:event_btnNovoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable tblEquivalencias;
    // End of variables declaration//GEN-END:variables

}
