package com.Clientes;

import com.Beans.Articulo;
import com.Beans.Ciudad;
import com.Beans.Cliente;
import javax.swing.JOptionPane;
import com.Beans.CondicionImpositiva;
import com.Ciudades.CiudadesDialog;
import com.DAO.ArticuloDAO;
import com.DAO.CiudadDAO;
import com.DAO.ClienteDAO;
import com.DAO.DAOGenerico;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ClienteFrame extends javax.swing.JInternalFrame {

    private RegistraPedido seteaClientePedido;
    private Cliente clienteSeleccionado;
    ClientesTableModel tableModel;
    List<Cliente> listClientes;
    ClienteDAO clienteDAO;
    CiudadDAO ciudadDAO;
    Cliente cliente;

    public ClienteFrame() {
        initComponents();
        listClientes = new ArrayList<>();
        cargaComboCiudad();
        defineModelo();
        filtro();
        btnSeleccionaCliente.setVisible(false);

        btnExcluir.setVisible(false);
    }

    public ClienteFrame(RegistraPedido seteaClientePedido) {
        initComponents();

        btnExcluir.setVisible(false);
        defineModelo();
        this.seteaClientePedido = seteaClientePedido;
    }

    public Cliente getClie() {
        return clienteSeleccionado;
    }

    public void setClie(Cliente clie) {
        this.clienteSeleccionado = clie;
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

    private void defineModelo() {

        try {
            cbCondicionImpo.setModel(new DefaultComboBoxModel(CondicionImpositiva.values()));

            ((DefaultTableCellRenderer) tblCliente.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

            tableModel = new ClientesTableModel(listClientes);
            tblCliente.setModel(tableModel);

            ListSelectionModel listModel = tblCliente.getSelectionModel();
            listModel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (tblCliente.getSelectedRow() != -1) {
                        clienteSeleccionado = listClientes.get(tblCliente.getSelectedRow());
                        btnExcluir.setEnabled(true);
                        btnAlterar.setEnabled(true);
                        btnInActivar.setEnabled(true);
                        clienteDAO = new ClienteDAO();
                        clienteSeleccionado = (Cliente) clienteDAO.buscarPorID(Cliente.class, clienteSeleccionado.getId_cliente());
                        muestraDetalle();

                    } else {
                        clienteSeleccionado = null;
                        btnInActivar.setEnabled(false);
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
        chActivo.setSelected(clienteSeleccionado.getActivo());
        txtCel.setText(clienteSeleccionado.getCel());
        cbCiudad.setSelectedItem(clienteSeleccionado.getCiudad());
        txtDireccion.setText(clienteSeleccionado.getDireccion());
        txtDocumento.setText(clienteSeleccionado.getDocumento());
        txtEmail.setText(clienteSeleccionado.getEmail());
        txtNombre.setText(clienteSeleccionado.getNombre());
        txtRazonSocial.setText(clienteSeleccionado.getRazon_social());
        txtTelefono.setText(clienteSeleccionado.getTelefono());
        dpFecha.setDate(clienteSeleccionado.getFecha_ingreso());
        cbCondicionImpo.setSelectedItem(clienteSeleccionado.getCondicionImpositiva());

    }

    void filtro() {
        try {

            tblCliente.clearSelection();
            clienteDAO = new ClienteDAO();
            listClientes.clear();
            listClientes.addAll(clienteDAO.filtroInteligenteClientes2(chInaActivos.isSelected(), txtFiltro.getText()));
            tableModel.fireTableDataChanged();

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error);
            error.printStackTrace();
        }

    }

    private void NuevoCliente() {
        chActivo.setSelected(true);
        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del cliente!", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
        } else {

            try {
                cliente = new Cliente();

                cliente.setActivo(chActivo.isSelected());
                cliente.setNombre(txtNombre.getText());
                cliente.setCel(txtCel.getText());
                cliente.setCiudad((Ciudad) cbCiudad.getSelectedItem());
                cliente.setDireccion(txtDireccion.getText());
                cliente.setEmail(txtEmail.getText());
                cliente.setFecha_ingreso(dpFecha.getDate());
                cliente.setRazon_social(txtRazonSocial.getText());
                cliente.setDocumento(txtDocumento.getText());
                cliente.setTelefono(txtTelefono.getText());
                cliente.setCondicionImpositiva((CondicionImpositiva) cbCondicionImpo.getSelectedItem());

                clienteDAO = new ClienteDAO(cliente);
                clienteDAO.guardar();

                JOptionPane.showMessageDialog(null, "Cliente registrado correctamente!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar registros " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        }
    }

    private void EditarCliente() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();

        } else {
            try {
                clienteSeleccionado.setActivo(chActivo.isSelected());
                clienteSeleccionado.setNombre(txtNombre.getText());
                clienteSeleccionado.setCel(txtCel.getText());
                clienteSeleccionado.setCiudad((Ciudad) cbCiudad.getSelectedItem());
                clienteSeleccionado.setDireccion(txtDireccion.getText());
                clienteSeleccionado.setEmail(txtEmail.getText());
                clienteSeleccionado.setFecha_ingreso(dpFecha.getDate());
                clienteSeleccionado.setRazon_social(txtRazonSocial.getText());
                clienteSeleccionado.setDocumento(txtDocumento.getText());
                clienteSeleccionado.setTelefono(txtTelefono.getText());
                clienteSeleccionado.setCondicionImpositiva((CondicionImpositiva) cbCondicionImpo.getSelectedItem());

                clienteDAO = new ClienteDAO(clienteSeleccionado);
                clienteDAO.actualiza();

                JOptionPane.showMessageDialog(null, "Cliente editado correctamente!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar registros " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        }

    }

    private void eliminaCliente() {
        try {
            clienteDAO = new ClienteDAO(clienteSeleccionado);
            clienteDAO.elimina();
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No es posible eliminar el registro seleccionado " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    private void habilitaCampos() {
        chActivo.setEnabled(true);
        txtNombre.setEditable(true);
        txtDireccion.setEditable(true);
        cbCiudad.setEditable(true);
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
        tblCliente.setEnabled(false);
        cbCiudad.setEnabled(true);
        cbCiudad.setEditable(true);
        tblCliente.setVisible(false);
        btnCiudad.setEnabled(true);

    }

    private void desabilitaCampos() {
        chActivo.setEnabled(false);
        txtNombre.setEditable(false);
        txtDireccion.setEditable(false);
        cbCiudad.setEditable(false);
        cbCiudad.setEnabled(false);
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
        tblCliente.setEnabled(true);
        tblCliente.setVisible(true);
        btnCiudad.setEnabled(false);

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
        chActivo.setSelected(true);
        txtNombre.setText("");
        txtDireccion.setText("");
        //cbCiudad.setText("");
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
        cbCiudad = new javax.swing.JComboBox();
        btnCiudad = new javax.swing.JButton();
        chActivo = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        txtFiltro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        chInaActivos = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        btnSeleccionaCliente = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnInActivar = new javax.swing.JButton();

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
        jLabel1.setText("Clientes");
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

        cbCondicionImpo.setEnabled(false);
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

        chActivo.setText("Activo");
        chActivo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(chActivo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        txtFiltro.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtFiltro, gridBagConstraints);

        tblCliente.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCliente.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblCliente);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        jLabel14.setText("Filtrar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel14, gridBagConstraints);

        chInaActivos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        chInaActivos.setSelected(true);
        chInaActivos.setText("Ver Activos");
        chInaActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chInaActivosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel3.add(chInaActivos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSeleccionaCliente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSeleccionaCliente.setMnemonic('C');
        btnSeleccionaCliente.setText("Selecciona Cliente");
        btnSeleccionaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaClienteActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaCliente);

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

        btnInActivar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnInActivar.setText("Inactivar/Activar");
        btnInActivar.setEnabled(false);
        btnInActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInActivarActionPerformed(evt);
            }
        });
        jPanel4.add(btnInActivar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        clienteSeleccionado = null;
        limpiaCampos();
        habilitaCampos();
        habilitaBotoes();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (clienteSeleccionado == null) {
            NuevoCliente();
        } else {
            EditarCliente();
        }

        filtro();
        desabilitaBotoes();
        desabilitaCampos();

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaBotoes();
        desabilitaCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        if (tblCliente.getSelectedRow() != -1) {
            habilitaBotoes();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (tblCliente.getSelectedRow() != -1) {
            String Nombre = txtRazonSocial.getText();
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación del Cliente " + Nombre + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                eliminaCliente();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        filtro();
        limpiaCampos();

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        filtro();
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnSeleccionaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaClienteActionPerformed

        int filaSeleccionada = tblCliente.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente en la tabla!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            Cliente cliente = new Cliente();
            cliente.setId_cliente(Integer.parseInt(tblCliente.getValueAt(filaSeleccionada, 0).toString()));
            cliente.setNombre(tblCliente.getValueAt(filaSeleccionada, 1).toString());
            if (seteaClientePedido == null) {
                seteaClientePedido.agregarCliente(cliente);
                this.dispose();
                seteaClientePedido.toFront();
            }

        }


    }//GEN-LAST:event_btnSeleccionaClienteActionPerformed

    private void btnCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCiudadActionPerformed
        CiudadesDialog ciudadesFrame = new CiudadesDialog(null, true, this);
        ciudadesFrame.setVisible(true);
        ciudadesFrame.toFront();
        cargaComboCiudad();

    }//GEN-LAST:event_btnCiudadActionPerformed

    private void btnInActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInActivarActionPerformed

        if (clienteSeleccionado.getActivo() == true) {
            clienteSeleccionado.setActivo(false);
            DAOGenerico DAO = new DAOGenerico(clienteSeleccionado);
            DAO.actualiza();
            filtro();
            JOptionPane.showMessageDialog(this, "Inactivado correctamente", "Atención", JOptionPane.INFORMATION_MESSAGE);
        } else if (clienteSeleccionado.getActivo() == false) {
            clienteSeleccionado.setActivo(true);
            DAOGenerico DAO = new DAOGenerico(clienteSeleccionado);
            DAO.actualiza();
            filtro();
            JOptionPane.showMessageDialog(this, "Activado correctamente", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnInActivarActionPerformed

    private void chInaActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chInaActivosActionPerformed
        filtro();
    }//GEN-LAST:event_chInaActivosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCiudad;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnInActivar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaCliente;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCiudad;
    private javax.swing.JComboBox cbCondicionImpo;
    private javax.swing.JCheckBox chActivo;
    private javax.swing.JCheckBox chInaActivos;
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
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
