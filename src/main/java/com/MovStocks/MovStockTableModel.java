/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MovStocks;

import com.Beans.EquivalenciaUnidades;
import com.Beans.FacturaCompra;
import com.Beans.MovStock;
import com.Beans.Remito;
import com.Beans.Unidad;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class MovStockTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Remito", "Compra", "Cant. Movimiento", "Stock unidad Base", "Stock equivalente"};
    //lista para a manipulacao do objeto
    private List<MovStock> list;
    JTextField txtTotal;

    public MovStockTableModel(List<MovStock> list) {
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
        MovStock c = list.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return c.getFecha();
            case 1:
                return c.getRemito();
            case 2:
                return c.getFacturaCompra();
            case 3:
                return c.getCantidadMov() + " " + c.getArticulo().getUnidad();
            case 4:
                return c.getArticulo().getUnidad() + " " + c.getSaldoStock();
            case 5:
                Double factorConversion = 1.0;
                Unidad unidadEquivalente = null;
                DecimalFormat formato = new DecimalFormat("#.##");
                for (EquivalenciaUnidades equivalencia : c.getArticulo().getListEquivalencias()) {
                    if (equivalencia.getUnidad() != c.getArticulo().getUnidad()) {
                        unidadEquivalente = equivalencia.getUnidad();
                        factorConversion = equivalencia.getFactor_conversion();
                    }
                }
                return unidadEquivalente + " " + formato.format(c.getSaldoStock() / factorConversion);
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
                return Date.class;
            case 1:
                return Remito.class;
            case 2:
                return FacturaCompra.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void agregar(MovStock articulosPedidos) {
        list.add(articulosPedidos);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void agregar(List<MovStock> movStock) {
        list.clear();
        list.addAll(movStock);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, MovStock articulosPedidos) {
        list.set(row, articulosPedidos);
        this.fireTableRowsUpdated(row, row);
    }

    public MovStock getCliente(int row) {
        return list.get(row);
    }

}
