package com.Ventas;

import com.DAO.DAOGenerico;
import com.Beans.Cotizacion;
import com.DAO.CotizacionDAO;
import com.Beans.Caja;
import com.Beans.Rubros;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class PagoCliente extends javax.swing.JDialog {

    Float cotizacionPR, cotizacionPD = null;
    Double total;
    Double pagoCliente, valorTotal, cambio = null;
    Integer nroFactura = null;
    private CotizacionDAO dao;
    private Cotizacion cotizacion;

    public PagoCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.total = RegistraVentaFrame.getInstance().getTotal();
        
        dao = new CotizacionDAO();
        cotizacion = new Cotizacion();
        cotizacion = (Cotizacion) dao.BuscarUltimaCotizacion();
        
        this.cotizacionPR = cotizacion.getReales();
        this.cotizacionPD = cotizacion.getDolares();
        btnConfirmar.setText("<html>Confirmar pago y<br> Calcular el cambio</html>");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        nroFactura = RegistraVentaFrame.getInstance().getIdfactura();

    }

    public void Cambio() {



        if (pagoCliente > valorTotal) {

            cambio = (pagoCliente - valorTotal);
            String cambioStr = String.format("%.2f", cambio).replace(",", ".");

            JOptionPane.showMessageDialog(null, "<html><font size=6 face=Verdana color=black>Valor de la venta " + cbMoneda.getSelectedItem() + ": " + txtTotal.getText() + "<br><br>"
                    + "Valor pago por el cliente " + cbMoneda.getSelectedItem() + ": " + txtPagoCliente.getText() + "<br><br>"
                    + "Valor del cambio " + cbMoneda.getSelectedItem() + ": " + cambioStr + "</font></html>", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void RegistraEnCaja() {

        DateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
        Date fecha = new Date();

        String date = formatoData.format(fecha);

        Date fechaBD = null;
        try {
            fechaBD = formatoData.parse(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error convirtiendo Fecha" + ex);
        }


        try {
            String moneda = (String) cbMoneda.getSelectedItem();

            switch (moneda) {

                case "Pesos $U":

                    Caja registroEnCaja = new Caja();

                    registroEnCaja.setConcepto("Venta contado factura Nro. " + nroFactura + "");
                    registroEnCaja.setEntrada_pesos(valorTotal);
                    registroEnCaja.setFecha(fechaBD);
                    Rubros rubro = new Rubros();
                    rubro.setId(1); //Rubro de venta debe ser el 1 !!
                    registroEnCaja.setRubro(rubro);

                    DAOGenerico dao = new DAOGenerico(registroEnCaja);
                    dao.guardar();


                case "Reales R$":

                    Caja registroEnCajaReales = new Caja();

                    registroEnCajaReales.setConcepto("Venta contado factura Nro. " + nroFactura + "");
                    registroEnCajaReales.setEntrada_reales(valorTotal);
                    registroEnCajaReales.setFecha(fechaBD);
                    Rubros rubro1 = new Rubros();
                    rubro1.setId(1); //Rubro de venta debe ser el 1 !!
                    registroEnCajaReales.setRubro(rubro1);

                    DAOGenerico dao1 = new DAOGenerico(registroEnCajaReales);
                    dao1.guardar();


                case "Dolares U$":


                    Caja registroEnCajaDolares = new Caja();

                    registroEnCajaDolares.setConcepto("Venta contado factura Nro. " + nroFactura + "");
                    registroEnCajaDolares.setEntrada_dolares(valorTotal);
                    registroEnCajaDolares.setFecha(fechaBD);
                    Rubros rubro2 = new Rubros();
                    rubro2.setId(1); //Rubro de venta debe ser el 1 !!
                    registroEnCajaDolares.setRubro(rubro2);

                    DAOGenerico dao2 = new DAOGenerico(registroEnCajaDolares);
                    dao2.guardar();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbMoneda = new javax.swing.JComboBox();
        txtTotal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtPagoCliente = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Confirmación de Pago");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pago..");
        jPanel1.add(jLabel1);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setText("Moneda:");

        cbMoneda.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pesos $U", "Reales R$", "Dolares U$" }));
        cbMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonedaActionPerformed(evt);
            }
        });
        cbMoneda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbMonedaFocusGained(evt);
            }
        });

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Total:");

        btnConfirmar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Valor pago por el Cliente:");

        txtPagoCliente.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(16, 16, 16)
                        .addComponent(cbMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtPagoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel9))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPagoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonedaActionPerformed

        if (cbMoneda.getSelectedItem().equals("Pesos $U")) {

            txtTotal.setText(String.format("%.2f", total).replace(",", "."));
            

        } else if (cbMoneda.getSelectedItem().equals("Reales R$")) {

            txtTotal.setText(String.format("%.2f", total / cotizacionPR).replace(",", "."));

        } else if (cbMoneda.getSelectedItem().equals("Dolares U$")) {

            txtTotal.setText(String.format("%.2f", total / cotizacionPD).replace(",", "."));
        }
    }//GEN-LAST:event_cbMonedaActionPerformed

    private void cbMonedaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbMonedaFocusGained

        if (cbMoneda.getSelectedItem().equals("Pesos $U")) {

            txtTotal.setText(String.format("%.2f", total).replace(",", "."));
            
        } else if (cbMoneda.getSelectedItem().equals("Reales R$")) {

            txtTotal.setText(String.format("%.2f", total / cotizacionPR).replace(",", "."));

        } else if (cbMoneda.getSelectedItem().equals("Dolares U$")) {

            txtTotal.setText(String.format("%.2f", total / cotizacionPD).replace(",", "."));
        }
    }//GEN-LAST:event_cbMonedaFocusGained

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        pagoCliente = Double.parseDouble(txtPagoCliente.getText());
        valorTotal = Double.parseDouble(txtTotal.getText());

        if (pagoCliente < valorTotal) {
            JOptionPane.showMessageDialog(null, "El valor pago por el cliente no puede \n "
                    + "ser menor que el valor total.-", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (txtPagoCliente.getText().equals("")) {

            txtPagoCliente.requestFocus();

        } else {
            Cambio();
            RegistraEnCaja();
            this.dispose();
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PagoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PagoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PagoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PagoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                PagoCliente dialog = new PagoCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbMoneda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtPagoCliente;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
