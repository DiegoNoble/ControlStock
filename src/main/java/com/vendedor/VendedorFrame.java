package com.vendedor;

import com.Beans.Ciudad;
import com.Beans.Vendedor;
import com.Ciudades.CiudadesDialog;
import com.DAO.CiudadDAO;
import javax.swing.JOptionPane;
import com.DAO.VendedorDAO;
import com.Pedidos.RegistraPedido;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class VendedorFrame extends javax.swing.JInternalFrame {

    private Vendedor vendedorSeleccionado;
    RegistraPedido seteaVendedorPedido;
    VendedoresTableModel tableModel;
    List<Vendedor> listVendedores;
    VendedorDAO vendedorDAO;
    Vendedor vendedor;
    CiudadDAO ciudadDAO;

    public VendedorFrame() {
        initComponents();
        listVendedores = new ArrayList<>();
        cargaComboCiudad();
        defineModelo();
        buscaTodos();
        btnSeleccionaVendedor.setVisible(false);

        btnExcluir.setVisible(false);
    }

    public VendedorFrame(RegistraPedido seteaVendedorPedido) {
        initComponents();

        btnExcluir.setVisible(false);
        cargaComboCiudad();
        defineModelo();
        this.seteaVendedorPedido = seteaVendedorPedido;
    }

    public final void cargaComboCiudad() {

        try {
            AutoCompleteDecorator.decorate(cbCiudad);
            List<Ciudad> ciudades = new ArrayList();

            ciudadDAO = new CiudadDAO();
            ciudades = ciudadDAO.BuscaTodos(Ciudad.class);

            cbCiudad.removeAllItems();

            for (Ciudad ciudad : ciudades) {

                cbCiudad.addItem(ciudad);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Vendedor getClie() {
        return vendedorSeleccionado;
    }

    public void setClie(Vendedor clie) {
        this.vendedorSeleccionado = clie;
    }

    void buscaTodos() {
        vendedorDAO = new VendedorDAO();
        tableModel.agregar(vendedorDAO.BuscaTodos(Vendedor.class));
    }

    private void defineModelo() {

        try {

            ((DefaultTableCellRenderer) tblVendedor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

            tableModel = new VendedoresTableModel(listVendedores);
            tblVendedor.setModel(tableModel);

            ListSelectionModel listModel = tblVendedor.getSelectionModel();
            listModel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (tblVendedor.getSelectedRow() != -1) {
                        vendedorSeleccionado = listVendedores.get(tblVendedor.getSelectedRow());
                        btnExcluir.setEnabled(true);
                        btnAlterar.setEnabled(true);
                        muestraDetalle();

                    } else {
                        vendedorSeleccionado = null;
                        btnExcluir.setEnabled(false);
                        btnAlterar.setEnabled(false);
                    }
                }

            });

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al definir modelo de tabla" + error);
            error.printStackTrace();
        }

    }

    private void muestraDetalle() {

        txtCel.setText(vendedorSeleccionado.getCel());
        cbCiudad.setSelectedItem(vendedorSeleccionado.getCiudad());
        txtDireccion.setText(vendedorSeleccionado.getDireccion());
        txtDocumento.setText(vendedorSeleccionado.getCi());
        txtEmail.setText(vendedorSeleccionado.getEmail());
        txtNombre.setText(vendedorSeleccionado.getNombre());
        txtTelefono.setText(vendedorSeleccionado.getTelefono());
        dpFecha.setDate(vendedorSeleccionado.getFecha_ingreso());

    }

    void filtro() {
        vendedorDAO = new VendedorDAO();
        if (rbCod.isSelected()) {
            try {
                tableModel.agregar(vendedorDAO.buscarPor(Vendedor.class, "id_vendedor", Integer.parseInt(txtFiltroNombre.getText())));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        } else if (rbNombre.isSelected()) {
            tableModel.agregar(vendedorDAO.buscarVendedor(txtFiltroNombre.getText()));
        }

    }

    private void NuevoVendedor() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del vendedor!", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
        } else {

            try {
                vendedor = new Vendedor();

                vendedor.setNombre(txtNombre.getText());
                vendedor.setCel(txtCel.getText());
                vendedor.setCiudad((Ciudad) cbCiudad.getSelectedItem());
                vendedor.setDireccion(txtDireccion.getText());
                vendedor.setEmail(txtEmail.getText());
                vendedor.setFecha_ingreso(dpFecha.getDate());
                vendedor.setCi(txtDocumento.getText());
                vendedor.setTelefono(txtTelefono.getText());

                vendedorDAO = new VendedorDAO(vendedor);
                vendedorDAO.guardar();

                JOptionPane.showMessageDialog(null, "Vendedor registrado correctamente!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar registros " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        }
    }

    private void EditarVendedor() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del vendedor!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();

        } else {
            try {
                vendedorSeleccionado.setNombre(txtNombre.getText());
                vendedorSeleccionado.setCel(txtCel.getText());
                vendedorSeleccionado.setCiudad((Ciudad) cbCiudad.getSelectedItem());
                vendedorSeleccionado.setDireccion(txtDireccion.getText());
                vendedorSeleccionado.setEmail(txtEmail.getText());
                vendedorSeleccionado.setFecha_ingreso(dpFecha.getDate());
                vendedorSeleccionado.setCi(txtDocumento.getText());
                vendedorSeleccionado.setTelefono(txtTelefono.getText());

                vendedorDAO = new VendedorDAO(vendedorSeleccionado);
                vendedorDAO.actualiza();

                JOptionPane.showMessageDialog(null, "Vendedor editado correctamente!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar registros " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        }

    }

    private void eliminaVendedor() {
        try {
            vendedorDAO = new VendedorDAO(vendedorSeleccionado);
            vendedorDAO.elimina();
            JOptionPane.showMessageDialog(null, "Vendedor eliminado correctamente!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No es posible eliminar el registro seleccionado " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        txtDireccion.setEditable(true);
        cbCiudad.setEnabled(true);
        txtDocumento.setEditable(true);
        txtTelefono.setEditable(true);
        txtEmail.setEditable(true);
        txtCel.setEditable(true);
        dpFecha.setEditable(true);
        dpFecha.setEnabled(true);
        txtDocumento.setEditable(true);
        tblVendedor.setEnabled(false);
        tblVendedor.setVisible(false);
    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        txtDireccion.setEditable(false);
        cbCiudad.setEnabled(false);
        txtDocumento.setEditable(false);
        txtTelefono.setEditable(false);
        txtEmail.setEditable(false);
        txtCel.setEditable(false);
        dpFecha.setEditable(false);
        dpFecha.setEnabled(false);
        txtDocumento.setEditable(false);
        tblVendedor.setEnabled(true);
        tblVendedor.setVisible(true);
    }

    private void desabilitaBotoes() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);
    }

    private void habilitaBotoes() {
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }

    private void limpiaCampos() {

        txtNombre.setText("");
        txtDireccion.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtCel.setText("");
        dpFecha.setDate(new Date());
        txtDocumento.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtDocumento = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        cbCiudad = new javax.swing.JComboBox();
        btnCiudad = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtFiltroNombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVendedor = new javax.swing.JTable();
        rbCod = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnSeleccionaVendedor = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(700, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Vendedores");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNombre, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Dirección");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel4, gridBagConstraints);

        txtDireccion.setEditable(false);
        txtDireccion.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtDireccion, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Ciudad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setText("Documento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setText("Teléfono");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel9, gridBagConstraints);

        txtTelefono.setEditable(false);
        txtTelefono.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtTelefono, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel10.setText("Email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel10, gridBagConstraints);

        txtEmail.setEditable(false);
        txtEmail.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtEmail, gridBagConstraints);

        txtDocumento.setEditable(false);
        txtDocumento.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtDocumento, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel11.setText("Fecha de Ingreso");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        jPanel2.add(jLabel11, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel13.setText("Cel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        jPanel2.add(jLabel13, gridBagConstraints);

        txtCel.setEditable(false);
        txtCel.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtCel, gridBagConstraints);

        dpFecha.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpFecha, gridBagConstraints);

        cbCiudad.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbCiudad, gridBagConstraints);

        btnCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/add.png"))); // NOI18N
        btnCiudad.setToolTipText("Nueva posición");
        btnCiudad.setBorder(null);
        btnCiudad.setEnabled(false);
        btnCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCiudadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel2.add(btnCiudad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        txtFiltroNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtFiltroNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtFiltroNombre, gridBagConstraints);

        tblVendedor.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblVendedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblVendedor.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblVendedor);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        buttonGroup1.add(rbCod);
        rbCod.setText("Cód.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel3.add(rbCod, gridBagConstraints);

        buttonGroup1.add(rbNombre);
        rbNombre.setSelected(true);
        rbNombre.setText("Nombre o Razón Social");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel3.add(rbNombre, gridBagConstraints);

        jLabel14.setText("Filtrar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel14, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSeleccionaVendedor.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSeleccionaVendedor.setMnemonic('C');
        btnSeleccionaVendedor.setText("Selecciona vendedor");
        btnSeleccionaVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaVendedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaVendedor);

        btnNovo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnNovo.setMnemonic('N');
        btnNovo.setText("Nuevo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNovo);

        btnAlterar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAlterar.setMnemonic('E');
        btnAlterar.setText("Editar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAlterar);

        btnExcluir.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(btnExcluir);

        btnSalvar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSalvar.setMnemonic('S');
        btnSalvar.setText("Salvar");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Cancelar");
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

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        vendedorSeleccionado = null;
        limpiaCampos();
        habilitaCampos();
        habilitaBotoes();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (vendedorSeleccionado == null) {
            NuevoVendedor();
        } else {
            EditarVendedor();
        }

        buscaTodos();
        desabilitaBotoes();
        desabilitaCampos();

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaBotoes();
        desabilitaCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        if (tblVendedor.getSelectedRow() != -1) {
            habilitaBotoes();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un vendedor en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (tblVendedor.getSelectedRow() != -1) {
            String Nombre = txtNombre.getText();
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación del Vendedor " + Nombre + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                eliminaVendedor();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un vendedor de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        buscaTodos();
        limpiaCampos();

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtFiltroNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroNombreActionPerformed

        filtro();
    }//GEN-LAST:event_txtFiltroNombreActionPerformed

    private void btnSeleccionaVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaVendedorActionPerformed

        /* int filaSeleccionada = tblVendedor.getSelectedRow();
         if (filaSeleccionada == -1) {
         JOptionPane.showMessageDialog(null, "Seleccione un vendedor en la tabla!", "Error", JOptionPane.ERROR_MESSAGE);
         } else {

         Vendedor vendedor = new Vendedor();
         vendedor.setId_vendedor(Integer.parseInt(tblVendedor.getValueAt(filaSeleccionada, 0).toString()));
         vendedor.setNombre(tblVendedor.getValueAt(filaSeleccionada, 1).toString());
         if (seteaVendedorVenta == null) {
         seteaVendedorPedido.agregarVendedor(vendedor);
         this.dispose();
         seteaVendedorPedido.toFront();
         } else if (seteaVendedorPedido == null) {
         seteaVendedorVenta.setClie(vendedor);
         this.dispose();
         seteaVendedorVenta.toFront();
         }

         }

         */
    }//GEN-LAST:event_btnSeleccionaVendedorActionPerformed

    private void btnCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCiudadActionPerformed
        CiudadesDialog ciudadesFrame = new CiudadesDialog(null, true, this);
        ciudadesFrame.setVisible(true);
        ciudadesFrame.toFront();
        cargaComboCiudad();
    }//GEN-LAST:event_btnCiudadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCiudad;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaVendedor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCiudad;
    private org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JRadioButton rbCod;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblVendedor;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFiltroNombre;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
