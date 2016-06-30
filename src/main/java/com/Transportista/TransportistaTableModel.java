/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Transportista;

import com.Beans.Transportista;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class TransportistaTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Cód", "Nombre", "Tel", "Cel"};
    //lista para a manipulacao do objeto
    private List<Transportista> list;

    public TransportistaTableModel(List<Transportista> list) {
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
        Transportista c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getNombre();
            case 2:
                return c.getTelefono();
            case 3:
                return c.getCel();
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

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void agregar(Transportista vendedores) {
        list.add(vendedores);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void agregar(List<Transportista> vendedores) {
        list.clear();
        list.addAll(vendedores);
        this.fireTableDataChanged();
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Transportista vendedores) {
        list.set(row, vendedores);
        this.fireTableRowsUpdated(row, row);
    }

    public Transportista getTransportista(int row) {
        return list.get(row);
    }

}
