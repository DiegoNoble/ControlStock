package com.Articulos;

import com.Beans.Articulo;
import com.Beans.ArticuloId;
import com.DAO.DAOGenerico;
import com.Beans.Categoria;
import com.Beans.EquivalenciaUnidades;
import com.Beans.Unidad;
import com.CategoriaArticulos.CategoriasDialog;
import com.Compras.RegistraCompra;
import com.DAO.ArticuloDAO;
import com.DAO.EquivalenciaUnidadesDAO;
import com.Pedidos.RegistraPedido;
import com.usuarios.frameLogin;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ArticulosFrame extends javax.swing.JInternalFrame {

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
        dpVencimiento.setEnabled(false);
        //btnExcluir.setVisible(false);

        if (frameLogin.getInstance().getPerfil().equals("Operador")) {

            btnSalvar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
        }
        actualizaCbCategoria();
        actualizaCbUnidad();

        accionTaggetButton();

    }

    public ArticulosFrame(RegistraCompra registraCompra) {
        initComponents();
        dpVencimiento.setEnabled(false);
        this.registraCompra = registraCompra;
        btnSeleccionaArticuloVenta.setVisible(true);
        defineModelo();
        filtros();

        if (frameLogin.getInstance().getPerfil().equals("Operador")) {

            btnSalvar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
        }

        actualizaCbCategoria();

        actualizaCbUnidad();
        accionTaggetButton();
    }

    public ArticulosFrame(RegistraPedido registraPedido) {
        initComponents();
        dpVencimiento.setEnabled(false);
        this.registraPedido = registraPedido;
        btnSeleccionaArticuloVenta.setVisible(true);
        defineModelo();
        filtros();

        if (frameLogin.getInstance().getPerfil().equals("Operador")) {

            btnSalvar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
        }
        actualizaCbCategoria();
        actualizaCbUnidad();
        accionTaggetButton();
    }

    void accionTaggetButton() {
        jXCollapsiblePane1.setAnimated(true);
        //jXCollapsiblePane1.setCollapsed(true);
        Action toggleAction = jXCollapsiblePane1.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);
        toggleAction.putValue(JXCollapsiblePane.EXPAND_ICON, UIManager.getIcon("Tree.collapsedIcon"));
        toggleAction.putValue(JXCollapsiblePane.COLLAPSE_ICON, UIManager.getIcon("Tree.expandedIcon"));

        jToggleButton1.setAction(toggleAction);
    }

    public final void actualizaCbCategoria() {

        try {
            AutoCompleteDecorator.decorate(cbCategoria);
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
            AutoCompleteDecorator.decorate(cbUnidad);
            List<Unidad> listaUnidad = new ArrayList();
            DAOGenerico DAO = new DAOGenerico();
            listaUnidad = DAO.BuscaTodos(Unidad.class);

            cbUnidad.removeAllItems();

            for (Unidad uni : listaUnidad) {

                cbUnidad.addItem(uni);
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

    private void eliminaEquivalencias() throws Exception {

        List<EquivalenciaUnidades> listEqui = articuloSeleccionado.getListEquivalencias();

        for (EquivalenciaUnidades equivalenciaUnidades : listEqui) {
            DAOGenerico dao = new DAOGenerico(equivalenciaUnidades);
            dao.elimina();

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

        int[] anchos = {5, 20, 300, 20, 20, 20, 20};

        for (int i = 0; i < tblArticulos.getColumnCount(); i++) {

            tblArticulos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        listArticulos.addAll(articuloDAO.BuscaTodos(Articulo.class));
        tableModelArticulos.fireTableDataChanged();

        listModelArticulos = tblArticulos.getSelectionModel();
        listModelArticulos.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tblArticulos.getSelectedRow() != -1) {
                        articuloSeleccionado = listArticulos.get(tblArticulos.getSelectedRow());
                        btnSeleccionaArticuloVenta.setEnabled(true);
                        btnAlterar.setEnabled(true);
                        btnInActivar.setEnabled(true);
                        btnExcluir.setEnabled(true);
                        detallesArticulos();

                    } else {
                        articuloSeleccionado = null;
                        btnAlterar.setEnabled(false);
                        btnExcluir.setEnabled(false);
                        btnInActivar.setEnabled(false);

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

                ArticuloId articuloId = new ArticuloId();
                articuloId.setId(txtCodigo.getText());
                articuloId.setLote(txtLote.getText());
                articulo.setArticuloId(articuloId);

                articulo.setNombre(txtNombre.getText());
                articulo.setCantidad(Double.parseDouble(txtCantMenorUnidad.getText()));
                articulo.setVencimiento(dpVencimiento.getDate());

                if (chActivo.isSelected()) {
                    articulo.setActivo("Activo");
                } else {
                    articulo.setActivo("Inactivo");
                }
                //articulo.setStock_mayor_unidad(Double.parseDouble(txtCantMayorUnidad.getText()));

                articulo.setDescripcion(txtDescripcion.getText());
                articulo.setValor_venta(Double.parseDouble(txtValorVenta.getText()));

                articulo.setCategoria((Categoria) cbCategoria.getSelectedItem());

                articulo.setUnidad((Unidad) cbUnidad.getSelectedItem());

                articulo.setIva(Double.parseDouble(String.valueOf(cbIva.getSelectedItem())));
                articulo.setValor_compra(Double.parseDouble(txtValor_CompraConImp.getText()));

                articuloDAO = new ArticuloDAO(articulo);
                articuloDAO.guardar();

                EquivalenciaUnidades equivalenciaUnidades = new EquivalenciaUnidades();
                equivalenciaUnidades.setArticulo(articulo);
                equivalenciaUnidades.setFactor_conversion(1.0);
                equivalenciaUnidades.setUnidad(articulo.getUnidad());
                EquivalenciaUnidadesDAO unidadesDAO = new EquivalenciaUnidadesDAO(equivalenciaUnidades);
                unidadesDAO.guardar();

                JOptionPane.showMessageDialog(null, "Articulo registrado correctamente!");

                filtros();
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

            ArticuloId articuloId = new ArticuloId();
            articuloId.setId(txtCodigo.getText());
            articuloId.setLote(txtLote.getText());
            articulo.setArticuloId(articuloId);

            articulo.setNombre(txtNombre.getText());
            articulo.setCantidad(Double.parseDouble(txtCantMenorUnidad.getText()));
            if (chActivo.isSelected()) {
                articulo.setActivo("Activo");
            } else {
                articulo.setActivo("Inactivo");
            }

            articulo.setVencimiento(dpVencimiento.getDate());
            //articulo.setStock_mayor_unidad(Double.parseDouble(txtCantMayorUnidad.getText()));

            articulo.setDescripcion(txtDescripcion.getText());
            articulo.setValor_venta(Double.parseDouble(txtValorVenta.getText()));

            articulo.setCategoria((Categoria) cbCategoria.getSelectedItem());

            articulo.setUnidad((Unidad) cbUnidad.getSelectedItem());

            articulo.setIva(Double.parseDouble(String.valueOf(cbIva.getSelectedItem())));
            articulo.setValor_compra(Double.parseDouble(txtValor_CompraConImp.getText()));

            articuloDAO = new ArticuloDAO(articulo);
            articuloDAO.actualiza();

            JOptionPane.showMessageDialog(null, "Articulo editado correctamente!");
            filtros();
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
            String situacion;
            if (chActivos.isSelected()) {
                situacion = ("Inactivo");
            } else {
                situacion = ("Activo");
            }
            if (rbCodigo.isSelected()) {
                listArticulos.clear();
                listArticulos.addAll(articuloDAO.BuscaPorCodigo(txtFiltro.getText(), situacion));
                tableModelArticulos.fireTableDataChanged();
            } else if (rbNombre.isSelected()) {
                listArticulos.clear();
                listArticulos.addAll(articuloDAO.buscarPor(Articulo.class, "nombre", txtFiltro.getText()));
                tableModelArticulos.fireTableDataChanged();

            } else if (rbDescripcion.isSelected()
                    == true) {
                listArticulos.clear();
                listArticulos.addAll(articuloDAO.buscarPor(Articulo.class, "descripcion", txtFiltro.getText()));
                tableModelArticulos.fireTableDataChanged();
            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error);
            error.printStackTrace();
        }
    }

    private void eliminaProducto() throws Exception {

        Articulo articulo = new Articulo();
        ArticuloId articuloId = new ArticuloId();
        articuloId.setId(txtCodigo.getText());
        articuloId.setLote(txtLote.getText());
        articulo.setArticuloId(articuloId);

        articulo.setArticuloId(articuloId);;

        DAOGenerico dao = new DAOGenerico(articulo);
        dao.elimina();

        JOptionPane.showMessageDialog(null, "Articulo eliminado correctamente!");

    }

    public void detallesArticulos() {

        limpiaCampos();

        if (articuloSeleccionado != null) {
            try {
                txtCantMenorUnidad.setText(articuloSeleccionado.getCantidad().toString());

                txtCodigo.setText(articuloSeleccionado.getArticuloId().getId());
                txtLote.setText(articuloSeleccionado.getArticuloId().getLote());

                dpVencimiento.setDate(articuloSeleccionado.getVencimiento());
                txtDescripcion.setText(articuloSeleccionado.getDescripcion());
                txtNombre.setText(articuloSeleccionado.getNombre());
                txtValorVenta.setText(articuloSeleccionado.getValor_venta().toString());
                txtValor_CompraConImp.setText(articuloSeleccionado.getValor_compra().toString());
                if (articuloSeleccionado.getActivo().equals("Activo")) {
                    chActivo.setSelected(true);
                } else {
                    chActivo.setSelected(false);
                }

                cbUnidad.setSelectedItem(articuloSeleccionado.getUnidad());

                cbCategoria.setSelectedItem(articuloSeleccionado.getCategoria());

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Error al mostrar detalles", "Error", JOptionPane.ERROR_MESSAGE);
                error.printStackTrace();
            }
        }
    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        txtValor_CompraConImp.setEditable(true);
        txtValorVenta.setEditable(true);
        txtCantMenorUnidad.setEditable(false);
        dpVencimiento.setEnabled(true);

        //txtCantMayorUnidad.setEditable(false);
        txtDescripcion.setEnabled(true);
        cbCategoria.setEnabled(true);
        cbUnidad.setEnabled(true);
        cbIva.setEditable(true);
        tblArticulos.setEnabled(false);
        //tblArticulos.setVisible(false);
        txtDescripcion.setEditable(true);
        txtDescripcion.setEnabled(true);
        btnSelecionaCategoria.setEnabled(true);
        btnEquivalencia.setEnabled(true);
        chActivo.setEnabled(true);

        if (perfil.equals("Gerente")) {
            txtCantMenorUnidad.setEnabled(true);
            txtCantMenorUnidad.setEditable(true);
        }

    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        txtValor_CompraConImp.setEditable(false);
        txtValorVenta.setEditable(false);
        dpVencimiento.setEnabled(false);
        txtLote.setEnabled(false);
        txtCantMenorUnidad.setEditable(false);
        //txtCantMayorUnidad.setEditable(false);
        txtCodigo.setEditable(false);
        tblArticulos.setEnabled(true);
        tblArticulos.setVisible(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setEnabled(true);
        cbCategoria.setEnabled(false);
        cbUnidad.setEnabled(false);
        cbIva.setEditable(false);
        btnSelecionaCategoria.setEnabled(false);
        btnEquivalencia.setEnabled(false);
        chActivo.setEnabled(false);

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
        txtLote.setText("");
        dpVencimiento.setDate(new Date());
        txtValor_CompraConImp.setText("");
        txtValorVenta.setText("");
        txtCantMenorUnidad.setText("");
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
        jXCollapsiblePane1 = new org.jdesktop.swingx.JXCollapsiblePane();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jlbCodigo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cbIva = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        txtValor_CompraConImp = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtValorVenta = new javax.swing.JFormattedTextField();
        cbCategoria = new javax.swing.JComboBox();
        btnSelecionaCategoria = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        cbUnidad = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCantMenorUnidad = new javax.swing.JFormattedTextField();
        btnEquivalencia = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        chActivo = new javax.swing.JCheckBox();
        jlbCodigo1 = new javax.swing.JLabel();
        txtLote = new javax.swing.JTextField();
        jlbCodigo2 = new javax.swing.JLabel();
        dpVencimiento = new org.jdesktop.swingx.JXDatePicker();
        jPanel4 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSeleccionaArticuloVenta = new javax.swing.JButton();
        btnInActivar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulos = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        rbCodigo = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbDescripcion = new javax.swing.JRadioButton();
        txtFiltro = new javax.swing.JTextField();
        chActivos = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(900, 700));
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
        jlbCodigo.setText("Lote");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jlbCodigo, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel7, gridBagConstraints);

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

        jTabbedPane3.addTab("Valores", jPanel7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jTabbedPane3, gridBagConstraints);

        cbCategoria.setEnabled(false);
        cbCategoria.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(cbCategoria, gridBagConstraints);

        btnSelecionaCategoria.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSelecionaCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/search.png"))); // NOI18N
        btnSelecionaCategoria.setBorderPainted(false);
        btnSelecionaCategoria.setEnabled(false);
        btnSelecionaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaCategoriaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(btnSelecionaCategoria, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel16.setText("Categoría");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel16, gridBagConstraints);

        jPanel12.setLayout(new java.awt.GridBagLayout());

        cbUnidad.setEnabled(false);
        cbUnidad.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(cbUnidad, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel20.setText("Menor Unidad ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel12.add(jLabel20, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel12.setText("Stock");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel12.add(jLabel12, gridBagConstraints);

        txtCantMenorUnidad.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(txtCantMenorUnidad, gridBagConstraints);

        btnEquivalencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/equal.png"))); // NOI18N
        btnEquivalencia.setBorderPainted(false);
        btnEquivalencia.setEnabled(false);
        btnEquivalencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEquivalenciaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel12.add(btnEquivalencia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jPanel12, gridBagConstraints);

        txtDescripcion.setEditable(false);
        txtDescripcion.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
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

        chActivo.setText("Activo");
        chActivo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(chActivo, gridBagConstraints);

        jlbCodigo1.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jlbCodigo1.setText("Vencimiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jlbCodigo1, gridBagConstraints);

        txtLote.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtLote, gridBagConstraints);

        jlbCodigo2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jlbCodigo2.setText("Código del Articulo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jlbCodigo2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(dpVencimiento, gridBagConstraints);

        jXCollapsiblePane1.getContentPane().add(jPanel8);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jXCollapsiblePane1, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        btnNovo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnNovo.setMnemonic('N');
        btnNovo.setText("Nuevo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnNovo, gridBagConstraints);

        btnAlterar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnAlterar.setMnemonic('E');
        btnAlterar.setText("Editar");
        btnAlterar.setEnabled(false);
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnAlterar, gridBagConstraints);

        btnExcluir.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnExcluir, gridBagConstraints);

        btnSalvar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSalvar.setMnemonic('S');
        btnSalvar.setText("Salvar");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnSalvar, gridBagConstraints);

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnCancelar, gridBagConstraints);

        btnSeleccionaArticuloVenta.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSeleccionaArticuloVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/images.jpg"))); // NOI18N
        btnSeleccionaArticuloVenta.setMnemonic('A');
        btnSeleccionaArticuloVenta.setText("Selecciona Articulo");
        btnSeleccionaArticuloVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaArticuloVentaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnSeleccionaArticuloVenta, gridBagConstraints);

        btnInActivar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnInActivar.setText("Inactivar/Activar");
        btnInActivar.setEnabled(false);
        btnInActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInActivarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnInActivar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

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
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtFiltro, gridBagConstraints);

        chActivos.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        chActivos.setText("Ver inactivos");
        chActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chActivosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel3.add(chActivos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jToggleButton1.setSelected(true);
        jToggleButton1.setText("Detalles");
        jPanel2.add(jToggleButton1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        Double compra = Double.parseDouble(txtValor_CompraConImp.getText());
        Double venta1 = Double.parseDouble(txtValorVenta.getText());
        if (compra > venta1) {
            JOptionPane.showMessageDialog(null, "Atención, el valor de venta no debe "
                    + "ser inferiror al valor de compra con Imp. Incl.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (articuloSeleccionado != null) {
                EditarProducto();
            } else {
                NuevoProducto();
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaBotoes();
        desabilitaCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        if (articuloSeleccionado != null) {
            habilitaBotoes();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        try {
            if (tblArticulos.getSelectedRow() != -1) {
                String Nombre = txtNombre.getText();
                int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación del Articulo " + Nombre + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    eliminaEquivalencias();
                    eliminaProducto();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No es posible eliminar el articulo seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
        }

        txtCodigo.setText("");
        filtros();
        limpiaCampos();

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        //  jPanel2.setVisible(true);
        txtCodigo.setEnabled(true);
        txtCodigo.setEditable(true);
        txtLote.setEnabled(true);
        txtLote.setEditable(true);
        txtCodigo.requestFocus();
        chActivo.setSelected(true);
        limpiaCampos();
        txtCantMenorUnidad.setText("0");
        habilitaCampos();
        habilitaBotoes();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        filtros();
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnSeleccionaArticuloVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaArticuloVentaActionPerformed

        seleccionaArticulo();
    }//GEN-LAST:event_btnSeleccionaArticuloVentaActionPerformed

    private void btnSelecionaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaCategoriaActionPerformed

        CategoriasDialog categorias = new CategoriasDialog(null, true, this);
        categorias.setVisible(true);
        categorias.toFront();
        actualizaCbCategoria();
    }//GEN-LAST:event_btnSelecionaCategoriaActionPerformed

    private void btnEquivalenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEquivalenciaActionPerformed

        /*EquivalenciasFrame equivalenciasFrame = new EquivalenciasFrame(articuloSeleccionado);
         this.getDesktopPane().add(equivalenciasFrame);
         equivalenciasFrame.setVisible(true);
         equivalenciasFrame.toFront();
         */
        EquivalenciasDialog equivalenciasDialog = new EquivalenciasDialog(null, true, this, articuloSeleccionado);
        equivalenciasDialog.setVisible(true);
        equivalenciasDialog.toFront();
    }//GEN-LAST:event_btnEquivalenciaActionPerformed

    private void chActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chActivosActionPerformed
        filtros();
    }//GEN-LAST:event_chActivosActionPerformed

    private void btnInActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInActivarActionPerformed

        if (articuloSeleccionado.getActivo().equals("Activo")) {
            articuloSeleccionado.setActivo("Inactivo");
            DAOGenerico DAO = new DAOGenerico(articuloSeleccionado);
            DAO.actualiza();
            filtros();
            JOptionPane.showMessageDialog(this, "Inactivado correctamente", "Atención", JOptionPane.INFORMATION_MESSAGE);
        } else if (articuloSeleccionado.getActivo().equals("Inactivo")) {
            articuloSeleccionado.setActivo("Activo");
            DAOGenerico DAO = new DAOGenerico(articuloSeleccionado);
            DAO.actualiza();
            filtros();
            JOptionPane.showMessageDialog(this, "Activado correctamente", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnInActivarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEquivalencia;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnInActivar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaArticuloVenta;
    private javax.swing.JButton btnSelecionaCategoria;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCategoria;
    private javax.swing.JComboBox cbIva;
    private javax.swing.JComboBox cbUnidad;
    private javax.swing.JCheckBox chActivo;
    private javax.swing.JCheckBox chActivos;
    private org.jdesktop.swingx.JXDatePicker dpVencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JToggleButton jToggleButton1;
    private org.jdesktop.swingx.JXCollapsiblePane jXCollapsiblePane1;
    private javax.swing.JLabel jlbCodigo;
    private javax.swing.JLabel jlbCodigo1;
    private javax.swing.JLabel jlbCodigo2;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbDescripcion;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblArticulos;
    private javax.swing.JFormattedTextField txtCantMenorUnidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtLote;
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
