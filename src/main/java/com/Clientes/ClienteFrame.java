package com.Clientes;

import com.Beans.Cliente;
import Utilidades.HibernateUtil;
import javax.swing.JOptionPane;
import org.hibernate.*;
import Utilidades.Utilidades;
import com.Pedidos.RegistraPedido;
import com.Ventas.RegistraVentaFrame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

public class ClienteFrame extends javax.swing.JInternalFrame {

    MaskFormatter formatoftxtFechaIngreso;

    private RegistraVentaFrame seteaClienteVenta;
    private RegistraPedido seteaClientePedido;
    String Filtro = "from Cliente";
    private Cliente clie;

    public ClienteFrame() {
        initComponents();

        muestraContenidoTabla();
        btnSeleccionaCliente.setVisible(false);

        btnExcluir.setVisible(false);
    }

    public ClienteFrame(RegistraVentaFrame seteaCliente) {
        initComponents();

        btnExcluir.setVisible(false);
        muestraContenidoTabla();
        this.seteaClienteVenta = seteaCliente;
    }

    public ClienteFrame(RegistraPedido seteaClientePedido) {
        initComponents();

        btnExcluir.setVisible(false);
        muestraContenidoTabla();
        this.seteaClientePedido = seteaClientePedido;
    }

    public Cliente getClie() {
        return clie;
    }

