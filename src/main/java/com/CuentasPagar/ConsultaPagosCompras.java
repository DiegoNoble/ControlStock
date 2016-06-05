package com.CuentasPagar;

import com.DAO.DAOGenerico;
import com.Beans.MonedaEnum;
import com.Beans.PagoCompra;
import com.Beans.FacturaCompra;
import com.DAO.FacturaCompraDAO;
import com.DAO.PagosComprasDAO;
import com.Beans.Proveedor;
import com.Renderers.MeDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ConsultaPagosCompras extends javax.swing.JInternalFrame {

    private DefaultTableModel tableModelPagos;
    private ListSelectionModel listModelPagos;
    private DefaultTableModel tableModelFacturasPagas;
    private List<PagoCompra> listPagosCompras;
    private List<FacturaCompra> listFacturasPagas;
    private PagosComprasDAO PagosComprasDAO;
    private FacturaCompraDAO facturaCompraDAO;
    private List<Proveedor> listProveedores;

    public ConsultaPagosCompras() {
        initComponents();
        PagosComprasDAO = new PagosComprasDAO();
        facturaCompraDAO = new FacturaCompraDAO();
        listPagosCompras = new ArrayList();
        listFacturasPagas = new ArrayList();
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

        ((DefaultTableCellRenderer) tblPagosCompras.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) tblFacturasPagas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tableModelPagos = (DefaultTableModel) tblPagosCompras.getModel();
        tableModelFacturasPagas = (DefaultTableModel) tblFacturasPagas.getModel();

        listModelPagos = tblPagosCompras.getSelectionModel();
        listModelPagos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    buscaFacturasPagas();
                    actualizaFacturasPagas();
                }
            }
        });
    }

    private void buscaPagos() {

        remueveFacturasPagas();
        
        Proveedor proveedorSeleccionado = (Proveedor) cbProveedores.getSelectedItem();
        if (proveedorSeleccionado == null) {
            return;
        }
        try {
            MonedaEnum moneda = (MonedaEnum) cbMoneda.getSelectedItem();
            listPagosCompras = PagosComprasDAO.buscaPagosPorProveedoryMoneda(proveedorSeleccionado, moneda);

        } catch (Exception ex) {
            Logger.getLogger(ConsultaPagosCompras.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void buscaFacturasPagas() {

        if (tblPagosCompras.getSelectedRow() != -1) {
            PagoCompra pagoSeelccionado = listPagosCompras.get(tblPagosCompras.getSelectedRow());

            try {
                listFacturasPagas = facturaCompraDAO.buscaFacturasPorPago(pagoSeelccionado);

            } catch (Exception ex) {
                Logger.getLogger(ConsultaPagosCompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void actualizaTblPagos() {

        remuevePagos();

        Double total = 0.0;
        for (PagoCompra pago : listPagosCompras) {

            total = total + pago.getValor();


            tableModelPagos.addRow(new Object[]{
                        pago.getNroDocumento(),
                        pago.getTipoPago().getDescription(),
                        pago.getFechaPago(),
                        pago.getValor(),
                        pago.getObs()});
        }

        txtTotal.setValue(total);


    }

    private void actualizaFacturasPagas() {
            if (tblPagosCompras.getSelectedRow() != -1) {
        try {
            remueveFacturasPagas();
            for (FacturaCompra facturaCompra : listFacturasPagas) {

                tableModelFacturasPagas.addRow(new Object[]{
                            facturaCompra.getNroFactura(),
                            facturaCompra.getSerieFactura(),
                            facturaCompra.getFecha(),
                            facturaCompra.getMoneda(),
                            facturaCompra.getTotal(),});
            }

        } catch (Exception ex) {
            Logger.getLogger(ConsultaPagosCompras.class.getName()).log(Level.SEVERE, null, ex);
        }
            }else{
                remueveFacturasPagas();
            }
    }

    private void remuevePagos() {
        
            tableModelPagos.setNumRows(0);
    }

    private void remueveFacturasPagas() {
        
            tableModelFacturasPagas.setNumRows(0);
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
        tblFacturasPagas = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPagosCompras = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cbMoneda = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();

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
        jLabel1.setText("Consulta cuentas a pagar");
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

        tblFacturasPagas.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblFacturasPagas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Serie", "Fecha", "Moneda", "Saldo"
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
        jScrollPane4.setViewportView(tblFacturasPagas);
        tblFacturasPagas.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturasPagas.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturasPagas.getColumnModel().getColumn(2).setCellRenderer(new MeDateCellRenderer());
        tblFacturasPagas.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturasPagas.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 120;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 50.0;
        jPanel4.add(jScrollPane4, gridBagConstraints);

        jTabbedPane1.addTab("Facturas pagas", jPanel4);

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

        tblPagosCompras.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblPagosCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. Documento", "Tipo", "Fecha", "Valor", "Observaciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class
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
        tblPagosCompras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tblPagosCompras);
        tblPagosCompras.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblPagosCompras.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblPagosCompras.getColumnModel().getColumn(1).setPreferredWidth(40);
        tblPagosCompras.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblPagosCompras.getColumnModel().getColumn(2).setPreferredWidth(40);
        tblPagosCompras.getColumnModel().getColumn(2).setCellRenderer(new MeDateCellRenderer());
        tblPagosCompras.getColumnModel().getColumn(3).setPreferredWidth(40);
        tblPagosCompras.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblPagosCompras.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblPagosCompras.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 120;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 50.0;
        jPanel5.add(jScrollPane3, gridBagConstraints);

        jTabbedPane2.addTab("Pagos", jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel3.add(jTabbedPane2, gridBagConstraints);

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
        jLabel6.setText("Total:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel7.add(jLabel6, gridBagConstraints);

        txtTotal.setEditable(false);
        txtTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtTotal, gridBagConstraints);

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProveedoresActionPerformed

        listPagosCompras.clear();
        buscaPagos();
        actualizaTblPagos();

    }//GEN-LAST:event_cbProveedoresActionPerformed

    private void cbMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonedaActionPerformed
        
        listPagosCompras.clear();
        buscaPagos();
        actualizaTblPagos();


    }//GEN-LAST:event_cbMonedaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbMoneda;
    private javax.swing.JComboBox cbProveedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tblFacturasPagas;
    private javax.swing.JTable tblPagosCompras;
    private javax.swing.JFormattedTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
