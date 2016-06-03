/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CuentasRecibir;

import com.Beans.PagoCreditosVenta;
import com.Beans.CreditosVenta;
import com.DAO.DAOGenerico;
import com.Beans.EstadoEnum;
import com.Beans.Factura;
import com.Ventas.RegistraVentaFrame;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class CreditosVentas extends javax.swing.JDialog {

    Factura factura = new Factura() ;
    private Double valorTotal;
    CreditosVenta credito = new CreditosVenta();
    List<PagoCreditosVenta> listaPagos;
    Date fechaFactura = new Date();
    Double valorCredito = null;
    Double valorCuota = null;

    public CreditosVentas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        initComponents();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //this.factura = RegistraVentaFrame.getInstance().getIdfactura();
        valorTotal = RegistraVentaFrame.getInstance().getTotal();
        txtValorTotal.setText(String.valueOf(valorTotal));
        listaPagos = new ArrayList<PagoCreditosVenta>();
        dpFechaPago.setFormats("dd/MM/yyyy");
        dpFechaPago.setDate(RegistraVentaFrame.getInstance().getFecha());
        int idFactura = RegistraVentaFrame.getInstance().getIdfactura();
        factura.setIdfactura(idFactura);

    }

   
    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void muestraContenidoTabla() {


        TableCellRenderer centerRenderer = new CreditosVentas.CenterRenderer();
        ((DefaultTableCellRenderer) tblPagos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        TableColumn column = tblPagos.getColumnModel().getColumn(0); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column1 = tblPagos.getColumnModel().getColumn(1);
        TableColumn column2 = tblPagos.getColumnModel().getColumn(2);
        TableColumn column3 = tblPagos.getColumnModel().getColumn(3);

        column.setCellRenderer(centerRenderer);
        column1.setCellRenderer(centerRenderer);
        column2.setCellRenderer(centerRenderer);
        column3.setCellRenderer(centerRenderer);



        DefaultTableModel modelo = (DefaultTableModel) tblPagos.getModel();
        modelo.setNumRows(0);

        tblPagos.getColumn("Cuota").setPreferredWidth(20); //------> Ajusta el tamaño de las columnas
        tblPagos.getColumn("Nro. Factura").setPreferredWidth(20);
        tblPagos.getColumn("Fecha").setPreferredWidth(25);
        tblPagos.getColumn("Valor U$").setPreferredWidth(15);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            String fechaMuestra = "";

            for (int i = 0; i < listaPagos.size(); i++) {

                fechaMuestra = formato.format(listaPagos.get(i).getFecha().getTime());
                modelo.insertRow(i, new Object[]{listaPagos.get(i).getNro_cuota(),
                            listaPagos.get(i).getCredito().getFactura().getIdfactura(),
                            fechaMuestra,
                            listaPagos.get(i).getValor()});
            };
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void desabilita(){
        
        txtNroPagos.setEditable(false);
        txtValorEntrega.setEditable(false);
        dpFechaPago.setEditable(false);
        btnConfirmar.setEnabled(true);
        btnGenerar.setEnabled(false);
    }
    
    private void habilita(){
        
        txtNroPagos.setEditable(true);
        txtValorEntrega.setEditable(true);
        dpFechaPago.setEditable(true);
        btnConfirmar.setEnabled(false);
        btnGenerar.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtValorTotal = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtValorEntrega = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNroPagos = new javax.swing.JTextField();
        btnGenerar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        dpFechaPago = new org.jdesktop.swingx.JXDatePicker();
        btnReiniciarPagos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPagos = new javax.swing.JTable();
        btnConfirmar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel1.setText("Valor total de la Factura U$:");

        txtValorTotal.setEditable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Valor de la entrega:");

        txtValorEntrega.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Nro. de pagos:");

        txtNroPagos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnGenerar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnGenerar.setText("Generar pagos");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Fecha de la 1er Cuota:");

        dpFechaPago.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnReiniciarPagos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnReiniciarPagos.setText("Reiniciar plan de pagos");
        btnReiniciarPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarPagosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGenerar)
                        .addGap(18, 18, 18)
                        .addComponent(btnReiniciarPagos)
                        .addGap(153, 153, 153))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtValorEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNroPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(dpFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtValorEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNroPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerar)
                    .addComponent(btnReiniciarPagos))
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Pagos", jPanel1);

        tblPagos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuota", "Nro. Factura", "Fecha", "Valor U$"
            }
        ));
        jScrollPane1.setViewportView(tblPagos);

        btnConfirmar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnConfirmar.setText("Confirmar credito");
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Registrar credito");
        jPanel2.add(jLabel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConfirmar)
                        .addGap(199, 199, 199))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jTabbedPane1)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnConfirmar)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed

        desabilita();

        try {
            int nroPagos = Integer.parseInt(txtNroPagos.getText());
            Double entrega = Double.parseDouble(txtValorEntrega.getText());
            if (entrega > valorTotal) {

                JOptionPane.showMessageDialog(null, "El valor de la entrega no puede ser \n"
                        + "superior que el valor total de la factura", "Error!", JOptionPane.ERROR_MESSAGE);

            } else {

                listaPagos.clear();
                SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");

                fechaFactura = dpFechaPago.getDate();
                String fechaFormatada = formato.format(fechaFactura);

                for (int i = 0; i < nroPagos; i++) {

                    valorCuota = (valorTotal - entrega) / nroPagos;
                    valorCredito = (valorTotal - entrega);
                    BigDecimal valorExato = new BigDecimal(valorCuota).setScale(2, RoundingMode.HALF_DOWN);

                    PagoCreditosVenta pago = new PagoCreditosVenta();
                    pago.setNro_cuota(i + 1);
                    credito.setFactura(factura);
                    pago.setCredito(credito);

                    Calendar c = Calendar.getInstance();
                    c.setTime(formato.parse(fechaFormatada));
                    c.add(Calendar.MONTH, i);
                    pago.setFecha(c);

                    pago.setValor(Double.parseDouble(String.valueOf(valorExato)));

                    listaPagos.add(pago);
                }
                muestraContenidoTabla();
            }

        } catch (NumberFormatException | HeadlessException | ParseException e) {
            JOptionPane.showMessageDialog(null, "Complete los campos requeridos", "Error!", JOptionPane.ERROR_MESSAGE);
            listaPagos.clear();
            habilita();
        }


    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        if (tblPagos.getRowCount() == 0) {

            JOptionPane.showMessageDialog(null, "Debe generar el plan de pagos", "Error!", JOptionPane.ERROR_MESSAGE);

        } else {

            int confirmacion = JOptionPane.showConfirmDialog(null, "Confirma el plan de pagos "
                    + "seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == 0) {
                try {
                    credito.setEntrega(Double.parseDouble(txtValorEntrega.getText()));
                    credito.setFactura(factura);
                    credito.setNroCuotas(Integer.parseInt(txtNroPagos.getText()));
                    credito.setValorCredito(valorCredito);

                    DAOGenerico dao = new DAOGenerico(credito);
                    dao.registra();

                    for (int i = 0; i < listaPagos.size(); i++) {

                        PagoCreditosVenta pagoDAO = new PagoCreditosVenta();
                        pagoDAO.setCredito(credito);
                        pagoDAO.setFecha(listaPagos.get(i).getFecha());
                        pagoDAO.setNro_cuota(listaPagos.get(i).getNro_cuota());
                        pagoDAO.setValor(listaPagos.get(i).getValor());
                        pagoDAO.setEstado(EstadoEnum.PENDIENTE);
                        DAOGenerico registraPagos = new DAOGenerico(pagoDAO);
                        registraPagos.registra();

                    }

                    JOptionPane.showMessageDialog(null, "Credito generado correctamente", "Atencio!", JOptionPane.OK_OPTION);
                    this.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: "+ex);
                }
            } else {

                txtNroPagos.setText("");
                txtValorEntrega.setText("");
                habilita();
                        

            }
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnReiniciarPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarPagosActionPerformed

        listaPagos.clear();
        muestraContenidoTabla();
        txtNroPagos.setText("");
        txtValorEntrega.setText("");
        habilita();

    }//GEN-LAST:event_btnReiniciarPagosActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                CreditosVentas dialog = new CreditosVentas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnReiniciarPagos;
    private org.jdesktop.swingx.JXDatePicker dpFechaPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblPagos;
    private javax.swing.JTextField txtNroPagos;
    private javax.swing.JTextField txtValorEntrega;
    private javax.swing.JTextField txtValorTotal;
    // End of variables declaration//GEN-END:variables
}
