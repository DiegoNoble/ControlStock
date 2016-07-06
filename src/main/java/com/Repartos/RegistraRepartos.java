package com.Repartos;

import Utilidades.ControlarEntradaTexto;
import com.Remito.*;
import com.Beans.Remito;
import com.Beans.Reparto;
import com.Beans.SituacionReparto;
import com.Beans.Transportista;
import com.DAO.ArticuloDAO;
import com.DAO.ArticulosPedidoDAO;
import com.DAO.MovStockDAO;
import com.DAO.PedidoDAO;
import com.DAO.RemitoDAO;
import com.DAO.RepartoDAO;
import com.DAO.TransportistaDAO;
import com.Renderers.MeDateCellRenderer;
import com.Renderers.TableRendererColorRemito;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

public final class RegistraRepartos extends javax.swing.JInternalFrame {

    RemitoTableModel tableModelDisponibles;
    List<Remito> listRemitosDisponibles;
    RemitoTableModel tableModelSeleccionados;
    List<Remito> listRemitosSeleccionados;
    Remito remitoDisponibleSeleccionado;
    Remito remitoSeleccionadoSeleccionado;
    RemitoDAO remitoDAO;
    PedidoDAO pedidoDAO;
    TransportistaDAO transportistaDAO;
    RepartoDAO repartoDAO;
    ArticulosPedidoDAO articulosPedidoDAO;
    ArticuloDAO articulosDAO;
    MovStockDAO movStockDAO;

    public RegistraRepartos() {
        initComponents();

        defineModelo();
        defineModeloSeleccionados();
        buscarPorFechas();
    }

    private void cargaComboTransportistas() {

        try {
            List<Transportista> listTransportistas = new ArrayList();

            transportistaDAO = new TransportistaDAO();
            listTransportistas = transportistaDAO.BuscaTodos(Transportista.class);

            cbTransportistas.removeAllItems();

            for (Transportista transport : listTransportistas) {

                cbTransportistas.addItem(transport);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar transportistas" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void buscar() {
        remitoDAO = new RemitoDAO();
        if (rbCodRemito.isSelected()) {
            try {
                tableModelDisponibles.agregar(remitoDAO.buscarPor(Remito.class, "id", txtFiltroCod.getText()));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        } else if (rbCodPedido.isSelected()) {
            try {
                tableModelDisponibles.agregar(remitoDAO.buscarPor(Remito.class, "remito.id", txtFiltroCod.getText()));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }

        }
    }

    private void buscarPorFechas() {
        remitoDAO = new RemitoDAO();
        tableModelDisponibles.agregar(remitoDAO.buscaEntreFechas(dpDesde.getDate(), dpHasta.getDate()));
    }

    private void defineModelo() {
        cargaComboTransportistas();
        dpDesde.setDate(new Date());
        dpHasta.setDate(new Date());
        dpDesde.setFormats("dd/MM/yyyy");
        dpHasta.setFormats("dd/MM/yyyy");
        dpfecha.setDate(new Date());
        dpfecha.setFormats("dd/MM/yyyy");
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        txtPorcentageComision.setDocument(new ControlarEntradaTexto(4, chs));

        ((DefaultTableCellRenderer) tblRemitosDisponibles.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listRemitosDisponibles = new ArrayList<Remito>();
        tableModelDisponibles = new RemitoTableModel(listRemitosDisponibles);
        tblRemitosDisponibles.setModel(tableModelDisponibles);
        tblRemitosDisponibles.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        tblRemitosDisponibles.getColumn("Tipo").setCellRenderer(new TableRendererColorRemito(0));

        ListSelectionModel listModel = tblRemitosDisponibles.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblRemitosDisponibles.getSelectedRow() != -1) {

                    remitoDisponibleSeleccionado = listRemitosDisponibles.get(tblRemitosDisponibles.getSelectedRow());
                    btnSelecciona.setEnabled(true);

                } else {
                    remitoDisponibleSeleccionado = null;
                    btnSelecciona.setEnabled(false);
                }
            }
        });

    }

