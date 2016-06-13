package com.Proveedores;

import com.Beans.Proveedor;
import javax.swing.JOptionPane;
import com.Beans.CondicionImpositiva;
import com.Compras.RegistraCompra;
import com.DAO.ProveedorDAO;
import com.Pedidos.RegistraPedido;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class ProveedoresFrame extends javax.swing.JInternalFrame {

    private Proveedor proveedorSeleccionado;
    ProveedoresTableModel tableModel;
    List<Proveedor> listProveedors;
    ProveedorDAO proveedorDAO;
    Proveedor proveedor;
    RegistraCompra registraCompra;

    public ProveedoresFrame() {
        initComponents();
        listProveedors = new ArrayList<>();
        defineModelo();
        buscaTodos();
        btnSeleccionaProveedor.setVisible(false);
        btnExcluir.setVisible(false);
    }

    public ProveedoresFrame(RegistraCompra registraCompra) {
        initComponents();

        btnExcluir.setVisible(false);
        defineModelo();
         buscaTodos();
        this.registraCompra = registraCompra;
    }

    public Proveedor getClie() {
        return proveedorSeleccionado;
    }

    public void setClie(Proveedor clie) {
        this.proveedorSeleccionado = clie;
    }

    void buscaTodos() {
        proveedorDAO = new ProveedorDAO();
        tableModel.agregar(proveedorDAO.BuscaTodos(Proveedor.class));
    }

    private void defineModelo() {

        try {
            cbCondicionImpo.setModel(new DefaultComboBoxModel(CondicionImpositiva.values()));

            ((DefaultTableCellRenderer) tblProveedor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

            tableModel = new ProveedoresTableModel(listProveedors);
            tblProveedor.setModel(tableModel);

            ListSelectionModel listModel = tblProveedor.getSelectionModel();
            listModel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (tblProveedor.getSelectedRow() != -1) {
                        proveedorSeleccionado = listProveedors.get(tblProveedor.getSelectedRow());
                        btnExcluir.setEnabled(true);
                        btnAlterar.setEnabled(true);
                        btnSeleccionaProveedor.setEnabled(true);
                        muestraDetalle();

                    } else {
                        proveedorSeleccionado = null;
                        btnSeleccionaProveedor.setEnabled(false);
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

        txtCel.setText(proveedorSeleccionado.getCel());
        txtCiudad.setText(proveedorSeleccionado.getCiudad());
        txtDireccion.setText(proveedorSeleccionado.getDireccion());
        txtDocumento.setText(proveedorSeleccionado.getDocumento());
        txtEmail.setText(proveedorSeleccionado.getEmail());
        txtNombre.setText(proveedorSeleccionado.getNombre());
        txtPaís.setText(proveedorSeleccionado.getPais());
        txtRazonSocial.setText(proveedorSeleccionado.getRazon_social());
        txtTelefono.setText(proveedorSeleccionado.getTelefono());
        dpFecha.setDate(proveedorSeleccionado.getFecha_ingreso());
        cbCondicionImpo.setSelectedItem(proveedorSeleccionado.getCondicionImpositiva());

    }

    void filtro() {
        proveedorDAO = new ProveedorDAO();
        if (rbCod.isSelected()) {
            try {
                tableModel.agregar(proveedorDAO.buscarPor(Proveedor.class, "id", Integer.parseInt(txtFiltroNombre.getText())));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        } else if (rbNombre.isSelected()) {
            tableModel.agregar(proveedorDAO.buscarProveedor(txtFiltroNombre.getText()));
        }

    }

    private void NuevoProveedor() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del proveedor!", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
        } else {

            try {
                proveedor = new Proveedor();

                proveedor.setNombre(txtNombre.getText());
                proveedor.setCel(txtCel.getText());
                proveedor.setCiudad(txtCiudad.getText());
                proveedor.setDireccion(txtDireccion.getText());
                proveedor.setEmail(txtEmail.getText());
                proveedor.setFecha_ingreso(dpFecha.getDate());
                proveedor.setPais(txtPaís.getText());
                proveedor.setRazon_social(txtRazonSocial.getText());
                proveedor.setDocumento(txtDocumento.getText());
                proveedor.setTelefono(txtTelefono.getText());
                proveedor.setCondicionImpositiva((CondicionImpositiva) cbCondicionImpo.getSelectedItem());

                proveedorDAO = new ProveedorDAO(proveedor);
                proveedorDAO.guardar();

                JOptionPane.showMessageDialog(null, "Proveedor registrado correctamente!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar registros " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        }
    }

    private void EditarProveedor() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del proveedor!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();

        } else {
            try {
                proveedorSeleccionado.setNombre(txtNombre.getText());
                proveedorSeleccionado.setCel(txtCel.getText());
                proveedorSeleccionado.setCiudad(txtCiudad.getText());
                proveedorSeleccionado.setDireccion(txtDireccion.getText());
                proveedorSeleccionado.setEmail(txtEmail.getText());
                proveedorSeleccionado.setFecha_ingreso(dpFecha.getDate());
                proveedorSeleccionado.setPais(txtPaís.getText());
                proveedorSeleccionado.setRazon_social(txtRazonSocial.getText());
                proveedorSeleccionado.setDocumento(txtDocumento.getText());
                proveedorSeleccionado.setTelefono(txtTelefono.getText());
                proveedorSeleccionado.setCondicionImpositiva((CondicionImpositiva) cbCondicionImpo.getSelectedItem());

                proveedorDAO = new ProveedorDAO(proveedorSeleccionado);
                proveedorDAO.actualiza();

                JOptionPane.showMessageDialog(null, "Proveedor editado correctamente!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar registros " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        }

    }

    private void eliminaProveedor() {
        try {
            proveedorDAO = new ProveedorDAO(proveedorSeleccionado);
            proveedorDAO.elimina();
            JOptionPane.showMessageDialog(null, "Proveedor eliminado correctamente!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No es posible eliminar el registro seleccionado " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        txtDireccion.setEditable(true);
        txtCiudad.setEditable(true);
        txtPaís.setEditable(true);
        txtDocumento.setEditable(true);
        txtTelefono.setEditable(true);
        txtEmail.setEditable(true);
        txtCel.setEditable(true);
        dpFecha.setEditable(true);
        dpFecha.setEnabled(true);
        cbCondicionImpo.setEditable(true);
        cbCondicionImpo.setEnabled(true);
        txtRazonSocial.setEditable(true);
        txtDocumento.setEditable(true);
        tblProveedor.setEnabled(false);
        tblProveedor.setVisible(false);
    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        txtDireccion.setEditable(false);
        txtCiudad.setEditable(false);
        txtPaís.setEditable(false);
        txtDocumento.setEditable(false);
        txtTelefono.setEditable(false);
        txtEmail.setEditable(false);
        cbCondicionImpo.setEditable(false);
        cbCondicionImpo.setEnabled(false);
        txtCel.setEditable(false);
        dpFecha.setEditable(false);
        dpFecha.setEnabled(false);
        txtRazonSocial.setEditable(false);
        txtDocumento.setEditable(false);
        tblProveedor.setEnabled(true);
        tblProveedor.setVisible(true);
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
        txtCiudad.setText("");
        txtPaís.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtCel.setText("");
        dpFecha.setDate(new Date());
        txtRazonSocial.setText("");
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
        txtCiudad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPaís = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtDocumento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        cbCondicionImpo = new javax.swing.JComboBox();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        jPanel3 = new javax.swing.JPanel();
        txtFiltroNombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProveedor = new javax.swing.JTable();
        rbCod = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnSeleccionaProveedor = new javax.swing.JButton();
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
        jLabel1.setText("Proveedores");
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

        txtCiudad.setEditable(false);
        txtCiudad.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtCiudad, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setText("País");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel6, gridBagConstraints);

        txtPaís.setEditable(false);
        txtPaís.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtPaís, gridBagConstraints);

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

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("Razón Social");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel7, gridBagConstraints);

        txtRazonSocial.setEditable(false);
        txtRazonSocial.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtRazonSocial, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel11.setText("Fecha de Ingreso");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        jPanel2.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel12.setText("Condición Impositiva");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        jPanel2.add(jLabel12, gridBagConstraints);

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbCondicionImpo, gridBagConstraints);

        dpFecha.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpFecha, gridBagConstraints);

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

        tblProveedor.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProveedor.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblProveedor);

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

        btnSeleccionaProveedor.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSeleccionaProveedor.setMnemonic('C');
        btnSeleccionaProveedor.setText("Selecciona proveedor");
        btnSeleccionaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaProveedor);

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

        proveedorSeleccionado = null;
        limpiaCampos();
        habilitaCampos();
        habilitaBotoes();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (proveedorSeleccionado == null) {
            NuevoProveedor();
        } else {
            EditarProveedor();
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

        if (tblProveedor.getSelectedRow() != -1) {
            habilitaBotoes();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (tblProveedor.getSelectedRow() != -1) {
            String Nombre = txtRazonSocial.getText();
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación del Proveedor " + Nombre + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                eliminaProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        buscaTodos();
        limpiaCampos();

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtFiltroNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroNombreActionPerformed

        filtro();
    }//GEN-LAST:event_txtFiltroNombreActionPerformed

    private void btnSeleccionaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaProveedorActionPerformed

        this.registraCompra.agregarProveedor(proveedorSeleccionado);

    }//GEN-LAST:event_btnSeleccionaProveedorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaProveedor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCondicionImpo;
    private org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JTable tblProveedor;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFiltroNombre;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPaís;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
