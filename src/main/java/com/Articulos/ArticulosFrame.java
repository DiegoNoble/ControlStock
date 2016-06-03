package com.Articulos;

import com.Beans.Articulo;
import com.DAO.DAOGenerico;
import Utilidades.HibernateUtil;
import Utilidades.Utilidades;
import com.Beans.Categoria;
import com.CategoriaArticulos.CategoriaFrame;
import com.Beans.ArticulosCompra;
import com.Beans.Unidad;
import com.Unidades.UnidadFrame;
import com.Beans.ArticulosVenta;
import com.DAO.ArticuloDAO;
import com.usuarios.frameLogin;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.hibernate.Session;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ArticulosFrame extends javax.swing.JInternalFrame {

    int edicion = 0;
    String Filtro = "from Articulo";
    private DefaultTableModel tableModelArticulos;
    private ListSelectionModel listModelArticulos;
    private DefaultTableModel tableModelCompras;
    private DefaultTableModel tableModelVentas;
    private Categoria categoria;
    private Unidad unidad;
    int panelVisible = 0;
    String codigoViejo = null;

    public ArticulosFrame() {
        initComponents();

        defineModelo();
        actualizaTable(Filtro);
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

        Date hoy = new Date();
        dpFechaInicial.setFormats("dd/MM/yyyy");
        dpFechaFinal.setFormats("dd/MM/yyyy");
        dpFechaInicial.setDate(Utilidades.fechaPantalla(hoy));
        dpFechaFinal.setDate(Utilidades.fechaPantalla(hoy));

        dpFechaInicial1.setFormats("dd/MM/yyyy");
        dpFechaFinal1.setFormats("dd/MM/yyyy");
        dpFechaInicial1.setDate(Utilidades.fechaPantalla(hoy));
        dpFechaFinal1.setDate(Utilidades.fechaPantalla(hoy));


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

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void defineModelo() {

        tableModelArticulos = (DefaultTableModel) tblArticulos.getModel();
        tableModelCompras = (DefaultTableModel) tblCompras.getModel();
        tableModelVentas = (DefaultTableModel) tblVentas.getModel();

        listModelArticulos = tblArticulos.getSelectionModel();
        listModelArticulos.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    detallesArticulos();

                }
            }
        });

        TableCellRenderer centerRenderer = new CenterRenderer();
        ((DefaultTableCellRenderer) tblArticulos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) tblCompras.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) tblVentas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        TableColumn column0 = tblArticulos.getColumnModel().getColumn(0);
        TableColumn column = tblArticulos.getColumnModel().getColumn(1); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column1 = tblArticulos.getColumnModel().getColumn(2);
        TableColumn column2 = tblArticulos.getColumnModel().getColumn(3);
        TableColumn column3 = tblArticulos.getColumnModel().getColumn(4);
        TableColumn column7 = tblArticulos.getColumnModel().getColumn(7);

        TableColumn column8 = tblCompras.getColumnModel().getColumn(0); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column9 = tblCompras.getColumnModel().getColumn(1);
        TableColumn column10 = tblCompras.getColumnModel().getColumn(2);
        TableColumn column11 = tblCompras.getColumnModel().getColumn(3);
        TableColumn column12 = tblCompras.getColumnModel().getColumn(4);

        TableColumn column13 = tblVentas.getColumnModel().getColumn(0); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column14 = tblVentas.getColumnModel().getColumn(1);
        TableColumn column15 = tblVentas.getColumnModel().getColumn(2);
        TableColumn column16 = tblVentas.getColumnModel().getColumn(3);
        TableColumn column17 = tblVentas.getColumnModel().getColumn(4);

        column0.setCellRenderer(centerRenderer);
        column.setCellRenderer(centerRenderer);
        column1.setCellRenderer(centerRenderer);
        column2.setCellRenderer(centerRenderer);
        column3.setCellRenderer(centerRenderer);
        column7.setCellRenderer(centerRenderer);

        column8.setCellRenderer(centerRenderer);
        column9.setCellRenderer(centerRenderer);
        column10.setCellRenderer(centerRenderer);
        column11.setCellRenderer(centerRenderer);
        column12.setCellRenderer(centerRenderer);

        column13.setCellRenderer(centerRenderer);
        column14.setCellRenderer(centerRenderer);
        column15.setCellRenderer(centerRenderer);
        column16.setCellRenderer(centerRenderer);
        column17.setCellRenderer(centerRenderer);


        tblArticulos.getColumn("Nro.").setPreferredWidth(1);
        tblArticulos.getColumn("Código").setPreferredWidth(100); //------> Ajusta el tamaño de las columnas
        tblArticulos.getColumn("Nombre").setPreferredWidth(250);
        tblArticulos.getColumn("Cantidad en Stock").setPreferredWidth(80);
        tblArticulos.getColumn("Precio con Descuento").setPreferredWidth(80);
        tblArticulos.getColumn("Precio Normal").setPreferredWidth(80);

        tblCompras.getColumn("Factura").setPreferredWidth(10); //------> Ajusta el tamaño de las columnas
        tblCompras.getColumn("Fecha").setPreferredWidth(20);
        tblCompras.getColumn("Precio Compra IVA Incl.").setPreferredWidth(20);
        tblCompras.getColumn("Unidades").setPreferredWidth(10);
        tblCompras.getColumn("Proveedor").setPreferredWidth(30);

        tblVentas.getColumn("Factura").setPreferredWidth(10); //------> Ajusta el tamaño de las columnas
        tblVentas.getColumn("Fecha").setPreferredWidth(20);
        tblVentas.getColumn("Precio Venta IVA Incl.").setPreferredWidth(20);
        tblVentas.getColumn("Unidades").setPreferredWidth(10);
        tblVentas.getColumn("Cliente").setPreferredWidth(30);


        txtValor_CompraSinImp.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (txtValor_CompraSinImp.getText().equals("")) {

                    Double valorSinImpuesto = 0.0;
                    Double precioConImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioConImp = valorSinImpuesto * impuesto;

                    txtValor_CompraConImp.setText(String.valueOf(Utilidades.Redondear(precioConImp, 2)));

                } else {

                    Double valorSinImpuesto = Double.parseDouble(txtValor_CompraSinImp.getText());
                    Double precioConImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioConImp = valorSinImpuesto * impuesto;

                    txtValor_CompraConImp.setText(String.valueOf(Utilidades.Redondear(precioConImp, 2)));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (txtValor_CompraSinImp.getText().equals("")) {

                    Double valorSinImpuesto = 0.0;
                    Double precioConImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioConImp = valorSinImpuesto * impuesto;

                    txtValor_CompraConImp.setText(String.valueOf(Utilidades.Redondear(precioConImp, 2)));

                } else {

                    Double valorSinImpuesto = Double.parseDouble(txtValor_CompraSinImp.getText());
                    Double precioConImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioConImp = valorSinImpuesto * impuesto;

                    txtValor_CompraConImp.setText(String.valueOf(Utilidades.Redondear(precioConImp, 2)));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (txtValor_CompraSinImp.getText().equals("")) {

                    Double valorSinImpuesto = 0.0;
                    Double precioConImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioConImp = valorSinImpuesto * impuesto;

                    txtValor_CompraConImp.setText(String.valueOf(Utilidades.Redondear(precioConImp, 2)));

                } else {

                    Double valorSinImpuesto = Double.parseDouble(txtValor_CompraSinImp.getText());
                    Double precioConImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioConImp = valorSinImpuesto * impuesto;

                    txtValor_CompraConImp.setText(String.valueOf(Utilidades.Redondear(precioConImp, 2)));
                }
            }
        });

        txtValor_CompraConImp.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtValor_CompraConImp.getText().equals("")) {

                    Double valorConImpuesto = 0.0;
                    Double precioSinImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioSinImp = (valorConImpuesto / impuesto);

                    txtValor_CompraSinImp.setText(String.valueOf(Utilidades.Redondear(precioSinImp, 2)));

                } else {

                    Double valorConImpuesto = Double.parseDouble(txtValor_CompraConImp.getText());
                    Double precioSinImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioSinImp = (valorConImpuesto / impuesto);

                    txtValor_CompraSinImp.setText(String.valueOf(Utilidades.Redondear(precioSinImp, 2)));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (txtValor_CompraConImp.getText().equals("")) {

                    Double valorConImpuesto = 0.0;
                    Double precioSinImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioSinImp = (valorConImpuesto / impuesto);

                    txtValor_CompraSinImp.setText(String.valueOf(Utilidades.Redondear(precioSinImp, 2)));

                } else {

                    Double valorConImpuesto = Double.parseDouble(txtValor_CompraConImp.getText());
                    Double precioSinImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioSinImp = (valorConImpuesto / impuesto);

                    txtValor_CompraSinImp.setText(String.valueOf(Utilidades.Redondear(precioSinImp, 2)));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (txtValor_CompraConImp.getText().equals("")) {

                    Double valorConImpuesto = 0.0;
                    Double precioSinImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioSinImp = (valorConImpuesto / impuesto);

                    txtValor_CompraSinImp.setText(String.valueOf(Utilidades.Redondear(precioSinImp, 2)));

                } else {

                    Double valorConImpuesto = Double.parseDouble(txtValor_CompraConImp.getText());
                    Double precioSinImp = null;
                    Double impuesto = Double.parseDouble(String.valueOf("1." + cbIva.getSelectedItem()));
                    precioSinImp = (valorConImpuesto / impuesto);

                    txtValor_CompraSinImp.setText(String.valueOf(Utilidades.Redondear(precioSinImp, 2)));
                }
            }
        });

        txtValorVenta.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (txtValorVenta.getText().equals("")) {

                    Double ganancia = (100) * 0.0 / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGanancia.setText(gananciaFinal);

                } else {

                    Double ganancia = (100) * Double.parseDouble(txtValorVenta.getText()) / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGanancia.setText(gananciaFinal);
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (txtValorVenta.getText().equals("")) {

                    Double ganancia = (100) * 0.0 / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGanancia.setText(gananciaFinal);

                } else {

                    Double ganancia = (100) * Double.parseDouble(txtValorVenta.getText()) / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGanancia.setText(gananciaFinal);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                if (txtValorVenta.getText().equals("")) {

                    Double ganancia = (100) * 0.0 / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGanancia.setText(gananciaFinal);

                } else {

                    Double ganancia = (100) * Double.parseDouble(txtValorVenta.getText()) / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGanancia.setText(gananciaFinal);
                }

            }
        });

        txtDescuento.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (txtDescuento.getText().equals("")) {

                    Double ganancia = (100) * 0.0 / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGananciaDescuento.setText(gananciaFinal);

                } else {
                    Double ganancia = (100) * Double.parseDouble(txtDescuento.getText()) / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGananciaDescuento.setText(gananciaFinal);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (txtDescuento.getText().equals("")) {

                    Double ganancia = (100) * 0.0 / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGananciaDescuento.setText(gananciaFinal);

                } else {
                    Double ganancia = (100) * Double.parseDouble(txtDescuento.getText()) / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGananciaDescuento.setText(gananciaFinal);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (txtDescuento.getText().equals("")) {

                    Double ganancia = (100) * 0.0 / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGananciaDescuento.setText(gananciaFinal);

                } else {
                    Double ganancia = (100) * Double.parseDouble(txtDescuento.getText()) / Double.parseDouble(txtValor_CompraConImp.getText()) - 100;
                    String gananciaFinal = String.format("%.2f", ganancia).replace(",", ".");
                    txtGananciaDescuento.setText(gananciaFinal);
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
                dao.registra();

                JOptionPane.showMessageDialog(null, "Articulo registrado correctamente!");

                actualizaTable(Filtro);
                //muestraContenidoTabla();
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
            actualizaTable(Filtro);
            //muestraContenidoTabla();
            desabilitaBotoes();
            desabilitaCampos();

        }

    }

    private void actualizaTable(String filtro) {

        try {

            tableModelArticulos.setNumRows(0);

            Session seccion = HibernateUtil.getSeccion();

            List<Articulo> lista_articulos = new ArrayList();
            lista_articulos = seccion.createQuery(Filtro).list();

            int tamano_lista = lista_articulos.size();

            for (int i = 0; i < tamano_lista; i++) {

                Articulo articulos = lista_articulos.get(i);

                Object[] linea = new Object[14];
                linea[0] = i + 1;
                linea[1] = articulos.getId();
                linea[2] = articulos.getNombre();
                linea[3] = articulos.getCantidad();
                linea[4] = articulos.getDescuento();
                linea[5] = articulos.getGanancia();
                linea[6] = articulos.getValor_compra();
                linea[7] = articulos.getValor_venta();
                linea[8] = articulos.getDescripcion();
                linea[9] = articulos.getGanancia_descuento();
                linea[10] = articulos.getCategoria().getNombre();
                linea[11] = articulos.getIva();
                linea[12] = articulos.getValor_compra_impuesto();
                linea[13] = articulos.getUnidad();

                tableModelArticulos.addRow(linea);
            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error);
        }
    }

    private void consultaCompras() {

        String articuloSeleccionado = tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 1).toString();

        if (tblArticulos.getSelectedRow() != -1) {

            List<ArticulosCompra> listaCompras = new ArrayList<>();

            String fechaInicial = Utilidades.fechaBD(dpFechaInicial.getDate());
            String fechaFinal = Utilidades.fechaBD(dpFechaFinal.getDate());
            DAOGenerico dao = new DAOGenerico();
            listaCompras = dao.buscaCompras(articuloSeleccionado, fechaInicial, fechaFinal);

            remueveCompras();
            for (int i = 0; i < listaCompras.size(); i++) {

                Date fecha = listaCompras.get(i).getFacturaCompra().getFecha();

                tableModelCompras.insertRow(i, new Object[]{listaCompras.get(i).getFacturaCompra().getIdfactura(),
                            Utilidades.fechaPantallaString(fecha), listaCompras.get(i).getValorSinIva(),
                            listaCompras.get(i).getCantidad(), listaCompras.get(i).getFacturaCompra().getProveedor().getNombre()});


            }
            try {
                Integer total = 0;
                for (int i = 0; i < tblCompras.getRowCount(); i++) {
                    String unidad = tblCompras.getValueAt(i, 3).toString();
                    Integer valor = Integer.parseInt(unidad);
                    total += valor;
                }
                txtTotalCompras.setText(String.valueOf(total));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            remueveCompras();
        }

    }

    private void consultaVentas() {

        String articuloSeleccionado = tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 1).toString();

        if (tblArticulos.getSelectedRow() != -1) {

            List<ArticulosVenta> listaVentas = new ArrayList<>();

            String fechaInicial = Utilidades.fechaBD(dpFechaInicial1.getDate());
            String fechaFinal = Utilidades.fechaBD(dpFechaFinal1.getDate());
            DAOGenerico dao = new DAOGenerico();
            listaVentas = dao.buscaVentas(articuloSeleccionado, fechaInicial, fechaFinal);

            remueveVentas();
            for (int i = 0; i < listaVentas.size(); i++) {

                Date fecha = listaVentas.get(i).getFactura().getFecha();

                tableModelVentas.insertRow(i, new Object[]{listaVentas.get(i).getFactura().getIdfactura(),
                            Utilidades.fechaPantallaString(fecha), listaVentas.get(i).getValorConIva(),
                            listaVentas.get(i).getCantidad(), listaVentas.get(i).getFactura().getCliente().getNombre()});


            }
            try {
                Integer total = 0;
                for (int i = 0; i < tblVentas.getRowCount(); i++) {
                    String unidad = tblVentas.getValueAt(i, 3).toString();
                    Integer valor = Integer.parseInt(unidad);
                    total += valor;
                }
                txtTotalVentas.setText(String.valueOf(total));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            remueveVentas();
        }

    }

    private void remueveCompras() {
        tableModelCompras = (DefaultTableModel) tblCompras.getModel();
        int numeroLineas = tblCompras.getRowCount();
        for (int i = 0; i < numeroLineas; i++) {
            tableModelCompras.removeRow(0);
        }
    }

    private void remueveVentas() {
        tableModelVentas = (DefaultTableModel) tblVentas.getModel();
        int numeroLineas = tblVentas.getRowCount();
        for (int i = 0; i < numeroLineas; i++) {
            tableModelVentas.removeRow(0);
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

        try {
            int filaSeleccionada = tblArticulos.getSelectedRow();

            txtCodigo.setText(tblArticulos.getValueAt(filaSeleccionada, 1).toString());

            txtNombre.setText(tblArticulos.getValueAt(filaSeleccionada, 2).toString());

            Object objCan = tblArticulos.getValueAt(filaSeleccionada, 3);
            if (objCan == null) {
            } else {
                txtCantidad.setText(tblArticulos.getValueAt(filaSeleccionada, 3).toString());
            }

            Object objDesc = tblArticulos.getValueAt(filaSeleccionada, 4);
            if (objDesc == null) {
            } else {
                txtDescuento.setText(tblArticulos.getValueAt(filaSeleccionada, 4).toString());
            }

            Object objGan = tblArticulos.getValueAt(filaSeleccionada, 5);
            if (objGan == null) {
            } else {
                txtGanancia.setText(tblArticulos.getValueAt(filaSeleccionada, 5).toString());
            }

            Object objVCom = tblArticulos.getValueAt(filaSeleccionada, 6);
            if (objVCom == null) {
            } else {
                txtValor_CompraSinImp.setText(tblArticulos.getValueAt(filaSeleccionada, 6).toString());
            }

            Object objVV = tblArticulos.getValueAt(filaSeleccionada, 7);
            if (objVV == null) {
            } else {
                txtValorVenta.setText(tblArticulos.getValueAt(filaSeleccionada, 7).toString());
            }

            Object objDes = tblArticulos.getValueAt(filaSeleccionada, 8);
            if (objDes == null) {
            } else {
                txtDescripcion.setText(tblArticulos.getValueAt(filaSeleccionada, 8).toString());
            }

            Object objGDes = tblArticulos.getValueAt(filaSeleccionada, 9);
            if (objGDes == null) {
            } else {
                txtGananciaDescuento.setText(tblArticulos.getValueAt(filaSeleccionada, 9).toString());
            }

            Object ObjCat = tblArticulos.getValueAt(filaSeleccionada, 10);
            if (ObjCat == null) {
            } else {
                cbCategoria.setSelectedItem(tblArticulos.getValueAt(filaSeleccionada, 10).toString());
            }

            Object ObjIva = tblArticulos.getValueAt(filaSeleccionada, 11);
            if (ObjIva == null) {
            } else {
                cbIva.setSelectedItem(tblArticulos.getValueAt(filaSeleccionada, 11).toString());
            }

            Object ObjVCI = tblArticulos.getValueAt(filaSeleccionada, 12);
            if (ObjVCI == null) {
            } else {
                txtValor_CompraConImp.setText(tblArticulos.getValueAt(filaSeleccionada, 12).toString());
            }

            Object ObjUni = tblArticulos.getValueAt(filaSeleccionada, 13);
            if (ObjUni == null) {
            } else {
                cbUnidad.setSelectedItem(tblArticulos.getValueAt(filaSeleccionada, 13).toString());
            }

        } catch (Exception error) {
        }

    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        txtValor_CompraSinImp.setEditable(true);
        txtValor_CompraConImp.setEditable(true);
        //txtGanancia.setEditable(true);
        txtValorVenta.setEditable(true);
        txtDescuento.setEditable(true);
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
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
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
        jLabel16 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtValor_CompraSinImp = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        cbIva = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        txtValor_CompraConImp = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        cbCategoria = new javax.swing.JComboBox();
        btnSelecionaCategoria = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        cbUnidad = new javax.swing.JComboBox();
        btnSelecionaUnidad = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCompras = new javax.swing.JTable();
        dpFechaInicial = new org.jdesktop.swingx.JXDatePicker();
        dpFechaFinal = new org.jdesktop.swingx.JXDatePicker();
        txtTotalCompras = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btnBuscarVentas = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        dpFechaInicial1 = new org.jdesktop.swingx.JXDatePicker();
        dpFechaFinal1 = new org.jdesktop.swingx.JXDatePicker();
        txtTotalVentas = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
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

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel11.setText("Stock Disponible");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel8.add(jLabel11, gridBagConstraints);

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

        txtCantidad.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtCantidad, gridBagConstraints);

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
        jPanel8.add(txtCodigo, gridBagConstraints);

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
        jPanel8.add(jTabbedPane1, gridBagConstraints);

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

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel17.setText("Valor Compra (Sin Imp.)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel17, gridBagConstraints);

        txtValor_CompraSinImp.setEditable(false);
        txtValor_CompraSinImp.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtValor_CompraSinImp, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel13.setText("I.V.A. %");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel7.add(jLabel13, gridBagConstraints);

        cbIva.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cbIva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22" }));
        cbIva.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbIva, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel18.setText("Valor Compra (Imp. Incl.)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel18, gridBagConstraints);

        txtValor_CompraConImp.setEditable(false);
        txtValor_CompraConImp.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtValor_CompraConImp, gridBagConstraints);

        jTabbedPane3.addTab("Datos compra", jPanel7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
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

        jTabbedPane4.addTab("Información Principal", jPanel8);

        jPanel9.setLayout(new java.awt.GridBagLayout());

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel10.setText("Total de compras en el período");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(jLabel10, gridBagConstraints);

        btnBuscar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnBuscar.setText("Consultar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel9.add(btnBuscar, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Fecha Inicial");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(jLabel2, gridBagConstraints);

        tblCompras.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Fecha", "Precio Compra IVA Incl.", "Unidades", "Proveedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblCompras);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel9.add(jScrollPane4, gridBagConstraints);

        dpFechaInicial.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(dpFechaInicial, gridBagConstraints);

        dpFechaFinal.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(dpFechaFinal, gridBagConstraints);

        txtTotalCompras.setEditable(false);
        txtTotalCompras.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(txtTotalCompras, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel14.setText("Fecha Final");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(jLabel14, gridBagConstraints);

        jTabbedPane4.addTab("Compras del Articulo", jPanel9);

        jPanel10.setLayout(new java.awt.GridBagLayout());

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel15.setText("Total de ventas en el período");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(jLabel15, gridBagConstraints);

        btnBuscarVentas.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnBuscarVentas.setText("Consultar");
        btnBuscarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarVentasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel10.add(btnBuscarVentas, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Fecha Inicial");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(jLabel4, gridBagConstraints);

        tblVentas.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Fecha", "Precio Venta IVA Incl.", "Unidades", "Cliente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblVentas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel10.add(jScrollPane5, gridBagConstraints);

        dpFechaInicial1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(dpFechaInicial1, gridBagConstraints);

        dpFechaFinal1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(dpFechaFinal1, gridBagConstraints);

        txtTotalVentas.setEditable(false);
        txtTotalVentas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(txtTotalVentas, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel19.setText("Fecha Final");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(jLabel19, gridBagConstraints);

        jTabbedPane4.addTab("Ventas del Articulo", jPanel10);

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

            },
            new String [] {
                "Nro.", "Código", "Nombre", "Cantidad en Stock", "Precio con Descuento", "Ganancia", "Valor Compra", "Precio Normal", "Descripcion", "Ganancia Descuento", "Categoria", "Iva", "Compra con Impuestos", "Unidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, true, true, true, true, true, true
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
        tblArticulos.getColumnModel().getColumn(5).setMinWidth(0);
        tblArticulos.getColumnModel().getColumn(5).setPreferredWidth(0);
        tblArticulos.getColumnModel().getColumn(5).setMaxWidth(0);
        tblArticulos.getColumnModel().getColumn(6).setMinWidth(0);
        tblArticulos.getColumnModel().getColumn(6).setPreferredWidth(0);
        tblArticulos.getColumnModel().getColumn(6).setMaxWidth(0);
        tblArticulos.getColumnModel().getColumn(8).setMinWidth(0);
        tblArticulos.getColumnModel().getColumn(8).setPreferredWidth(0);
        tblArticulos.getColumnModel().getColumn(8).setMaxWidth(0);
        tblArticulos.getColumnModel().getColumn(9).setMinWidth(0);
        tblArticulos.getColumnModel().getColumn(9).setPreferredWidth(0);
        tblArticulos.getColumnModel().getColumn(9).setMaxWidth(0);
        tblArticulos.getColumnModel().getColumn(10).setMinWidth(0);
        tblArticulos.getColumnModel().getColumn(10).setPreferredWidth(0);
        tblArticulos.getColumnModel().getColumn(10).setMaxWidth(0);
        tblArticulos.getColumnModel().getColumn(11).setMinWidth(0);
        tblArticulos.getColumnModel().getColumn(11).setPreferredWidth(0);
        tblArticulos.getColumnModel().getColumn(11).setMaxWidth(0);
        tblArticulos.getColumnModel().getColumn(12).setMinWidth(0);
        tblArticulos.getColumnModel().getColumn(12).setPreferredWidth(0);
        tblArticulos.getColumnModel().getColumn(12).setMaxWidth(0);
        tblArticulos.getColumnModel().getColumn(13).setMinWidth(0);
        tblArticulos.getColumnModel().getColumn(13).setPreferredWidth(0);
        tblArticulos.getColumnModel().getColumn(13).setMaxWidth(0);

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

        btnDetalles.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnDetalles.setMnemonic('D');
        btnDetalles.setText("Exibir/Ocultar Detalles");
        btnDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });
        jPanel4.add(btnDetalles);

        btnListado.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnListado.setMnemonic('L');
        btnListado.setText("Listado de Articulos");
        btnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListadoActionPerformed(evt);
            }
        });
        jPanel4.add(btnListado);

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
        actualizaTable(Filtro);
        //muestraContenidoTabla();
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

        if (rbCodigo.isSelected() == true) {

            if (txtFiltro.getText().equals("")) {
                Filtro = "from Articulos";
            } else {
                Filtro = "from Articulos where id like '%" + txtFiltro.getText() + "%'";
            }
            actualizaTable(Filtro);
            //muestraContenidoTabla();
        } else if (rbNombre.isSelected() == true) {
            Filtro = "from Articulos where nombre like '%" + txtFiltro.getText() + "%'";
            actualizaTable(Filtro);
            //muestraContenidoTabla();
        } else if (rbDescripcion.isSelected() == true) {
            Filtro = "from Articulos where descripcion like '%" + txtFiltro.getText() + "%'";
            actualizaTable(Filtro);
            //muestraContenidoTabla();
        }
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        consultaCompras();

  }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnBuscarVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarVentasActionPerformed

        consultaVentas();
    }//GEN-LAST:event_btnBuscarVentasActionPerformed

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarVentas;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDetalles;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnListado;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSelecionaCategoria;
    private javax.swing.JButton btnSelecionaUnidad;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCategoria;
    private javax.swing.JComboBox cbIva;
    private javax.swing.JComboBox cbUnidad;
    private org.jdesktop.swingx.JXDatePicker dpFechaFinal;
    private org.jdesktop.swingx.JXDatePicker dpFechaFinal1;
    private org.jdesktop.swingx.JXDatePicker dpFechaInicial;
    private org.jdesktop.swingx.JXDatePicker dpFechaInicial1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
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
    private javax.swing.JTable tblCompras;
    private javax.swing.JTable tblVentas;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JFormattedTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JFormattedTextField txtDescuento;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JFormattedTextField txtGanancia;
    private javax.swing.JTextField txtGananciaDescuento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTotalCompras;
    private javax.swing.JTextField txtTotalVentas;
    private javax.swing.JFormattedTextField txtValorVenta;
    private javax.swing.JFormattedTextField txtValor_CompraConImp;
    private javax.swing.JFormattedTextField txtValor_CompraSinImp;
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
