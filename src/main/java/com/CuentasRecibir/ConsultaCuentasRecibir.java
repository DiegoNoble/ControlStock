package com.CuentasRecibir;

import com.Beans.PagoCreditosVenta;
import com.Beans.CreditosVenta;
import com.Beans.Factura;
import com.DAO.DAOGenerico;
import com.Ventas.*;
import Utilidades.Utilidades;
import com.Beans.EstadoEnum;
import com.Beans.Cliente;
import com.DAO.FacturaVentaDAO;
import com.Renderers.MyCalendarCellRenderer;
import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import java.lang.Exception;
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

public class ConsultaCuentasRecibir extends javax.swing.JInternalFrame {

    private DefaultTableModel tableModelFacturas;
    private ListSelectionModel listModelFacturas;
    private DefaultTableModel tableModelCreditos;
    private ListSelectionModel listModelCreditos;
    private DefaultTableModel tableModelPagos;
    private ListSelectionModel listModelPagos;
    private List<Cliente> listaClientes;
    Integer idClienteSeleccionado = null;
    String fechaInicio, fechaFin;
    private List<PagoCreditosVenta> listaPagos;
    private List<Factura> listaFacturas;
    private List<CreditosVenta> listaCreditos;
    private FacturaVentaDAO facturaVentaDAO;

    public ConsultaCuentasRecibir() {
        initComponents();
        setSize(850, 650);
        defineModelo();

        AutoCompleteDecorator.decorate(cbNombres);

        listaClientes = new DAOGenerico().BuscaTodos(Cliente.class);

        for (Cliente clientes : listaClientes) {
            cbNombres.addItem(clientes);
        }

    }

