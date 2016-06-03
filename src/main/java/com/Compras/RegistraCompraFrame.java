package com.Compras;

import com.Beans.FacturaCompra;
import com.Beans.ArticulosCompra;
import com.DAO.DAOGenerico;
import Utilidades.Utilidades;
import com.Beans.Articulos;
import com.Articulos.FrameSeleccionaArticulo;
import com.Beans.Cotizacion;
import com.Beans.EstadoEnum;
import com.Beans.MonedaEnum;
import com.Beans.TipoDocumentoEnum;
import com.DAO.ArticuloDAO;
import com.DAO.ArticulosCompraDAO;
import com.DAO.CotizacionDAO;
import com.DAO.FacturaCompraDAO;
import com.Beans.Proveedor;
import com.Proveedores.ProveedorFrame;
import com.Renderers.MyDefaultCellRenderer;
import com.usuarios.frameLogin;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class RegistraCompraFrame extends javax.swing.JInternalFrame {

    private Articulos articulo;
    private ArticulosCompraDAO articulosCompraDao;
    private FacturaCompraDAO facturaCompraDAO;
    private ArticuloDAO articuloDAO;
    FacturaCompra facturaCompra = new FacturaCompra();
    private Proveedor proveedor;
    List<ArticulosCompra> listaArticulos;
    String perfil = null;
    public DefaultTableModel tableModel;
    public ListSelectionModel listModelFactura;
    double subTotal;
    double IVA;
    double redondeo;

    public RegistraCompraFrame() {
        initComponents();
        this.perfil = frameLogin.getInstance().getPerfil();
        listaArticulos = new ArrayList();
        TableModel();
        AutoCompleteDecorator.decorate(txtNombreProveedor);
        completaComboBoxes();

    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;

        tableModel.addRow(new Object[]{articulo.getId(),
                    articulo.getNombre()});
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        proveedor.getId();
        proveedor.getNombre();
        txtNombreProveedor.setSelectedItem(proveedor);
        facturaCompra.setProveedor(proveedor);
    }

    private void completaComboBoxes() {

        try {
            List<Proveedor> listaProveedores = new ArrayList();

            DAOGenerico DAO = new DAOGenerico();
            listaProveedores = DAO.BuscaTodos(Proveedor.class);

            txtNombreProveedor.removeAllItems();

            for (Proveedor proveedores : listaProveedores) {

                txtNombreProveedor.addItem(proveedores);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        DefaultComboBoxModel comboModel = new DefaultComboBoxModel(MonedaEnum.values());
        cbMoneda.setModel(comboModel);
        cbMoneda.removeItemAt(2);

        DefaultComboBoxModel comboModel2 = new DefaultComboBoxModel(TipoDocumentoEnum.values());
        cbTipoFactura.setModel(comboModel2);

    }

    private void TableModel() {

        ((DefaultTableCellRenderer) tblArticulosCompra.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        tableModel = (DefaultTableModel) tblArticulosCompra.getModel();
        tableModel.setNumRows(0);

        tblArticulosCompra.getColumn("Código del Articulo").setPreferredWidth(30); //------> Ajusta el tamaño de las columnas
        tblArticulosCompra.getColumn("Nombre").setPreferredWidth(190);
        tblArticulosCompra.getColumn("Unidades").setPreferredWidth(5);
        tblArticulosCompra.getColumn("P. Unitario").setPreferredWidth(30);
        tblArticulosCompra.getColumn("Descuento %").setPreferredWidth(5);
        tblArticulosCompra.getColumn("P. Uni. Dto").setPreferredWidth(30);
        tblArticulosCompra.getColumn("Importe Neto").setPreferredWidth(30);


        txtTotal.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                txtRedondeo.setEnabled(true);
                txtRedondeo.setEditable(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txtRedondeo.setEnabled(true);
                txtRedondeo.setEditable(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                txtRedondeo.setEnabled(true);
                txtRedondeo.setEditable(true);
            }
        });
    }

    private Cotizacion verificaCotizacion() {

        Cotizacion cotizacion = new Cotizacion();
        CotizacionDAO cotizacionDAO = new CotizacionDAO();
        cotizacion = (Cotizacion) cotizacionDAO.BuscarUltimaCotizacion();

        return cotizacion;

    }

    private void confirmaCompra() {

        try {


            if (getProveedor() == null) {
                JOptionPane.showMessageDialog(this, "Selecione un proveedor!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un articulo a comprar!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                for (int i = 0; i < tableModel.getRowCount(); i++) {

                    String idArticulo = tableModel.getValueAt(i, 0).toString();
                    double valorSinIvaConDescuento = Double.valueOf(tableModel.getValueAt(i, 5).toString());
                    double valorConIvaConDescuento = Utilidades.Redondear(valorSinIvaConDescuento * 1.22, 2);
                    String descuento = (String) tableModel.getValueAt(i, 4);
                    double unidades = Double.valueOf(tableModel.getValueAt(i, 2).toString());

                    ArticulosCompra articulosCompra = new ArticulosCompra();

                    Articulos articulo = new Articulos();
                    articulo.setId(idArticulo);
                    articulosCompra.setArticulo(articulo);
                    articulosCompra.setDescuento(descuento);
                    articulosCompra.setCantidad(unidades);
                    articulosCompra.setFacturaCompra(facturaCompra);

                    String moneda = cbMoneda.getSelectedItem().toString();
                    switch (moneda) {
                        case "PESOS":

                            articulosCompra.setValorSinIva(valorSinIvaConDescuento);
                            articulosCompra.setValorConIva(valorConIvaConDescuento);
                            break;

                        case "DOLARES":

                            articulosCompra.setValorSinIva(Utilidades.Redondear(valorSinIvaConDescuento * verificaCotizacion().getDolares(), 2));
                            articulosCompra.setValorConIva(Utilidades.Redondear(valorConIvaConDescuento * verificaCotizacion().getDolares(), 2));

                            break;

                    }
                    articuloDAO = new ArticuloDAO();
                    Articulos oldArticulo = (Articulos) articuloDAO.buscaArtUnicoPorIDStr(articulosCompra.getArticulo().getId());
                    DAOGenerico dao = new DAOGenerico(articulosCompra);
                    dao.registra();


                    articulosCompraDao = new ArticulosCompraDAO();
                    articulosCompraDao.calculaCostoMedio(idArticulo, articulosCompra.getValorSinIva(), articulosCompra.getCantidad(), oldArticulo.getValor_compra(), oldArticulo.getCantidad());


                }
                JOptionPane.showMessageDialog(this, "Compra realizada correctamente!", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al salvar Articulo Compra" + e);
            return;
        }

    }

    private void retirarArticulo() {

        if (tblArticulosCompra.getSelectedRow() != -1) {
            tableModel.removeRow(tblArticulosCompra.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um articlo de lista!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtIVA = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulosCompra = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        btnSelecionaArticulo = new javax.swing.JButton();
        txtFiltroCodigo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        btnSelecionaProveedor = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JComboBox();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNroFactura = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSerieFactura = new javax.swing.JTextField();
        cbTipoFactura = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbMoneda = new javax.swing.JComboBox();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        jPanel4 = new javax.swing.JPanel();
        btnRetirarArticulo = new javax.swing.JButton();
        btnCalcularTotales = new javax.swing.JButton();
        btnRegistraCompra = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtRedondeo = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();

        jTextField1.setText("jTextField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(920, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Registrar Compras");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("IVA...........");
        jPanel2.add(jLabel7);

        txtIVA.setEnabled(false);
        txtIVA.setMinimumSize(new java.awt.Dimension(6, 17));
        txtIVA.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel2.add(txtIVA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        tblArticulosCompra.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblArticulosCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código del Articulo", "Nombre", "Unidades", "P. Unitario", "Descuento %", "P. Uni. Dto", "Importe Neto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, false, false
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
        tblArticulosCompra.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulosCompra.getColumnModel().getColumn(6).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jPanel5.setLayout(new java.awt.GridBagLayout());

        btnSelecionaArticulo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSelecionaArticulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/Zoom.png"))); // NOI18N
        btnSelecionaArticulo.setMnemonic('A');
        btnSelecionaArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaArticuloActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(btnSelecionaArticulo, gridBagConstraints);

        txtFiltroCodigo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtFiltroCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroCodigoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtFiltroCodigo, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setText("Busqueda rápida");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel9, gridBagConstraints);

        jTabbedPane1.addTab("Seleccione el Articulo", jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jTabbedPane1, gridBagConstraints);

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnSelecionaProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/Zoom.png"))); // NOI18N
        btnSelecionaProveedor.setMnemonic('P');
        btnSelecionaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaProveedorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 30;
        gridBagConstraints.gridheight = 40;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnSelecionaProveedor, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Proveedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jLabel2, gridBagConstraints);

        txtNombreProveedor.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtNombreProveedor, gridBagConstraints);

        jTabbedPane2.addTab("Seleccione el Proveedor", jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jTabbedPane2, gridBagConstraints);

        jTabbedPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jPanel10.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setText("Nro. Factura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(jLabel6, gridBagConstraints);

        txtNroFactura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNroFacturaFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(txtNroFactura, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Serie Factura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(txtSerieFactura, gridBagConstraints);

        cbTipoFactura.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(cbTipoFactura, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel10.add(jLabel4, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel12.setText("Tipo de Factura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel10.add(jLabel12, gridBagConstraints);

        cbMoneda.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(cbMoneda, gridBagConstraints);

        jTabbedPane3.addTab("Documento", jPanel10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jTabbedPane3, gridBagConstraints);

        jTabbedPane4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        dpFecha.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        dpFecha.setFormats("dd/MM/yyyy");
        jPanel11.add(dpFecha);

        jTabbedPane4.addTab("Fecha", jPanel11);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jTabbedPane4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnRetirarArticulo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnRetirarArticulo.setMnemonic('Q');
        btnRetirarArticulo.setText("Quitar Articulo");
        btnRetirarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetirarArticuloActionPerformed(evt);
            }
        });
        jPanel4.add(btnRetirarArticulo);

        btnCalcularTotales.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCalcularTotales.setMnemonic('C');
        btnCalcularTotales.setText("Calcular Totales");
        btnCalcularTotales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularTotalesActionPerformed(evt);
            }
        });
        jPanel4.add(btnCalcularTotales);

        btnRegistraCompra.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnRegistraCompra.setMnemonic('R');
        btnRegistraCompra.setText("Registrar Compra");
        btnRegistraCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraCompraActionPerformed(evt);
            }
        });
        jPanel4.add(btnRegistraCompra);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setText("Redondeo...");
        jPanel7.add(jLabel8);

        txtRedondeo.setEnabled(false);
        txtRedondeo.setMinimumSize(new java.awt.Dimension(6, 17));
        txtRedondeo.setPreferredSize(new java.awt.Dimension(100, 30));
        txtRedondeo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRedondeoActionPerformed(evt);
            }
        });
        txtRedondeo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRedondeoFocusLost(evt);
            }
        });
        jPanel7.add(txtRedondeo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel7, gridBagConstraints);

        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel10.setText("Total .........");
        jPanel8.add(jLabel10);

        txtTotal.setEnabled(false);
        txtTotal.setMinimumSize(new java.awt.Dimension(6, 17));
        txtTotal.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel8.add(txtTotal);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel8, gridBagConstraints);

        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel11.setText("Subtotal.....");
        jPanel9.add(jLabel11);

        txtSubTotal.setEnabled(false);
        txtSubTotal.setMinimumSize(new java.awt.Dimension(6, 17));
        txtSubTotal.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel9.add(txtSubTotal);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel9, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaProveedorActionPerformed

        ProveedorFrame proveedor = new ProveedorFrame(this);
        this.getDesktopPane().add(proveedor);
        proveedor.setVisible(true);
        proveedor.toFront();
    }//GEN-LAST:event_btnSelecionaProveedorActionPerformed

    private void btnRetirarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirarArticuloActionPerformed

        retirarArticulo();
    }//GEN-LAST:event_btnRetirarArticuloActionPerformed

    private void btnRegistraCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraCompraActionPerformed
        try {
            facturaCompraDAO = new FacturaCompraDAO();
            FacturaCompra factura = facturaCompraDAO.buscaPorNroFacturaYSerie(txtNroFactura.getText(), txtSerieFactura.getText());
            if (factura == null) {

                setProveedor((Proveedor) txtNombreProveedor.getSelectedItem());
                facturaCompra.setTipo((TipoDocumentoEnum) cbTipoFactura.getSelectedItem());
                facturaCompra.setTotal(Double.parseDouble(txtTotal.getText()));
                facturaCompra.setFecha(Utilidades.RecibeDateRetornaDateFormatoBD(dpFecha.getDate()));
                facturaCompra.setNroFactura(Integer.parseInt(txtNroFactura.getText()));
                facturaCompra.setSerieFactura(txtSerieFactura.getText());
                facturaCompra.setMoneda((MonedaEnum) cbMoneda.getSelectedItem());
                String tipoFactura = cbTipoFactura.getSelectedItem().toString();
                switch (tipoFactura) {
                    case ("Crédito"):
                        facturaCompra.setEstado(EstadoEnum.PENDIENTE);
                        confirmaCompra();
                        this.dispose();

                        break;
                    case ("Contado"):
                        facturaCompra.setEstado(EstadoEnum.PAGO);
                        confirmaCompra();
                        this.dispose();

                        break;
                }

            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un documento ingresado con el mismo nro y serie, verifique", "Error en el documento!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar la compra "+ex, "Error, contacte al técnico !", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnRegistraCompraActionPerformed

    private void btnSelecionaArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaArticuloActionPerformed

        FrameSeleccionaArticulo articuloCompra = new FrameSeleccionaArticulo(this, perfil);
        this.getDesktopPane().add(articuloCompra);
        articuloCompra.setVisible(true);
        articuloCompra.toFront();

    }//GEN-LAST:event_btnSelecionaArticuloActionPerformed

    private void txtFiltroCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroCodigoActionPerformed

        try {
            articuloDAO = new ArticuloDAO();
            Articulos articuloCompra = (Articulos) articuloDAO.buscaArtUnicoPorIDStr(txtFiltroCodigo.getText());

            tableModel.addRow(new Object[]{articuloCompra.getId(),
                        articuloCompra.getNombre()});

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Articulo no encontrado!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        txtFiltroCodigo.setText("");


    }//GEN-LAST:event_txtFiltroCodigoActionPerformed

    private void btnCalcularTotalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularTotalesActionPerformed

        try {

            for (int i = 0; i < tableModel.getRowCount(); i++) {

                double unidades = (double) tableModel.getValueAt(i, 2);
                double precioUnitario = (double) tableModel.getValueAt(i, 3);
                String descuentos = (String) tableModel.getValueAt(i, 4);

                if (descuentos.contains("+")) {

                    int indexSeperacion = descuentos.indexOf("+");
                    int lastIndexSeperacion = descuentos.lastIndexOf("+");

                    if (indexSeperacion == lastIndexSeperacion) {
                        int descuentoUno = Integer.parseInt(descuentos.substring(0, indexSeperacion));
                        int descuentoDos = Integer.parseInt(descuentos.substring(indexSeperacion + 1));


                        double precioUnitarioConDescuentoUno = Utilidades.Redondear(precioUnitario - (precioUnitario * descuentoUno / 100), 4);
                        double precioUnitarioConDescuentos = Utilidades.Redondear(precioUnitarioConDescuentoUno - (precioUnitarioConDescuentoUno * descuentoDos / 100), 4);


                        double valorNeto = Utilidades.Redondear((precioUnitarioConDescuentos * unidades), 4);
                        tableModel.setValueAt(Utilidades.Redondear(precioUnitarioConDescuentos, 2), i, 5);
                        tableModel.setValueAt(Utilidades.Redondear(valorNeto, 2), i, 6);

                    } else {

                        int descuentoUno = Integer.parseInt(descuentos.substring(0, indexSeperacion));
                        int descuentoDos = Integer.parseInt(descuentos.substring(indexSeperacion + 1, lastIndexSeperacion));
                        int descuentoTres = Integer.parseInt(descuentos.substring(lastIndexSeperacion + 1));


                        double precioUnitarioConDescuentoUno = Utilidades.Redondear(precioUnitario - (precioUnitario * descuentoUno / 100), 4);
                        double precioUnitarioConDescuentoDos = Utilidades.Redondear(precioUnitarioConDescuentoUno - (precioUnitarioConDescuentoUno * descuentoDos / 100), 4);
                        double precioUnitarioConDescuentos = Utilidades.Redondear(precioUnitarioConDescuentoDos - (precioUnitarioConDescuentoDos * descuentoTres / 100), 4);


                        double valorNeto = Utilidades.Redondear((precioUnitarioConDescuentos * unidades), 4);
                        tableModel.setValueAt(Utilidades.Redondear(precioUnitarioConDescuentos, 2), i, 5);
                        tableModel.setValueAt(Utilidades.Redondear(valorNeto, 2), i, 6);
                    }

                } else {

                    int descuentoUnico = Integer.parseInt(descuentos);
                    double precioUnitarioConDescuentoUnico = Utilidades.Redondear(precioUnitario - (precioUnitario * descuentoUnico / 100), 4);

                    double valorNeto = Utilidades.Redondear((precioUnitarioConDescuentoUnico * unidades), 4);
                    tableModel.setValueAt(Utilidades.Redondear(precioUnitarioConDescuentoUnico, 2), i, 5);
                    tableModel.setValueAt(Utilidades.Redondear(valorNeto, 2), i, 6);

                }

            }
            subTotal = 0;
            for (int i = 0; i < tblArticulosCompra.getRowCount(); i++) {

                double suma = (double) tblArticulosCompra.getValueAt(i, 6);
                subTotal = subTotal + suma;

            }
            txtSubTotal.setText(String.valueOf(Utilidades.Redondear(subTotal, 2)));
            redondeo = 0.0;
            txtRedondeo.setText(String.valueOf(redondeo));
            IVA = Utilidades.Redondear(subTotal * 22 / 100, 2);
            txtIVA.setText(String.valueOf(IVA));
            txtTotal.setText(String.valueOf(Utilidades.Redondear(subTotal + IVA + redondeo, 2)));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_btnCalcularTotalesActionPerformed

    private void txtRedondeoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRedondeoFocusLost

        try {
            if (txtRedondeo.getText().contains("-")) {

                redondeo = Double.valueOf(txtRedondeo.getText().replace("-", "0"));
                txtTotal.setText(String.valueOf(Utilidades.Redondear(subTotal + IVA - redondeo, 2)));

            } else {

                redondeo = Double.valueOf(txtRedondeo.getText());
                txtTotal.setText(String.valueOf(Utilidades.Redondear(subTotal + IVA + redondeo, 2)));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Caracteres invalidos!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtRedondeoFocusLost

    private void txtRedondeoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRedondeoActionPerformed

        try {
            if (txtRedondeo.getText().contains("-")) {

                redondeo = Double.valueOf(txtRedondeo.getText().replace("-", "0"));
                txtTotal.setText(String.valueOf(Utilidades.Redondear(subTotal + IVA - redondeo, 2)));

            } else {

                redondeo = Double.valueOf(txtRedondeo.getText());
                txtTotal.setText(String.valueOf(Utilidades.Redondear(subTotal + IVA + redondeo, 2)));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Caracteres invalidos!", "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_txtRedondeoActionPerformed

    private void txtNroFacturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNroFacturaFocusLost
        try {
            Integer caracteresIngresados = Integer.parseInt(txtNroFactura.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "El nro de factura solamente puede contener caracteres numéricos", "Caracteres invalidos!", JOptionPane.ERROR_MESSAGE);

            txtNroFactura.requestFocus();
        }
    }//GEN-LAST:event_txtNroFacturaFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcularTotales;
    private javax.swing.JButton btnRegistraCompra;
    private javax.swing.JButton btnRetirarArticulo;
    private javax.swing.JButton btnSelecionaArticulo;
    private javax.swing.JButton btnSelecionaProveedor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbMoneda;
    private javax.swing.JComboBox cbTipoFactura;
    private org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblArticulosCompra;
    private javax.swing.JTextField txtFiltroCodigo;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JComboBox txtNombreProveedor;
    private javax.swing.JTextField txtNroFactura;
    private javax.swing.JTextField txtRedondeo;
    private javax.swing.JTextField txtSerieFactura;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
