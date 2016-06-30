package com.Repartos;

import com.Remito.*;
import com.Beans.Articulo;
import com.Beans.ArticulosPedido;
import com.Beans.MovStock;
import com.Beans.Pedido;
import com.Beans.Remito;
import com.Beans.SituacionPedido;
import com.Beans.TipoRemito;
import com.DAO.ArticuloDAO;
import com.DAO.ArticulosPedidoDAO;
import com.DAO.MovStockDAO;
import com.DAO.PedidoDAO;
import com.DAO.RemitoDAO;
import com.Renderers.MeDateCellRenderer;
import com.Renderers.TableRendererColorRemito;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

public final class RegistraRepartos extends javax.swing.JInternalFrame {

    RemitoTableModel tableModel;
    List<Remito> listRemitosDisponibles;
    Remito remitoSeleccionado;
    RemitoDAO remitoDAO;
    PedidoDAO pedidoDAO;
    ArticulosPedidoDAO articulosPedidoDAO;
    ArticuloDAO articulosDAO;
    MovStockDAO movStockDAO;

    public RegistraRepartos() {
        initComponents();

        defineModelo();
        buscarPorFechas();
        btnImprimirRemito.setEnabled(false);
        //btnAnular.setVisible(false);
        btnAnular.setEnabled(false);
    }

    public void buscar() {
        remitoDAO = new RemitoDAO();
        if (rbCodRemito.isSelected()) {
            try {
                tableModel.agregar(remitoDAO.buscarPor(Remito.class, "id", txtFiltroCod.getText()));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        } else if (rbCodPedido.isSelected()) {
            try {
                tableModel.agregar(remitoDAO.buscarPor(Remito.class, "remito.id", txtFiltroCod.getText()));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }

        }
    }

    private void buscarPorFechas() {
        remitoDAO = new RemitoDAO();
        tableModel.agregar(remitoDAO.buscaEntreFechas(dpDesde.getDate(), dpHasta.getDate()));
    }

    private void defineModelo() {
        dpDesde.setDate(new Date());
        dpHasta.setDate(new Date());
        dpDesde.setFormats("dd/MM/yyyy");
        dpHasta.setFormats("dd/MM/yyyy");

        ((DefaultTableCellRenderer) tblRemitosDisponibles.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listRemitosDisponibles = new ArrayList<Remito>();
        tableModel = new RemitoTableModel(listRemitosDisponibles);
        tblRemitosDisponibles.setModel(tableModel);
        tblRemitosDisponibles.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        tblRemitosDisponibles.getColumn("Tipo").setCellRenderer(new TableRendererColorRemito(0));
        int[] anchos = {20, 20, 20, 20, 20, 20};

        for (int i = 0; i < tblRemitosDisponibles.getColumnCount(); i++) {

            tblRemitosDisponibles.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        ListSelectionModel listModel = tblRemitosDisponibles.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblRemitosDisponibles.getSelectedRow() != -1) {
                    btnImprimirRemito.setEnabled(true);
                    remitoSeleccionado = listRemitosDisponibles.get(tblRemitosDisponibles.getSelectedRow());
                    if (remitoSeleccionado.getAnulado() == false) {
                        btnAnular.setEnabled(true);
                    } else {
                        btnAnular.setEnabled(false);
                    }
                } else {
                    btnImprimirRemito.setEnabled(false);
                    remitoSeleccionado = null;
                    btnAnular.setEnabled(false);
                }
            }
        });