    private void defineModelo() {

        tableModelFacturas = (DefaultTableModel) tblFacturas.getModel();
        tableModelCreditos = (DefaultTableModel) tblCreditos.getModel();
        tableModelPagos = (DefaultTableModel) tblPagos.getModel();
        listModelFacturas = tblFacturas.getSelectionModel();
        listModelFacturas.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    muestraCreditos();
                }
            }
        });

        listModelCreditos = tblCreditos.getSelectionModel();
        listModelCreditos.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    muestraPagos();
                }
            }
        });

        listModelPagos = tblPagos.getSelectionModel();
        listModelPagos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (tblPagos.getSelectedRow() != -1) {
                    btnRegistrarPago.setEnabled(true);
                } else {
                    btnRegistrarPago.setEnabled(false);
                }
            }
        });


        try {

            ((DefaultTableCellRenderer) tblFacturas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            ((DefaultTableCellRenderer) tblCreditos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            ((DefaultTableCellRenderer) tblPagos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        } catch (Exception ex) {
            Logger.getLogger(ConsultaCuentasRecibir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizatblFacturas() {


        remueveFacturas();

        for (Factura factura : listaFacturas) {

            tableModelFacturas.addRow(new Object[]{
                        factura.getCliente().getNombre(),
                        factura.getIdfactura(),
                        factura.getNroDocumento(),
                        factura.getFecha(),
                        factura.getTipo(),
                        factura.getMoneda().getDescription(),
                        factura.getTotal()});

        }
    }

    private void muestraCreditos() {

        try {
            if (tblFacturas.getSelectedRow() != -1) {

                int factura = (int) listaFacturas.get(tblFacturas.getSelectedRow()).getIdfactura();

                listaCreditos = new DAOGenerico().buscarPor(CreditosVenta.class, "id_factura", String.valueOf(factura));

                remueveCreditos();

                for (CreditosVenta creditosVenta : listaCreditos) {

                    tableModelCreditos.addRow(new Object[]{creditosVenta.getId(),
                                creditosVenta.getNroCuotas(),
                                creditosVenta.getEntrega(),
                                creditosVenta.getValorCredito()});

                    tblCreditos.setRowSelectionInterval(0, 0);

                }
            } else {
                remueveCreditos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void muestraPagos() {

        try {
            if (tblCreditos.getSelectedRow() != -1) {

                int factura = (int) listaCreditos.get(tblCreditos.getSelectedRow()).getId();
                listaPagos = new DAOGenerico().buscarPor(PagoCreditosVenta.class, "id_credito", String.valueOf(factura));

                remuevePagos();

                for (PagoCreditosVenta pagos : listaPagos) {

                    tableModelPagos.addRow(new Object[]{pagos.getId(),
                                pagos.getFecha(),
                                pagos.getFecha_pago(),
                                pagos.getNro_cuota(),
                                pagos.getValor(),
                                pagos.getEstado()});

                }

            } else {
                remuevePagos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void remueveFacturas() {
        DefaultTableModel modelo = (DefaultTableModel) tblFacturas.getModel();
        int numeroLineas = tblFacturas.getRowCount();
        for (int i = 0; i < numeroLineas; i++) {
            modelo.removeRow(0);
        }
    }

    private void remueveCreditos() {
        DefaultTableModel modelo = (DefaultTableModel) tblCreditos.getModel();
        int numeroLineas = tblCreditos.getRowCount();
        for (int i = 0; i < numeroLineas; i++) {
            modelo.removeRow(0);
        }
    }

    private void remuevePagos() {
        DefaultTableModel modelo = (DefaultTableModel) tblPagos.getModel();
        int numeroLineas = tblPagos.getRowCount();
        for (int i = 0; i < numeroLineas; i++) {
            modelo.removeRow(0);
        }
    }

    private void regitrarPago() {

        PagoCreditosVenta pago = listaPagos.get(tblPagos.getSelectedRow());
        if (pago.getEstado().equals(EstadoEnum.PAGO)) {
            JOptionPane.showMessageDialog(null, "La cuota seleccionada ya esta saldada", "Atención", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                JdPagoParcial msg = new JdPagoParcial(null, closable, pago, this);
                msg.setVisible(true);
                msg.toFront();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPagos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCreditos = new javax.swing.JTable();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblFacturas = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        cbNombres = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dpInicio = new org.jdesktop.swingx.JXDatePicker();
        jLabel7 = new javax.swing.JLabel();
        dpFin = new org.jdesktop.swingx.JXDatePicker();
        btnBuscaPorFecha = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNroFactura = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNroReferencia = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnRegistrarPago = new javax.swing.JButton();

        jRadioButton1.setText("jRadioButton1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(700, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cuentas a recibir");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        tblPagos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Fecha Vencimiento", "Fecha del Pago", "Cuota", "Saldo $", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPagos.setToolTipText("Para registrar un pago, seleccione en la grilla la cuota que desea dar de baja, y luego, click en el botón \"Registrar Pago\".");
        tblPagos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblPagos);
        tblPagos.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblPagos.getColumnModel().getColumn(1).setCellRenderer(new MyCalendarCellRenderer());
        tblPagos.getColumnModel().getColumn(2).setCellRenderer(new MyDateCellRenderer());
        tblPagos.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblPagos.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
        tblPagos.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 120;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(jScrollPane1, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setText("Pagos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel4, gridBagConstraints);

        tblCreditos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblCreditos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Cantidad de Cuotas", "Entrega", "Total del Crédito"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCreditos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblCreditos);
        tblCreditos.getColumnModel().getColumn(0).setMinWidth(0);
        tblCreditos.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblCreditos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCreditos.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblCreditos.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblCreditos.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblCreditos.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 450;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.weighty = 2.0;
        jPanel4.add(jScrollPane2, gridBagConstraints);

        jTabbedPane1.addTab("Detalles de la Cuenta", jPanel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel3.add(jTabbedPane1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        tblFacturas.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cliente", "Factura", "Referencia", "Fecha", "Tipo", "Moneda", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
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
        tblFacturas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(tblFacturas);
        tblFacturas.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturas.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturas.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturas.getColumnModel().getColumn(3).setCellRenderer(new MyDateCellRenderer());
        tblFacturas.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturas.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
        tblFacturas.getColumnModel().getColumn(6).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 120;
        gridBagConstraints.weightx = 1.0;
        jPanel6.add(jScrollPane7, gridBagConstraints);

        jTabbedPane6.addTab("Facturas", jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel3.add(jTabbedPane6, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N
        jPanel5.setLayout(new java.awt.GridBagLayout());

        cbNombres.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNombresActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbNombres, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel2.setText("Cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jLabel2, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Por Fechas:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel5, gridBagConstraints);

        dpInicio.setFormats("dd/MM/yyyy");
        dpInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpInicioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel5.add(dpInicio, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("al");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel7, gridBagConstraints);

        dpFin.setFormats("dd/MM/yyyy");
        dpFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpFinActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel5.add(dpFin, gridBagConstraints);

        btnBuscaPorFecha.setText("Buscar");
        btnBuscaPorFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaPorFechaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(btnBuscaPorFecha, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setText("Nro Factura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel6, gridBagConstraints);

        txtNroFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroFacturaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtNroFactura, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setText("Nro. Referencia");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel8, gridBagConstraints);

        txtNroReferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroReferenciaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtNroReferencia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel3.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        btnRegistrarPago.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnRegistrarPago.setText("Registrar pago");
        btnRegistrarPago.setToolTipText("Para registrar un pago, seleccione la cuota que desea pagar en la tabla \"Pagos\" y luego presione \"Registrar Pago\"");
        btnRegistrarPago.setEnabled(false);
        btnRegistrarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarPagoActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegistrarPago);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarPagoActionPerformed

        regitrarPago();

    }//GEN-LAST:event_btnRegistrarPagoActionPerformed

    private void cbNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNombresActionPerformed
        
        try {
            Cliente clienteSeleccionado = (Cliente) cbNombres.getSelectedItem();

            idClienteSeleccionado = clienteSeleccionado.getId_cliente();

            fechaInicio = Utilidades.fechaBD(dpInicio.getDate());
            fechaFin = Utilidades.fechaBD(dpFin.getDate());

            listaFacturas = new FacturaVentaDAO().buscaPorClienteCredito(clienteSeleccionado.getId_cliente());
            
            actualizatblFacturas();
            
        } catch (Exception ex) {
            Logger.getLogger(ConsultaCuentasRecibir.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_cbNombresActionPerformed

    private void dpInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpInicioActionPerformed

        actualizatblFacturas();

    }//GEN-LAST:event_dpInicioActionPerformed

    private void dpFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpFinActionPerformed

        actualizatblFacturas();

    }//GEN-LAST:event_dpFinActionPerformed

    private void txtNroFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroFacturaActionPerformed

        try {
            listaFacturas = new FacturaVentaDAO().buscaPorNroFacturaVentaCredito(txtNroFactura.getText());
            actualizatblFacturas();
        } catch (Exception ex) {
            Logger.getLogger(ConsultaCuentasRecibir.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_txtNroFacturaActionPerformed

    private void btnBuscaPorFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaPorFechaActionPerformed
        


        fechaInicio = Utilidades.fechaBD(dpInicio.getDate());
        fechaFin = Utilidades.fechaBD(dpFin.getDate());

        listaFacturas = new FacturaVentaDAO().buscaEntreFechasFacturasCredito(Factura.class,fechaInicio, fechaFin, "Crédito");
        
        actualizatblFacturas();
    }//GEN-LAST:event_btnBuscaPorFechaActionPerformed

    private void txtNroReferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroReferenciaActionPerformed

        listaFacturas = new FacturaVentaDAO().buscarPor(Factura.class, "nro", txtNroReferencia.getText());
        
        actualizatblFacturas();
        
    }//GEN-LAST:event_txtNroReferenciaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscaPorFecha;
    private javax.swing.JButton btnRegistrarPago;
    private javax.swing.JComboBox cbNombres;
    private org.jdesktop.swingx.JXDatePicker dpFin;
    private org.jdesktop.swingx.JXDatePicker dpInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTable tblCreditos;
    private javax.swing.JTable tblFacturas;
    private javax.swing.JTable tblPagos;
    private javax.swing.JTextField txtNroFactura;
    private javax.swing.JTextField txtNroReferencia;
    // End of variables declaration//GEN-END:variables
}