    public void setClie(Cliente clie) {
        this.clie = clie;
    }

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void muestraContenidoTabla() {

        TableCellRenderer centerRenderer = new CenterRenderer();
        ((DefaultTableCellRenderer) tblCliente.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        TableColumn column = tblCliente.getColumnModel().getColumn(0); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column1 = tblCliente.getColumnModel().getColumn(1);
        TableColumn column2 = tblCliente.getColumnModel().getColumn(2);
        TableColumn column3 = tblCliente.getColumnModel().getColumn(3);
        TableColumn column4 = tblCliente.getColumnModel().getColumn(4);

        column.setCellRenderer(centerRenderer);
        column1.setCellRenderer(centerRenderer);
        column2.setCellRenderer(centerRenderer);
        column3.setCellRenderer(centerRenderer);
        column4.setCellRenderer(centerRenderer);

        DefaultTableModel modelo = (DefaultTableModel) tblCliente.getModel();
        modelo.setNumRows(0);

        tblCliente.getColumn("Código").setPreferredWidth(10); //------> Ajusta el tamaño de las columnas
        tblCliente.getColumn("Nombre").setPreferredWidth(100);
        tblCliente.getColumn("C.I.").setPreferredWidth(20);
        tblCliente.getColumn("Razón Social").setPreferredWidth(100);
        tblCliente.getColumn("R.U.T.").setPreferredWidth(20);

        try {
            Session seccion = HibernateUtil.getSeccion();

            List<Cliente> lista_clientes = new ArrayList();
            lista_clientes = seccion.createQuery(Filtro).list();

            int tamano_lista = lista_clientes.size();

            for (int i = 0; i < tamano_lista; i++) {

                Cliente clientes = lista_clientes.get(i);

                Object[] linea = new Object[12];
                linea[0] = clientes.getId_cliente();
                linea[1] = clientes.getNombre();
                linea[2] = clientes.getCi();
                linea[3] = clientes.getRazon_social();
                linea[4] = clientes.getDocumento();
                linea[5] = clientes.getDireccion();
                linea[6] = clientes.getCiudad();
                linea[7] = clientes.getPais();
                linea[8] = clientes.getCel();
                linea[9] = clientes.getTelefono();
                linea[10] = clientes.getEmail();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");     //Formatação da data do BD para mostrar na Tela
                Date dataIngreso = clientes.getFecha_ingreso();
                if (dataIngreso != null) {
                    linea[11] = formato.format(dataIngreso);
                }

                modelo.addRow(linea);
            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error);
        }

    }

    private void NuevoCliente() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del cliente!", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
        } else {

            Session seccion = HibernateUtil.getSeccion();
            Cliente cliente = new Cliente();
            cliente.setNombre(txtNombre.getText());
            cliente.setCel(txtCel.getText());
            cliente.setCi(txtCI.getText());
            cliente.setCiudad(txtCiudad.getText());
            cliente.setDireccion(txtDireccion.getText());
            cliente.setEmail(txtEmail.getText());

            String FechaIngreso = Utilidades.getDataTelaToBD(txtFechaIngreso.getText());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            Date fechaConvertida = null;
            try {
                fechaConvertida = formato.parse(FechaIngreso);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Error al dar formato a la fecha" + ex);
            }

            cliente.setFecha_ingreso(fechaConvertida);
            cliente.setPais(txtPaís.getText());
            cliente.setRazon_social(txtRazonSocial.getText());
            cliente.setDocumento(txtRut.getText());
            cliente.setTelefono(txtTelefono.getText());

            Transaction transacion = seccion.beginTransaction();
            seccion.save(cliente);
            transacion.commit();
            seccion.close();

            JOptionPane.showMessageDialog(null, "Cliente registrado correctamente!");

        }
    }

    private void EditarCliente() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();

        } else {

            Session seccion = HibernateUtil.getSeccion();
            Cliente cliente = new Cliente();
            cliente.setId_cliente(Integer.parseInt(jlbCodigo.getText()));
            cliente.setNombre(txtNombre.getText());
            cliente.setCel(txtCel.getText());
            cliente.setCi(txtCI.getText());
            cliente.setCiudad(txtCiudad.getText());
            cliente.setDireccion(txtDireccion.getText());
            cliente.setEmail(txtEmail.getText());
            String FechaIngreso = Utilidades.getDataTelaToBD(txtFechaIngreso.getText());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            Date fechaConvertida = null;
            try {
                fechaConvertida = formato.parse(FechaIngreso);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Error al dar formato a la fecha" + ex);
            }

            cliente.setFecha_ingreso(fechaConvertida);
            cliente.setPais(txtPaís.getText());
            cliente.setRazon_social(txtRazonSocial.getText());
            cliente.setDocumento(txtRut.getText());
            cliente.setTelefono(txtTelefono.getText());

            Transaction transacion = seccion.beginTransaction();
            seccion.update(cliente);
            transacion.commit();
            seccion.close();

            JOptionPane.showMessageDialog(null, "Cliente editado correctamente!");

        }

    }

    private void eliminaCliente() {

        Session seccion = HibernateUtil.getSeccion();
        Cliente cliente = new Cliente();
        cliente.setId_cliente(Integer.parseInt(jlbCodigo.getText()));
        Transaction transacion = seccion.beginTransaction();
        seccion.delete(cliente);
        transacion.commit();
        seccion.close();

        JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente!");

    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        txtDireccion.setEditable(true);
        txtCiudad.setEditable(true);
        txtPaís.setEditable(true);
        txtCI.setEditable(true);
        txtTelefono.setEditable(true);
        txtEmail.setEditable(true);
        txtCel.setEditable(true);
        txtFechaIngreso.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtRut.setEditable(true);
        tblCliente.setEnabled(false);
        tblCliente.setVisible(false);
    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        txtDireccion.setEditable(false);
        txtCiudad.setEditable(false);
        txtPaís.setEditable(false);
        txtCI.setEditable(false);
        txtTelefono.setEditable(false);
        txtEmail.setEditable(false);
        txtCel.setEditable(false);
        txtFechaIngreso.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtRut.setEditable(false);
        tblCliente.setEnabled(true);
        tblCliente.setVisible(true);
    }

    private void desabilitaBotoes() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);
    }

    private void habilitaBotoes() {
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }

    private void limpiaCampos() {

        jlbCodigo.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
        txtPaís.setText("");
        txtCI.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtCel.setText("");
        txtFechaIngreso.setText("");
        txtRazonSocial.setText("");
        txtRut.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPaís = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtCI = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        try{
            formatoftxtFechaIngreso = new MaskFormatter("##/##/####");
        }catch (Exception error){
            JOptionPane.showMessageDialog(null, "No fue posible crear la mascara, error ="+error);
        }
        txtFechaIngreso = new JFormattedTextField(formatoftxtFechaIngreso);
        jlbCodigo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFiltroNombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnSeleccionaCliente = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(700, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Clientes");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNombre, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Dirección");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel4, gridBagConstraints);

        txtDireccion.setEditable(false);
        txtDireccion.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtDireccion, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Ciudad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel5, gridBagConstraints);

        txtCiudad.setEditable(false);
        txtCiudad.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtCiudad, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setText("País");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel6, gridBagConstraints);

        txtPaís.setEditable(false);
        txtPaís.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtPaís, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setText("C.I");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setText("Teléfono");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel9, gridBagConstraints);

        txtTelefono.setEditable(false);
        txtTelefono.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtTelefono, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel10.setText("Email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel10, gridBagConstraints);

        txtEmail.setEditable(false);
        txtEmail.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtEmail, gridBagConstraints);

        txtCI.setEditable(false);
        txtCI.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtCI, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("Razón Social");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel2.add(jLabel7, gridBagConstraints);

        txtRazonSocial.setEditable(false);
        txtRazonSocial.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtRazonSocial, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel11.setText("Fecha de Ingreso");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        jPanel2.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel12.setText("Rut");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        jPanel2.add(jLabel12, gridBagConstraints);

        txtRut.setEditable(false);
        txtRut.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtRut, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel13.setText("Cel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        jPanel2.add(jLabel13, gridBagConstraints);

        txtCel.setEditable(false);
        txtCel.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtCel, gridBagConstraints);

        txtFechaIngreso.setEditable(false);
        txtFechaIngreso.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtFechaIngreso, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jlbCodigo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Filtro por Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel2, gridBagConstraints);

        txtFiltroNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtFiltroNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtFiltroNombre, gridBagConstraints);

        tblCliente.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "C.I.", "Razón Social", "R.U.T.", "Dirección", "ciudad", "pais", "cel", "telefono", "email", "fecha_ingreso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCliente.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCliente);
        tblCliente.getColumnModel().getColumn(5).setMinWidth(0);
        tblCliente.getColumnModel().getColumn(5).setPreferredWidth(0);
        tblCliente.getColumnModel().getColumn(5).setMaxWidth(0);
        tblCliente.getColumnModel().getColumn(6).setMinWidth(0);
        tblCliente.getColumnModel().getColumn(6).setPreferredWidth(0);
        tblCliente.getColumnModel().getColumn(6).setMaxWidth(0);
        tblCliente.getColumnModel().getColumn(7).setMinWidth(0);
        tblCliente.getColumnModel().getColumn(7).setPreferredWidth(0);
        tblCliente.getColumnModel().getColumn(7).setMaxWidth(0);
        tblCliente.getColumnModel().getColumn(8).setMinWidth(0);
        tblCliente.getColumnModel().getColumn(8).setPreferredWidth(0);
        tblCliente.getColumnModel().getColumn(8).setMaxWidth(0);
        tblCliente.getColumnModel().getColumn(9).setMinWidth(0);
        tblCliente.getColumnModel().getColumn(9).setPreferredWidth(0);
        tblCliente.getColumnModel().getColumn(9).setMaxWidth(0);
        tblCliente.getColumnModel().getColumn(10).setMinWidth(0);
        tblCliente.getColumnModel().getColumn(10).setPreferredWidth(0);
        tblCliente.getColumnModel().getColumn(10).setMaxWidth(0);
        tblCliente.getColumnModel().getColumn(11).setMinWidth(0);
        tblCliente.getColumnModel().getColumn(11).setPreferredWidth(0);
        tblCliente.getColumnModel().getColumn(11).setMaxWidth(0);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSeleccionaCliente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSeleccionaCliente.setMnemonic('C');
        btnSeleccionaCliente.setText("Selecciona Cliente");
        btnSeleccionaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaClienteActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaCliente);

        btnNovo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnNovo.setMnemonic('N');
        btnNovo.setText("Nuevo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNovo);

        btnAlterar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAlterar.setMnemonic('E');
        btnAlterar.setText("Editar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAlterar);

        btnExcluir.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(btnExcluir);

        btnSalvar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSalvar.setMnemonic('S');
        btnSalvar.setText("Salvar");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        limpiaCampos();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dia = new Date();
        String diaFormateado = formato.format(dia);
        txtFechaIngreso.setText(diaFormateado);
        habilitaCampos();
        habilitaBotoes();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (jlbCodigo.getText().equals("")) {
            NuevoCliente();
        } else {
            EditarCliente();
        }

        muestraContenidoTabla();
        desabilitaBotoes();
        desabilitaCampos();

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaBotoes();
        desabilitaCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        if (tblCliente.getSelectedRow() != -1) {
            habilitaBotoes();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (tblCliente.getSelectedRow() != -1) {
            String Nombre = txtNombre.getText();
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación del Cliente " + Nombre + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                eliminaCliente();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        muestraContenidoTabla();
        limpiaCampos();

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked

        limpiaCampos();

        try {
            int filaSeleccionada = tblCliente.getSelectedRow();

            jlbCodigo.setText(tblCliente.getValueAt(filaSeleccionada, 0).toString());

            txtNombre.setText(tblCliente.getValueAt(filaSeleccionada, 1).toString());

            Object objCI = tblCliente.getValueAt(filaSeleccionada, 2);
            if (objCI == null) {
            } else {
                txtCI.setText(tblCliente.getValueAt(filaSeleccionada, 2).toString());
            }

            Object objRS = tblCliente.getValueAt(filaSeleccionada, 3);
            if (objRS == null) {
            } else {
                txtRazonSocial.setText(tblCliente.getValueAt(filaSeleccionada, 3).toString());
            }

            Object objRUT = tblCliente.getValueAt(filaSeleccionada, 4);
            if (objRUT == null) /* || ((objCI instanceof String)&& ((String)objCI).length() == 0))*/ {
                //NO HACE NADA, EVITAMOS EL NULLPOINEXCEPTION
            } else {
                txtRut.setText(tblCliente.getValueAt(filaSeleccionada, 4).toString());
            }

            Object objDir = tblCliente.getValueAt(filaSeleccionada, 5);
            if (objDir == null) {
            } else {
                txtDireccion.setText(tblCliente.getValueAt(filaSeleccionada, 5).toString());
            }

            Object objCiu = tblCliente.getValueAt(filaSeleccionada, 6);
            if (objCiu == null) {
            } else {
                txtCiudad.setText(tblCliente.getValueAt(filaSeleccionada, 6).toString());
            }

            Object objPai = tblCliente.getValueAt(filaSeleccionada, 7);
            if (objPai == null) {
            } else {
                txtPaís.setText(tblCliente.getValueAt(filaSeleccionada, 7).toString());
            }

            Object objCel = tblCliente.getValueAt(filaSeleccionada, 8);
            if (objCel == null) {
            } else {
                txtCel.setText(tblCliente.getValueAt(filaSeleccionada, 8).toString());
            }

            Object objTel = tblCliente.getValueAt(filaSeleccionada, 9);
            if (objTel == null) {
            } else {
                txtTelefono.setText(tblCliente.getValueAt(filaSeleccionada, 9).toString());
            }

            Object objEmail = tblCliente.getValueAt(filaSeleccionada, 10);
            if (objEmail == null) {
            } else {
                txtEmail.setText(tblCliente.getValueAt(filaSeleccionada, 10).toString());
            }

            Object objFI = tblCliente.getValueAt(filaSeleccionada, 11);
            if (objFI == null) {
            } else {
                txtFechaIngreso.setText(tblCliente.getValueAt(filaSeleccionada, 11).toString());
            }

        } catch (Exception error) {
        }

    }//GEN-LAST:event_tblClienteMouseClicked

    private void txtFiltroNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroNombreActionPerformed

        Filtro = "from Clientes where nombre like '%" + txtFiltroNombre.getText() + "%'";
        muestraContenidoTabla();
    }//GEN-LAST:event_txtFiltroNombreActionPerformed

    private void btnSeleccionaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaClienteActionPerformed

        int filaSeleccionada = tblCliente.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente en la tabla!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            Cliente cliente = new Cliente();
            cliente.setId_cliente(Integer.parseInt(tblCliente.getValueAt(filaSeleccionada, 0).toString()));
            cliente.setNombre(tblCliente.getValueAt(filaSeleccionada, 1).toString());
            if (seteaClienteVenta == null) {
                seteaClientePedido.agregarCliente(cliente);
                this.dispose();
                seteaClientePedido.toFront();
            } else if (seteaClientePedido == null) {
                seteaClienteVenta.setClie(cliente);
                this.dispose();
                seteaClienteVenta.toFront();
            }

        }


    }//GEN-LAST:event_btnSeleccionaClienteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel jlbCodigo;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JFormattedTextField txtFechaIngreso;
    private javax.swing.JTextField txtFiltroNombre;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPaís;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtRut;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
