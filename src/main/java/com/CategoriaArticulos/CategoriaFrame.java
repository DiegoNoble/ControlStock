package com.CategoriaArticulos;

import com.Beans.Categoria;
import com.DAO.DAOGenerico;
import com.Articulos.FrameSeleccionaArticulo;
import com.Articulos.ArticulosFrame;
import com.Compras.RegistraCompraFrame;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

public class CategoriaFrame extends javax.swing.JInternalFrame {

    MaskFormatter formatoftxtFechaIngreso;
    private DefaultTableModel tableModelCategoria;
    private ListSelectionModel listModelCategoria;
    private FrameSeleccionaArticulo articulosCompraFrame;
    private ArticulosFrame articulosFrame;
    
    public CategoriaFrame() {
        initComponents();

        defineModelo();
        actualizaTable();
        btnSeleccionaCategoriaArticuloFrame.setVisible(false);
        btnSeleccionaCategoría.setVisible(false);
    }
    
     public CategoriaFrame(ArticulosFrame articulosFrame) {
        initComponents();

        defineModelo();
        actualizaTable();
        
        btnSeleccionaCategoría.setVisible(false);
        this.articulosFrame = articulosFrame;
    }
     
     public CategoriaFrame(FrameSeleccionaArticulo articulosCompraFrame) {
        initComponents();

        defineModelo();
        actualizaTable();
        
        btnSeleccionaCategoriaArticuloFrame.setVisible(false);
        this.articulosCompraFrame = articulosCompraFrame;
    }

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void defineModelo() {

        tableModelCategoria = (DefaultTableModel) tblCategoria.getModel();

        listModelCategoria = tblCategoria.getSelectionModel();
        listModelCategoria.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    detallesCategoria();
                }
            }
        });

        TableCellRenderer centerRenderer = new CenterRenderer();
        ((DefaultTableCellRenderer) tblCategoria.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        TableColumn column = tblCategoria.getColumnModel().getColumn(0); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column1 = tblCategoria.getColumnModel().getColumn(1);
       
        column.setCellRenderer(centerRenderer);
        column1.setCellRenderer(centerRenderer);

        DefaultTableModel modelo = (DefaultTableModel) tblCategoria.getModel();
        modelo.setNumRows(0);

        tblCategoria.getColumn("Código").setPreferredWidth(10); //------> Ajusta el tamaño de las columnas
        tblCategoria.getColumn("Categoría").setPreferredWidth(100);
        
    }

    private void actualizaTable() {

        try {
            DAOGenerico dao = new DAOGenerico();
            List<Categoria> lista_categorias = dao.BuscaTodos(Categoria.class);

            int tamano_lista = lista_categorias.size();

            tableModelCategoria.setNumRows(0);
            
            for (int i = 0; i < tamano_lista; i++) {

                Categoria categoria = lista_categorias.get(i);

                Object[] linea = new Object[2];
                linea[0] = categoria.getId();
                linea[1] = categoria.getNombre();
               
                tableModelCategoria.addRow(linea);
            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error);
        }
    }
      
    private void NuevaCategoria() {

        if (txtCategoria.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre de la Categoría!", "Error", JOptionPane.ERROR_MESSAGE);
            txtCategoria.requestFocus();
        } else {
            try {
                Categoria categoria = new Categoria();
                categoria.setNombre(txtCategoria.getText());

                DAOGenerico dao = new DAOGenerico(categoria);
                dao.registra();

                JOptionPane.showMessageDialog(null, "Categoría registrada correctamente!");
            } catch (Exception ex) {
               ex.printStackTrace();
               JOptionPane.showMessageDialog(rootPane, "Error al salvar en base de datos: "+ex);
            }

        }
    }

    private void EditarCategoría() {

        if (txtCategoria.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre de la categoría!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtCategoria.requestFocus();

        } else {
            
            Categoria categoria = new Categoria();
            categoria.setId(Integer.parseInt(jlbCodigo.getText()));
            categoria.setNombre(txtCategoria.getText());

            DAOGenerico dao = new DAOGenerico(categoria);
            dao.actualiza();

            JOptionPane.showMessageDialog(null, "Categoría editada correctamente!");

        }

    }

    private void eliminaCategoria() {

        
        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(jlbCodigo.getText()));
        
        DAOGenerico dao = new DAOGenerico(categoria);
        dao.elimina();
           
        JOptionPane.showMessageDialog(null, "Categoría eliminada correctamente!");

    }

    private void detallesCategoria() {

        limpiaCampos();

        try {

            int filaSeleccionada = tblCategoria.getSelectedRow();

            jlbCodigo.setText(tblCategoria.getValueAt(filaSeleccionada, 0).toString());
            txtCategoria.setText(tblCategoria.getValueAt(filaSeleccionada, 1).toString());
            
        } catch (Exception error) {
        }

    }

    private void habilitaCampos() {
        txtCategoria.setEditable(true);
        tblCategoria.setEnabled(false);
        tblCategoria.setVisible(false);
    }

    private void desabilitaCampos() {
        txtCategoria.setEditable(false);
        tblCategoria.setEnabled(true);
        tblCategoria.setVisible(true);
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
        txtCategoria.setText("");
        jlbCodigo.setText("");


    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategoria = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        jlbCodigo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnSeleccionaCategoriaArticuloFrame = new javax.swing.JButton();
        btnSeleccionaCategoría = new javax.swing.JButton();
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
        setPreferredSize(new java.awt.Dimension(600, 500));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Categorías de Articulos"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Categoría"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblCategoria.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoriaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCategoria);

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

        jLabel2.setText("Categoría"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel2, gridBagConstraints);

        txtCategoria.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtCategoria, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jlbCodigo, gridBagConstraints);

        jTabbedPane1.addTab("Datos", jPanel5);

        jPanel3.add(jTabbedPane1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSeleccionaCategoriaArticuloFrame.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSeleccionaCategoriaArticuloFrame.setMnemonic('C');
        btnSeleccionaCategoriaArticuloFrame.setText("Selecciona Categoría");
        btnSeleccionaCategoriaArticuloFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaCategoriaArticuloFrameActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaCategoriaArticuloFrame);

        btnSeleccionaCategoría.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSeleccionaCategoría.setMnemonic('C');
        btnSeleccionaCategoría.setText("Selecciona Categoría");
        btnSeleccionaCategoría.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaCategoríaActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaCategoría);

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        limpiaCampos();
        habilitaCampos();
        habilitaBotones();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (jlbCodigo.getText().equals("")) {
            NuevaCategoria();
        } else {
            EditarCategoría();
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

        if (tblCategoria.getSelectedRow() != -1) {
            habilitaBotones();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }


    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (tblCategoria.getSelectedRow() != -1) {
            String Nombre = txtCategoria.getText();
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación de la categoría " + Nombre + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                eliminaCategoria();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        actualizaTable();
        limpiaCampos();

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoriaMouseClicked
    }//GEN-LAST:event_tblCategoriaMouseClicked

    private void btnSeleccionaCategoríaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaCategoríaActionPerformed

        try {

            int filaSeleccionada = tblCategoria.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una categoría en la tabla!", "Error", JOptionPane.ERROR_MESSAGE);

            } else {

                int articuloSeleccionado = (int) tblCategoria.getValueAt(filaSeleccionada, 0);
                DAOGenerico dao = new DAOGenerico();
                Categoria categoria = (Categoria) dao.buscarPorID(Categoria.class, articuloSeleccionado);

                articulosCompraFrame.setCategoria(categoria);
                this.dispose();
                articulosCompraFrame.toFront();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnSeleccionaCategoríaActionPerformed

    private void btnSeleccionaCategoriaArticuloFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaCategoriaArticuloFrameActionPerformed

        try {

            int filaSeleccionada = tblCategoria.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una categoría en la tabla!", "Error", JOptionPane.ERROR_MESSAGE);

            } else {

                int articuloSeleccionado = (int) tblCategoria.getValueAt(filaSeleccionada, 0);
                DAOGenerico dao = new DAOGenerico();
                Categoria categoria = (Categoria) dao.buscarPorID(Categoria.class, articuloSeleccionado);

                articulosFrame.setCategoria(categoria);
                this.dispose();
                articulosFrame.toFront();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_btnSeleccionaCategoriaArticuloFrameActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaCategoriaArticuloFrame;
    private javax.swing.JButton btnSeleccionaCategoría;
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
    private javax.swing.JTable tblCategoria;
    private javax.swing.JTextField txtCategoria;
    // End of variables declaration//GEN-END:variables
}
