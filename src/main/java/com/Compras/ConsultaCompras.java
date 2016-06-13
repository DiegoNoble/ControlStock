package com.Compras;

import com.Beans.ArticulosCompra;
import com.Beans.FacturaCompra;
import com.DAO.ArticuloDAO;
import com.DAO.ArticulosCompraDAO;
import com.DAO.MovStockDAO;
import com.DAO.FacturaCompraDAO;
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

public final class ConsultaCompras extends javax.swing.JInternalFrame {

    ComprasTableModel tableModelCompras;
    ArticulosCompraTableModel tableModelArticulos;
    List<FacturaCompra> listCompras;
    List<ArticulosCompra> listArticulosCompra;
    FacturaCompra remitoSeleccionado;
    FacturaCompraDAO facturaCompraDAO;
    ArticulosCompraDAO articulosCompraDAO;
    ArticuloDAO articulosDAO;
    MovStockDAO movStockDAO;

    public ConsultaCompras() {
        initComponents();

        defineModelo();
        buscarPorFechas();
        //btnAnular.setVisible(false);
        btnAnular.setEnabled(false);
    }

    public void buscar() {
        facturaCompraDAO = new FacturaCompraDAO();
        if (rbNroDocumento.isSelected()) {
            try {
                tableModelCompras.agregar(facturaCompraDAO.buscarPor(FacturaCompra.class, "nroDocumento", txtFiltroCod.getText()));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        } else if (rbproveedor.isSelected()) {
            try {
                tableModelCompras.agregar(facturaCompraDAO.buscarPor(FacturaCompra.class, "provedor.razonSocial", txtFiltroCod.getText()));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }

        }
    }

    private void buscarPorFechas() {
        facturaCompraDAO = new FacturaCompraDAO();
        tableModelCompras.agregar(facturaCompraDAO.buscaEntreFechas(dpDesde.getDate(), dpHasta.getDate()));
    }

    private void buscaArticulos(FacturaCompra facturaCompra) {
        articulosCompraDAO = new ArticulosCompraDAO();
        tableModelArticulos.agregar(articulosCompraDAO.buscarPor(ArticulosCompra.class, "facturaCompra.id", facturaCompra.getId()));
    }

    private void defineModelo() {
        dpDesde.setDate(new Date());
        dpHasta.setDate(new Date());
        dpDesde.setFormats("dd/MM/yyyy");
        dpHasta.setFormats("dd/MM/yyyy");

        ((DefaultTableCellRenderer) tblArticulosCompra.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listArticulosCompra = new ArrayList<>();
        tableModelArticulos = new ArticulosCompraTableModel(listArticulosCompra);
        tblArticulosCompra.setModel(tableModelArticulos);

        ((DefaultTableCellRenderer) tblCompras.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listCompras = new ArrayList<FacturaCompra>();
        tableModelCompras = new ComprasTableModel(listCompras);
        tblCompras.setModel(tableModelCompras);
        tblCompras.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        int[] anchos = {20, 20, 20, 20, 20};

        for (int i = 0; i < tblCompras.getColumnCount(); i++) {

            tblCompras.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        ListSelectionModel listModel = tblCompras.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblCompras.getSelectedRow() != -1) {
                    remitoSeleccionado = listCompras.get(tblCompras.getSelectedRow());
                    buscaArticulos(remitoSeleccionado);
                } else {
                    remitoSeleccionado = null;
                    btnAnular.setEnabled(false);
                }
            }
        });

    }

