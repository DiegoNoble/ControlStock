/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Compras;

import com.Beans.Articulo;
import com.Beans.ArticulosCompra;
import com.Beans.Unidad;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class ArticulosCompraTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Pos", "Cod.", "Nombre", "Unidad", "Cantidad", "Unitario", "Total"};
    //lista para a manipulacao do objeto
    private List<ArticulosCompra> list;
    JTextField txtTotal;
    JTextField txtSubTotal;
    JTextField txtIVA;

    public ArticulosCompraTableModel(List<ArticulosCompra> list) {
        this.list = list;
    }

    public ArticulosCompraTableModel(List<ArticulosCompra> list, JTextField txtTotal, JTextField txtSubTotal, JTextField txtIVA) {
        this.txtTotal = txtTotal;
        this.txtSubTotal = txtSubTotal;
        this.txtIVA = txtIVA;
        this.list = list;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return list.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArticulosCompra c = list.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return c.getPosicion();
            case 1:
                return c.getArticulo();
            case 2:
                if (c.getArticulo() != null) {
                    return c.getArticulo().getNombre();
                } else {
                    return "";
                }
            case 3:
                return c.getEquivalenciaUnidades().getUnidad();
            case 4:
                return c.getCantidad();
            case 5:
                return c.getValor();

            case 6:
                return c.getValor() * c.getCantidad();

            default:
                return null;
        }
    }

    //determina o nome das colunas
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    //determina que tipo de objeto cada coluna irï¿½ suportar
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return Articulo.class;
            case 2:
                return String.class;
            case 3:
                return Unidad.class;
            case 4:
                return Double.class;
            case 5:
                return Double.class;
            case 6:
                return Double.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void agregar(ArticulosCompra articulosCompras) {
        list.add(articulosCompras);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
        CalculaTotal();
    }

    public void agregar(List<ArticulosCompra> articulosCompras) {
        list.clear();

        list.addAll(articulosCompras);

        this.fireTableDataChanged();
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
        CalculaTotal();
    }

    public void atualizar(int row, ArticulosCompra articulosCompras) {
        list.set(row, articulosCompras);
        this.fireTableRowsUpdated(row, row);
        CalculaTotal();
    }

    public ArticulosCompra getCliente(int row) {
        return list.get(row);
    }

    public void CalculaTotal() {
        Double total = 0.0;
        Double subtotal = 0.0;
        Double iva = 0.0;
        for (ArticulosCompra articulosCompra : list) {
            if (articulosCompra.getValor() != null) {
                subtotal = subtotal + articulosCompra.getValor() * articulosCompra.getCantidad();

            }

        }
        total = subtotal * 1.21;
        iva = total - subtotal;
        //BigDecimal to = new BigDecimal

        //DecimalFormat formato = new DecimalFormat("#,##");
        //formato.setRoundingMode(RoundingMode.CEILING);
        txtTotal.setText(String.format("%.2f", total));
        txtSubTotal.setText(String.format("%.2f", subtotal));
        txtIVA.setText(String.format("%.2f", iva));

    }
}
