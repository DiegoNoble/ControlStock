package com.CuentasPagar;

import com.Beans.FacturaCompra;
import com.DAO.FacturaCompraDAO;
import com.Renderers.MeDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public final class FrameRecordatorioCuentasPagar extends javax.swing.JInternalFrame {

    private DefaultTableModel tableModelFacturas;
    private List<FacturaCompra> listFactVencimientos;
    private FacturaCompraDAO facturaCompraDAO;

    public FrameRecordatorioCuentasPagar(List<FacturaCompra> listFactVencimientos) {
        initComponents();
        this.setSize(650, 200);
        this.listFactVencimientos = listFactVencimientos;
        defineModelo();
        actualizatblFacturasPendientes();
    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblVencimientos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tableModelFacturas = (DefaultTableModel) tblVencimientos.getModel();

    }

    private void actualizatblFacturasPendientes() {

        tableModelFacturas.setNumRows(0);
        
        for (FacturaCompra facturaCompra : listFactVencimientos) {

            tableModelFacturas.addRow(new Object[]{
                        facturaCompra.getProveedor().getNombre(),
                        facturaCompra.getNroFactura(),
                        facturaCompra.getSerieFactura(),
                        facturaCompra.getFecha(),
                        facturaCompra.getFechaVencimiento(),
                        facturaCompra.getMoneda(),
                        facturaCompra.getTotal()});
        }
    }

    public void buscaVencimientos() {

        try {
            facturaCompraDAO = new FacturaCompraDAO();
            listFactVencimientos = facturaCompraDAO.buscaProximosVencimientos();
            actualizatblFacturasPendientes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar vencimientos" + ex);
            ex.printStackTrace();

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblVencimientos = new javax.swing.JTable();
        btnRegistrarPago = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblVencimientos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblVencimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proveedor", "Factura", "Serie", "Fecha", "Vencimiento", "Moneda", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVencimientos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tblVencimientos);
        tblVencimientos.getColumnModel().getColumn(0).setPreferredWidth(35);
        tblVencimientos.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblVencimientos.getColumnModel().getColumn(1).setPreferredWidth(30);
        tblVencimientos.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblVencimientos.getColumnModel().getColumn(2).setPreferredWidth(10);
        tblVencimientos.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblVencimientos.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblVencimientos.getColumnModel().getColumn(3).setCellRenderer(new MeDateCellRenderer());
        tblVencimientos.getColumnModel().getColumn(4).setPreferredWidth(20);
        tblVencimientos.getColumnModel().getColumn(4).setCellRenderer(new MeDateCellRenderer());
        tblVencimientos.getColumnModel().getColumn(5).setPreferredWidth(10);
        tblVencimientos.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
        tblVencimientos.getColumnModel().getColumn(6).setPreferredWidth(10);
        tblVencimientos.getColumnModel().getColumn(6).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 120;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 50.0;
        jPanel2.add(jScrollPane3, gridBagConstraints);

        jTabbedPane1.addTab("Proximos Vencimientos a pagar", jPanel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        btnRegistrarPago.setText("Registrar Pago");
        btnRegistrarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarPagoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(btnRegistrarPago, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarPagoActionPerformed

        if (tblVencimientos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una factura en la tabla");
        } else {
            FacturaCompra fact = listFactVencimientos.get(tblVencimientos.getSelectedRow());
            ConfirmaPago cp = new ConfirmaPago(null, true, this, fact);
            cp.setVisible(true);
            cp.toFront();
        }
    }//GEN-LAST:event_btnRegistrarPagoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrarPago;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblVencimientos;
    // End of variables declaration//GEN-END:variables
}
