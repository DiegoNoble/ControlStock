package com.Compras;

import Utilidades.ControlarEntradaTexto;
import com.Articulos.ArticulosFrame;
import com.Beans.Articulo;
import com.Beans.ArticulosCompra;
import com.Beans.ArticulosPedido;
import com.Beans.EquivalenciaUnidades;
import com.Beans.FacturaCompra;
import com.Beans.MovStock;
import com.Beans.Proveedor;
import com.DAO.ArticuloDAO;
import com.DAO.ArticulosCompraDAO;
import com.DAO.FacturaCompraDAO;
import com.DAO.MovStockDAO;
import com.DAO.ProveedorDAO;
import com.Proveedores.ProveedoresFrame;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class RegistraCompra extends javax.swing.JInternalFrame {

    List<Proveedor> listProveedores;
    List<Articulo> listArticulos;
    ArticulosCompraTableModel tableModel;
    List<ArticulosCompra> listArticulosCompra;
    FacturaCompra compra;
    ArticuloDAO articulosDAO;
    ProveedorDAO proveedorDAO;
    ArticulosCompraDAO articulosCompraDAO;
    FacturaCompraDAO compraDAO;

    public RegistraCompra() {
        initComponents();

        dpFecha.setDate(new Date());

        defineModelo();

        AutoCompleteDecorator.decorate(cbProveedor);
        AutoCompleteDecorator.decorate(cbArticulos);
        cargaComboArticulos();
        cargaComboProveedores();

    }

    private void cargaComboProveedores() {

        try {
            listProveedores = new ArrayList();

            proveedorDAO = new ProveedorDAO();
            listProveedores = proveedorDAO.BuscaTodos(Proveedor.class);

            cbProveedor.removeAllItems();

            for (Proveedor proveedor : listProveedores) {

                cbProveedor.addItem(proveedor);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar proveedores" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void cargaComboArticulos() {
        try {
            articulosDAO = new ArticuloDAO();

            cbArticulos.removeAllItems();
            listArticulos = articulosDAO.BuscaTodos(Articulo.class);

            for (Articulo articulo : listArticulos) {

                cbArticulos.addItem(articulo);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar articulos" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void defineModelo() {

        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        txtCantidad.setDocument(new ControlarEntradaTexto(10, chs));
        txtValorUnitario.setDocument(new ControlarEntradaTexto(10, chs));

        ((DefaultTableCellRenderer) tblArticulosCompra.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listArticulosCompra = new ArrayList<ArticulosCompra>();
        tableModel = new ArticulosCompraTableModel(listArticulosCompra, txtTotal, txtSubTotal, txtIva);

        tblArticulosCompra.setModel(tableModel);

        int[] anchos = {5, 100, 200, 20, 20, 20, 50};

        for (int i = 0; i < tblArticulosCompra.getColumnCount(); i++) {

            tblArticulosCompra.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        ListSelectionModel listModel = tblArticulosCompra.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblArticulosCompra.getSelectedRow() != -1) {
                    btnEliminar.setEnabled(true);
                } else {
                    btnEliminar.setEnabled(false);
                }
            }
        });

    }

    private void confirmaCompra() {

        try {
            if (listArticulosCompra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un articulo", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                compra = new FacturaCompra();
                Double total = 0.0;
                Double subtotal = 0.0;
                Double iva = 0.0;
                List<MovStock> listMovStock = new ArrayList<>();
                compra = new FacturaCompra();
                for (ArticulosCompra articulosCompra : listArticulosCompra) {
                    subtotal = subtotal + articulosCompra.getValor() * articulosCompra.getCantidad();
                    articulosCompra.setFacturaCompra(compra);

                    Double factor = articulosCompra.getEquivalenciaUnidades().getFactor_conversion();
                    MovStock movStock = new MovStock();
                    movStock.setArticulo(articulosCompra.getArticulo());
                    movStock.setCantidadMov(articulosCompra.getCantidad()*factor);
                    movStock.setFecha(new Date());
                    movStock.setSaldoStock(articulosCompra.getArticulo().getCantidad() + (articulosCompra.getCantidad()*factor));

                    listMovStock.add(movStock);

                    Articulo articulo = movStock.getArticulo();
                    articulo.setCantidad(movStock.getSaldoStock());
                    articulo.setValor_compra(articulosCompra.getValor()/factor);
                    articulosDAO = new ArticuloDAO(articulo);
                    articulosDAO.actualiza();

                }
                total = subtotal * 1.21;
                iva = total - subtotal;

                compra.setTotal(total);
                compra.setIva(iva);
                compra.setSubtotal(subtotal);
                compra.setFecha(dpFecha.getDate());
                compra.setNroDocumento(txtDocumento.getText());
                compra.setProveedor((Proveedor) cbProveedor.getSelectedItem());
                compra.setArticulosCompra(listArticulosCompra);
                compraDAO = new FacturaCompraDAO(compra);
                compraDAO.guardar();

                for (MovStock movStock : listMovStock) {
                    movStock.setFacturaCompra(compra);
                    MovStockDAO movStockDAO = new MovStockDAO(movStock);
                    movStockDAO.guardar();

                }

                JOptionPane.showMessageDialog(this, "Compra registrada correctamente!", "Información", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
        }
    }

    void limbiaCampo() {
        txtCantidad.setText("");
        txtValorUnitario.setText("");
    }

    public void agregarArticuloCompra(Articulo articulo) {
        if (listArticulos.contains(articulo)) {

            cbArticulos.setSelectedItem(articulo);
        } else {

            cargaComboArticulos();
            cbArticulos.setSelectedItem(articulo);
        }
    }

    public void agregarProveedor(Proveedor proveedor) {
        if (listProveedores.contains(proveedor)) {
            cbProveedor.setSelectedItem(proveedor);
        } else {
            cbProveedor.addItem(proveedor);
            cbProveedor.setSelectedItem(proveedor);
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
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        btnSelecionaProveedor = new javax.swing.JButton();
        cbProveedor = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulosCompra = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        cbArticulos = new javax.swing.JComboBox();
        txtCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtValorUnitario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbEquivalencias = new javax.swing.JComboBox();
        jPanel10 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        txtIva = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        btnRegistraVenta = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(1024, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Registrar compra");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel11.setLayout(new java.awt.GridBagLayout());

        btnSelecionaProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnSelecionaProveedor.setBorder(null);
        btnSelecionaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaProveedorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(btnSelecionaProveedor, gridBagConstraints);

        cbProveedor.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(cbProveedor, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Proveedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(jLabel7, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Nro documento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(jLabel13, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(txtDocumento, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Fecha factura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel11.add(dpFecha, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel3.add(jPanel11, gridBagConstraints);

        jPanel12.setLayout(new java.awt.GridBagLayout());

        tblArticulosCompra.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblArticulosCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblArticulosCompra.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblArticulosCompra);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel12.add(jScrollPane1, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnBuscar.setToolTipText("Buscar articulo");
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel13.add(btnBuscar, gridBagConstraints);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/add.png"))); // NOI18N
        btnNuevo.setToolTipText("Nueva posición");
        btnNuevo.setBorder(null);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel13.add(btnNuevo, gridBagConstraints);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/delete.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar posición");
        btnEliminar.setBorder(null);
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel13.add(btnEliminar, gridBagConstraints);

        cbArticulos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbArticulosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(cbArticulos, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtCantidad, gridBagConstraints);

        jLabel5.setText("Articulo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel13.add(jLabel5, gridBagConstraints);

        jLabel8.setText("Valor unitario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        jPanel13.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtValorUnitario, gridBagConstraints);

        jLabel9.setText("Cantidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel13.add(jLabel9, gridBagConstraints);

        jLabel6.setText("Unidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel13.add(jLabel6, gridBagConstraints);

        cbEquivalencias.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(cbEquivalencias, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel12.add(jPanel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jPanel12, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel3.add(jPanel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel10.setText("Subtotal");
        jPanel8.add(jLabel10, new java.awt.GridBagConstraints());

        txtSubTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$ #,##0.00"))));
        txtSubTotal.setEnabled(false);
        txtSubTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 140;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel8.add(txtSubTotal, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel11.setText("IVA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel8.add(jLabel11, gridBagConstraints);

        txtIva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$ #,##0.00"))));
        txtIva.setEnabled(false);
        txtIva.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 140;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel8.add(txtIva, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel12.setText("Total compra");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel8.add(jLabel12, gridBagConstraints);

        txtTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$ #,##0.00"))));
        txtTotal.setEnabled(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 140;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel8.add(txtTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jPanel8, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnRegistraVenta.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnRegistraVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/pedido.png"))); // NOI18N
        btnRegistraVenta.setMnemonic('R');
        btnRegistraVenta.setText("Registrar compra");
        btnRegistraVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraVentaActionPerformed(evt);
            }
        });
        jPanel4.add(btnRegistraVenta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistraVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraVentaActionPerformed

        confirmaCompra();
        listArticulosCompra.clear();
        tableModel.fireTableDataChanged();
        txtIva.setText("");
        txtSubTotal.setText("");
        txtTotal.setText("");


    }//GEN-LAST:event_btnRegistraVentaActionPerformed

    private void btnSelecionaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaProveedorActionPerformed

        ProveedoresFrame proveedoresFrame = new ProveedoresFrame(this);
        this.getDesktopPane().add(proveedoresFrame);
        proveedoresFrame.setVisible(true);
        proveedoresFrame.toFront();
    }//GEN-LAST:event_btnSelecionaProveedorActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        ArticulosFrame articulosFrame = new ArticulosFrame(this);
        this.getDesktopPane().add(articulosFrame);
        articulosFrame.setVisible(true);
        articulosFrame.toFront();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        Articulo articulo = (Articulo) cbArticulos.getSelectedItem();
        Double cantidad = Double.valueOf(txtCantidad.getText());
        Double unitario = Double.valueOf(txtValorUnitario.getText());
        
        if (cantidad != 0 && unitario != 0) {

            tableModel.agregar(new ArticulosCompra(listArticulosCompra.size() + 1, unitario, cantidad, articulo, (EquivalenciaUnidades) cbEquivalencias.getSelectedItem()));
            limbiaCampo();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione una cantidad y un valor unitario!", "Erro", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        tableModel.eliminar(tblArticulosCompra.getSelectedRow());

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void cbArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbArticulosActionPerformed

        cbEquivalencias.removeAllItems();
        for (EquivalenciaUnidades equivalenciaUnidades : ((Articulo) cbArticulos.getSelectedItem()).getListEquivalencias()) {
            cbEquivalencias.addItem(equivalenciaUnidades);
        }

    }//GEN-LAST:event_cbArticulosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRegistraVenta;
    private javax.swing.JButton btnSelecionaProveedor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbArticulos;
    private javax.swing.JComboBox cbEquivalencias;
    private javax.swing.JComboBox cbProveedor;
    private org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblArticulosCompra;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JFormattedTextField txtIva;
    private javax.swing.JFormattedTextField txtSubTotal;
    private javax.swing.JFormattedTextField txtTotal;
    private javax.swing.JTextField txtValorUnitario;
    // End of variables declaration//GEN-END:variables

}
