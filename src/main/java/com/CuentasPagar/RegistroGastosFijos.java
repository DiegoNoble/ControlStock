/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CuentasPagar;

import com.DAO.DAOGenerico;
import Utilidades.Utilidades;
import com.Beans.EstadoEnum;
import com.Beans.TipoDocumentoEnum;
import com.Beans.FacturaCompra;
import com.Beans.MonedaEnum;
import com.Beans.Proveedor;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class RegistroGastosFijos extends JInternalFrame {

    Double valorTotal;

    public RegistroGastosFijos() {

        initComponents();
        this.setClosable(true);

        AutoCompleteDecorator.decorate(cbNombres);

        List<Proveedor> listaProveedor = new DAOGenerico().BuscaTodos(Proveedor.class);

        try {
            for (Proveedor proveedor : listaProveedor) {
                cbNombres.addItem(proveedor);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        DefaultComboBoxModel comboModel = new DefaultComboBoxModel(MonedaEnum.values());
        cbMoneda.setModel(comboModel);
    }

    public void registrarGasto() {


        try {

            int confirmacion = JOptionPane.showConfirmDialog(null, "Confirma el registro de la cuentas a pagar ? ", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == 0) {

                valorTotal = Double.valueOf(txtValorTotal.getText());
                Date fechaEmision = Utilidades.recibeDateRetornaDateBD(dpFechaEmision.getDate());
                Date fechaVencimiento = Utilidades.RecibeDateRetornaDateFormatoBD(dpVencimiento.getDate());

                Proveedor proveedorSeleccionado = (Proveedor) cbNombres.getSelectedItem();

                FacturaCompra factura = new FacturaCompra();
                factura.setFecha(fechaEmision);
                factura.setTipo(TipoDocumentoEnum.CREDITO);
                factura.setEstado(EstadoEnum.PENDIENTE);
                factura.setFechaVencimiento(fechaVencimiento);
                factura.setNroFactura(Integer.parseInt(txtxNroFact.getText()));
                factura.setSerieFactura(txtFactura.getText());
                factura.setMoneda((MonedaEnum) cbMoneda.getSelectedItem());
                factura.setTotal(valorTotal);
                factura.setProveedor(proveedorSeleccionado);
                new DAOGenerico(factura).registra();
                
                JOptionPane.showMessageDialog(null, "Gasto registrado correctamente !", "Proceso realizado correctamente", JOptionPane.INFORMATION_MESSAGE);
                
            } else {
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar Cuenta a pagar: " + e);
        }
    }

    public void limpiaCampos() {

        txtxNroFact.setText("");
        txtFactura.setText("");
        txtValorTotal.setText("");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dpVencimiento = new org.jdesktop.swingx.JXDatePicker();
        dpFechaEmision = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        txtValorTotal = new javax.swing.JTextField();
        cbNombres = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        txtFactura = new javax.swing.JTextField();
        txtxNroFact = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbMoneda = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(720, 298));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Fecha de emisión");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Nro Factura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel4, gridBagConstraints);

        dpVencimiento.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        dpVencimiento.setFormats("dd/MM/yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dpVencimiento, gridBagConstraints);

        dpFechaEmision.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        dpFechaEmision.setFormats("dd/MM/yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dpFechaEmision, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel1.setText("Total IVA incl. $ ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtValorTotal, gridBagConstraints);

        cbNombres.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbNombres, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel6.setText("Proveedor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel6, gridBagConstraints);

        btnConfirmar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(btnConfirmar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtFactura, gridBagConstraints);

        txtxNroFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtxNroFactActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtxNroFact, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setText("Serie Factura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setText("Moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel9, gridBagConstraints);

        cbMoneda.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbMoneda, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel10.setText("Fecha de Vencimineto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Gastos fijos");
        jPanel2.add(jLabel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        registrarGasto();
        
        limpiaCampos();

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void txtxNroFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtxNroFactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtxNroFactActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbMoneda;
    private javax.swing.JComboBox cbNombres;
    private org.jdesktop.swingx.JXDatePicker dpFechaEmision;
    private org.jdesktop.swingx.JXDatePicker dpVencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtFactura;
    private javax.swing.JTextField txtValorTotal;
    private javax.swing.JTextField txtxNroFact;
    // End of variables declaration//GEN-END:variables
}
