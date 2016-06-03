package com.Articulos;

import com.Beans.Articulo;
import com.DAO.DAOGenerico;
import com.Beans.Categoria;
import com.CategoriaArticulos.CategoriaFrame;
import com.Beans.ArticulosCompra;
import com.Compras.RegistraCompraFrame;
import com.DAO.ArticuloDAO;
import com.Renderers.MyDefaultCellRenderer;
import com.Beans.Unidad;
import com.Unidades.UnidadFrame;
import com.Beans.ArticulosVenta;
import com.Pedidos.RegistraPedido;
import com.Ventas.RegistraVentaFrame;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class FrameSeleccionaArticulo extends javax.swing.JInternalFrame {

    int edicion = 0;
    private RegistraCompraFrame registraCompraFrame;
    private RegistraVentaFrame registraVentaFrame;
    private RegistraPedido registraPedido;
    private Categoria categoria;
    private Unidad unidad;
    private DefaultTableModel tableModelArticulos;
    private ListSelectionModel listModelArticulos;
    private List<Articulo> listaArticulos;
    private ArticuloDAO articulosDAO;
    private DAOGenerico dAOGenerico;
    String codigoViejo = null;
    int panelVisible = 0;

    public FrameSeleccionaArticulo() {
        initComponents();

        defineModelo();
        listaArticulos = new ArrayList();
        articulosDAO = new ArticuloDAO();
        listaArticulos = articulosDAO.BuscaTodos(Articulo.class);
        muestraContenidoTabla();

    }

    public FrameSeleccionaArticulo(RegistraVentaFrame seteaArticulo) {
        initComponents();
        btnSeleccionaArticuloCompra.setVisible(false);
        defineModelo();
        listaArticulos = new ArrayList();
        articulosDAO = new ArticuloDAO();
        listaArticulos = articulosDAO.BuscaTodos(Articulo.class);
        muestraContenidoTabla();
        this.registraVentaFrame = seteaArticulo;
        AutoCompleteDecorator.decorate(cbCategoria);
        actualizaComboBox();

        AutoCompleteDecorator.decorate(cbUnidad);
        actualizaCbUnidad();
        jPanel2.setVisible(false);

        this.setSize(950, 650);

        //if (Perfil.equals("Operador")) {
        //  btnSalvar.setEnabled(false);
        //btnCancelar.setEnabled(false);
        //btnAlterar.setVisible(false);
        //}
    }

    public FrameSeleccionaArticulo(RegistraPedido seteaArticulo) {
        initComponents();
        btnSeleccionaArticuloCompra.setVisible(false);
        defineModelo();
        listaArticulos = new ArrayList();
        articulosDAO = new ArticuloDAO();
        listaArticulos = articulosDAO.BuscaTodos(Articulo.class);
        muestraContenidoTabla();
        this.registraPedido = seteaArticulo;
        AutoCompleteDecorator.decorate(cbCategoria);
        actualizaComboBox();

        AutoCompleteDecorator.decorate(cbUnidad);
        actualizaCbUnidad();
        jPanel2.setVisible(false);

        this.setSize(950, 650);

        //if (Perfil.equals("Operador")) {
        //  btnSalvar.setEnabled(false);
        //btnCancelar.setEnabled(false);
        //btnAlterar.setVisible(false);
        //}
    }

    public FrameSeleccionaArticulo(RegistraCompraFrame seteaArticulo, String Perfil) {
        initComponents();
        btnSeleccionaArticuloVenta.setVisible(false);
        defineModelo();
        listaArticulos = new ArrayList();
        articulosDAO = new ArticuloDAO();
        listaArticulos = articulosDAO.BuscaTodos(Articulo.class);
        muestraContenidoTabla();
        this.registraCompraFrame = seteaArticulo;
        AutoCompleteDecorator.decorate(cbCategoria);
        actualizaComboBox();

        AutoCompleteDecorator.decorate(cbUnidad);
        actualizaCbUnidad();
        jPanel2.setVisible(false);

        if (Perfil.equals("Operador")) {

            btnSalvar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnAlterar.setVisible(false);

        }

    }

    public final void actualizaComboBox() {

        try {
            List<Categoria> listadeCategorias = new ArrayList();

            DAOGenerico DAO = new DAOGenerico();
            listadeCategorias = DAO.BuscaTodos(Categoria.class);

            cbCategoria.removeAllItems();

            for (Categoria categorias : listadeCategorias) {

                cbCategoria.addItem(categorias);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public final void actualizaCbUnidad() {

        try {
            List<Unidad> listaUnidad = new ArrayList();

            DAOGenerico DAO = new DAOGenerico();
            listaUnidad = DAO.BuscaTodos(Unidad.class);

            cbUnidad.removeAllItems();

            for (Unidad unidades : listaUnidad) {

                cbUnidad.addItem(unidades);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void defineModelo() {

        tableModelArticulos = (DefaultTableModel) tblArticulos.getModel();

        listModelArticulos = tblArticulos.getSelectionModel();
        listModelArticulos.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    detallesArticulos();

                }
            }
        });

        ((DefaultTableCellRenderer) tblArticulos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void muestraContenidoTabla() {

        try {

            tableModelArticulos.setNumRows(0);

            for (Articulo articulo : listaArticulos) {

                tableModelArticulos.addRow(new Object[]{
                    articulo.getId(),
                    articulo.getNombre(),
                    articulo.getDescripcion(),
                    articulo.getCantidad(),
                    articulo.getDescuento(),
                    articulo.getValor_venta()});
            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error);
        }
    }

    private void detallesArticulos() {

        limpiaCampos();

        if (tblArticulos.getSelectedRow() != -1) {
            try {

                txtCodigo.setText(listaArticulos.get(tblArticulos.getSelectedRow()).getId());
                txtNombre.setText(listaArticulos.get(tblArticulos.getSelectedRow()).getNombre());
                txtCantidad.setText(String.valueOf(listaArticulos.get(tblArticulos.getSelectedRow()).getCantidad()));
                txtDescuento.setText(String.valueOf(listaArticulos.get(tblArticulos.getSelectedRow()).getDescuento()));
                txtGanancia.setText(String.valueOf(listaArticulos.get(tblArticulos.getSelectedRow()).getGanancia()));
                txtValorVenta.setText(String.valueOf(listaArticulos.get(tblArticulos.getSelectedRow()).getValor_venta()));
                txtValor_CompraSinImp.setText(String.valueOf(listaArticulos.get(tblArticulos.getSelectedRow()).getValor_compra()));
                txtDescripcion.setText(listaArticulos.get(tblArticulos.getSelectedRow()).getDescripcion());
                txtGananciaDescuento.setText(String.valueOf(listaArticulos.get(tblArticulos.getSelectedRow()).getGanancia_descuento()));
                cbCategoria.setSelectedItem(listaArticulos.get(tblArticulos.getSelectedRow()).getCategoria());
                cbIva.setSelectedItem(listaArticulos.get(tblArticulos.getSelectedRow()).getIva());
                txtValor_CompraConImp.setText(String.valueOf(listaArticulos.get(tblArticulos.getSelectedRow()).getValor_compra_impuesto()));
                cbUnidad.setSelectedItem(listaArticulos.get(tblArticulos.getSelectedRow()).getUnidad());

            } catch (Exception error) {
                error.printStackTrace();
            }
        }
    }

    private void NuevoProducto() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del articulo!", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();

        } else {

            try {

                Articulo articulo = new Articulo();
                articulo.setId(txtCodigo.getText());
                articulo.setNombre(txtNombre.getText());
                articulo.setCantidad(Double.parseDouble(txtCantidad.getText()));
                articulo.setDescuento(Double.parseDouble(txtDescuento.getText()));
                articulo.setGanancia(Double.parseDouble(txtGanancia.getText()));
                articulo.setGanancia_descuento(Double.parseDouble(txtGananciaDescuento.getText()));
                articulo.setDescripcion(txtDescripcion.getText());
                articulo.setValor_compra(Double.parseDouble(txtValor_CompraSinImp.getText()));
                articulo.setValor_venta(Double.parseDouble(txtValorVenta.getText()));

                List<Categoria> categoriaSeleccionada = new DAOGenerico().buscarPor(Categoria.class, "nombre", cbCategoria.getSelectedItem().toString());
                articulo.setCategoria(categoriaSeleccionada.get(0));

                List<Unidad> unidadSeleccionada = new DAOGenerico().buscarPor(Unidad.class, "descripcion", cbUnidad.getSelectedItem().toString());
                articulo.setUnidad(unidadSeleccionada.get(0));

                articulo.setIva(Double.parseDouble(String.valueOf(cbIva.getSelectedItem())));
                articulo.setValor_compra_impuesto(Double.parseDouble(txtValor_CompraConImp.getText()));

                dAOGenerico = new DAOGenerico(articulo);
                dAOGenerico.registra();

                JOptionPane.showMessageDialog(null, "Articulo registrado correctamente!");
                listaArticulos.clear();
                articulosDAO = new ArticuloDAO();
                listaArticulos = articulosDAO.BuscaTodos(Articulo.class);
                muestraContenidoTabla();
                desabilitaBotoes();
                desabilitaCampos();

            } catch (Exception ex) {
                Logger.getLogger(FrameSeleccionaArticulo.class.getName()).log(Level.SEVERE, null, ex);

                JOptionPane.showMessageDialog(null, "Verifique si el código del producto ya se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void EditarProducto() {

        try {
            if (txtNombre.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Informe el nombre del articulo!", "Error", JOptionPane.ERROR_MESSAGE);
                txtNombre.requestFocus();

            } else {
                Articulo articulo = new Articulo();
                articulo.setId(txtCodigo.getText());
                articulo.setNombre(txtNombre.getText());
                articulo.setCantidad(Double.parseDouble(txtCantidad.getText()));
                articulo.setDescuento(Double.parseDouble(txtDescuento.getText()));
                articulo.setGanancia(Double.parseDouble(txtGanancia.getText()));
                articulo.setGanancia_descuento(Double.parseDouble(txtGananciaDescuento.getText()));
                articulo.setDescripcion(txtDescripcion.getText());
                articulo.setValor_compra(Double.parseDouble(txtValor_CompraSinImp.getText()));
                articulo.setValor_venta(Double.parseDouble(txtValorVenta.getText()));

                List<Categoria> categoriaSeleccionada = new DAOGenerico().buscarPor(Categoria.class, "nombre", cbCategoria.getSelectedItem().toString());
                articulo.setCategoria(categoriaSeleccionada.get(0));

                List<Unidad> unidadSeleccionada = new DAOGenerico().buscarPor(Unidad.class, "descripcion", cbUnidad.getSelectedItem().toString());
                articulo.setUnidad(unidadSeleccionada.get(0));

                articulo.setIva(Double.parseDouble(String.valueOf(cbIva.getSelectedItem())));
                articulo.setValor_compra_impuesto(Double.parseDouble(txtValor_CompraConImp.getText()));

                DAOGenerico dao = new DAOGenerico(articulo);
                dao.registraOActualiza();

                if (!codigoViejo.equals(txtCodigo.getText())) {
                    DAOGenerico daoElimina = new DAOGenerico(articulo);
                    articulo.setId(codigoViejo);
                    daoElimina.elimina();
                }

                JOptionPane.showMessageDialog(null, "Articulo editado correctamente!");
                listaArticulos.clear();
                articulosDAO = new ArticuloDAO();
                listaArticulos = articulosDAO.BuscaTodos(Articulo.class);
                muestraContenidoTabla();
                desabilitaBotoes();
                desabilitaCampos();

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al editar articulo");
        }

    }

    private void eliminaProducto() {

        Articulo articulo = new Articulo();
        articulo.setId(listaArticulos.get(tblArticulos.getSelectedRow()).getId());

        dAOGenerico = new DAOGenerico(articulo);
        dAOGenerico.elimina();

        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente!");

    }

    private void seleccionaArticulo() {

        if (tblArticulos.getSelectedRow() == -1) {

            JOptionPane.showMessageDialog(null, "Selecciones un articulo en la tabla!", "Error", JOptionPane.ERROR_MESSAGE);

        } else {

            Articulo articulo = new Articulo();
            articulo.setId(listaArticulos.get(tblArticulos.getSelectedRow()).getId());
            articulo.setNombre(listaArticulos.get(tblArticulos.getSelectedRow()).getNombre());
            articulo.setValor_venta(listaArticulos.get(tblArticulos.getSelectedRow()).getValor_venta());

            if (registraVentaFrame == null) {
                registraPedido.setArticulo(articulo);
            } else if (registraPedido == null) {
                registraVentaFrame.setArticulo(articulo);
            }

        }
    }

    private void seleccionaArticuloCompra() {

        if (tblArticulos.getSelectedRow() == -1) {

            JOptionPane.showMessageDialog(null, "Selecciones un articulo en la tabla!", "Error", JOptionPane.ERROR_MESSAGE);

        } else {

            Articulo articulo = new Articulo();
            articulo.setId(listaArticulos.get(tblArticulos.getSelectedRow()).getId());
            articulo.setNombre(listaArticulos.get(tblArticulos.getSelectedRow()).getNombre());

            registraCompraFrame.setArticulo(articulo);

        }
    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        txtDescripcion.setEnabled(true);
        cbCategoria.setEnabled(true);
        cbUnidad.setEnabled(true);
        tblArticulos.setEnabled(false);
        tblArticulos.setVisible(false);
        txtDescripcion.setEditable(true);
        txtDescripcion.setEnabled(true);
        btnSelecionaCategoria.setEnabled(true);
        btnSelecionaUnidad.setEnabled(true);
        txtCantidad.setEditable(false);
    }

    private void desabilitaCampos() {

        txtNombre.setEditable(false);
        txtValor_CompraSinImp.setEditable(false);
        txtValor_CompraConImp.setEditable(false);
        txtGanancia.setEditable(false);
        txtValorVenta.setEditable(false);
        txtDescuento.setEditable(false);
        txtCantidad.setEditable(false);
        txtCodigo.setEditable(false);
        tblArticulos.setEnabled(true);
        tblArticulos.setVisible(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setEnabled(true);
        cbCategoria.setEnabled(false);
        cbUnidad.setEnabled(false);
        cbIva.setEditable(false);
        btnSelecionaCategoria.setEnabled(false);
        btnSelecionaUnidad.setEnabled(false);

    }

    private void desabilitaBotoes() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnAlterar.setEnabled(true);
    }

    private void habilitaBotoes() {
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnAlterar.setEnabled(false);
    }

    private void limpiaCampos() {

        txtCodigo.setText("");
        txtNombre.setText("");
        txtValor_CompraSinImp.setText("");
        txtValor_CompraConImp.setText("");
        txtGanancia.setText("");
        txtGananciaDescuento.setText("");
        txtValorVenta.setText("");
        txtDescuento.setText("");
        txtCantidad.setText("");
        txtDescripcion.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulos = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jlbCodigo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JFormattedTextField();
        txtCodigo = new javax.swing.JFormattedTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtValorVenta = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtGanancia = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        txtGananciaDescuento = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbIva = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtValor_CompraSinImp = new javax.swing.JTextField();
        txtValor_CompraConImp = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        cbCategoria = new javax.swing.JComboBox();
        btnSelecionaCategoria = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        cbUnidad = new javax.swing.JComboBox();
        btnSelecionaUnidad = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();
        rbCodigo = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbDescripcion = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        btnSeleccionaArticuloVenta = new javax.swing.JButton();
        btnSeleccionaArticuloCompra = new javax.swing.JButton();
        btnDetalles = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(900, 680));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Articulos");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        tblArticulos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Descripcion", "Stock", "Precio con Descuento", "Precio Normal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblArticulos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblArticulos);
        tblArticulos.getColumnModel().getColumn(0).setPreferredWidth(25);
        tblArticulos.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulos.getColumnModel().getColumn(1).setPreferredWidth(70);
        tblArticulos.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulos.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblArticulos.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulos.getColumnModel().getColumn(3).setPreferredWidth(15);
        tblArticulos.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulos.getColumnModel().getColumn(4).setPreferredWidth(15);
        tblArticulos.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
        tblArticulos.getColumnModel().getColumn(5).setPreferredWidth(15);
        tblArticulos.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setText("Filtro por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel9, gridBagConstraints);

        txtFiltro.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtFiltro.setToolTipText("Digite aqui lo que quiera buscar y presione ENTER");
        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtFiltro, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNombre, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel11.setText("Stock Disponible");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel2.add(jLabel11, gridBagConstraints);

        jlbCodigo.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jlbCodigo.setText("Código del Articulo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jlbCodigo, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel7, gridBagConstraints);

        txtCantidad.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtCantidad, gridBagConstraints);

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtCodigo, gridBagConstraints);

        jTabbedPane1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setText("Valor Venta (Imp. Incl.)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel6, gridBagConstraints);

        txtValorVenta.setEditable(false);
        txtValorVenta.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtValorVenta, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Ganancia (%)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel5, gridBagConstraints);

        txtGanancia.setEditable(false);
        txtGanancia.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtGanancia, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setText("Valor con Descuento (Imp Incl.)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel8, gridBagConstraints);

        txtDescuento.setEditable(false);
        txtDescuento.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtDescuento, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel12.setText("Ganancia con descuento (%)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel5.add(jLabel12, gridBagConstraints);

        txtGananciaDescuento.setEditable(false);
        txtGananciaDescuento.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtGananciaDescuento, gridBagConstraints);

        jTabbedPane1.addTab("Datos Venta", jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jTabbedPane1, gridBagConstraints);

        jTabbedPane2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel14.setText("Valor Compra (Sin Imp.)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jLabel14, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel10.setText("I.V.A. %");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel6.add(jLabel10, gridBagConstraints);

        cbIva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22" }));
        cbIva.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(cbIva, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Valor Compra (Imp. Incl.)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jLabel4, gridBagConstraints);

        txtValor_CompraSinImp.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtValor_CompraSinImp, gridBagConstraints);

        txtValor_CompraConImp.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtValor_CompraConImp, gridBagConstraints);

        jTabbedPane2.addTab("Datos compra", jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jTabbedPane2, gridBagConstraints);

        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cbCategoria.setEnabled(false);
        cbCategoria.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel7.add(cbCategoria);

        btnSelecionaCategoria.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSelecionaCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/Zoom.png"))); // NOI18N
        btnSelecionaCategoria.setEnabled(false);
        btnSelecionaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaCategoriaActionPerformed(evt);
            }
        });
        jPanel7.add(btnSelecionaCategoria);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel7, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel15.setText("Categoría del Articulo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel2.add(jLabel15, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel20.setText("Unidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel2.add(jLabel20, gridBagConstraints);

        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cbUnidad.setEnabled(false);
        cbUnidad.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel12.add(cbUnidad);

        btnSelecionaUnidad.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSelecionaUnidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/Zoom.png"))); // NOI18N
        btnSelecionaUnidad.setEnabled(false);
        btnSelecionaUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaUnidadActionPerformed(evt);
            }
        });
        jPanel12.add(btnSelecionaUnidad);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel12, gridBagConstraints);

        txtDescripcion.setEditable(false);
        txtDescripcion.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtDescripcion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jPanel2, gridBagConstraints);

        buttonGroup1.add(rbCodigo);
        rbCodigo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        rbCodigo.setSelected(true);
        rbCodigo.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel3.add(rbCodigo, gridBagConstraints);

        buttonGroup1.add(rbNombre);
        rbNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        rbNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel3.add(rbNombre, gridBagConstraints);

        buttonGroup1.add(rbDescripcion);
        rbDescripcion.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        rbDescripcion.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel3.add(rbDescripcion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSeleccionaArticuloVenta.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSeleccionaArticuloVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/images.jpg"))); // NOI18N
        btnSeleccionaArticuloVenta.setMnemonic('A');
        btnSeleccionaArticuloVenta.setText("Selecciona Articulo");
        btnSeleccionaArticuloVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaArticuloVentaActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaArticuloVenta);

        btnSeleccionaArticuloCompra.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSeleccionaArticuloCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/images.jpg"))); // NOI18N
        btnSeleccionaArticuloCompra.setMnemonic('A');
        btnSeleccionaArticuloCompra.setText("Selecciona Articulo");
        btnSeleccionaArticuloCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaArticuloCompraActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaArticuloCompra);

        btnDetalles.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnDetalles.setMnemonic('D');
        btnDetalles.setText("Exibir/Ocultar Detalles");
        btnDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });
        jPanel4.add(btnDetalles);

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

        btnVolver.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnVolver.setMnemonic('V');
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        jPanel4.add(btnVolver);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        jPanel2.setVisible(true);
        txtCodigo.setEnabled(true);
        txtCodigo.setEditable(true);
        txtCodigo.requestFocus();
        limpiaCampos();
        txtValor_CompraConImp.setText("0");
        txtValor_CompraSinImp.setText("0");
        txtDescuento.setText("0");
        txtValorVenta.setText("0");
        txtCantidad.setText("0");
        txtGanancia.setText("0");
        txtGananciaDescuento.setText("0");
        habilitaCampos();
        habilitaBotoes();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        Double compra = Double.parseDouble(txtValor_CompraConImp.getText());
        Double venta1 = Double.parseDouble(txtValorVenta.getText());
        if (compra > venta1) {
            JOptionPane.showMessageDialog(null, "Atención, el valor de venta no debe "
                    + "ser inferiror al valor de compra con Imp. Incl.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (edicion == 1) {
                EditarProducto();
            } else {
                NuevoProducto();
            }
            edicion = 0;
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaBotoes();
        desabilitaCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        List ListVerificaCompras = new DAOGenerico().buscarPor(ArticulosCompra.class, "id_articulo", txtCodigo.getText());

        List ListVerificaVentas = new DAOGenerico().buscarPor(ArticulosVenta.class, "id_articulo", txtCodigo.getText());

        if (!ListVerificaCompras.isEmpty()) {
            txtCodigo.setEditable(false);
        } else if (!ListVerificaVentas.isEmpty()) {
            txtCodigo.setEditable(false);
        } else {
            txtCodigo.setEditable(true);

        }

        if (tblArticulos.getSelectedRow() != -1) {
            jPanel2.setVisible(true);
            habilitaBotoes();
            habilitaCampos();
            edicion = 1;
            codigoViejo = txtCodigo.getText();
            //txtCodigo.setEditable(false);

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        if (rbCodigo.isSelected() == true) {

            if (txtFiltro.getText().equals("")) {
                listaArticulos.clear();
                listaArticulos = articulosDAO.BuscaTodos(Articulo.class);
            } else {

                listaArticulos.clear();
                listaArticulos = articulosDAO.buscarPorIDString(Articulo.class, txtFiltro.getText());
                muestraContenidoTabla();

            }
            muestraContenidoTabla();

        } else if (rbNombre.isSelected() == true) {

            listaArticulos.clear();
            listaArticulos = articulosDAO.buscarPor(Articulo.class, "nombre", txtFiltro.getText());
            muestraContenidoTabla();

        } else if (rbDescripcion.isSelected() == true) {

            listaArticulos.clear();
            listaArticulos = articulosDAO.buscarPor(Articulo.class, "descripcion", txtFiltro.getText());
            muestraContenidoTabla();
        }

    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnSeleccionaArticuloCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaArticuloCompraActionPerformed

        seleccionaArticuloCompra();

    }//GEN-LAST:event_btnSeleccionaArticuloCompraActionPerformed

    private void btnSelecionaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaCategoriaActionPerformed

        CategoriaFrame categorias = new CategoriaFrame(this);
        this.getDesktopPane().add(categorias);
        categorias.setVisible(true);
        categorias.toFront();
    }//GEN-LAST:event_btnSelecionaCategoriaActionPerformed

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost

        try {
            Articulo articulo = new ArticuloDAO().buscaArtUnicoPorIDStr(txtCodigo.getText());
            if ((articulo != null && txtNombre.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "El código ingresado ya existe en la base de datos", "Error de código", JOptionPane.ERROR_MESSAGE);
                txtCodigo.requestFocus();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar articulo " + ex);
            ex.printStackTrace();
        }

    }//GEN-LAST:event_txtCodigoFocusLost

    private void btnSelecionaUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaUnidadActionPerformed

        UnidadFrame unidades = new UnidadFrame(this);
        this.getDesktopPane().add(unidades);
        unidades.setVisible(true);
        unidades.toFront();

    }//GEN-LAST:event_btnSelecionaUnidadActionPerformed

    private void btnDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed

        switch (panelVisible) {
            case 0:
                panelVisible = 1;
                jPanel2.setVisible(true);
                break;
            case 1:
                panelVisible = 0;
                jPanel2.setVisible(false);
                break;
        }

    }//GEN-LAST:event_btnDetallesActionPerformed

    private void btnSeleccionaArticuloVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaArticuloVentaActionPerformed

        seleccionaArticulo();
    }//GEN-LAST:event_btnSeleccionaArticuloVentaActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();

    }//GEN-LAST:event_btnVolverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDetalles;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaArticuloCompra;
    private javax.swing.JButton btnSeleccionaArticuloVenta;
    private javax.swing.JButton btnSelecionaCategoria;
    private javax.swing.JButton btnSelecionaUnidad;
    private javax.swing.JButton btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCategoria;
    private javax.swing.JComboBox cbIva;
    private javax.swing.JComboBox cbUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel jlbCodigo;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbDescripcion;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblArticulos;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JFormattedTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JFormattedTextField txtDescuento;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JFormattedTextField txtGanancia;
    private javax.swing.JTextField txtGananciaDescuento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JFormattedTextField txtValorVenta;
    private javax.swing.JTextField txtValor_CompraConImp;
    private javax.swing.JTextField txtValor_CompraSinImp;
    // End of variables declaration//GEN-END:variables

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        actualizaComboBox();
        cbCategoria.setSelectedItem(categoria);
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
        actualizaCbUnidad();
        cbUnidad.setSelectedItem(unidad);
    }
}