    private void defineModeloSeleccionados() {

        ((DefaultTableCellRenderer) tblRemitosSeleccionados.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listRemitosSeleccionados = new ArrayList<Remito>();
        tableModelSeleccionados = new RemitoTableModel(listRemitosSeleccionados);
        tblRemitosSeleccionados.setModel(tableModelSeleccionados);
        tblRemitosSeleccionados.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        tblRemitosSeleccionados.getColumn("Tipo").setCellRenderer(new TableRendererColorRemito(0));

        ListSelectionModel listModel = tblRemitosSeleccionados.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblRemitosSeleccionados.getSelectedRow() != -1) {

                    remitoSeleccionadoSeleccionado = listRemitosSeleccionados.get(tblRemitosSeleccionados.getSelectedRow());
                    btnQuitar.setEnabled(true);

                } else {
                    remitoSeleccionadoSeleccionado = null;
                    btnQuitar.setEnabled(false);
                }
            }
        });

    }

    void generarReparto() {

        if (txtPorcentageComision.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el porcentage de comisión", "Error", JOptionPane.ERROR_MESSAGE);
            txtPorcentageComision.requestFocus();
        } else if (listRemitosSeleccionados.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Seleccione los remitos del reparto", "Error", JOptionPane.ERROR_MESSAGE);

        } else {

            try {
                Double total = Double.parseDouble(txtTotal.getText().replace(",", "."));
                Double porcentageComision = Double.parseDouble(txtPorcentageComision.getText());
                Double comision = total * porcentageComision / 100;
                Reparto reparto = new Reparto();
                reparto.setComision(comision);
                reparto.setFecha(dpfecha.getDate());
                reparto.setObservaciones(txtObservaciones.getText());
                reparto.setPorcentageComision(porcentageComision);
                reparto.setSituacionReparto(SituacionReparto.EN_CURSO);
                reparto.setTotalRepartoSinIVA(total);
                reparto.setTransportista((Transportista) cbTransportistas.getSelectedItem());
                reparto.setRemitos(listRemitosSeleccionados);

                repartoDAO = new RepartoDAO(reparto);
                repartoDAO.registraOActualiza();

                JOptionPane.showMessageDialog(null, "Generado correctamente!");
                listRemitosSeleccionados.clear();
                repartoDAO = new RepartoDAO();
                repartoDAO.ResumenReparto(listRemitosSeleccionados);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar datos" + e, "Error", JOptionPane.ERROR_MESSAGE);

            }
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
        jPanel4 = new javax.swing.JPanel();
        btnGenerar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        cbTransportistas = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dpfecha = new org.jdesktop.swingx.JXDatePicker();
        txtPorcentageComision = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
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
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRemitosDisponibles = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRemitosSeleccionados = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnSelecciona = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();

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
        jLabel1.setText("Registra Repartos");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        btnGenerar.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/pedido.png"))); // NOI18N
        btnGenerar.setMnemonic('R');
        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel4.add(btnGenerar, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Observaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel8.setLayout(new java.awt.GridBagLayout());

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane3.setViewportView(txtObservaciones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jScrollPane3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(jPanel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        cbTransportistas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbTransportistas, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Total sin IVA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
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

        txtPorcentageComision.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPorcentageComision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentageComisionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtPorcentageComision, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Transportista");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel6, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("% Comisión");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel8, gridBagConstraints);

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotal.setEnabled(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel7, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Buscar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(btnBuscarPorFechas, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jPanel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jPanel2, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        tblRemitosSeleccionados.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblRemitosSeleccionados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblRemitosSeleccionados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblRemitosSeleccionados);

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
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weightx = 500.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jPanel5, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnSelecciona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/flechaDerecha.png"))); // NOI18N
        btnSelecciona.setBorderPainted(false);
        btnSelecciona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnSelecciona, gridBagConstraints);

        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/flechaIzquiera.png"))); // NOI18N
        btnQuitar.setBorderPainted(false);
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnQuitar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jPanel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

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

    private void btnBuscarPorFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPorFechasActionPerformed

        buscarPorFechas();
    }//GEN-LAST:event_btnBuscarPorFechasActionPerformed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed

        generarReparto();

    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnSeleccionaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaActionPerformed

        if (listRemitosSeleccionados.contains(remitoDisponibleSeleccionado) == true) {
            JOptionPane.showMessageDialog(this, "El remito ya se encuentra incluido en la lista", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (remitoDisponibleSeleccionado.getReparto() != null) {
            JOptionPane.showMessageDialog(this, "El remito ya se pertenece a otro reparto", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            tableModelSeleccionados.agregar(remitoDisponibleSeleccionado);
            Double total = 0.0;

            for (Remito remitoSeleccionados : listRemitosSeleccionados) {
                total = total + (remitoSeleccionados.getImporteAtendido() / 1.21);
            }
            DecimalFormat format = new DecimalFormat("#.##");
            txtTotal.setText(format.format(total));
        }


    }//GEN-LAST:event_btnSeleccionaActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed

        tableModelSeleccionados.eliminar(tblRemitosSeleccionados.getSelectedRow());
        Double total = 0.0;

        for (Remito remitoSeleccionados : listRemitosSeleccionados) {
            total = total + (remitoSeleccionados.getImporteAtendido() / 1.21);
        }
        DecimalFormat format = new DecimalFormat("#.##");
        txtTotal.setText(format.format(total));

    }//GEN-LAST:event_btnQuitarActionPerformed

    private void txtPorcentageComisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentageComisionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentageComisionActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarPorFechas;
    private javax.swing.JButton btnGenerar;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton rbCodPedido;
    private javax.swing.JRadioButton rbCodRemito;
    private javax.swing.JTable tblRemitosDisponibles;
    private javax.swing.JTable tblRemitosSeleccionados;
    private javax.swing.JTextField txtFiltroCod;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtPorcentageComision;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

}
