package com.CuentasPagar;

import com.DAO.DAOGenerico;
import com.Beans.MonedaEnum;
import com.Beans.FacturaCompra;
import com.DAO.FacturaCompraDAO;
import com.Beans.Proveedor;
import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class RegistraPagos extends javax.swing.JInternalFrame {

    private DefaultTableModel tableModelFacturas;
    private DefaultTableModel tableModelFacturasAPagar;
    private List<FacturaCompra> listFacturasPendientes;
    private List<FacturaCompra> listFacturasAPagar;
    private FacturaCompraDAO facturaCompraDAO;
    private List<Proveedor> listProveedores;

    public RegistraPagos() {
        initComponents();
        facturaCompraDAO = new FacturaCompraDAO();
        listFacturasAPagar = new ArrayList();

        defineModelo();
        cargaCombos();
        AutoCompleteDecorator.decorate(cbProveedores);
        AutoCompleteDecorator.decorate(cbMoneda);

    }

    private void cargaCombos() {


        DefaultComboBoxModel modelo = new DefaultComboBoxModel(MonedaEnum.values());
        cbMoneda.setModel(modelo);

        listProveedores = new DAOGenerico().BuscaTodos(Proveedor.class);

        try {
            for (Proveedor proveedor : listProveedores) {
                cbProveedores.addItem(proveedor);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblFacturasPendientes.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) tblFacturasAPagar.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tableModelFacturas = (DefaultTableModel) tblFacturasPendientes.getModel();
        tableModelFacturasAPagar = (DefaultTableModel) tblFacturasAPagar.getModel();

    }

    public void buscaFacturasPendientes() {


        Proveedor proveedorSeleccionado = (Proveedor) cbProveedores.getSelectedItem();
        if (proveedorSeleccionado == null) {
            return;
        }
        try {
            MonedaEnum moneda = (MonedaEnum) cbMoneda.getSelectedItem();
            listFacturasPendientes = facturaCompraDAO.buscaFacturasPendientesPorMoneda(proveedorSeleccionado, moneda);

        } catch (Exception ex) {
            Logger.getLogger(RegistraPagos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void actualizatblFacturasPendientes() {
        
        
        remueveFacturasPendientes();

        Double total = 0.0;
        for (FacturaCompra facturaCompra : listFacturasPendientes) {

            total = total + facturaCompra.getTotal();


            tableModelFacturas.addRow(new Object[]{
                        facturaCompra.getNroFactura(),
                        facturaCompra.getSerieFactura(),
                        facturaCompra.getFecha(),
                        facturaCompra.getMoneda(),
                        facturaCompra.getTotal()});
        }
      
            txtSaldo.setValue(total);
       

    }

    private void actualizatblFacturasAPagar() {
        try {

            remueveFacturasAPagar();
            Double total = 0.0;
            for (FacturaCompra facturaCompra : listFacturasAPagar) {

                total = total + facturaCompra.getTotal();
                tableModelFacturasAPagar.addRow(new Object[]{
                            facturaCompra.getNroFactura(),
                            facturaCompra.getSerieFactura(),
                            facturaCompra.getFecha(),
                            facturaCompra.getMoneda(),
                            facturaCompra.getTotal(),});
            }
            txtTotal.setValue(total);

        } catch (Exception ex) {
            Logger.getLogger(RegistraPagos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void remueveFacturasPendientes() {
        DefaultTableModel modelo = (DefaultTableModel) tblFacturasPendientes.getModel();
        int numeroLineas = tblFacturasPendientes.getRowCount();
        for (int i = 0; i < numeroLineas; i++) {
            modelo.removeRow(0);
        }
    }

    public void remueveFacturasAPagar() {
        DefaultTableModel modelo = (DefaultTableModel) tblFacturasAPagar.getModel();
        int numeroLineas = tblFacturasAPagar.getRowCount();
        for (int i = 0; i < numeroLineas; i++) {
            modelo.removeRow(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbProveedores = new javax.swing.JComboBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblFacturasAPagar = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblFacturasPendientes = new javax.swing.JTable();
        btnCargar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JFormattedTextField();
        btnRetirar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbMoneda = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        btnRegistrarPago = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(900, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cuentas a pagar");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel2.setText("Proveedor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel3.add(jLabel2, gridBagConstraints);

        cbProveedores.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cbProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProveedoresActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbProveedores, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        tblFacturasAPagar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblFacturasAPagar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. Documento", "Serie", "Fecha", "Moneda", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblFacturasAPagar);
        tblFacturasAPagar.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturasAPagar.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturasAPagar.getColumnModel().getColumn(2).setCellRenderer(new MyDateCellRenderer());
        tblFacturasAPagar.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturasAPagar.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 120;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 50.0;
        jPanel4.add(jScrollPane4, gridBagConstraints);

        jTabbedPane1.addTab("Facturas a pagar", jPanel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel3.add(jTabbedPane1, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        tblFacturasPendientes.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblFacturasPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. Documento", "Serie", "Fecha", "Moneda", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFacturasPendientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tblFacturasPendientes);
        tblFacturasPendientes.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturasPendientes.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturasPendientes.getColumnModel().getColumn(2).setCellRenderer(new MyDateCellRenderer());
        tblFacturasPendientes.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturasPendientes.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 120;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 50.0;
        jPanel5.add(jScrollPane3, gridBagConstraints);

        jTabbedPane2.addTab("Facturas Pendientes", jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel3.add(jTabbedPane2, gridBagConstraints);

        btnCargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/images.jpg"))); // NOI18N
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnCargar, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        jLabel3.setText("Total del Pago:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel6.add(jLabel3, gridBagConstraints);

        txtTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel3.add(jPanel6, gridBagConstraints);

        btnRetirar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/flechaArriba.jpg"))); // NOI18N
        btnRetirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetirarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnRetirar, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel4.setText("Moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel3.add(jLabel4, gridBagConstraints);

        cbMoneda.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cbMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonedaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbMoneda, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        jLabel6.setText("Saldo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel7.add(jLabel6, gridBagConstraints);

        txtSaldo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSaldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtSaldo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel3.add(jPanel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        btnRegistrarPago.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnRegistrarPago.setText("Registrar pago");
        btnRegistrarPago.setToolTipText("Para registrar un pago, seleccione la cuota que desea pagar en la tabla \"Pagos\" y luego presione \"Registrar Pago\"");
        btnRegistrarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarPagoActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegistrarPago);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarPagoActionPerformed

        ConfirmaPago pago = new ConfirmaPago(null, true, listFacturasAPagar,this, txtTotal.getValue());
        pago.setVisible(true);
        pago.toFront();

    }//GEN-LAST:event_btnRegistrarPagoActionPerformed

    private void cbProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProveedoresActionPerformed

        listFacturasAPagar.clear();
        buscaFacturasPendientes();
        actualizatblFacturasPendientes();
        actualizatblFacturasAPagar();

    }//GEN-LAST:event_cbProveedoresActionPerformed

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed

        if (tblFacturasPendientes.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una factura en la grilla de Pendientes", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            FacturaCompra facturaSeleccionada = listFacturasPendientes.get(tblFacturasPendientes.getSelectedRow());
            listFacturasPendientes.remove(tblFacturasPendientes.getSelectedRow());
            actualizatblFacturasPendientes();
            listFacturasAPagar.add(facturaSeleccionada);
            actualizatblFacturasAPagar();
        }
    }//GEN-LAST:event_btnCargarActionPerformed

    private void btnRetirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirarActionPerformed

        if (tblFacturasAPagar.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una factura en la grilla de facturas a pagar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            FacturaCompra facturaSeleccionada = listFacturasAPagar.get(tblFacturasAPagar.getSelectedRow());
            listFacturasAPagar.remove(tblFacturasAPagar.getSelectedRow());
            actualizatblFacturasAPagar();
            listFacturasPendientes.add(facturaSeleccionada);
            actualizatblFacturasPendientes();

        }
    }//GEN-LAST:event_btnRetirarActionPerformed

    private void cbMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonedaActionPerformed

        listFacturasAPagar.clear();
        buscaFacturasPendientes();
        actualizatblFacturasPendientes();
        actualizatblFacturasAPagar();


    }//GEN-LAST:event_cbMonedaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnRegistrarPago;
    private javax.swing.JButton btnRetirar;
    private javax.swing.JComboBox cbMoneda;
    private javax.swing.JComboBox cbProveedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tblFacturasAPagar;
    private javax.swing.JTable tblFacturasPendientes;
    private javax.swing.JFormattedTextField txtSaldo;
    private javax.swing.JFormattedTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
