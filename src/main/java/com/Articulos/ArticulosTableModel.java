/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Articulos;

import com.Beans.Articulo;
import com.Beans.Unidad;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class ArticulosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Código", "Nombre", "Valor compra", "Valor venta", "Unidad", "Cantidad"};
    //lista para a manipulacao do objeto
    private List<Articulo> list;
    JTextField txtTotal;

    public ArticulosTableModel(List<Articulo> list) {
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
        Articulo c = list.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return c.getId();
            case 1:
                return c.getNombre();
            case 2:
                return c.getValor_compra_impuesto();
            case 3:
                return c.getValor_venta();
            case 4:
                return c.getUnidad();
            case 5:
                return c.getCantidad();
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
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Double.class;
            case 3:
                return Double.class;
            case 4:
                return Unidad.class;
            case 5:
                return Double.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void agregar(Articulo articulosPedidos) {
        list.add(articulosPedidos);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Articulo articulosPedidos) {
        list.set(row, articulosPedidos);
        this.fireTableRowsUpdated(row, row);
    }

    public Articulo getCliente(int row) {
        return list.get(row);
    }

}
