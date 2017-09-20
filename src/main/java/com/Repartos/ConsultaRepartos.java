package com.Repartos;

import com.Beans.Remito;
import com.Beans.Reparto;
import com.Beans.SituacionReparto;
import com.DAO.ArticuloDAO;
import com.DAO.ArticulosPedidoDAO;
import com.DAO.MovStockDAO;
import com.DAO.PedidoDAO;
import com.DAO.RemitoDAO;
import com.DAO.RepartoDAO;
import com.Renderers.MeDateCellRenderer;
import com.Renderers.TableRendererColorReparto;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

public final class ConsultaRepartos extends javax.swing.JInternalFrame {

    RepartoTableModel tableModel;
    List<Reparto> listRepartos;
    Reparto repartoSeleccionado;
    RepartoDAO repartoDAO;
    PedidoDAO pedidoDAO;
    ArticulosPedidoDAO articulosPedidoDAO;
    ArticuloDAO articulosDAO;
    MovStockDAO movStockDAO;
    RemitoDAO remitoDAO;

    public ConsultaRepartos() {
        initComponents();

        defineModelo();
        buscarPorFechas();
        btnImprimirReparto.setEnabled(false);
        //btnAnular.setVisible(false);
        btnAnular.setEnabled(false);
        btnModificar.setEnabled(false);
        btnFinalizar.setEnabled(false);
    }

    public void buscar() {
        repartoDAO = new RepartoDAO();
        if (rbCodReparto.isSelected()) {
            try {
                tableModel.agregar(repartoDAO.buscarPor(Reparto.class, "id", txtFiltroCod.getText()));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        } else if (rbTransportista.isSelected()) {
            try {
                tableModel.agregar(repartoDAO.buscarPor(Reparto.class, "transportista.nombre", txtFiltroCod.getText()));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Los codigos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }

        }
    }

    public void buscarPorFechas() {
        repartoDAO = new RepartoDAO();
        tableModel.agregar(repartoDAO.buscaEntreFechas(dpDesde.getDate(), dpHasta.getDate()));
    }

    private void defineModelo() {
        dpDesde.setDate(new Date());
        dpHasta.setDate(new Date());
        dpDesde.setFormats("dd/MM/yyyy");
        dpHasta.setFormats("dd/MM/yyyy");

        ((DefaultTableCellRenderer) tblRepartos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listRepartos = new ArrayList<Reparto>();
        tableModel = new RepartoTableModel(listRepartos);
        tblRepartos.setModel(tableModel);
        tblRepartos.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        tblRepartos.getColumn("Situación").setCellRenderer(new TableRendererColorReparto(1));
        int[] anchos = {10, 20, 50, 100, 20, 20, 20};

        for (int i = 0; i < tblRepartos.getColumnCount(); i++) {

            tblRepartos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        ListSelectionModel listModel = tblRepartos.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblRepartos.getSelectedRow() != -1) {

                    repartoSeleccionado = listRepartos.get(tblRepartos.getSelectedRow());
                    btnImprimirReparto.setEnabled(true);
                    if (repartoSeleccionado.getSituacionReparto() == SituacionReparto.EN_CURSO) {
                        btnModificar.setEnabled(true);
                        btnFinalizar.setEnabled(true);
                        btnAnular.setEnabled(true);
                    }
                } else {
                    repartoSeleccionado = null;
                    btnImprimirReparto.setEnabled(false);
                    btnAnular.setEnabled(false);
                    btnModificar.setEnabled(false);
                    btnFinalizar.setEnabled(false);
                }
            }
        });

