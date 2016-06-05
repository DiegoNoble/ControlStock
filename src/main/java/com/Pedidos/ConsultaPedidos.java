package com.Pedidos;

import com.Beans.Pedido;
import com.Beans.Articulo;
import com.Beans.Cliente;
import com.Beans.Vendedor;
import com.DAO.ArticuloDAO;
import com.DAO.ArticulosPedidoDAO;
import com.DAO.ClienteDAO;
import com.DAO.PedidoDAO;
import com.DAO.VendedorDAO;
import com.Renderers.MeDateCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public final class ConsultaPedidos extends javax.swing.JInternalFrame {

    List<Cliente> listClientes;
    List<Vendedor> listVendedores;
    List<Articulo> listArticulos;
    PedidosTableModel tableModel;
    List<Pedido> listPedidos;
    Pedido pedidoSeleccionado;
    static Pedido pedidoSingleton;
    ArticuloDAO articulosDAO;
    ClienteDAO clienteDAO;
    VendedorDAO vendedorDAO;
    ArticulosPedidoDAO articulosPedidoDAO;
    PedidoDAO pedidoDAO;

    public ConsultaPedidos() {
        initComponents();

        dpFecha.setDate(new Date());

        defineModelo();

        btnAtenderPedido.setEnabled(false);
    }

    public static Pedido getInstance() {

        if (pedidoSingleton == null) {
            pedidoSingleton = new Pedido();
        }

        return pedidoSingleton;
    }

    /*void filtro() {
     if (rbCodPedido.isSelected()) {
     pedidoDAO = new PedidoDAO();
     List codPedidos = pedidoDAO.buscarIdsPedidos();
     AutoCompleteDecorator.decorate(txtFiltroCod, codPedidos, true);
     } else if (rbCliente.isSelected()) {
     clienteDAO = new ClienteDAO();
     List clientes = clienteDAO.BuscaTodos(Cliente.class);
     AutoCompleteDecorator.decorate(txtFiltroCod, clientes, true);
     } else if (rbVendedor.isSelected()) {
     vendedorDAO = new VendedorDAO();
     List vendedores = vendedorDAO.BuscaTodos(Vendedor.class);
     AutoCompleteDecorator.decorate(txtFiltroCod, vendedores, true);
     }

     }*/
    void buscar() {
        pedidoDAO = new PedidoDAO();
        if (rbCodPedido.isSelected()) {
            listPedidos.clear();
            listPedidos.addAll(pedidoDAO.buscarPor(Pedido.class, "id", txtFiltroCod.getText()));
            tableModel.fireTableDataChanged();
        } else if (rbCliente.isSelected()) {
            listPedidos.clear();
            listPedidos.addAll(pedidoDAO.buscarPor(Pedido.class, "cliente.nombre", txtFiltroCod.getText()));
            tableModel.fireTableDataChanged();
        } else if (rbVendedor.isSelected()) {
            listPedidos.clear();
            listPedidos.addAll(pedidoDAO.buscarPor(Pedido.class, "vendedor.nombre", txtFiltroCod.getText()));
            tableModel.fireTableDataChanged();
        }

    }

    private void defineModelo() {
        ((DefaultTableCellRenderer) tblPedidos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listPedidos = new ArrayList<Pedido>();
        tableModel = new PedidosTableModel(listPedidos);
        tblPedidos.setModel(tableModel);
        tblPedidos.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        int[] anchos = {20, 20, 20, 100, 100, 20};

        for (int i = 0; i < tblPedidos.getColumnCount(); i++) {

            tblPedidos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        ListSelectionModel listModel = tblPedidos.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblPedidos.getSelectedRow() != -1) {
                    btnAtenderPedido.setEnabled(true);
                    pedidoSeleccionado = listPedidos.get(tblPedidos.getSelectedRow());
                } else {
                    btnAtenderPedido.setEnabled(false);
                    pedidoSeleccionado = null;
                }
            }
        });

        tblPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    atenderPedido();
                }
            }
        });

    }

    private void atenderPedido() {

        try {
            if (pedidoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                AtenderPedido atenderPedido = new AtenderPedido(pedidoSeleccionado);
                super.getDesktopPane().add(atenderPedido);
                atenderPedido.setVisible(true);
                atenderPedido.toFront();
                buscar();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
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
        jLabel7 = new javax.swing.JLabel();
        txtFiltroCod = new javax.swing.JTextField();
        rbCodPedido = new javax.swing.JRadioButton();
        rbCliente = new javax.swing.JRadioButton();
        rbVendedor = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        jPanel4 = new javax.swing.JPanel();
        btnAtenderPedido = new javax.swing.JButton();

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
        jLabel1.setText("Pedidos");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel7.setText("Buscar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(jLabel7, gridBagConstraints);

        txtFiltroCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroCodActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(txtFiltroCod, gridBagConstraints);

        buttonGroup1.add(rbCodPedido);
        rbCodPedido.setSelected(true);
        rbCodPedido.setText("Cod Pedido");
        rbCodPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCodPedidoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel11.add(rbCodPedido, gridBagConstraints);

        buttonGroup1.add(rbCliente);
        rbCliente.setText("Cliente");
        rbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbClienteActionPerformed(evt);
            }
        });
        jPanel11.add(rbCliente, new java.awt.GridBagConstraints());

        buttonGroup1.add(rbVendedor);
        rbVendedor.setText("Vendedor");
        rbVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbVendedorActionPerformed(evt);
            }
        });
        jPanel11.add(rbVendedor, new java.awt.GridBagConstraints());

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jPanel11.add(btnBuscar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel3.add(jPanel11, gridBagConstraints);

        jPanel12.setLayout(new java.awt.GridBagLayout());

        tblPedidos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblPedidos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblPedidos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel12.add(jScrollPane1, gridBagConstraints);

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnAtenderPedido.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnAtenderPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/pedido.png"))); // NOI18N
        btnAtenderPedido.setMnemonic('R');
        btnAtenderPedido.setText("Atender pedido");
        btnAtenderPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderPedidoActionPerformed(evt);
            }
        });
        jPanel4.add(btnAtenderPedido);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtenderPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderPedidoActionPerformed

        atenderPedido();

    }//GEN-LAST:event_btnAtenderPedidoActionPerformed

    private void rbCodPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodPedidoActionPerformed

    }//GEN-LAST:event_rbCodPedidoActionPerformed

    private void rbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbClienteActionPerformed


    }//GEN-LAST:event_rbClienteActionPerformed

    private void rbVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbVendedorActionPerformed

    }//GEN-LAST:event_rbVendedorActionPerformed

    private void txtFiltroCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroCodActionPerformed
        buscar();
    }//GEN-LAST:event_txtFiltroCodActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtenderPedido;
    private javax.swing.JButton btnBuscar;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton rbCliente;
    private javax.swing.JRadioButton rbCodPedido;
    private javax.swing.JRadioButton rbVendedor;
    private javax.swing.JTable tblPedidos;
    private javax.swing.JTextField txtFiltroCod;
    // End of variables declaration//GEN-END:variables

}