        tblRemitosDisponibles.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    imprimirRemito();
                }
            }
        });

    }

    private void imprimirRemito() {

        try {
            if (remitoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un remito", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (remitoSeleccionado.getTipoRemito() == TipoRemito.REMITO) {
                    remitoDAO = new RemitoDAO();
                    remitoDAO.imprimeRemito(remitoSeleccionado);
                } else {
                    remitoDAO = new RemitoDAO();
                    remitoDAO.imprimeContraRemito(remitoSeleccionado);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
        }
    }

    private void anularRemito() {

        try {
            Double mov = 0.0;
            Double valorUnitario = 0.0;
            Double importeRemito = 0.0;
            Double unitarioBonificado = 0.0;
            List<MovStock> listMovStock = new ArrayList<>();
            Pedido pedido = remitoSeleccionado.getPedido();
            for (ArticulosPedido articulosPedido : pedido.getArticulosPedido()) {

                if (articulosPedido.getCantPendiente() - articulosPedido.getCantPedida() != 0) {
                    Double factor = articulosPedido.getEquivalenciaUnidades().getFactor_conversion();
                    mov = ((articulosPedido.getCantPedida() * factor) * -1);

                    valorUnitario = (articulosPedido.getArticulo().getValor_venta());
                    unitarioBonificado = valorUnitario - (valorUnitario * (articulosPedido.getBonificacion() / 100));
                    importeRemito = importeRemito + unitarioBonificado * mov;

                    articulosPedido.setCantAtendida(articulosPedido.getCantAtendida() - articulosPedido.getCantAtendida());
                    //articulosPedido.setCantPendiente(articulosPedido.getCantPedida());
                    //articulosPedido.setImportePendiente(articulosPedido.getImportePedido());
                    articulosPedido.setImporteAtendido(articulosPedido.getImporteAtendido() - articulosPedido.getImporteAtendido());
                    articulosPedidoDAO = new ArticulosPedidoDAO(articulosPedido);
                    articulosPedidoDAO.actualiza();
                    MovStock movStock = new MovStock();
                    movStock.setArticulo(articulosPedido.getArticulo());
                    movStock.setCantidadMov(-mov);
                    movStock.setSaldoStock(articulosPedido.getArticulo().getCantidad() + (-mov));
                    movStock.setFecha(new Date());
                    listMovStock.add(movStock);
                }
            }
            //pedido.setImportePendiente(pedido.getImporteTotal());
            pedido.setEstadoPedido(SituacionPedido.CON_REMITO_ANULADO);
            //pedido.setImporteAtendido(pedido.getImporteAtendido() + importeRemito);

            pedidoDAO = new PedidoDAO(pedido);
            pedidoDAO.actualiza();

            Remito remito = new Remito();
            remito.setFecha(new Date());
            remito.setImporteRemito(importeRemito);
            remito.setPedido(pedido);
            remito.setTipoRemito(TipoRemito.CONTRA_REMITO);
            remito.setAnulado(true);
            remitoDAO = new RemitoDAO(remito);
            remitoDAO.guardar();
            remitoSeleccionado.setAnulado(true);
            remitoDAO = new RemitoDAO(remitoSeleccionado);
            remitoDAO.actualiza();

            for (MovStock movStock : listMovStock) {
                movStock.setRemito(remito);
                movStockDAO = new MovStockDAO(movStock);
                movStockDAO.guardar();

                Articulo articulo = movStock.getArticulo();
                articulo.setCantidad(movStock.getSaldoStock());
                articulosDAO = new ArticuloDAO(articulo);
                articulosDAO.actualiza();

            }
            JOptionPane.showMessageDialog(this, "Remito generado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            //this.consultaPedido.buscar();
            //this.dispose();
            remitoDAO = new RemitoDAO();
            remitoDAO.imprimeContraRemito(remito);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
            ex.printStackTrace();
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
        jPanel6 = new javax.swing.JPanel();
        btnSelecciona = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRemitosDisponibles = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtFiltroCod = new javax.swing.JTextField();
        rbCodRemito = new javax.swing.JRadioButton();
        rbCodPedido = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        btnBuscarPorFechas = new javax.swing.JButton();
        dpHasta = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        dpDesde = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnImprimirRemito = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbRemitosSeleccionados = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        cbTransportistas = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dpfecha = new org.jdesktop.swingx.JXDatePicker();

        jTextField1.setText("jTextField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(600, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Registra Repartos");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnSelecciona.setText(">>>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnSelecciona, gridBagConstraints);

        btnQuitar.setText("<<<");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnQuitar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jPanel6, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel12.setLayout(new java.awt.GridBagLayout());

        tblRemitosDisponibles.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblRemitosDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblRemitosDisponibles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblRemitosDisponibles);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel12.add(jScrollPane1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Buscar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel7, gridBagConstraints);

        txtFiltroCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroCodActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtFiltroCod, gridBagConstraints);

        buttonGroup1.add(rbCodRemito);
        rbCodRemito.setSelected(true);
        rbCodRemito.setText("Cod remito");
        rbCodRemito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCodRemitoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel2.add(rbCodRemito, gridBagConstraints);

        buttonGroup1.add(rbCodPedido);
        rbCodPedido.setText("Cod pedido");
        rbCodPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCodPedidoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel2.add(rbCodPedido, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        jPanel2.add(btnBuscar, gridBagConstraints);

        btnBuscarPorFechas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnBuscarPorFechas.setBorder(null);
        btnBuscarPorFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPorFechasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnBuscarPorFechas, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpHasta, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("hasta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpDesde, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Buscar entre fechas, desde");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(jPanel2, gridBagConstraints);

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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnImprimirRemito.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnImprimirRemito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/printer.png"))); // NOI18N
        btnImprimirRemito.setMnemonic('R');
        btnImprimirRemito.setText("Imprimir");
        btnImprimirRemito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirRemitoActionPerformed(evt);
            }
        });
        jPanel4.add(btnImprimirRemito);

        btnAnular.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/delete_32.png"))); // NOI18N
        btnAnular.setMnemonic('R');
        btnAnular.setText("Anuar");
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        jPanel4.add(btnAnular);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        tbRemitosSeleccionados.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tbRemitosSeleccionados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tbRemitosSeleccionados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tbRemitosSeleccionados);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel5, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        cbTransportistas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbTransportistas, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Transportista");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(dpfecha, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel7, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbCodRemitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodRemitoActionPerformed

    }//GEN-LAST:event_rbCodRemitoActionPerformed

    private void rbCodPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodPedidoActionPerformed


    }//GEN-LAST:event_rbCodPedidoActionPerformed

    private void txtFiltroCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroCodActionPerformed
        buscar();
    }//GEN-LAST:event_txtFiltroCodActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnImprimirRemitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirRemitoActionPerformed

        imprimirRemito();
    }//GEN-LAST:event_btnImprimirRemitoActionPerformed

    private void btnBuscarPorFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPorFechasActionPerformed

        buscarPorFechas();
    }//GEN-LAST:event_btnBuscarPorFechasActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        anularRemito();
    }//GEN-LAST:event_btnAnularActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarPorFechas;
    private javax.swing.JButton btnImprimirRemito;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnSelecciona;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbTransportistas;
    private org.jdesktop.swingx.JXDatePicker dpDesde;
    private org.jdesktop.swingx.JXDatePicker dpHasta;
    private org.jdesktop.swingx.JXDatePicker dpfecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton rbCodPedido;
    private javax.swing.JRadioButton rbCodRemito;
    private javax.swing.JTable tbRemitosSeleccionados;
    private javax.swing.JTable tblRemitosDisponibles;
    private javax.swing.JTextField txtFiltroCod;
    // End of variables declaration//GEN-END:variables

}
