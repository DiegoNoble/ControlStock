/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pedidos;

import com.Beans.Articulo;
import com.Beans.ArticulosPedido;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class ArticulosPedidoTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Pos", "Cod.", "Nombre", "Cantidad", "Unitario", "Total"};
    //lista para a manipulacao do objeto
    private List<ArticulosPedido> list;

    public ArticulosPedidoTableModel() {
        list = new LinkedList<ArticulosPedido>();
    }

    public ArticulosPedidoTableModel(List<ArticulosPedido> list) {
        this.list = list;
    }

    //numero de linhas
    public int getRowCount() {
        return list.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArticulosPedido c = list.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return c.getPosicion();
            case 1:
                return c.getArticulo().getId();
            case 2:
                return c.getArticulo().getNombre();
            case 3:
                return c.getCantPedida();
            case 4:
                return c.getArticulo().getValor_venta();
            case 5:
                return c.getCantPedida() * c.getArticulo().getValor_venta();

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
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Double.class;
            case 5:
                return Double.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return true;

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ArticulosPedido c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                c.setPosicion((Integer) aValue);
                break;
            case 1:
                c.setArticulo((Articulo) aValue);
                break;
            case 2:
                c.setArticulo((Articulo) aValue);
                break;
            case 3:
                c.setCantPedida((Double) aValue);
                break;
            case 5:
                c.setImportePedido((Double) aValue);
                break;

        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    public void agregar(ArticulosPedido articulosPedidos) {
        list.add(articulosPedidos);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, ArticulosPedido articulosPedidos) {
        list.set(row, articulosPedidos);
        this.fireTableRowsUpdated(row, row);
    }

    public ArticulosPedido getCliente(int row) {
        return list.get(row);
    }

}
