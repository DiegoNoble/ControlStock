/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pedidos;

import com.Beans.Articulo;
import com.Beans.ArticulosPedido;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class AtencionArticulosPedidoTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Pos", "Cod.", "Nombre", "Pedido", "Pendiente"};
    //lista para a manipulacao do objeto
    private List<ArticulosPedido> list;
    JTextField txtTotal;

    public AtencionArticulosPedidoTableModel(List<ArticulosPedido> list) {
        this.txtTotal = txtTotal;
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
        ArticulosPedido c = list.get(rowIndex);

        switch (columnIndex) {

            case 0:
                return c.getPosicion();
            case 1:
                return c.getArticulo();
            case 2:
                return c.getArticulo().getNombre();
            case 3:
                return c.getCantPedida();
            case 4:
                return c.getCantPendiente();

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
                return Double.class;
            case 4:
                return Double.class;

            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ArticulosPedido c = list.get(rowIndex);
        switch (columnIndex) {

            case 4:
                if (c.getCantPendiente() > ((Double) aValue)&&(((Double) aValue)>=0)) {
                    c.setCantPendiente((Double) aValue);
                    break;
                }

        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
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
