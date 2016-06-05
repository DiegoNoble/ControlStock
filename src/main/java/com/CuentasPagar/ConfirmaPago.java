package com.CuentasPagar;

import com.DAO.DAOGenerico;
import com.Beans.EstadoEnum;
import com.Beans.CompraPago;
import com.Beans.PagoCompra;
import com.Beans.TipoPagoEnum;
import com.Beans.FacturaCompra;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class ConfirmaPago extends javax.swing.JDialog {

    private List<FacturaCompra> ListFacturasAPagar;
    private RegistraPagos framePadre;
    private FrameRecordatorioCuentasPagar frame;

    public ConfirmaPago(java.awt.Frame parent, boolean modal, List ListFacturasAPagar, RegistraPagos framePadre, Object total) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.framePadre = framePadre;
        btnConfirmarFact.setVisible(false);
        btnConfirmarList.setVisible(true);
        this.ListFacturasAPagar = ListFacturasAPagar;
        txtImporte.setText(String.valueOf(total));
        cargaTiposdePago();
    }
    
    public ConfirmaPago(java.awt.Frame parent, boolean modal, FrameRecordatorioCuentasPagar frame, FacturaCompra fact) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        ListFacturasAPagar = new ArrayList();
        this.frame = frame;
        btnConfirmarFact.setVisible(true);
        btnConfirmarList.setVisible(false);
        this.ListFacturasAPagar.add(fact);
        txtImporte.setText(String.valueOf(fact.getTotal()));
        cargaTiposdePago();
    }

    private void cargaTiposdePago() {

        DefaultComboBoxModel modelo = new DefaultComboBoxModel(TipoPagoEnum.values());
        cbTipoPago.setModel(modelo);
    }

    private void regitrarPagoFacturas() {

        int confirmacion = JOptionPane.showConfirmDialog(null,"Esta seguro que desea registrar el pago ?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmacion != 1) {

            PagoCompra pagoCompra = new PagoCompra();
            pagoCompra.setFechaPago(dpDate.getDate());
            pagoCompra.setValor(Double.parseDouble(txtImporte.getText()));
            pagoCompra.setNroDocumento(txtNroDocumento.getText());
            pagoCompra.setTipoPago((TipoPagoEnum) cbTipoPago.getSelectedItem());
            pagoCompra.setObs(txtObservaciones.getText());

            CompraPago facturasPagas = new CompraPago();
            for (FacturaCompra facturaCompra : ListFacturasAPagar) {
                try {
                    facturasPagas.setFactura(facturaCompra);
                    facturasPagas.setPago(pagoCompra);

                    facturaCompra.setEstado(EstadoEnum.PAGO);

                    DAOGenerico daoRegistraPago = new DAOGenerico(facturasPagas);
                    daoRegistraPago.guardar();

                    DAOGenerico daoActualizaFacturaCompra = new DAOGenerico(facturaCompra);
                    daoActualizaFacturaCompra.actualiza();
                    this.dispose();
                    framePadre.buscaFacturasPendientes();
                    framePadre.remueveFacturasAPagar();
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error", "Error al registrar el Pago: " + ex, JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(null, "Pago registrado correctamente");
        }
    }

    private void regitrarPagoFactura() {

        int confirmacion = JOptionPane.showConfirmDialog(null,"Esta seguro que desea registrar el pago ?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmacion != 1) {

            PagoCompra pagoCompra = new PagoCompra();
            pagoCompra.setFechaPago(dpDate.getDate());
            pagoCompra.setValor(Double.parseDouble(txtImporte.getText()));
            pagoCompra.setNroDocumento(txtNroDocumento.getText());
            pagoCompra.setTipoPago((TipoPagoEnum) cbTipoPago.getSelectedItem());
            pagoCompra.setObs(txtObservaciones.getText());

            CompraPago facturasPagas = new CompraPago();
            for (FacturaCompra facturaCompra : ListFacturasAPagar) {
                try {
                    facturasPagas.setFactura(facturaCompra);
                    facturasPagas.setPago(pagoCompra);

                    facturaCompra.setEstado(EstadoEnum.PAGO);

                    DAOGenerico daoRegistraPago = new DAOGenerico(facturasPagas);
                    daoRegistraPago.guardar();

                    DAOGenerico daoActualizaFacturaCompra = new DAOGenerico(facturaCompra);
                    daoActualizaFacturaCompra.actualiza();
                    this.dispose();
                    frame.buscaVencimientos();
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error", "Error al registrar el Pago: " + ex, JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(null, "Pago registrado correctamente");
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dpDate = new org.jdesktop.swingx.JXDatePicker();
        cbTipoPago = new javax.swing.JComboBox();
        txtNroDocumento = new javax.swing.JTextField();
        txtObservaciones = new javax.swing.JTextField();
        txtImporte = new javax.swing.JTextField();
        btnConfirmarList = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnConfirmarFact = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Pago..");
        setPreferredSize(new java.awt.Dimension(450, 300));
        setType(java.awt.Window.Type.POPUP);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Pago...");
        jPanel1.add(jLabel7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Fecha del Pago:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel1, gridBagConstraints);

        jLabel4.setText("Importe:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel3.setText("Tipo de Pago:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        jLabel5.setText("Observaciones:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Nro. Documento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel6, gridBagConstraints);

        dpDate.setFormats("dd/MM/yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpDate, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbTipoPago, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNroDocumento, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtObservaciones, gridBagConstraints);

        txtImporte.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtImporte, gridBagConstraints);

        btnConfirmarList.setText("Registrar pago");
        btnConfirmarList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarListActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnConfirmarList, gridBagConstraints);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnCancelar, gridBagConstraints);

        btnConfirmarFact.setText("Registrar pago");
        btnConfirmarFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarFactActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnConfirmarFact, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        this.dispose();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarListActionPerformed

        regitrarPagoFacturas();

    }//GEN-LAST:event_btnConfirmarListActionPerformed

    private void btnConfirmarFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarFactActionPerformed

        regitrarPagoFactura();
        
    }//GEN-LAST:event_btnConfirmarFactActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfirmaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfirmaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfirmaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfirmaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfirmaPago dialog = new ConfirmaPago(new javax.swing.JFrame(), true, null, null ,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    } 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmarFact;
    private javax.swing.JButton btnConfirmarList;
    private javax.swing.JComboBox cbTipoPago;
    private org.jdesktop.swingx.JXDatePicker dpDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtNroDocumento;
    private javax.swing.JTextField txtObservaciones;
    // End of variables declaration//GEN-END:variables
}
