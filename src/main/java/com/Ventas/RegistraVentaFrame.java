package com.Ventas;

import Utilidades.HibernateUtil;
import com.Beans.Factura;
import com.Beans.ArticulosVenta;
import com.DAO.DAOGenerico;
import Utilidades.Utilidades;
import com.Beans.Articulo;
import com.Beans.Caja;
import com.Beans.MonedaEnum;
import com.Beans.TipoDocumentoEnum;
import com.Clientes.ClienteFrame;
import com.Beans.Cliente;
import com.Beans.Rubros;
import com.CuentasRecibir.CreditosVentas;
import com.DAO.ArticuloDAO;
import com.Renderers.MyDefaultCellRenderer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class RegistraVentaFrame extends javax.swing.JInternalFrame {

    private Articulo articulo;
    private Cliente Clie;
    public DefaultTableModel tableModel;
    List<ArticulosVenta> listaArticulos;
    private Factura facturaVenta;
    private static Factura facturaVentaSingleton;
    private ArticuloDAO articulosDAO;
    double subTotal;
    double IVA;
    double redondeo;

    public RegistraVentaFrame() {
        initComponents();
        txtNroDocumento.setVisible(false);
        lblNroDocumento.setVisible(false);

        DateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
        dpFecha.setDate(new Date());
        Date fecha = dpFecha.getDate();

        String date = formatoData.format(fecha);

        Date fechaBD = null;
        try {
            fechaBD = formatoData.parse(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error convirtiendo Fecha" + ex);
        }

        listaArticulos = new ArrayList<ArticulosVenta>();
        TableModel();
        facturaVenta = new Factura();
        facturaVenta.setFecha(fechaBD);

        AutoCompleteDecorator.decorate(cbNombreCliente);
        cargaComboBoxes();

    }

    public static Factura getInstance() {

        if (facturaVentaSingleton == null) {
            facturaVentaSingleton = new Factura();
        }

        return facturaVentaSingleton;
    }

    private void cargaComboBoxes() {

        try {
            List<Cliente> listadeClientes = new ArrayList();

            DAOGenerico dao = new DAOGenerico();
            listadeClientes = dao.BuscaTodos(Cliente.class);

            cbNombreCliente.removeAllItems();

            for (Cliente clientes : listadeClientes) {

                cbNombreCliente.addItem(clientes);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        DefaultComboBoxModel Moneda = new DefaultComboBoxModel(MonedaEnum.values());
        cbMoneda.setModel(Moneda);
        cbMoneda.removeItemAt(2);

        DefaultComboBoxModel comboModelTipo = new DefaultComboBoxModel(TipoDocumentoEnum.values());
        cbTipoFactura.setModel(comboModelTipo);

    }

    private void TableModel() {

        ((DefaultTableCellRenderer) tblArticulosVenta.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        tableModel = (DefaultTableModel) tblArticulosVenta.getModel();
        tableModel.setNumRows(0);

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

    private void confirmaVenta() {

        try {
            if (getClie() == null) {
                JOptionPane.showMessageDialog(this, "Selecione un cliente!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un articulo a vender!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                for (int i = 0; i < tableModel.getRowCount(); i++) {

                    String idArticulo = tableModel.getValueAt(i, 0).toString();
                    double valorSinIvaConDescuento = Double.valueOf(tableModel.getValueAt(i, 5).toString());
                    double valorConIvaConDescuento = Utilidades.Redondear(valorSinIvaConDescuento * 1.22, 2);
                    String descuento = (String) tableModel.getValueAt(i, 4);
                    double unidades = Double.valueOf(tableModel.getValueAt(i, 2).toString());

                    ArticulosVenta articulosVenta = new ArticulosVenta();

                    Articulo articulo = new Articulo();
                    articulo.setId(idArticulo);
                    articulosVenta.setArticulo(articulo);
                    articulosVenta.setDescuento(descuento);
                    articulosVenta.setValorConIva(valorConIvaConDescuento);
                    articulosVenta.setValorSinIva(valorSinIvaConDescuento);
                    articulosVenta.setCantidad(unidades);
                    articulosVenta.setFactura(facturaVenta);

                    DAOGenerico dao = new DAOGenerico(articulosVenta);
                    dao.guardar();

                }
                JOptionPane.showMessageDialog(this, "Venta realizada correctamente!", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
        }
    }

    private void retirarArticulo() {

        if (tblArticulosVenta.getSelectedRow() != -1) {
            tableModel.removeRow(tblArticulosVenta.getSelectedRow());
            calcularTotales();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item da lista!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void registraMovimiento() {
        try {
            DateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
            Date fecha = dpFecha.getDate();

            String date = formatoData.format(fecha);

            Date fechaBD = null;

            fechaBD = formatoData.parse(date);

            if (cbMoneda.getSelectedItem() == MonedaEnum.PESOS) {

                Session seccion = HibernateUtil.getSeccion();
                Transaction transaccion = seccion.beginTransaction();

                String valor = (txtTotal.getText()).replace(",", ".");

                Caja caja = new Caja();
                caja.setConcepto("VENTA MERCADERÍA");
                caja.setFecha(fechaBD);

                caja.setRubro(new Rubros(8));
                caja.setEntrada_pesos(Double.parseDouble(valor));
                seccion.save(caja);
                transaccion.commit();
                seccion.close();

            } else if (cbMoneda.getSelectedItem() == MonedaEnum.REALES) {

                Session seccion = HibernateUtil.getSeccion();
                Transaction transaccion = seccion.beginTransaction();

                String valor = (txtTotal.getText()).replace(",", ".");

                Caja caja = new Caja();
                caja.setConcepto("VENTA MERCADERÍA");
                caja.setFecha(fechaBD);

                caja.setRubro(new Rubros(8));
                caja.setEntrada_reales(Double.parseDouble(valor));
                seccion.save(caja);
                transaccion.commit();
                seccion.close();
            } else if (cbMoneda.getSelectedItem() == MonedaEnum.DOLARES) {

                Session seccion = HibernateUtil.getSeccion();
                Transaction transaccion = seccion.beginTransaction();

                Caja caja = new Caja();
                caja.setConcepto("VENTA MERCADERÍA");
                caja.setFecha(fechaBD);

                caja.setRubro(new Rubros(8));
                String valor = (txtTotal.getText()).replace(",", ".");
                caja.setEntrada_dolares(Double.parseDouble(valor));
                seccion.save(caja);
                transaccion.commit();
                seccion.close();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);

        }
    }

    void calcularTotales() {
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
            for (int i = 0; i < tblArticulosVenta.getRowCount(); i++) {

                double suma = (double) tblArticulosVenta.getValueAt(i, 6);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        btnSelecionaArticulo = new javax.swing.JButton();
        txtFiltroCodigo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        btnSelecionaCliente = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cbNombreCliente = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulosVenta = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        jLabel4 = new javax.swing.JLabel();
        cbTipoFactura = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cbMoneda = new javax.swing.JComboBox();
        lblNroDocumento = new javax.swing.JLabel();
        txtNroDocumento = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtIVA = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtRedondeo = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnRetirarArticulo = new javax.swing.JButton();
        btnCalcularTotales = new javax.swing.JButton();
        btnRegistraVenta = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(900, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Registrar Venta");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jPanel5.setLayout(new java.awt.GridBagLayout());

        btnSelecionaArticulo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSelecionaArticulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/Zoom.png"))); // NOI18N
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jTabbedPane1, gridBagConstraints);

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnSelecionaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/Zoom.png"))); // NOI18N
        btnSelecionaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 30;
        gridBagConstraints.gridheight = 40;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnSelecionaCliente, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jLabel2, gridBagConstraints);

        cbNombreCliente.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(cbNombreCliente, gridBagConstraints);

        jTabbedPane2.addTab("Seleccione el Cliente", jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jTabbedPane2, gridBagConstraints);

        tblArticulosVenta.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblArticulosVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código del Articulo", "Nombre", "Unidades", "P. Unitario", "Descuento %", "P. Uni. Dto", "Importe Neto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
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
        tblArticulosVenta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblArticulosVenta);
        if (tblArticulosVenta.getColumnModel().getColumnCount() > 0) {
            tblArticulosVenta.getColumnModel().getColumn(0).setPreferredWidth(25);
            tblArticulosVenta.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
            tblArticulosVenta.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblArticulosVenta.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
            tblArticulosVenta.getColumnModel().getColumn(2).setPreferredWidth(5);
            tblArticulosVenta.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
            tblArticulosVenta.getColumnModel().getColumn(3).setPreferredWidth(5);
            tblArticulosVenta.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
            tblArticulosVenta.getColumnModel().getColumn(4).setPreferredWidth(5);
            tblArticulosVenta.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
            tblArticulosVenta.getColumnModel().getColumn(5).setPreferredWidth(20);
            tblArticulosVenta.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
            tblArticulosVenta.getColumnModel().getColumn(6).setPreferredWidth(20);
            tblArticulosVenta.getColumnModel().getColumn(6).setCellRenderer(new MyDefaultCellRenderer());
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Fecha de la factura:");
        jPanel10.add(jLabel3);
        jPanel10.add(dpFecha);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Tipo de Factura");
        jPanel10.add(jLabel4);

        cbTipoFactura.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbTipoFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoFacturaActionPerformed(evt);
            }
        });
        jPanel10.add(cbTipoFactura);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Moneda");
        jPanel10.add(jLabel5);

        cbMoneda.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jPanel10.add(cbMoneda);

        lblNroDocumento.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        lblNroDocumento.setText("Nro. Referencia");
        jPanel10.add(lblNroDocumento);

        txtNroDocumento.setMinimumSize(new java.awt.Dimension(30, 22));
        txtNroDocumento.setPreferredSize(new java.awt.Dimension(70, 22));
        jPanel10.add(txtNroDocumento);

        jTabbedPane3.addTab("Documento", jPanel10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jTabbedPane3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

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

        btnRegistraVenta.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnRegistraVenta.setMnemonic('R');
        btnRegistraVenta.setText("Registrar Venta");
        btnRegistraVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraVentaActionPerformed(evt);
            }
        });
        jPanel4.add(btnRegistraVenta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaClienteActionPerformed

        ClienteFrame clienteFrame = new ClienteFrame(this);
        this.getDesktopPane().add(clienteFrame);
        clienteFrame.setVisible(true);
        clienteFrame.toFront();

    }//GEN-LAST:event_btnSelecionaClienteActionPerformed

    private void btnSelecionaArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaArticuloActionPerformed

        /*Articu articuloVenta = new FrameSeleccionaArticulo(this);
        this.getDesktopPane().add(articuloVenta);
        articuloVenta.setVisible(true);
        articuloVenta.toFront();
*/
    }//GEN-LAST:event_btnSelecionaArticuloActionPerformed

    private void txtFiltroCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroCodigoActionPerformed

        try {

            articulosDAO = new ArticuloDAO();
            Articulo articuloCompra = (Articulo) articulosDAO.buscaArtUnicoPorIDStr(txtFiltroCodigo.getText());
            //Articulos articuloAcessoRapido = new Articulos();
            //articuloAcessoRapido.setId(articuloCompra.getId());
            //articuloAcessoRapido.setNombre(articuloCompra.getNombre());

            BigDecimal valorUnitario = new BigDecimal(articuloCompra.getValor_venta() / 1.22);
            Double valorVenta = valorUnitario.setScale(2, RoundingMode.CEILING).doubleValue();
            tableModel.addRow(new Object[]{articuloCompra.getId(),
                articuloCompra.getNombre(), new Double(1.0), valorVenta, "0"});
            //calcularTotales();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Articulo no encontrado!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        txtFiltroCodigo.setText("");

    }//GEN-LAST:event_txtFiltroCodigoActionPerformed

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

    private void btnRetirarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirarArticuloActionPerformed

        retirarArticulo();
    }//GEN-LAST:event_btnRetirarArticuloActionPerformed

    private void btnCalcularTotalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularTotalesActionPerformed
        calcularTotales();

    }//GEN-LAST:event_btnCalcularTotalesActionPerformed

    private void btnRegistraVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraVentaActionPerformed

        setClie((Cliente) cbNombreCliente.getSelectedItem());
        facturaVenta.setTipo((TipoDocumentoEnum) cbTipoFactura.getSelectedItem());
        facturaVenta.setTotal(Double.parseDouble(txtTotal.getText()));
        facturaVenta.setFecha(Utilidades.RecibeDateRetornaDateFormatoBD(dpFecha.getDate()));
        facturaVenta.setMoneda((MonedaEnum) cbMoneda.getSelectedItem());
        String tipoFactura = cbTipoFactura.getSelectedItem().toString();

        switch (tipoFactura) {
            case ("Crédito"):
                facturaVenta.setNroDocumento(Integer.parseInt(txtNroDocumento.getText()));
                facturaVentaSingleton = facturaVenta;
                confirmaVenta();
                CreditosVentas venta = new CreditosVentas(null, true);
                venta.setLocationRelativeTo(null);
                venta.setVisible(true);
                this.dispose();

                break;
            case ("Contado"):
                confirmaVenta();
                registraMovimiento();
                this.dispose();

                break;
        }

    }//GEN-LAST:event_btnRegistraVentaActionPerformed

    private void cbTipoFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoFacturaActionPerformed

        if (cbTipoFactura.getSelectedItem().equals(TipoDocumentoEnum.CREDITO)) {
            txtNroDocumento.setVisible(true);
            lblNroDocumento.setVisible(true);

        } else {
            txtNroDocumento.setVisible(false);
            lblNroDocumento.setVisible(false);
        }
    }//GEN-LAST:event_cbTipoFacturaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcularTotales;
    private javax.swing.JButton btnRegistraVenta;
    private javax.swing.JButton btnRetirarArticulo;
    private javax.swing.JButton btnSelecionaArticulo;
    private javax.swing.JButton btnSelecionaCliente;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbMoneda;
    private javax.swing.JComboBox cbNombreCliente;
    private javax.swing.JComboBox cbTipoFactura;
    private org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblNroDocumento;
    private javax.swing.JTable tblArticulosVenta;
    private javax.swing.JTextField txtFiltroCodigo;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JTextField txtNroDocumento;
    private javax.swing.JTextField txtRedondeo;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;

        BigDecimal valorUnitario = new BigDecimal(articulo.getValor_venta() / 1.22);
        Double valorVenta = valorUnitario.setScale(2, RoundingMode.CEILING).doubleValue();
        tableModel.addRow(new Object[]{articulo.getId(),
            articulo.getNombre(), new Double(1.0), valorVenta, "0"});
        //calcularTotales();
    }

    public Cliente getClie() {
        return Clie;
    }

    public void setClie(Cliente Clie) {
        this.Clie = Clie;
        Clie.getId_cliente();
        cbNombreCliente.setSelectedItem(Clie);
        facturaVenta.setCliente(Clie);
    }
}
