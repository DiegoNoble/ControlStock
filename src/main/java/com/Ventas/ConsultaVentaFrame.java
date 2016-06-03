package com.Ventas;

import com.Beans.Factura;
import com.Beans.ArticulosVenta;
import com.DAO.DAOGenerico;
import com.Beans.Cliente;
import com.DAO.FacturaVentaDAO;
import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.usuarios.frameLogin;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ConsultaVentaFrame extends javax.swing.JInternalFrame {

    private List<Factura> lista_factura;
    private List<Cliente> listaClientes;
    private DefaultTableModel tableModelFactura;
    private DefaultTableModel tableModelArticulosVenta;
    private ListSelectionModel listModelFactura;
    private FacturaVentaDAO facturaVentaDAO;

    public ConsultaVentaFrame() {

        initComponents();
        facturaVentaDAO = new FacturaVentaDAO();
        if (frameLogin.getInstance().getPerfil().equals("Operador")) {
            btnAnulaFactura.setEnabled(false);
        }
        defineModelo();
        cargaClientes();

    }

    private void cargaClientes() {
        AutoCompleteDecorator.decorate(cbNombres);

        listaClientes = new DAOGenerico().BuscaTodos(Cliente.class);

        for (Cliente clientes : listaClientes) {
            cbNombres.addItem(clientes);
        }

    }

    private void defineModelo() {
        tableModelFactura = (DefaultTableModel) tblFactura.getModel();
        tableModelArticulosVenta = (DefaultTableModel) tblArticulosVenta.getModel();
        listModelFactura = tblFactura.getSelectionModel();
        listModelFactura.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mustraDetallesVenda();
                }
            }
        });
        try {
            ((DefaultTableCellRenderer) tblFactura.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            ((DefaultTableCellRenderer) tblArticulosVenta.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        } catch (Exception ex) {
            Logger.getLogger(ConsultaVentaFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizatblFacturas() {

        try {
            tableModelFactura.setNumRows(0);

            for (Factura facturaVenta : lista_factura) {
                tableModelFactura.addRow(new Object[]{
                            facturaVenta.getIdfactura(),
                            facturaVenta.getCliente().getNombre(),
                            facturaVenta.getFecha(),
                            facturaVenta.getTipo().getDescription(),
                            facturaVenta.getMoneda().getDescription(),
                            facturaVenta.getTotal()
                        });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
    }

    private void mustraDetallesVenda() {

        if (tblFactura.getSelectedRow() != -1) {

            int factura = (int) tblFactura.getValueAt(tblFactura.getSelectedRow(), 0);

            List<ArticulosVenta> lista_articulos = new ArrayList();
            lista_articulos = facturaVentaDAO.buscarPor(ArticulosVenta.class, "id_factura", String.valueOf(factura));

            remueveArticulosVenta();
            for (ArticulosVenta articulosVenta : lista_articulos) {

                tableModelArticulosVenta.addRow(new Object[]{
                            articulosVenta.getArticulo().getId(),
                            articulosVenta.getArticulo().getNombre(),
                            articulosVenta.getCantidad(),
                            articulosVenta.getDescuento(),
                            articulosVenta.getValorSinIva(),
                            articulosVenta.getValorConIva()});
            }
        } else {
            remueveArticulosVenta();
        }
    }

    private void remueveArticulosVenta() {

        tableModelArticulosVenta.setNumRows(0);
    }

    private void buscaPorNroDocumento() {
        try {
            lista_factura = new FacturaVentaDAO().buscaPorNroFactura(txtNroFactura.getText());
            actualizatblFacturas();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }

        actualizatblFacturas();
    }

    private void buscaPorClienteEntreFecha() {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            String fechaInicial = formato.format(dpFechaInicial.getDate());
            String fechaFinal = formato.format(dpFechaFinal.getDate());

            lista_factura = facturaVentaDAO.buscaEntreFechasPorProveedor((Cliente) cbNombres.getSelectedItem(), fechaInicial, fechaFinal);

            actualizatblFacturas();
        } catch (Exception ex) {
            Logger.getLogger(ConsultaVentaFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void anularFactura() {

        if (tblFactura.getSelectedRow() != -1) {

            int confirmacion = JOptionPane.showConfirmDialog(null, "Confirma anulación de factura? \n Obs: automaticamente se devolverán los Articulos al Stock");

            if (confirmacion == 0) {

                Factura facturaSeleccionada = new Factura();
                facturaSeleccionada = lista_factura.get(tblFactura.getSelectedRow());
                DAOGenerico dao = new DAOGenerico(facturaSeleccionada);
                dao.elimina();//realiza cascada en la base de datos en todas las referencias de la factura

                actualizatblFacturas();
            }

        } else {

            JOptionPane.showMessageDialog(null, "Seleccione la factura que desea anular!", "Error!", JOptionPane.ERROR_MESSAGE);

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulosVenta = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFactura = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cbNombres = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dpFechaInicial = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        dpFechaFinal = new org.jdesktop.swingx.JXDatePicker();
        btnBuscar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtNroFactura = new javax.swing.JTextField();
        btnBuscarPorNroDocumento = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAnulaFactura = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(850, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consultar Ventas");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        tblArticulosVenta.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblArticulosVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre Articulo", "Cantidad", "Descuento", "Unitario Sin IVA", "Unitario Iva Inlc."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblArticulosVenta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblArticulosVenta);
        tblArticulosVenta.getColumnModel().getColumn(0).setPreferredWidth(5);
        tblArticulosVenta.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosVenta.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblArticulosVenta.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosVenta.getColumnModel().getColumn(2).setPreferredWidth(5);
        tblArticulosVenta.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosVenta.getColumnModel().getColumn(3).setPreferredWidth(5);
        tblArticulosVenta.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosVenta.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblArticulosVenta.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosVenta.getColumnModel().getColumn(5).setPreferredWidth(10);
        tblArticulosVenta.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        tblFactura.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Fecha", "Tipo", "Moneda", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFactura.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblFactura);
        tblFactura.getColumnModel().getColumn(0).setPreferredWidth(5);
        tblFactura.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblFactura.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblFactura.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblFactura.getColumnModel().getColumn(2).setPreferredWidth(20);
        tblFactura.getColumnModel().getColumn(2).setCellRenderer(new MyDateCellRenderer());
        tblFactura.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblFactura.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblFactura.getColumnModel().getColumn(4).setPreferredWidth(20);
        tblFactura.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
        tblFactura.getColumnModel().getColumn(5).setPreferredWidth(20);
        tblFactura.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane2, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Articulos de la Venta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel4, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel5.setLayout(new java.awt.GridBagLayout());

        cbNombres.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbNombres, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel5, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Fecha Inicial");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel2, gridBagConstraints);

        dpFechaInicial.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        dpFechaInicial.setFormats("dd/MM/yyyy"
        );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(dpFechaInicial, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Fecha Final");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel3, gridBagConstraints);

        dpFechaFinal.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        dpFechaFinal.setFormats("dd/MM/yyyy"
        );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(dpFechaFinal, gridBagConstraints);

        btnBuscar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnBuscar.setText("Consultar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(btnBuscar, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setText("Nro Factura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel8, gridBagConstraints);

        txtNroFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroFacturaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtNroFactura, gridBagConstraints);

        btnBuscarPorNroDocumento.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnBuscarPorNroDocumento.setText("Consultar");
        btnBuscarPorNroDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPorNroDocumentoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnBuscarPorNroDocumento, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        jPanel5.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        jPanel3.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        btnAnulaFactura.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAnulaFactura.setText("Anular Factura");
        btnAnulaFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnulaFacturaActionPerformed(evt);
            }
        });
        jPanel2.add(btnAnulaFactura);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed


        buscaPorClienteEntreFecha();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnAnulaFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnulaFacturaActionPerformed

        anularFactura();

    }//GEN-LAST:event_btnAnulaFacturaActionPerformed

    private void txtNroFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroFacturaActionPerformed

        buscaPorNroDocumento();

    }//GEN-LAST:event_txtNroFacturaActionPerformed

    private void btnBuscarPorNroDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPorNroDocumentoActionPerformed

        buscaPorNroDocumento();

    }//GEN-LAST:event_btnBuscarPorNroDocumentoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnulaFactura;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarPorNroDocumento;
    private javax.swing.JComboBox cbNombres;
    private org.jdesktop.swingx.JXDatePicker dpFechaFinal;
    private org.jdesktop.swingx.JXDatePicker dpFechaInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblArticulosVenta;
    private javax.swing.JTable tblFactura;
    private javax.swing.JTextField txtNroFactura;
    // End of variables declaration//GEN-END:variables
}
