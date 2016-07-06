/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Repartos;

import com.Beans.Reparto;
import com.Beans.SituacionReparto;
import com.Beans.Transportista;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class RepartoTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Cód", "Situación", "Fecha", "Transportista", "Importe total", "% Comisión", "Comisión"};
    //lista para a manipulacao do objeto
    private List<Reparto> list;

    public RepartoTableModel(List<Reparto> list) {
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
        Reparto c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getSituacionReparto();
            case 2:
                return c.getFecha();
            case 3:
                return c.getTransportista();
            case 4:
                return c.getTotalRepartoSinIVA();
            case 5:
                return c.getPorcentageComision();
            case 6:
                return c.getComision();
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
                return SituacionReparto.class;
            case 2:
                return Date.class;
            case 3:
                return Transportista.class;
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

    public void agregar(Reparto pedidos) {
        list.add(pedidos);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void agregar(List<Reparto> reparto) {
        list.clear();
        list.addAll(reparto);

        this.fireTableDataChanged();
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Reparto pedidos) {
        list.set(row, pedidos);
        this.fireTableRowsUpdated(row, row);
    }

    public Reparto getCliente(int row) {
        return list.get(row);
    }

}
