package com.Pedidos;

import com.Beans.Pedido;
import com.Beans.ArticulosPedido;
import com.Beans.Articulo;
import com.Beans.Cliente;
import com.Beans.MovStock;
import com.Beans.Remito;
import com.Beans.SituacionPedido;
import com.Beans.Vendedor;
import com.DAO.ArticuloDAO;
import com.DAO.ArticulosPedidoDAO;
import com.DAO.ClienteDAO;
import com.DAO.MovStockDAO;
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

public class AtenderPedido extends javax.swing.JInternalFrame {

    List<Cliente> listClientes;
    List<Vendedor> listVendedores;
    List<Articulo> listArticulos;
    AtencionArticulosPedidoTableModel tableModel;
    List<ArticulosPedido> listArticulosPedido;
    Pedido pedido;
    ArticuloDAO articulosDAO;
    ClienteDAO clienteDAO;
    VendedorDAO vendedorDAO;
    ArticulosPedidoDAO articulosPedidoDAO;
    PedidoDAO pedidoDAO;
    RemitoDAO remitoDAO;
    MovStockDAO movStockDAO;
    ConsultaPedidos consultaPedido;

    public AtenderPedido(ConsultaPedidos consultaPedido, Pedido pedido) {
        initComponents();
        this.pedido = pedido;
        this.consultaPedido = consultaPedido;
        listArticulosPedido = pedido.getArticulosPedido();
        dpFecha.setDate(pedido.getFecha());
        cbCliente.addItem(pedido.getCliente());
        cbCliente.setSelectedItem(pedido.getCliente());
        cbVendedor.addItem(pedido.getVendedor());
        cbVendedor.setSelectedItem(pedido.getVendedor());
        defineModelo();

    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblArticulosPedido.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tableModel = new AtencionArticulosPedidoTableModel(listArticulosPedido);
        tblArticulosPedido.setModel(tableModel);

        int[] anchos = {5, 100, 200, 20, 20, 20};

        for (int i = 0; i < tblArticulosPedido.getColumnCount(); i++) {

            tblArticulosPedido.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        ListSelectionModel listModel = tblArticulosPedido.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblArticulosPedido.getSelectedRow() != -1) {
                } else {
                }
            }
        });

    }

    private void atenderPedido() {

        try {
            if (listArticulosPedido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un articulo", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Double mov = 0.0;
                Double valorUnitario = 0.0;
                Double importeRemito = 0.0;
                List<MovStock> listMovStock = new ArrayList<>();
                for (ArticulosPedido articulosPedido : listArticulosPedido) {

                    if (articulosPedido.getCantPendiente() - articulosPedido.getCantPedida() != 0) {
                        mov = articulosPedido.getCantPedida() - articulosPedido.getCantPendiente() - articulosPedido.getCantAtendida();

                        valorUnitario = articulosPedido.getArticulo().getValor_venta();
                        importeRemito = importeRemito + valorUnitario * mov;
                        articulosPedido.setCantAtendida(articulosPedido.getCantPedida() - articulosPedido.getCantPendiente());

                        articulosPedido.setImportePendiente(articulosPedido.getCantPendiente() * valorUnitario);
                        articulosPedido.setImporteAtendido(articulosPedido.getCantAtendida() * valorUnitario);
                        articulosPedidoDAO = new ArticulosPedidoDAO(articulosPedido);
                        articulosPedidoDAO.actualiza();

                        MovStock movStock = new MovStock();
                        movStock.setArticulo(articulosPedido.getArticulo());
                        movStock.setCantidadMov(mov * -1);
                        movStock.setSaldoStock(articulosPedido.getArticulo().getCantidad() + (mov * -1));
                        movStock.setFecha(new Date());
                        listMovStock.add(movStock);
                    }
                }
                pedido.setImportePendiente(pedido.getImporteTotal() - importeRemito);
                if (pedido.getImportePendiente() != pedido.getImporteTotal()) {
                    pedido.setEstadoPedido(SituacionPedido.ATENDIDO);
                    pedido.setImporteAtendido(pedido.getImporteAtendido() + importeRemito);

                    pedidoDAO = new PedidoDAO(pedido);
                    pedidoDAO.actualiza();

                    Remito remito = new Remito();
                    remito.setFecha(new Date());
                    remito.setImporteRemito(importeRemito);
                    remito.setPedido(pedido);
                    remitoDAO = new RemitoDAO(remito);
                    remitoDAO.guardar();

                    for (MovStock movStock : listMovStock) {
                        movStock.setRemito(remito);
                        movStockDAO = new MovStockDAO(movStock);
                        movStockDAO.guardar();

                        Articulo articulo = movStock.getArticulo();
                        articulo.setCantidad(movStock.getSaldoStock());
                        articulosDAO = new ArticuloDAO(articulo);
                        articulosDAO.actualiza();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se atendio ninguna posición del pedido", "Atención", JOptionPane.ERROR_MESSAGE);
                }

            }
            JOptionPane.showMessageDialog(this, "Remito generado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
            ex.printStackTrace();
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
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        jPanel13 = new javax.swing.JPanel();
        btnTotales = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnRegistraVenta = new javax.swing.JButton();

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
        jLabel1.setText("Atender Pedido");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
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

        dpFecha.setEnabled(false);
        jPanel10.add(dpFecha);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel3.add(jPanel10, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        btnTotales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/accept.png"))); // NOI18N
        btnTotales.setToolTipText("Nueva posición");
        btnTotales.setBorder(null);
        btnTotales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel13.add(btnTotales, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Atender totales pendientes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jPanel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnRegistraVenta.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnRegistraVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/pedido.png"))); // NOI18N
        btnRegistraVenta.setMnemonic('R');
        btnRegistraVenta.setText("Generar remito");
        btnRegistraVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraVentaActionPerformed(evt);
            }
        });
        jPanel4.add(btnRegistraVenta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaClienteActionPerformed


    }//GEN-LAST:event_btnSelecionaClienteActionPerformed

    private void btnRegistraVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraVentaActionPerformed

        atenderPedido();
        this.consultaPedido.buscar();
        this.dispose();

    }//GEN-LAST:event_btnRegistraVentaActionPerformed

    private void btnSelecionaCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaCliente1ActionPerformed


    }//GEN-LAST:event_btnSelecionaCliente1ActionPerformed

    private void btnTotalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalesActionPerformed
        for (ArticulosPedido articulosPedido : listArticulosPedido) {
            articulosPedido.setCantPendiente(0.0);

        }
        tableModel.fireTableDataChanged();
    }//GEN-LAST:event_btnTotalesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistraVenta;
    private javax.swing.JButton btnSelecionaCliente;
    private javax.swing.JButton btnSelecionaCliente1;
    private javax.swing.JButton btnTotales;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCliente;
    private javax.swing.JComboBox cbVendedor;
    private org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblArticulosPedido;
    // End of variables declaration//GEN-END:variables

}
