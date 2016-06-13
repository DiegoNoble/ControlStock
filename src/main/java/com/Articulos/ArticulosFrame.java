package com.Articulos;

import com.Beans.Articulo;
import com.DAO.DAOGenerico;
import com.Beans.Categoria;
import com.CategoriaArticulos.CategoriaFrame;
import com.Beans.ArticulosCompra;
import com.Beans.Unidad;
import com.Unidades.UnidadFrame;
import com.Beans.ArticulosVenta;
import com.Compras.RegistraCompra;
import com.DAO.ArticuloDAO;
import com.Pedidos.RegistraPedido;
import com.usuarios.frameLogin;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ArticulosFrame extends javax.swing.JInternalFrame {

    int edicion = 0;
    RegistraCompra registraCompra;
    RegistraPedido registraPedido;
    private ArticulosTableModel tableModelArticulos;
    private ListSelectionModel listModelArticulos;
    private Categoria categoria;
    private Unidad unidad;
    int panelVisible = 0;
    String codigoViejo = null;
    Articulo articuloSeleccionado;
    List<Articulo> listArticulos;
    ArticuloDAO articuloDAO;
    String perfil;

    public ArticulosFrame(String perfil) {
        initComponents();
        this.perfil = perfil;
        btnSeleccionaArticuloVenta.setVisible(false);
        defineModelo();
        filtros();
        //btnExcluir.setVisible(false);
        this.setSize(950, 650);

        if (frameLogin.getInstance().getPerfil().equals("Operador")) {

            btnSalvar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
        }
        AutoCompleteDecorator.decorate(cbCategoria);
        actualizaCbCategoria();
        AutoCompleteDecorator.decorate(cbUnidad);
        actualizaCbUnidad();

        jPanel2.setVisible(false);
    }

    public ArticulosFrame(RegistraCompra registraCompra) {
        initComponents();
        this.registraCompra = registraCompra;
        btnSeleccionaArticuloVenta.setVisible(true);
        defineModelo();
        filtros();
        //btnExcluir.setVisible(false);
        this.setSize(950, 650);

        if (frameLogin.getInstance().getPerfil().equals("Operador")) {

            btnSalvar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
        }
        AutoCompleteDecorator.decorate(cbCategoria);
        actualizaCbCategoria();
        AutoCompleteDecorator.decorate(cbUnidad);
        actualizaCbUnidad();

        jPanel2.setVisible(false);
    }

    public ArticulosFrame(RegistraPedido registraPedido) {
        initComponents();
        this.registraPedido = registraPedido;
        btnSeleccionaArticuloVenta.setVisible(true);
        defineModelo();
        filtros();
        //btnExcluir.setVisible(false);
        this.setSize(950, 650);

        if (frameLogin.getInstance().getPerfil().equals("Operador")) {

            btnSalvar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
        }
        AutoCompleteDecorator.decorate(cbCategoria);
        actualizaCbCategoria();
        AutoCompleteDecorator.decorate(cbUnidad);
        actualizaCbUnidad();

        jPanel2.setVisible(false);
    }

    public final void actualizaCbCategoria() {

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

    private void seleccionaArticulo() {

        if (registraPedido != null) {
            registraPedido.agregarArticuloPedido(articuloSeleccionado);
        } else if (registraCompra != null) {
            registraCompra.agregarArticuloCompra(articuloSeleccionado);

        }
    }

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void defineModelo() {
        listArticulos = new ArrayList<>();
        articuloDAO = new ArticuloDAO();
        tableModelArticulos = new ArticulosTableModel(listArticulos);
        tblArticulos.setModel(tableModelArticulos);
        listArticulos.addAll(articuloDAO.BuscaTodos(Articulo.class));
        tableModelArticulos.fireTableDataChanged();

        listModelArticulos = tblArticulos.getSelectionModel();
        listModelArticulos.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tblArticulos.getSelectedRow() != -1) {
                        articuloSeleccionado = listArticulos.get(tblArticulos.getSelectedRow());
                        btnSeleccionaArticuloVenta.setEnabled(true);
                        detallesArticulos();

                    } else {
                        articuloSeleccionado = null;
                        btnSeleccionaArticuloVenta.setEnabled(true);
                    }
                }
            }
        });

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
                articulo.setDescripcion(txtDescripcion.getText());
                articulo.setValor_venta(Double.parseDouble(txtValorVenta.getText()));

                List<Categoria> categoriaSeleccionada = new DAOGenerico().buscarPor(Categoria.class, "nombre", cbCategoria.getSelectedItem().toString());
                articulo.setCategoria(categoriaSeleccionada.get(0));

                List<Unidad> unidadSeleccionada = new DAOGenerico().buscarPor(Unidad.class, "descripcion", cbUnidad.getSelectedItem().toString());
                articulo.setUnidad(unidadSeleccionada.get(0));

                articulo.setIva(Double.parseDouble(String.valueOf(cbIva.getSelectedItem())));
                articulo.setValor_compra(Double.parseDouble(txtValor_CompraConImp.getText()));

                DAOGenerico dao = new DAOGenerico(articulo);
                dao.guardar();

                JOptionPane.showMessageDialog(null, "Articulo registrado correctamente!");

                buscarTodos();
                desabilitaBotoes();
                desabilitaCampos();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error " + ex);
            }
        }
    }

    private void EditarProducto() {

        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del articulo!", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();

        } else {

            Articulo articulo = new Articulo();
            articulo.setId(txtCodigo.getText());
            articulo.setNombre(txtNombre.getText());
            articulo.setCantidad(Double.parseDouble(txtCantidad.getText()));
            articulo.setDescripcion(txtDescripcion.getText());
            articulo.setValor_venta(Double.parseDouble(txtValorVenta.getText()));

            List<Categoria> categoriaSeleccionada = new DAOGenerico().buscarPor(Categoria.class, "nombre", cbCategoria.getSelectedItem().toString());
            articulo.setCategoria(categoriaSeleccionada.get(0));

            List<Unidad> unidadSeleccionada = new DAOGenerico().buscarPor(Unidad.class, "descripcion", cbUnidad.getSelectedItem().toString());
            articulo.setUnidad(unidadSeleccionada.get(0));

            articulo.setIva(Double.parseDouble(String.valueOf(cbIva.getSelectedItem())));
            articulo.setValor_compra(Double.parseDouble(txtValor_CompraConImp.getText()));

            DAOGenerico dao = new DAOGenerico(articulo);
            dao.registraOActualiza();

            if (!codigoViejo.equals(txtCodigo.getText())) {
                DAOGenerico daoElimina = new DAOGenerico(articulo);
                articulo.setId(codigoViejo);
                daoElimina.elimina();
            }

            JOptionPane.showMessageDialog(null, "Articulo editado correctamente!");
            buscarTodos();
            desabilitaBotoes();
            desabilitaCampos();

        }

    }

    void buscarTodos() {
        articuloDAO = new ArticuloDAO();
        listArticulos.clear();
        listArticulos.addAll(articuloDAO.BuscaTodos(Articulo.class));
        tableModelArticulos.fireTableDataChanged();
    }

    private void filtros() {

        try {

            tblArticulos.clearSelection();
            articuloDAO = new ArticuloDAO();
            if (rbCodigo.isSelected()) {

                if (txtFiltro.getText().equals("")) {
                    listArticulos.clear();
                    listArticulos.addAll(articuloDAO.BuscaTodos(Articulo.class));
                    tableModelArticulos.fireTableDataChanged();
                } else {
                    listArticulos.clear();
                    listArticulos.addAll(articuloDAO.buscarPor(Articulo.class, "id", txtFiltro.getText()));
                    tableModelArticulos.fireTableDataChanged();
                }

            } else if (rbNombre.isSelected()) {
                listArticulos.clear();
                listArticulos.addAll(articuloDAO.buscarPor(Articulo.class, "nombre", txtFiltro.getText()));
                tableModelArticulos.fireTableDataChanged();

            } else if (rbDescripcion.isSelected() == true) {
                listArticulos.clear();
                listArticulos.addAll(articuloDAO.buscarPor(Articulo.class, "descripcion", txtFiltro.getText()));
                tableModelArticulos.fireTableDataChanged();
            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error);
            error.printStackTrace();
        }
    }

    private void eliminaProducto() {

        Articulo articulo = new Articulo();
        articulo.setId(txtCodigo.getText());

        DAOGenerico dao = new DAOGenerico(articulo);
        dao.elimina();

        JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente!");

    }

    public void detallesArticulos() {

        limpiaCampos();

        if (articuloSeleccionado != null) {
            try {
                txtCantidad.setText(articuloSeleccionado.getCantidad().toString());
                txtCodigo.setText(articuloSeleccionado.getId());
                txtDescripcion.setText(articuloSeleccionado.getDescripcion());
                txtNombre.setText(articuloSeleccionado.getNombre());
                txtValorVenta.setText(articuloSeleccionado.getValor_venta().toString());
                txtValor_CompraConImp.setText(articuloSeleccionado.getValor_compra().toString());

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Error al mostrar detalles", "Error", JOptionPane.ERROR_MESSAGE);
                error.printStackTrace();
            }
        }
    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        txtValor_CompraConImp.setEditable(true);
        //txtGanancia.setEditable(true);
        txtValorVenta.setEditable(true);
        txtCantidad.setEditable(false);
        //txtCodigo.setEditable(true);
        txtDescripcion.setEnabled(true);
        cbCategoria.setEnabled(true);
        cbUnidad.setEnabled(true);
        cbIva.setEditable(true);
        tblArticulos.setEnabled(false);
        tblArticulos.setVisible(false);
        txtDescripcion.setEditable(true);
        txtDescripcion.setEnabled(true);
        btnSelecionaCategoria.setEnabled(true);
        btnSelecionaUnidad.setEnabled(true);
        if (perfil.equals("Gerente")) {
            txtCantidad.setEnabled(true);
            txtCantidad.setEditable(true);
        }

    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        txtValor_CompraConImp.setEditable(false);
        txtValorVenta.setEditable(false);
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
        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnNovo.setEnabled(true);
    }

    private void habilitaBotoes() {
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnNovo.setEnabled(false);
    }

    private void limpiaCampos() {

        txtCodigo.setText("");
        txtNombre.setText("");
        txtValor_CompraConImp.setText("");
        txtValorVenta.setText("");
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
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jlbCodigo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cbIva = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        txtValor_CompraConImp = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtValorVenta = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        cbCategoria = new javax.swing.JComboBox();
        btnSelecionaCategoria = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        cbUnidad = new javax.swing.JComboBox();
        btnSelecionaUnidad = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulos = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        rbCodigo = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbDescripcion = new javax.swing.JRadioButton();
        txtFiltro = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnDetalles = new javax.swing.JButton();
        btnListado = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSeleccionaArticuloVenta = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(900, 730));
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

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel3, gridBagConstraints);

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtNombre, gridBagConstraints);

        jlbCodigo.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jlbCodigo.setText("Código del Articulo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jlbCodigo, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel7, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel16.setText("Categoría del Articulo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel8.add(jLabel16, gridBagConstraints);

        jTabbedPane3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel13.setText("I.V.A. %");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel7.add(jLabel13, gridBagConstraints);

        cbIva.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbIva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21" }));
        cbIva.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbIva, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel18.setText("Valor Compra (Imp. Incl.)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel18, gridBagConstraints);

        txtValor_CompraConImp.setEditable(false);
        txtValor_CompraConImp.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtValor_CompraConImp, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setText("Valor Venta (Imp. Incl.)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel7.add(jLabel6, gridBagConstraints);

        txtValorVenta.setEditable(false);
        txtValorVenta.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtValorVenta, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel11.setText("Stock Disponible");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel7.add(jLabel11, gridBagConstraints);

        txtCantidad.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtCantidad, gridBagConstraints);

        jTabbedPane3.addTab("Valores", jPanel7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jTabbedPane3, gridBagConstraints);

        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cbCategoria.setEnabled(false);
        cbCategoria.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel11.add(cbCategoria);

        btnSelecionaCategoria.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSelecionaCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/Zoom.png"))); // NOI18N
        btnSelecionaCategoria.setEnabled(false);
        btnSelecionaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaCategoriaActionPerformed(evt);
            }
        });
        jPanel11.add(btnSelecionaCategoria);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jPanel11, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel20.setText("Unidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel8.add(jLabel20, gridBagConstraints);

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
        jPanel8.add(jPanel12, gridBagConstraints);

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
        jPanel8.add(txtDescripcion, gridBagConstraints);

        txtCodigo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtCodigo, gridBagConstraints);

        jTabbedPane4.addTab("Información Principal", jPanel8);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jTabbedPane4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        tblArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblArticulos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblArticulos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnDetalles.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnDetalles.setMnemonic('D');
        btnDetalles.setText("Exibir/Ocultar Detalles");
        btnDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });
        jPanel4.add(btnDetalles);

        btnListado.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnListado.setMnemonic('L');
        btnListado.setText("Listado de Articulos");
        btnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListadoActionPerformed(evt);
            }
        });
        jPanel4.add(btnListado);

        btnNovo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnNovo.setMnemonic('N');
        btnNovo.setText("Nuevo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNovo);

        btnAlterar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnAlterar.setMnemonic('E');
        btnAlterar.setText("Editar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAlterar);

        btnExcluir.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(btnExcluir);

        btnSalvar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSalvar.setMnemonic('S');
        btnSalvar.setText("Salvar");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);

        btnSeleccionaArticuloVenta.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSeleccionaArticuloVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/images.jpg"))); // NOI18N
        btnSeleccionaArticuloVenta.setMnemonic('A');
        btnSeleccionaArticuloVenta.setText("Selecciona Articulo");
        btnSeleccionaArticuloVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaArticuloVentaActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaArticuloVenta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

        if (tblArticulos.getSelectedRow()
                != -1) {
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

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        if (tblArticulos.getSelectedRow() != -1) {
            String Nombre = txtNombre.getText();
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación del Cliente " + Nombre + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                eliminaProducto();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        txtCodigo.setText("");
        filtros();
        limpiaCampos();

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListadoActionPerformed

        new DAOGenerico<>().informe();


    }//GEN-LAST:event_btnListadoActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        jPanel2.setVisible(true);
        txtCodigo.setEnabled(true);
        txtCodigo.setEditable(true);
        txtCodigo.requestFocus();
        limpiaCampos();
        txtCantidad.setText("0");
        habilitaCampos();
        habilitaBotoes();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        filtros();
    }//GEN-LAST:event_txtFiltroActionPerformed

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

    private void btnSelecionaUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaUnidadActionPerformed

        UnidadFrame unidades = new UnidadFrame(this);
        this.getDesktopPane().add(unidades);
        unidades.setVisible(true);
        unidades.toFront();
    }//GEN-LAST:event_btnSelecionaUnidadActionPerformed

    private void btnSelecionaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaCategoriaActionPerformed

        CategoriaFrame categorias = new CategoriaFrame(this);
        this.getDesktopPane().add(categorias);
        categorias.setVisible(true);
        categorias.toFront();
    }//GEN-LAST:event_btnSelecionaCategoriaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDetalles;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnListado;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaArticuloVenta;
    private javax.swing.JButton btnSelecionaCategoria;
    private javax.swing.JButton btnSelecionaUnidad;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCategoria;
    private javax.swing.JComboBox cbIva;
    private javax.swing.JComboBox cbUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel jlbCodigo;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbDescripcion;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblArticulos;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JFormattedTextField txtValorVenta;
    private javax.swing.JFormattedTextField txtValor_CompraConImp;
    // End of variables declaration//GEN-END:variables

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        actualizaCbCategoria();
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
