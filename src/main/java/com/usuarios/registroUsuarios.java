package com.usuarios;

import com.Beans.Usuario;
import com.DAO.DAOGenerico;
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

public class registroUsuarios extends javax.swing.JInternalFrame {

    MaskFormatter formatoftxtFechaIngreso;
    private DefaultTableModel tableModelUsuarios;
    private ListSelectionModel listModelUsuarios;
   
    public registroUsuarios() {
        initComponents();

        defineModelo();
        actualizaTable();
    }

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void defineModelo() {

        tableModelUsuarios = (DefaultTableModel) tblUsuarios.getModel();

        listModelUsuarios = tblUsuarios.getSelectionModel();
        listModelUsuarios.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    detallesUsuarios();
                }
            }
        });

        TableCellRenderer centerRenderer = new CenterRenderer();
        ((DefaultTableCellRenderer) tblUsuarios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        TableColumn column = tblUsuarios.getColumnModel().getColumn(0); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column1 = tblUsuarios.getColumnModel().getColumn(1);
        TableColumn column2 = tblUsuarios.getColumnModel().getColumn(2);
        TableColumn column3 = tblUsuarios.getColumnModel().getColumn(3);


        column.setCellRenderer(centerRenderer);
        column1.setCellRenderer(centerRenderer);
        column2.setCellRenderer(centerRenderer);
        column3.setCellRenderer(centerRenderer);


        DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();
        modelo.setNumRows(0);

        tblUsuarios.getColumn("Código").setPreferredWidth(10); //------> Ajusta el tamaño de las columnas
        tblUsuarios.getColumn("Usuario").setPreferredWidth(100);
        //tblUsuarios.getColumn("Pass").setPreferredWidth(20);
        tblUsuarios.getColumn("Perfil").setPreferredWidth(100);



    }

    private void actualizaTable() {

        try {

            DAOGenerico dao = new DAOGenerico();
            List<Usuario> lista_clientes = dao.BuscaTodos(Usuario.class);

            int tamano_lista = lista_clientes.size();

            tableModelUsuarios.setNumRows(0);
            
            for (int i = 0; i < tamano_lista; i++) {

                Usuario usuarios = lista_clientes.get(i);

                Object[] linea = new Object[4];
                linea[0] = usuarios.getId();
                linea[1] = usuarios.getNombre();
                linea[2] = usuarios.getPass();
                linea[3] = usuarios.getPerfil();


                tableModelUsuarios.addRow(linea);
            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error);
        }


    }

      
    private void NuevoUsuario() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del Usuario!", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
        } else {
            try {
                String pass = new String(ptxtPass.getPassword()).trim();

                
                Usuario usuarios = new Usuario();
                usuarios.setNombre(txtNombre.getText());
                usuarios.setPass(pass);
                usuarios.setPerfil(cbPerfil.getSelectedItem().toString());

                DAOGenerico dao = new DAOGenerico(usuarios);
                dao.registra();

                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: "+ex);
            }



        }
    }

    private void EditarCliente() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del Usuario!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();

        } else {

            String pass = new String(ptxtPass.getPassword()).trim();
            
            Usuario usuarios = new Usuario();
            usuarios.setId(Integer.parseInt(jlbCodigo.getText()));
            usuarios.setNombre(txtNombre.getText());
            usuarios.setPass(pass);
            usuarios.setPerfil(cbPerfil.getSelectedItem().toString());

            DAOGenerico dao = new DAOGenerico(usuarios);
            dao.actualiza();

            JOptionPane.showMessageDialog(null, "Usuario editado correctamente!");


        }

    }

    private void eliminaCliente() {

        
        Usuario usuarios = new Usuario();
        usuarios.setId(Integer.parseInt(jlbCodigo.getText()));
        
        DAOGenerico dao = new DAOGenerico(usuarios);
        dao.elimina();
           
        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente!");

    }

    private void detallesUsuarios() {

        limpiaCampos();

        try {

            int filaSeleccionada = tblUsuarios.getSelectedRow();

            jlbCodigo.setText(tblUsuarios.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(tblUsuarios.getValueAt(filaSeleccionada, 1).toString());
            ptxtPass.setText(tblUsuarios.getValueAt(filaSeleccionada, 2).toString());
            cbPerfil.setSelectedItem(tblUsuarios.getValueAt(filaSeleccionada, 3).toString());
        } catch (Exception error) {
        }

    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        ptxtPass.setEditable(true);
        tblUsuarios.setEnabled(false);
        tblUsuarios.setVisible(false);
    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        ptxtPass.setEditable(false);
        tblUsuarios.setEnabled(true);
        tblUsuarios.setVisible(true);
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
        txtNombre.setText("");
        ptxtPass.setText("");
        jlbCodigo.setText("");


    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        ptxtPass = new javax.swing.JPasswordField();
        cbPerfil = new javax.swing.JComboBox();
        jlbCodigo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
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
        jLabel1.setText("Usuarios"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Usuario", "Pass", "Perfil"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);
        tblUsuarios.getColumnModel().getColumn(2).setMinWidth(0);
        tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(0);
        tblUsuarios.getColumnModel().getColumn(2).setMaxWidth(0);

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

        jLabel2.setText("Usuario"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel2, gridBagConstraints);

        jLabel4.setText("Perfil"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel4, gridBagConstraints);

        jLabel14.setText("Pass"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel14, gridBagConstraints);

        txtNombre.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtNombre, gridBagConstraints);

        ptxtPass.setEditable(false);
        ptxtPass.setText("jPasswordField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(ptxtPass, gridBagConstraints);

        cbPerfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Operador", "Gerente", "Administrador" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbPerfil, gridBagConstraints);
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        limpiaCampos();
        habilitaCampos();
        habilitaBotones();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (jlbCodigo.getText().equals("")) {
            NuevoUsuario();
        } else {
            EditarCliente();
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

        if (tblUsuarios.getSelectedRow() != -1) {
            habilitaBotones();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Usuario en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }


    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (tblUsuarios.getSelectedRow() != -1) {
            String Nombre = txtNombre.getText();
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación del Cliente " + Nombre + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                eliminaCliente();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        actualizaTable();
        limpiaCampos();

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
    }//GEN-LAST:event_tblUsuariosMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cbPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlbCodigo;
    private javax.swing.JPasswordField ptxtPass;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