        tblRepartos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    imprimirReparto();
                }
            }
        });

    }

    private void imprimirReparto() {

        try {

            if (repartoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un reparto", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                repartoDAO = new RepartoDAO();
                repartoDAO.ResumenReparto(repartoSeleccionado);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al salvar en base de datos: " + ex);
        }
    }

    private void anularReparto() {

        try {
            if (JOptionPane.showConfirmDialog(this, "Esta seguro que desea eliminar el reparto "
                    + "seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                for (Remito remito : repartoSeleccionado.getRemitos()) {
                    remito.setReparto(null);
                    remitoDAO = new RemitoDAO(remito);
                    remitoDAO.registraOActualiza();
                }

                repartoDAO = new RepartoDAO();
                repartoDAO.eliminarPor(Reparto.class, "id", repartoSeleccionado.getId().toString());
                JOptionPane.showMessageDialog(this, "Eliminado correctamente");
                buscarPorFechas();
            }
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
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtFiltroCod = new javax.swing.JTextField();
        rbCodReparto = new javax.swing.JRadioButton();
        rbTransportista = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRepartos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnBuscarPorFechas = new javax.swing.JButton();
        dpHasta = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        dpDesde = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnImprimirReparto = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();

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
        jLabel1.setText("Consulta repartos");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Buscar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(jLabel7, gridBagConstraints);

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
        jPanel11.add(txtFiltroCod, gridBagConstraints);

        buttonGroup1.add(rbCodReparto);
        rbCodReparto.setSelected(true);
        rbCodReparto.setText("Cod reparto");
        rbCodReparto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCodRepartoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel11.add(rbCodReparto, gridBagConstraints);

        buttonGroup1.add(rbTransportista);
        rbTransportista.setText("Transportista");
        rbTransportista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTransportistaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel11.add(rbTransportista, gridBagConstraints);

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
        jPanel11.add(btnBuscar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jPanel11, gridBagConstraints);

        jPanel12.setLayout(new java.awt.GridBagLayout());

        tblRepartos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblRepartos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblRepartos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblRepartos);

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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jPanel12, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnImprimirReparto.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnImprimirReparto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/printer.png"))); // NOI18N
        btnImprimirReparto.setMnemonic('R');
        btnImprimirReparto.setText("Imprimir");
        btnImprimirReparto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirRepartoActionPerformed(evt);
            }
        });
        jPanel4.add(btnImprimirReparto);

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

        btnModificar.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/edit.png"))); // NOI18N
        btnModificar.setMnemonic('R');
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel4.add(btnModificar);

        btnFinalizar.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/accept.png"))); // NOI18N
        btnFinalizar.setMnemonic('R');
        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });
        jPanel4.add(btnFinalizar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbCodRepartoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodRepartoActionPerformed

    }//GEN-LAST:event_rbCodRepartoActionPerformed

    private void txtFiltroCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroCodActionPerformed
        buscar();
    }//GEN-LAST:event_txtFiltroCodActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnImprimirRepartoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirRepartoActionPerformed

        imprimirReparto();
    }//GEN-LAST:event_btnImprimirRepartoActionPerformed

    private void btnBuscarPorFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPorFechasActionPerformed

        buscarPorFechas();
    }//GEN-LAST:event_btnBuscarPorFechasActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        anularReparto();
    }//GEN-LAST:event_btnAnularActionPerformed

    private void rbTransportistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTransportistaActionPerformed

    }//GEN-LAST:event_rbTransportistaActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        RegistraRepartos registraRepartos = new RegistraRepartos(this, repartoSeleccionado);
        super.getDesktopPane().add(registraRepartos);
        registraRepartos.setVisible(true);
        registraRepartos.toFront();


    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed

        repartoSeleccionado.setSituacionReparto(SituacionReparto.FINALIZADO);
        repartoDAO = new RepartoDAO(repartoSeleccionado);
        repartoDAO.actualiza();
        buscarPorFechas();
    }//GEN-LAST:event_btnFinalizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarPorFechas;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnImprimirReparto;
    private javax.swing.JButton btnModificar;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.jdesktop.swingx.JXDatePicker dpDesde;
    private org.jdesktop.swingx.JXDatePicker dpHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton rbCodReparto;
    private javax.swing.JRadioButton rbTransportista;
    private javax.swing.JTable tblRepartos;
    private javax.swing.JTextField txtFiltroCod;
    // End of variables declaration//GEN-END:variables

}
