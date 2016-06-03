package com.CuentasRecibir;

import com.Beans.PagoCreditosVenta;
import com.DAO.DAOGenerico;
import Utilidades.Utilidades;
import com.Beans.EstadoEnum;
import java.util.Date;
import javax.swing.JOptionPane;

public class JdPagoParcial extends javax.swing.JDialog {

    private PagoCreditosVenta pago;
    private ConsultaCuentasRecibir frame;

    public JdPagoParcial(java.awt.Frame parent, boolean modal, PagoCreditosVenta pago, ConsultaCuentasRecibir frame) {
        super(parent, modal);
        setLocationRelativeTo(null);
        initComponents();
        this.pago = pago;
        this.frame = frame;

    }

    private void registraPagoSaldo() {

        try {
            int comfirmacion = JOptionPane.showConfirmDialog(null, "Comfirma el pago de la cuota?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (comfirmacion == 0) {

                Date hoyBD = Utilidades.RecibeDateRetornaDateFormatoBD(txtDate.getDate());

                DAOGenerico dao = new DAOGenerico(pago);
                pago.setEstado(EstadoEnum.PAGO);
                pago.setValor(0.0);
                pago.setFecha_pago(hoyBD);

                dao.actualiza();

                JOptionPane.showMessageDialog(null, "Pago registrado correctamente",
                        "Confirmación", JOptionPane.OK_OPTION);
                frame.muestraPagos();
                this.dispose();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void registraPagoParcial() {

        try {
            int comfirmacion = JOptionPane.showConfirmDialog(null, "Comfirma el pago de la cuota?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (comfirmacion == 0) {

                Double importe = Double.parseDouble(txtValor.getText());

                if (importe <= pago.getValor()) {

                    Date hoyBD = Utilidades.RecibeDateRetornaDateFormatoBD(txtDate.getDate());

                    DAOGenerico dao = new DAOGenerico(pago);
                    pago.setEstado(EstadoEnum.PARCIAL);
                    pago.setFecha_pago(hoyBD);
                    pago.setValor(Utilidades.Redondear(pago.getValor() - importe, 2));

                    dao.actualiza();

                    JOptionPane.showMessageDialog(null, "Pago registrado correctamente",
                            "Confirmación", JOptionPane.OK_OPTION);
                    frame.muestraPagos();
                    this.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "El importe ingresado no es correcto", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnConfirma = new javax.swing.JButton();
        btnCancela = new javax.swing.JButton();
        rbPagoSaldo = new javax.swing.JRadioButton();
        rbPagoParcial = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDate = new org.jdesktop.swingx.JXDatePicker();
        txtValor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Valor del pago parcial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel1, gridBagConstraints);

        btnConfirma.setText("Confima");
        btnConfirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnConfirma, gridBagConstraints);

        btnCancela.setText("Cancela");
        btnCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnCancela, gridBagConstraints);

        buttonGroup1.add(rbPagoSaldo);
        rbPagoSaldo.setSelected(true);
        rbPagoSaldo.setText("Paga el saldo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 13, 0, 0);
        jPanel2.add(rbPagoSaldo, gridBagConstraints);

        buttonGroup1.add(rbPagoParcial);
        rbPagoParcial.setText("Pago parcial");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(rbPagoParcial, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Registrar pago");
        jPanel1.add(jLabel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel1, gridBagConstraints);

        jLabel3.setText("Valor del pago parcial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtDate.setFormats("dd/MM/yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtDate, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rbPagoParcial, org.jdesktop.beansbinding.ELProperty.create("${selected}"), txtValor, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtValor, gridBagConstraints);

        getContentPane().add(jPanel2, new java.awt.GridBagConstraints());

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmaActionPerformed

        if (rbPagoParcial.isSelected()) {
            registraPagoParcial();
        } else if (rbPagoSaldo.isSelected()) {
            registraPagoSaldo();
        }
    }//GEN-LAST:event_btnConfirmaActionPerformed

    private void btnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelaActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelaActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(JdPagoParcial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JdPagoParcial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JdPagoParcial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JdPagoParcial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JdPagoParcial dialog = new JdPagoParcial(new javax.swing.JFrame(), true, null,null);
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
    private javax.swing.JButton btnCancela;
    private javax.swing.JButton btnConfirma;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton rbPagoParcial;
    private javax.swing.JRadioButton rbPagoSaldo;
    private org.jdesktop.swingx.JXDatePicker txtDate;
    private javax.swing.JTextField txtValor;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
