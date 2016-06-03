package com.caja;

import com.Beans.Caja;
import Utilidades.HibernateUtil;
import com.Beans.Rubros;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.hibernate.Session;

public class consultaCajaFrame extends javax.swing.JInternalFrame {

    String fechaInicio;
    String FechaFin;

    public consultaCajaFrame() {

        initComponents();

        dpInicio.setFormats("dd/MM/yyyy");
        dpFin.setFormats("dd/MM/yyyy");
        Date hoy = new Date();
        dpInicio.setDate(hoy);
        dpFin.setDate(hoy);
        actualizaComboBox();
        muestraContenidoTabla();

    }

    public void actualizaComboBox() {

        try {
            Session seccion = HibernateUtil.getSeccion();

            List<Rubros> listaRubros = new ArrayList();
            listaRubros = seccion.createQuery("from Rubros").list();

            for (Rubros rubro : listaRubros) {
                cbRubro.addItem(rubro);
            }
            int tamano_lista = listaRubros.size();

            for (int i = 0; i < tamano_lista; i++) {

                Rubros rubros = listaRubros.get(i);
                cbRubro.addItem(rubros.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void muestraContenidoTabla() {

        TableCellRenderer centerRenderer = new CenterRenderer();
        ((DefaultTableCellRenderer) tblCaja.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        TableColumn column = tblCaja.getColumnModel().getColumn(0); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column1 = tblCaja.getColumnModel().getColumn(1);
        TableColumn column2 = tblCaja.getColumnModel().getColumn(2);
        TableColumn column3 = tblCaja.getColumnModel().getColumn(3);
        TableColumn column4 = tblCaja.getColumnModel().getColumn(4);
        TableColumn column5 = tblCaja.getColumnModel().getColumn(5);
        TableColumn column6 = tblCaja.getColumnModel().getColumn(6);
        TableColumn column7 = tblCaja.getColumnModel().getColumn(7);
        TableColumn column8 = tblCaja.getColumnModel().getColumn(8);
        TableColumn column9 = tblCaja.getColumnModel().getColumn(9);

        column.setCellRenderer(centerRenderer);
        column1.setCellRenderer(centerRenderer);
        column2.setCellRenderer(centerRenderer);
        column3.setCellRenderer(centerRenderer);
        column4.setCellRenderer(centerRenderer);
        column5.setCellRenderer(centerRenderer);
        column6.setCellRenderer(centerRenderer);
        column7.setCellRenderer(centerRenderer);
        column8.setCellRenderer(centerRenderer);
        column9.setCellRenderer(centerRenderer);

        DefaultTableModel modelo = (DefaultTableModel) tblCaja.getModel();
        modelo.setNumRows(0);

        tblCaja.getColumn("Código").setPreferredWidth(5); //------> Ajusta el tamaño de las columnas
        tblCaja.getColumn("Fecha").setPreferredWidth(40);
        tblCaja.getColumn("Rubro").setPreferredWidth(150);
        tblCaja.getColumn("Entrada $U").setPreferredWidth(35);
        tblCaja.getColumn("Salida $U").setPreferredWidth(35);
        tblCaja.getColumn("Entrada R$").setPreferredWidth(35);
        tblCaja.getColumn("Salida R$").setPreferredWidth(35);
        tblCaja.getColumn("Entrada U$").setPreferredWidth(35);
        tblCaja.getColumn("Salida U$").setPreferredWidth(35);
        tblCaja.getColumn("Concepto").setPreferredWidth(180);

        try {
            SimpleDateFormat formatoConsulta = new SimpleDateFormat("yyyy/MM/dd");
            fechaInicio = formatoConsulta.format(dpInicio.getDate());
            FechaFin = formatoConsulta.format(dpFin.getDate());

            Session seccion = HibernateUtil.getSeccion();

            List<Caja> listaCaja = new ArrayList();
            Rubros rubroSeleccionado = (Rubros) cbRubro.getSelectedItem();
            listaCaja = seccion.createQuery("from Caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();

            int tamano_lista = listaCaja.size();

            for (int i = 0; i < tamano_lista; i++) {

                Caja caja = listaCaja.get(i);

                Object[] linea = new Object[10];
                linea[0] = caja.getId();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");     //Formatação da data do BD para mostrar na Tela
                Date fecha = caja.getFecha();
                if (fecha != null) {
                    linea[1] = formato.format(fecha);
                }
                linea[2] = caja.getConcepto();
                linea[3] = caja.getRubro().getNombre();
                linea[4] = caja.getEntrada_pesos();
                linea[5] = caja.getSalida_pesos();
                linea[6] = caja.getEntrada_reales();
                linea[7] = caja.getSalida_reales();
                linea[8] = caja.getEntrada_dolares();
                linea[9] = caja.getSalida_dolares();

                modelo.addRow(linea);
            }

            List queryEntradaP = seccion.createSQLQuery("select sum(entrada_pesos)"
                    + "from caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();
            Double saldoAnteriorP = (Double) queryEntradaP.get(0);
            txtEntradaP.setText(String.valueOf(saldoAnteriorP));

            List querySalidaP = seccion.createSQLQuery("select sum(salida_pesos) from "
                    + "caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();
            Double saldoDiaP = (Double) querySalidaP.get(0);
            txtSalidaP.setText(String.valueOf(saldoDiaP));

            List querySaldoP = seccion.createSQLQuery("select sum(entrada_pesos)-sum(salida_pesos) from "
                    + "caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();
            Double saldoP = (Double) querySaldoP.get(0);
            txtTotalP.setText(String.valueOf(saldoP));

            List queryEntradaR = seccion.createSQLQuery("select sum(entrada_reales)"
                    + "from caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();
            Double saldoAnteriorR = (Double) queryEntradaR.get(0);
            txtEntradaR.setText(String.valueOf(saldoAnteriorR));

            List querySalidaR = seccion.createSQLQuery("select sum(salida_reales) from "
                    + "caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();
            Double saldoDiaR = (Double) querySalidaR.get(0);
            txtSalidaR.setText(String.valueOf(saldoDiaR));

            List querySaldoR = seccion.createSQLQuery("select sum(entrada_reales)-sum(salida_reales) from "
                    + "caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();
            Double saldoR = (Double) querySaldoR.get(0);
            txtTotalR.setText(String.valueOf(saldoR));

            List queryEntradaD = seccion.createSQLQuery("select sum(entrada_dolares)"
                    + "from caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();
            Double saldoAnteriorD = (Double) queryEntradaD.get(0);
            txtEntradaD.setText(String.valueOf(saldoAnteriorD));

            List querySalidaD = seccion.createSQLQuery("select sum(salida_dolares) from "
                    + "caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();
            Double saldoDiaD = (Double) querySalidaD.get(0);
            txtSalidaD.setText(String.valueOf(saldoDiaD));

            List querySaldoD = seccion.createSQLQuery("select sum(entrada_dolares)-sum(salida_dolares) from "
                    + "caja where fecha between '" + fechaInicio + "' and '" + FechaFin + "' and id_rubro = '"+rubroSeleccionado.getId()+"'").list();
            Double saldoD = (Double) querySaldoD.get(0);
            txtTotalD.setText(String.valueOf(saldoD));

        } catch (Exception error) {

            JOptionPane.showMessageDialog(null, "Error" + error);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaja = new javax.swing.JTable();
        dpInicio = new org.jdesktop.swingx.JXDatePicker();
        dpFin = new org.jdesktop.swingx.JXDatePicker();
        jLabel5 = new javax.swing.JLabel();
        cbRubro = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSalidaP = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTotalP = new javax.swing.JTextField();
        txtEntradaP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTotalR = new javax.swing.JTextField();
        txtSalidaR = new javax.swing.JTextField();
        txtEntradaR = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTotalD = new javax.swing.JTextField();
        txtSalidaD = new javax.swing.JTextField();
        txtEntradaD = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(950, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consulta Movimientos Caja"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnBuscar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnBuscar.setMnemonic('C');
        btnBuscar.setText("Consultar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscar, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setText("al"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel9, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel17.setText("Ingrese el período del filtro"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel17, gridBagConstraints);

        tblCaja.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Fecha", "Concepto", "Rubro", "Entrada $U", "Salida $U", "Entrada R$", "Salida R$", "Entrada U$", "Salida U$"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCaja);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpInicio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpFin, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Rubro:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel5, gridBagConstraints);

        cbRubro.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRubroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbRubro, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel5, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Total Entrada R$:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel2, gridBagConstraints);

        txtSalidaP.setEditable(false);
        txtSalidaP.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSalidaP, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel14.setText("Total Salida R$:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel14, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setText("Resultado del Pedíodo en R$:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel6, gridBagConstraints);

        txtTotalP.setEditable(false);
        txtTotalP.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtTotalP, gridBagConstraints);

        txtEntradaP.setEditable(false);
        txtEntradaP.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtEntradaP, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Total Entrada U$:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel3, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel15.setText("Total Salida U$:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel15, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("Resultado del Pedíodo en U$:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel7, gridBagConstraints);

        txtTotalR.setEditable(false);
        txtTotalR.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtTotalR, gridBagConstraints);

        txtSalidaR.setEditable(false);
        txtSalidaR.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSalidaR, gridBagConstraints);

        txtEntradaR.setEditable(false);
        txtEntradaR.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtEntradaR, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Total Entrada $U:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel4, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel16.setText("Total Salida $U:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel16, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setText("Resultado del Pedíodo en $U:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel8, gridBagConstraints);

        txtTotalD.setEditable(false);
        txtTotalD.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtTotalD, gridBagConstraints);

        txtSalidaD.setEditable(false);
        txtSalidaD.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSalidaD, gridBagConstraints);

        txtEntradaD.setEditable(false);
        txtEntradaD.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtEntradaD, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        muestraContenidoTabla();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void cbRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRubroActionPerformed


    }//GEN-LAST:event_cbRubroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbRubro;
    private org.jdesktop.swingx.JXDatePicker dpFin;
    private org.jdesktop.swingx.JXDatePicker dpInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCaja;
    private javax.swing.JTextField txtEntradaD;
    private javax.swing.JTextField txtEntradaP;
    private javax.swing.JTextField txtEntradaR;
    private javax.swing.JTextField txtSalidaD;
    private javax.swing.JTextField txtSalidaP;
    private javax.swing.JTextField txtSalidaR;
    private javax.swing.JTextField txtTotalD;
    private javax.swing.JTextField txtTotalP;
    private javax.swing.JTextField txtTotalR;
    // End of variables declaration//GEN-END:variables
}
