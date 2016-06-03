package com.Compras;

import com.Beans.FacturaCompra;
import com.Beans.ArticulosCompra;
import com.DAO.DAOGenerico;
import com.DAO.FacturaCompraDAO;
import com.DAO.ProveedorDAO;
import com.Beans.Proveedor;
import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.usuarios.frameLogin;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ConsultaCompraFrame extends javax.swing.JInternalFrame {

    private List<FacturaCompra> listFactura;
    private DefaultTableModel tableModelFactura;
    private DefaultTableModel tableModelArticulosCompra;
    private ListSelectionModel listModelFactura;
    private List<Proveedor> listaProveedores;
    private FacturaCompraDAO facturaCompraDAO;
    private ProveedorDAO proveedorDAO;

    public ConsultaCompraFrame() {
        initComponents();
        listFactura = new ArrayList();
        facturaCompraDAO = new FacturaCompraDAO();

        if (frameLogin.getInstance().getPerfil().equals("Operador")) {
            btnAnulaFactura.setEnabled(false);
        }
        defineModelo();
        AutoCompleteDecorator.decorate(cbProveedores);
        completaCbProveedores();

    }

    private void completaCbProveedores() {

        try {
            proveedorDAO = new ProveedorDAO();
            listaProveedores = proveedorDAO.BuscaTodos(Proveedor.class);
            cbProveedores.removeAllItems();
            for (Proveedor proveedor : listaProveedores) {
                cbProveedores.addItem(proveedor);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void defineModelo() {
        ((DefaultTableCellRenderer) tblFacturaCompra.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) tblArticulosCompra.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tableModelFactura = (DefaultTableModel) tblFacturaCompra.getModel();
        tableModelArticulosCompra = (DefaultTableModel) tblArticulosCompra.getModel();
        listModelFactura = tblFacturaCompra.getSelectionModel();
        listModelFactura.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mustraDetallesVenda();
                }
            }
        });

    }

    private void buscaFacturasEntreFechasProProveedor() {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaInicial = formato.format(dpFechaInicial.getDate());
            String fechaFinal = formato.format(dpFechaFinal.getDate());
            Proveedor proveedor = (Proveedor) cbProveedores.getSelectedItem();
            listFactura.clear();
            listFactura = facturaCompraDAO.buscaEntreFechasPorProveedor(proveedor, fechaInicial, fechaFinal);
            actualizatblFacturas();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }

    }

    private void buscaPorNroFactura() {
        try {
            facturaCompraDAO = new FacturaCompraDAO();
            listFactura = facturaCompraDAO.buscaPorNroFactura(txtNroFactura.getText());
            actualizatblFacturas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar por Nro de Factura");
        }

    }

    private void actualizatblFacturas() {

        tableModelFactura.setRowCount(0);
        for (FacturaCompra facturaCompra : listFactura) {
            tableModelFactura.addRow(new Object[]{
                        facturaCompra.getNroFactura(),
                        facturaCompra.getSerieFactura(),
                        facturaCompra.getProveedor().getNombre(),
                        facturaCompra.getFecha(),
                        facturaCompra.getTipo().getDescription(),
                        facturaCompra.getMoneda().getDescription(),
                        facturaCompra.getTotal()
                    });
        }

    }

    private void mustraDetallesVenda() {

        if (tblFacturaCompra.getSelectedRow() != -1) {

            int idFactura = listFactura.get(tblFacturaCompra.getSelectedRow()).getIdfactura();

            List<ArticulosCompra> lista_articulos = new ArrayList();
            lista_articulos = facturaCompraDAO.buscarPor(ArticulosCompra.class, "id_factura", String.valueOf(idFactura));

            remueveArticulosVenta();
            for (ArticulosCompra articulosCompra : lista_articulos) {
                tableModelArticulosCompra.addRow(new Object[]{
                            articulosCompra.getArticulo().getId(),
                            articulosCompra.getArticulo().getNombre(),
                            articulosCompra.getCantidad(),
                            articulosCompra.getDescuento(),
                            articulosCompra.getValorSinIva(),
                            articulosCompra.getValorConIva()
                        });
            }

        } else {
            remueveArticulosVenta();
        }
    }

    private void remueveArticulosVenta() {
        DefaultTableModel modelo = (DefaultTableModel) tblArticulosCompra.getModel();
        int numeroLineas = tblArticulosCompra.getRowCount();
        for (int i = 0; i < numeroLineas; i++) {
            modelo.removeRow(0);
        }
    }

    private void buscaTodasFacturas() {
        facturaCompraDAO = new FacturaCompraDAO();
        listFactura = facturaCompraDAO.BuscaTodos(FacturaCompra.class);
    }

    private void anularFactura() {

        if (tblFacturaCompra.getSelectedRow() != -1) {

            int confirmacion = JOptionPane.showConfirmDialog(null, "Confirma anulación de factura? \n Obs: automaticamente se devolverán los Articulos al Stock");

            if (confirmacion == 0) {

                FacturaCompra facturaSeleccionada = new FacturaCompra();
                facturaSeleccionada = listFactura.get(tblFacturaCompra.getSelectedRow());

                DAOGenerico dao = new DAOGenerico(facturaSeleccionada);
                dao.elimina();  //realiza cascada en la base de datos en todas las referencias de la factura
                buscaTodasFacturas();
                actualizatblFacturas();
            }
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
        tblArticulosCompra = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFacturaCompra = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        dpFechaFinal = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dpFechaInicial = new org.jdesktop.swingx.JXDatePicker();
        btnBuscar = new javax.swing.JButton();
        cbProveedores = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        txtNroFactura = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnBuscarPorNroDocumento = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAnulaFactura = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(850, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consultar Compras");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        tblArticulosCompra.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblArticulosCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre Articulo", "Cantidad", "Descuento", "Unitario Sin IVA", "Unitario Iva Incl."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
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
        tblArticulosCompra.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblArticulosCompra);
        tblArticulosCompra.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblArticulosCompra.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblArticulosCompra.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(2).setPreferredWidth(25);
        tblArticulosCompra.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(3).setPreferredWidth(25);
        tblArticulosCompra.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        tblFacturaCompra.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblFacturaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro Fact.", "Serie", "Proveedor", "Fecha", "Tipo", "Moneda", "Total IVA Incl."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
        jScrollPane2.setViewportView(tblFacturaCompra);
        tblFacturaCompra.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblFacturaCompra.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturaCompra.getColumnModel().getColumn(1).setPreferredWidth(5);
        tblFacturaCompra.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturaCompra.getColumnModel().getColumn(2).setPreferredWidth(110);
        tblFacturaCompra.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturaCompra.getColumnModel().getColumn(3).setPreferredWidth(15);
        tblFacturaCompra.getColumnModel().getColumn(3).setCellRenderer(new MyDateCellRenderer());
        tblFacturaCompra.getColumnModel().getColumn(4).setPreferredWidth(5);
        tblFacturaCompra.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturaCompra.getColumnModel().getColumn(5).setPreferredWidth(15);
        tblFacturaCompra.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturaCompra.getColumnModel().getColumn(6).setPreferredWidth(40);
        tblFacturaCompra.getColumnModel().getColumn(6).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane2, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Articulos de la Compra");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel4, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel4.setLayout(new java.awt.GridBagLayout());

        dpFechaFinal.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        dpFechaFinal.setFormats("dd/MM/yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(dpFechaFinal, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Fecha Inicial");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Fecha Final");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel3, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Proveedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel5, gridBagConstraints);

        dpFechaInicial.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        dpFechaInicial.setFormats("dd/MM/yyyy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(dpFechaInicial, gridBagConstraints);

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
        jPanel4.add(btnBuscar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbProveedores, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new java.awt.GridBagLayout());

        txtNroFactura.setToolTipText("Digite en Nro de factura y presione ENTER");
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
        jPanel5.add(txtNroFactura, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setText("Nro. Factura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel6, gridBagConstraints);

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
        jPanel5.add(btnBuscarPorNroDocumento, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        jPanel4.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        jPanel3.add(jPanel4, gridBagConstraints);

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

        buscaFacturasEntreFechasProProveedor();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnAnulaFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnulaFacturaActionPerformed

        anularFactura();

    }//GEN-LAST:event_btnAnulaFacturaActionPerformed

    private void txtNroFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroFacturaActionPerformed

        buscaPorNroFactura();

    }//GEN-LAST:event_txtNroFacturaActionPerformed

    private void btnBuscarPorNroDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPorNroDocumentoActionPerformed
    
         buscaPorNroFactura();
    }//GEN-LAST:event_btnBuscarPorNroDocumentoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnulaFactura;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarPorNroDocumento;
    private javax.swing.JComboBox cbProveedores;
    private org.jdesktop.swingx.JXDatePicker dpFechaFinal;
    private org.jdesktop.swingx.JXDatePicker dpFechaInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblArticulosCompra;
    private javax.swing.JTable tblFacturaCompra;
    private javax.swing.JTextField txtNroFactura;
    // End of variables declaration//GEN-END:variables
}