    /*  private void anularFacturaCompra() {

     try {
     Double mov = 0.0;
     Double valorUnitario = 0.0;
     Double importeFacturaCompra = 0.0;
     List<MovStock> listMovStock = new ArrayList<>();
     Pedido pedido = remitoSeleccionado.getPedido();
     for (ArticulosPedido articulosPedido : pedido.getArticulosPedido()) {

     if (articulosPedido.getCantPendiente() - articulosPedido.getCantPedida() != 0) {
     mov = (articulosPedido.getCantPedida() * -1);

     valorUnitario = articulosPedido.getArticulo().getValor_venta();
     importeFacturaCompra = importeFacturaCompra + valorUnitario * mov;
     articulosPedido.setCantAtendida(articulosPedido.getCantPedida() + mov);
     articulosPedido.setCantPendiente(articulosPedido.getCantPedida());

     articulosPedido.setImportePendiente(articulosPedido.getImportePedido());
     articulosPedido.setImporteAtendido(articulosPedido.getImporteAtendido() - articulosPedido.getImportePedido());
     articulosCompraDAO = new ArticulosPedidoDAO(articulosPedido);
     articulosCompraDAO.actualiza();

     MovStock movStock = new MovStock();
     movStock.setArticulo(articulosPedido.getArticulo());
     movStock.setCantidadMov(-mov);
     movStock.setSaldoStock(articulosPedido.getArticulo().getCantidad() + (-mov));
     movStock.setFecha(new Date());
     listMovStock.add(movStock);
     }
     }
     pedido.setImportePendiente(pedido.getImporteTotal());
     pedido.setEstadoPedido(SituacionPedido.CON_REMITO_ANULADO);
     pedido.setImporteAtendido(pedido.getImporteAtendido() + importeFacturaCompra);

     pedidoDAO = new PedidoDAO(pedido);
     pedidoDAO.actualiza();

     FacturaCompra remito = new FacturaCompra();
     remito.setFecha(new Date());
     remito.setImporteFacturaCompra(importeFacturaCompra);
     remito.setPedido(pedido);
     remito.setTipoFacturaCompra(TipoFacturaCompra.CONTRA_REMITO);
     remito.setAnulado(true);
     facturaCompraDAO = new FacturaCompraDAO(remito);
     facturaCompraDAO.guardar();
     remitoSeleccionado.setAnulado(true);
     facturaCompraDAO = new FacturaCompraDAO(remitoSeleccionado);
     facturaCompraDAO.actualiza();

     for (MovStock movStock : listMovStock) {
     movStock.setFacturaCompra(remito);
     movStockDAO = new MovStockDAO(movStock);
     movStockDAO.guardar();

     Articulo articulo = movStock.getArticulo();
     articulo.setCantidad(movStock.getSaldoStock());
     articulosDAO = new ArticuloDAO(articulo);
     articulosDAO.actualiza();

     }
     JOptionPane.showMessageDialog(this, "FacturaCompra generado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
     //this.consultaPedido.buscar();
     //this.dispose();
     facturaCompraDAO = new FacturaCompraDAO();
     facturaCompraDAO.imprimeContraFacturaCompra(remito);

     } catch (Exception ex) {

     JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
     ex.printStackTrace();
     }
     }*/
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
        rbNroDocumento = new javax.swing.JRadioButton();
        rbproveedor = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCompras = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblArticulosCompra = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnBuscarPorFechas = new javax.swing.JButton();
        dpHasta = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        dpDesde = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnAnular = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(600, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consulta Compras");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Buscar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(jLabel7, gridBagConstraints);

        txtFiltroCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroCodActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(txtFiltroCod, gridBagConstraints);

        buttonGroup1.add(rbNroDocumento);
        rbNroDocumento.setSelected(true);
        rbNroDocumento.setText("Nro Documento");
        rbNroDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNroDocumentoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel11.add(rbNroDocumento, gridBagConstraints);

        buttonGroup1.add(rbproveedor);
        rbproveedor.setText("Proveedor");
        rbproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbproveedorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel11.add(rbproveedor, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        jPanel11.add(btnBuscar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jPanel11, gridBagConstraints);

        jPanel12.setLayout(new java.awt.GridBagLayout());

        tblCompras.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblCompras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblCompras);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel12.add(jScrollPane1, gridBagConstraints);

        tblArticulosCompra.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblArticulosCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblArticulosCompra.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblArticulosCompra);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel12.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jPanel12, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnBuscarPorFechas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnBuscarPorFechas.setBorder(null);
        btnBuscarPorFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPorFechasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnBuscarPorFechas, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpHasta, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("hasta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpDesde, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Buscar entre fechas, desde");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnAnular.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/delete_32.png"))); // NOI18N
        btnAnular.setMnemonic('R');
        btnAnular.setText("Anuar");
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        jPanel4.add(btnAnular);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbNroDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNroDocumentoActionPerformed

    }//GEN-LAST:event_rbNroDocumentoActionPerformed

    private void rbproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbproveedorActionPerformed


    }//GEN-LAST:event_rbproveedorActionPerformed

    private void txtFiltroCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroCodActionPerformed
        buscar();
    }//GEN-LAST:event_txtFiltroCodActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnBuscarPorFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPorFechasActionPerformed

        buscarPorFechas();
    }//GEN-LAST:event_btnBuscarPorFechasActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
    }//GEN-LAST:event_btnAnularActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarPorFechas;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.jdesktop.swingx.JXDatePicker dpDesde;
    private org.jdesktop.swingx.JXDatePicker dpHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton rbNroDocumento;
    private javax.swing.JRadioButton rbproveedor;
    private javax.swing.JTable tblArticulosCompra;
    private javax.swing.JTable tblCompras;
    private javax.swing.JTextField txtFiltroCod;
    // End of variables declaration//GEN-END:variables

}
