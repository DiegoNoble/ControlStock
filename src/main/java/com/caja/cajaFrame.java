package com.caja;

import com.Beans.Caja;
import com.Beans.Rubros;
import Utilidades.HibernateUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class cajaFrame extends javax.swing.JInternalFrame {

    String fechaConsulta;

    public cajaFrame() {
        initComponents();

        txtrubro.setVisible(false);
        btnOK.setVisible(false);
        btnCancelar.setVisible(false);
        lbRubro.setVisible(false);

        dataPiker.setFormats("dd/MM/yyyy");
        Date hoy = new Date();
        dataPiker.setDate(hoy);

        actualizaComboBox();
        muestraContenidoTabla();

    }

    public void actualizaComboBox() {

        try {
            Session seccion = HibernateUtil.getSeccion();

            List<Rubros> listaRubros = new ArrayList();
            listaRubros = seccion.createQuery("from Rubros").list();

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

    public void registraMovimiento() {

        DateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
        Date fecha = dataPiker.getDate();

        String date = formatoData.format(fecha);

        Date fechaBD = null;
        try {
            fechaBD = formatoData.parse(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error convirtiendo Fecha" + ex);
        }

        if (cbTIpo.getSelectedItem().equals("Entrada") && (cbMoneda.getSelectedItem().equals("Pesos $U"))) {

            try {
                Session seccion = HibernateUtil.getSeccion();
                Transaction transaccion = seccion.beginTransaction();

                String valor = (txtValor.getText()).replace(",", ".");

                Caja caja = new Caja();
                caja.setConcepto(txtConcepto.getText());
                caja.setFecha(fechaBD);

                Rubros rubro = new Rubros();
                rubro.setId(Integer.parseInt(lbRubro.getText()));

                caja.setRubro(rubro);
                caja.setEntrada_pesos(Double.parseDouble(valor));
                seccion.save(caja);
                transaccion.commit();
                seccion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }

        } else if (cbTIpo.getSelectedItem().equals("Salida") && (cbMoneda.getSelectedItem().equals("Pesos $U"))) {

            try {
                Session seccion = HibernateUtil.getSeccion();
                Transaction transaccion = seccion.beginTransaction();

                String valor = (txtValor.getText()).replace(",", ".");

                Caja caja = new Caja();
                caja.setConcepto(txtConcepto.getText());
                caja.setFecha(fechaBD);

                Rubros rubro = new Rubros();
                rubro.setId(Integer.parseInt(lbRubro.getText()));

                caja.setRubro(rubro);
                caja.setSalida_pesos(Double.parseDouble(valor));
                seccion.save(caja);
                transaccion.commit();
                seccion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        } else if (cbTIpo.getSelectedItem().equals("Entrada") && (cbMoneda.getSelectedItem().equals("Reales R$"))) {

            try {
                Session seccion = HibernateUtil.getSeccion();
                Transaction transaccion = seccion.beginTransaction();

                String valor = (txtValor.getText()).replace(",", ".");

                Caja caja = new Caja();
                caja.setConcepto(txtConcepto.getText());
                caja.setFecha(fechaBD);

                Rubros rubro = new Rubros();
                rubro.setId(Integer.parseInt(lbRubro.getText()));

                caja.setRubro(rubro);
                caja.setEntrada_reales(Double.parseDouble(valor));
                seccion.save(caja);
                transaccion.commit();
                seccion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        } else if (cbTIpo.getSelectedItem().equals("Salida") && (cbMoneda.getSelectedItem().equals("Reales R$"))) {

            try {
                Session seccion = HibernateUtil.getSeccion();
                Transaction transaccion = seccion.beginTransaction();

                Caja caja = new Caja();
                caja.setConcepto(txtConcepto.getText());
                caja.setFecha(fechaBD);

                Rubros rubro = new Rubros();
                rubro.setId(Integer.parseInt(lbRubro.getText()));

                caja.setRubro(rubro);

                String valor = (txtValor.getText()).replace(",", ".");
                caja.setSalida_reales(Double.parseDouble(valor));
                seccion.save(caja);
                transaccion.commit();
                seccion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        } else if (cbTIpo.getSelectedItem().equals("Entrada") && (cbMoneda.getSelectedItem().equals("Dolares U$"))) {

            try {
                Session seccion = HibernateUtil.getSeccion();
                Transaction transaccion = seccion.beginTransaction();

                Caja caja = new Caja();
                caja.setConcepto(txtConcepto.getText());
                caja.setFecha(fechaBD);

                Rubros rubro = new Rubros();
                rubro.setId(Integer.parseInt(lbRubro.getText()));

                caja.setRubro(rubro);
                String valor = (txtValor.getText()).replace(",", ".");
                caja.setEntrada_dolares(Double.parseDouble(valor));
                seccion.save(caja);
                transaccion.commit();
                seccion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        } else if (cbTIpo.getSelectedItem().equals("Salida") && (cbMoneda.getSelectedItem().equals("Dolares U$"))) {

            try {
                Session seccion = HibernateUtil.getSeccion();
                Transaction transaccion = seccion.beginTransaction();

                Caja caja = new Caja();
                caja.setConcepto(txtConcepto.getText());
                caja.setFecha(fechaBD);

                Rubros rubro = new Rubros();
                rubro.setId(Integer.parseInt(lbRubro.getText()));

                caja.setRubro(rubro);
                String valor = (txtValor.getText()).replace(",", ".");
                caja.setSalida_dolares(Double.parseDouble(valor));
                seccion.save(caja);
                transaccion.commit();
                seccion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
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

        column.setCellRenderer(centerRenderer);
        column1.setCellRenderer(centerRenderer);
        column2.setCellRenderer(centerRenderer);
        column3.setCellRenderer(centerRenderer);
        column4.setCellRenderer(centerRenderer);
        column5.setCellRenderer(centerRenderer);
        column6.setCellRenderer(centerRenderer);
        column7.setCellRenderer(centerRenderer);
        column8.setCellRenderer(centerRenderer);

        DefaultTableModel modelo = (DefaultTableModel) tblCaja.getModel();
        modelo.setNumRows(0);

        tblCaja.getColumn("Código").setPreferredWidth(5); //------> Ajusta el tamaño de las columnas
        tblCaja.getColumn("Fecha").setPreferredWidth(40);
        tblCaja.getColumn("Rubro").setPreferredWidth(150);
        tblCaja.getColumn("Concepto").setPreferredWidth(200);
        tblCaja.getColumn("Entrada $U").setPreferredWidth(35);
        tblCaja.getColumn("Salida $U").setPreferredWidth(35);
        tblCaja.getColumn("Entrada R$").setPreferredWidth(35);
        tblCaja.getColumn("Salida R$").setPreferredWidth(35);
        tblCaja.getColumn("Entrada U$").setPreferredWidth(35);
        tblCaja.getColumn("Salida U$").setPreferredWidth(35);

        try {
            SimpleDateFormat formatoConsulta = new SimpleDateFormat("yyyy/MM/dd");
            fechaConsulta = formatoConsulta.format(dataPiker.getDate());
            Session seccion = HibernateUtil.getSeccion();

            List<Caja> listaCaja = new ArrayList();
            listaCaja = seccion.createQuery("from Caja where fecha = '" + fechaConsulta + "'").list();

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
                linea[2] = caja.getRubro().getNombre();
                linea[3] = caja.getConcepto();
                linea[4] = caja.getEntrada_pesos();
                linea[5] = caja.getSalida_pesos();
                linea[6] = caja.getEntrada_reales();
                linea[7] = caja.getSalida_reales();
                linea[8] = caja.getEntrada_dolares();
                linea[9] = caja.getSalida_dolares();

                modelo.addRow(linea);
            }

            List querySaldoAnteriorP = seccion.createSQLQuery("select sum(entrada_pesos)-sum(salida_pesos) "
                    + "from caja where fecha < '" + fechaConsulta + "'").list();
            Double saldoAnteriorP = (Double) querySaldoAnteriorP.get(0);
            txtSaldoAnteriorP.setText(String.valueOf(saldoAnteriorP));

            List querySaldoDiaP = seccion.createSQLQuery("select sum(entrada_pesos)-sum(salida_pesos) from caja ").list();
            Double saldoDiaP = (Double) querySaldoDiaP.get(0);
            txtSaldoDiaP.setText(String.valueOf(saldoDiaP));

            List querySaldoAnteriorR = seccion.createSQLQuery("select sum(entrada_reales)-sum(salida_reales) "
                    + "from caja where fecha < '" + fechaConsulta + "'").list();
            Double saldoAnteriorR = (Double) querySaldoAnteriorR.get(0);
            txtSaldoAnteriorR.setText(String.valueOf(saldoAnteriorR));

            List querySaldoDiaR = seccion.createSQLQuery("select sum(entrada_reales)-sum(salida_reales) from caja ").list();
            Double saldoDiaR = (Double) querySaldoDiaR.get(0);
            txtSaldoDiaR.setText(String.valueOf(saldoDiaR));

            List querySaldoAnteriorD = seccion.createSQLQuery("select sum(entrada_dolares)-sum(salida_dolares) "
                    + "from caja where fecha < '" + fechaConsulta + "'").list();
            Double saldoAnteriorD = (Double) querySaldoAnteriorD.get(0);
            txtSaldoAnteriorD.setText(String.valueOf(saldoAnteriorD));

            List querySaldoDiaD = seccion.createSQLQuery("select sum(entrada_dolares)-sum(salida_dolares) from caja ").list();
            Double saldoDiaD = (Double) querySaldoDiaD.get(0);
            txtSaldoDiaD.setText(String.valueOf(saldoDiaD));

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error " + error);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        txtConcepto = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbRubro = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbTIpo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtrubro = new javax.swing.JTextField();
        btnOK = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        dataPiker = new org.jdesktop.swingx.JXDatePicker();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbMoneda = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        lbRubro = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSaldoDiaP = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtSaldoAnteriorP = new javax.swing.JTextField();
        txtSaldoDiaR = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtSaldoAnteriorR = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSaldoDiaD = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSaldoAnteriorD = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaja = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(950, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        txtConcepto.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(txtConcepto, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel1.setText("Concepto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 1;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Rubro:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 1;
        jPanel1.add(jLabel2, gridBagConstraints);

        cbRubro.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRubroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(cbRubro, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Tipo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jLabel3, gridBagConstraints);

        cbTIpo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbTIpo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entrada", "Salida" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(cbTIpo, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Valor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jLabel5, gridBagConstraints);

        txtValor.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(txtValor, gridBagConstraints);

        jButton1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButton1.setMnemonic('A');
        jButton1.setText("Alta de nuevo rubro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 1;
        jPanel1.add(jButton1, gridBagConstraints);

        txtrubro.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        jPanel1.add(txtrubro, gridBagConstraints);

        btnOK.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnOK.setMnemonic('O');
        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 1;
        jPanel1.add(btnOK, gridBagConstraints);

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        jPanel1.add(btnCancelar, gridBagConstraints);

        dataPiker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataPikerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(dataPiker, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setText("Fecha:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setText("Moneda:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel9, gridBagConstraints);

        cbMoneda.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pesos $U", "Reales R$", "Dolares U$" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(cbMoneda, gridBagConstraints);

        jLabel6.setEnabled(false);
        jLabel6.setName("lbRubro"); // NOI18N
        jPanel1.add(jLabel6, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(lbRubro, gridBagConstraints);

        btnRegistrar.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnRegistrar.setMnemonic('R');
        btnRegistrar.setText("Registrar el Movimiento");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        jPanel1.add(btnRegistrar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 400));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Movimientos de Caja"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel7.setText("Anterior R$ :"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel7, gridBagConstraints);

        txtSaldoDiaP.setEditable(false);
        txtSaldoDiaP.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoDiaP, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel14.setText("Del Día R$ :"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jLabel14, gridBagConstraints);

        txtSaldoAnteriorP.setEditable(false);
        txtSaldoAnteriorP.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoAnteriorP, gridBagConstraints);

        txtSaldoDiaR.setEditable(false);
        txtSaldoDiaR.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoDiaR, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel10.setText("Anterior U$ :"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel10, gridBagConstraints);

        txtSaldoAnteriorR.setEditable(false);
        txtSaldoAnteriorR.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoAnteriorR, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel15.setText("Del Día $U :"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jLabel15, gridBagConstraints);

        txtSaldoDiaD.setEditable(false);
        txtSaldoDiaD.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoDiaD, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel11.setText("Anterior $U :"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel11, gridBagConstraints);

        txtSaldoAnteriorD.setEditable(false);
        txtSaldoAnteriorD.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoAnteriorD, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel16.setText("Del Día $U :"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jLabel16, gridBagConstraints);

        btnActualizar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnActualizar.setMnemonic('C');
        btnActualizar.setText("Actualizar datos");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(441, Short.MAX_VALUE)
                .addComponent(btnActualizar)
                .addGap(34, 34, 34)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizar)
                .addGap(33, 33, 33))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel4, gridBagConstraints);

        tblCaja.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
        tblCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Fecha", "Rubro", "Concepto", "Entrada $U", "Salida $U", "Entrada R$", "Salida R$", "Entrada U$", "Salida U$"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, true
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed

        registraMovimiento();

        txtConcepto.setText("");
        txtValor.setText("");
        txtrubro.setText("");

        muestraContenidoTabla();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        txtrubro.setVisible(true);
        txtrubro.setText("");
        btnOK.setVisible(true);
        btnCancelar.setVisible(true);
        txtrubro.requestFocus();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed

        if (txtrubro.getText().equals("")) {
            txtrubro.setVisible(false);
            btnOK.setVisible(false);
            btnCancelar.setVisible(false);

        } else {

            Session seccion = HibernateUtil.getSeccion();
            Transaction transacion = seccion.beginTransaction();

            Rubros rubro = new Rubros();
            rubro.setNombre(txtrubro.getText());
            seccion.save(rubro);
            transacion.commit();
            seccion.close();

        }

        cbRubro.removeAllItems();
        actualizaComboBox();

        txtrubro.setVisible(false);
        btnOK.setVisible(false);


    }//GEN-LAST:event_btnOKActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        txtrubro.setVisible(false);
        btnOK.setVisible(false);
        btnCancelar.setVisible(false);
        cbRubro.requestFocus();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRubroActionPerformed

        Session seccion = HibernateUtil.getSeccion();

        List<Rubros> listaRubros = new ArrayList();
        listaRubros = seccion.createQuery("from Rubros where nombre = '" + cbRubro.getSelectedItem() + "'").list();

        int tamano_lista = listaRubros.size();

        for (int i = 0; i < tamano_lista; i++) {

            Rubros rubros = listaRubros.get(i);
            lbRubro.setText(String.valueOf(rubros.getId()));
        }
    }//GEN-LAST:event_cbRubroActionPerformed

    private void dataPikerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataPikerActionPerformed

        muestraContenidoTabla();
    }//GEN-LAST:event_dataPikerActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        muestraContenidoTabla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox cbMoneda;
    private javax.swing.JComboBox cbRubro;
    private javax.swing.JComboBox cbTIpo;
    private org.jdesktop.swingx.JXDatePicker dataPiker;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel lbRubro;
    private javax.swing.JTable tblCaja;
    private javax.swing.JFormattedTextField txtConcepto;
    private javax.swing.JTextField txtSaldoAnteriorD;
    private javax.swing.JTextField txtSaldoAnteriorP;
    private javax.swing.JTextField txtSaldoAnteriorR;
    private javax.swing.JTextField txtSaldoDiaD;
    private javax.swing.JTextField txtSaldoDiaP;
    private javax.swing.JTextField txtSaldoDiaR;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField txtrubro;
    // End of variables declaration//GEN-END:variables
}
