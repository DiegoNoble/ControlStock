package com.Pedidos;

import Utilidades.ControlarEntradaTexto;
import com.Articulos.ArticulosFrame;
import com.Beans.Pedido;
import com.Beans.ArticulosPedido;
import com.Beans.Articulo;
import com.Clientes.ClienteFrame;
import com.Beans.Cliente;
import com.Beans.SituacionPedido;
import com.Beans.Vendedor;
import com.DAO.ArticuloDAO;
import com.DAO.ArticulosPedidoDAO;
import com.DAO.ClienteDAO;
import com.DAO.PedidoDAO;
import com.DAO.RemitoDAO;
import com.DAO.VendedorDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class RegistraPedido extends javax.swing.JInternalFrame {

    List<Cliente> listClientes;
    List<Vendedor> listVendedores;
    List<Articulo> listArticulos;
    ArticulosPedidoTableModel tableModel;
    List<ArticulosPedido> listArticulosPedido;
    Pedido pedido;
    ArticuloDAO articulosDAO;
    ClienteDAO clienteDAO;
    VendedorDAO vendedorDAO;
    ArticulosPedidoDAO articulosPedidoDAO;
    PedidoDAO pedidoDAO;
    RemitoDAO remitoDAO;
    double subTotal;
    double IVA;
    double redondeo;

    public RegistraPedido() {
        initComponents();

        dpFecha.setDate(new Date());

        defineModelo();

        AutoCompleteDecorator.decorate(cbCliente);
        AutoCompleteDecorator.decorate(cbVendedor);
        AutoCompleteDecorator.decorate(cbArticulos);
        cargaComboArticulos();
        cargaComboClientes();
        cargaComboVendedor();

    }

    private void cargaComboClientes() {

        try {
            listClientes = new ArrayList();

            clienteDAO = new ClienteDAO();
            listClientes = clienteDAO.BuscaTodos(Cliente.class);

            cbCliente.removeAllItems();

            for (Cliente clientes : listClientes) {

                cbCliente.addItem(clientes);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void cargaComboVendedor() {

        try {
            listVendedores = new ArrayList();

            vendedorDAO = new VendedorDAO();
            listVendedores = vendedorDAO.BuscaTodos(Vendedor.class);

            cbVendedor.removeAllItems();

            for (Vendedor vendedor : listVendedores) {

                cbVendedor.addItem(vendedor);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar vendedores" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    void cargaComboArticulos() {
        try {
            articulosDAO = new ArticuloDAO();

            cbArticulos.removeAllItems();
            listArticulos = articulosDAO.BuscaTodos(Articulo.class);

            for (Articulo articulo : listArticulos) {

                cbArticulos.addItem(articulo);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void defineModelo() {

        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        txtCantidad.setDocument(new ControlarEntradaTexto(10, chs));

        ((DefaultTableCellRenderer) tblArticulosPedido.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listArticulosPedido = new ArrayList<ArticulosPedido>();
        tableModel = new ArticulosPedidoTableModel(listArticulosPedido, txtTotal);

        tblArticulosPedido.setModel(tableModel);

        int[] anchos = {5, 100, 200, 20, 20, 50};

        for (int i = 0; i < tblArticulosPedido.getColumnCount(); i++) {

            tblArticulosPedido.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        ListSelectionModel listModel = tblArticulosPedido.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblArticulosPedido.getSelectedRow() != -1) {
                    btnEliminar.setEnabled(true);
                } else {
                    btnEliminar.setEnabled(false);
                }
            }
        });

    }

    private void confirmaPedido() {

        try {
            if (listArticulosPedido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un articulo", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                pedido = new Pedido(dpFecha.getDate(), (Cliente) cbCliente.getSelectedItem(),
                        (Vendedor) cbVendedor.getSelectedItem(), SituacionPedido.NUEVO, listArticulosPedido);
                pedido.setObservaciones(txtObservaciones.getText());
                Double total = 0.0;
                for (ArticulosPedido articulosPedido : listArticulosPedido) {
                    articulosPedido.setPedido(pedido);
                    total = total + articulosPedido.getImportePedido();
                }
                pedido.setImporteTotal(total);
                pedido.setImportePendiente(total);
                pedidoDAO = new PedidoDAO(pedido);
                pedidoDAO.guardar();

            }
            JOptionPane.showMessageDialog(this, "Venta realizada correctamente!", "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
        }
    }

    void limbiaCampo() {
        txtCantidad.setText("");
        txtTotal.setText("");
        txtObservaciones.setText("");
    }

    private void retirarArticulo() {

        if (tblArticulosPedido.getSelectedRow() != -1) {
            tableModel.eliminar(tblArticulosPedido.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item da lista!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void agregarArticuloPedido(Articulo articulo) {
        if (listArticulos.contains(articulo)) {

            cbArticulos.setSelectedItem(articulo);
        } else {

            cargaComboArticulos();
            cbArticulos.setSelectedItem(articulo);
        }
    }

    public void agregarVendedor(Vendedor vendedor) {
        if (listVendedores.contains(vendedor)) {
            cbVendedor.setSelectedItem(vendedor);
        } else {
            cbVendedor.addItem(vendedor);
            cbVendedor.setSelectedItem(vendedor);
        }
    }

    public void agregarCliente(Cliente cliente) {
        if (listClientes.contains(cliente)) {
            cbCliente.setSelectedItem(cliente);
        } else {
            cbCliente.addItem(cliente);
            cbCliente.setSelectedItem(cliente);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        btnSelecionaCliente1 = new javax.swing.JButton();
        cbVendedor = new javax.swing.JComboBox();
        btnSelecionaCliente = new javax.swing.JButton();
        cbCliente = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulosPedido = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        cbArticulos = new javax.swing.JComboBox();
        txtCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnNuevo1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        btnRegistraVenta = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();

        jTextField1.setText("jTextField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(1024, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Registrar Pedido");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel11.setLayout(new java.awt.GridBagLayout());

        btnSelecionaCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnSelecionaCliente1.setBorder(null);
        btnSelecionaCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaCliente1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(btnSelecionaCliente1, gridBagConstraints);

        cbVendedor.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(cbVendedor, gridBagConstraints);

        btnSelecionaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnSelecionaCliente.setBorder(null);
        btnSelecionaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(btnSelecionaCliente, gridBagConstraints);

        cbCliente.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(cbCliente, gridBagConstraints);

        jLabel6.setText("Vendedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(jLabel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel3.add(jPanel11, gridBagConstraints);

        jPanel12.setLayout(new java.awt.GridBagLayout());

        tblArticulosPedido.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblArticulosPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblArticulosPedido.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblArticulosPedido);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel12.add(jScrollPane1, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnBuscar.setToolTipText("Buscar articulo");
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel13.add(btnBuscar, gridBagConstraints);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/add.png"))); // NOI18N
        btnNuevo.setToolTipText("Nueva posición");
        btnNuevo.setBorder(null);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel13.add(btnNuevo, gridBagConstraints);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/delete.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar posición");
        btnEliminar.setBorder(null);
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel13.add(btnEliminar, gridBagConstraints);

        cbArticulos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(cbArticulos, gridBagConstraints);

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtCantidad, gridBagConstraints);

        jLabel5.setText("Articulo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel13.add(jLabel5, gridBagConstraints);

        jLabel8.setText("Cantidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel13.add(jLabel8, gridBagConstraints);

        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/add.png"))); // NOI18N
        btnNuevo1.setToolTipText("Nueva posición");
        btnNuevo1.setBorder(null);
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel13.add(btnNuevo1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel12.add(jPanel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jPanel12, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Fecha del pedido");
        jPanel10.add(jLabel3);
        jPanel10.add(dpFecha);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel3.add(jPanel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel10.setText("Total pedido");
        jPanel8.add(jLabel10, new java.awt.GridBagConstraints());

        txtTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$ #,##0.00"))));
        txtTotal.setEnabled(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 140;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel8.add(txtTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jPanel8, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnRegistraVenta.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnRegistraVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/pedido.png"))); // NOI18N
        btnRegistraVenta.setMnemonic('R');
        btnRegistraVenta.setText("Registrar Pedido");
        btnRegistraVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraVentaActionPerformed(evt);
            }
        });
        jPanel4.add(btnRegistraVenta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jPanel4, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Observaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel2.setLayout(new java.awt.GridBagLayout());

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtObservaciones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaClienteActionPerformed

        ClienteFrame clienteFrame = new ClienteFrame(this);
        this.getDesktopPane().add(clienteFrame);
        clienteFrame.setVisible(true);
        clienteFrame.toFront();

    }//GEN-LAST:event_btnSelecionaClienteActionPerformed

    private void btnRegistraVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraVentaActionPerformed

        confirmaPedido();
        listArticulosPedido.clear();
        tableModel.fireTableDataChanged();


    }//GEN-LAST:event_btnRegistraVentaActionPerformed

    private void btnSelecionaCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaCliente1ActionPerformed


    }//GEN-LAST:event_btnSelecionaCliente1ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        ArticulosFrame articulosFrame = new ArticulosFrame(this);
        this.getDesktopPane().add(articulosFrame);
        articulosFrame.setVisible(true);
        articulosFrame.toFront();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        Articulo articulo = (Articulo) cbArticulos.getSelectedItem();
        Double cantidad = Double.valueOf(txtCantidad.getText());
        Double total = cantidad * articulo.getValor_venta();
        tableModel.agregar(new ArticulosPedido(listArticulosPedido.size() + 1, articulo, cantidad, total));

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        tableModel.eliminar(tblArticulosPedido.getSelectedRow());

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed

        Articulo articulo = (Articulo) cbArticulos.getSelectedItem();
        Double cantidad = Double.valueOf(txtCantidad.getText());
        Double total = cantidad * articulo.getValor_venta();
        tableModel.agregar(new ArticulosPedido(listArticulosPedido.size() + 1, articulo, cantidad, total));


    }//GEN-LAST:event_txtCantidadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JButton btnRegistraVenta;
    private javax.swing.JButton btnSelecionaCliente;
    private javax.swing.JButton btnSelecionaCliente1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbArticulos;
    private javax.swing.JComboBox cbCliente;
    private javax.swing.JComboBox cbVendedor;
    private org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblArticulosPedido;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JFormattedTextField txtTotal;
    // End of variables declaration//GEN-END:variables

}
