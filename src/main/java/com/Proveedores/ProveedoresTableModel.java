/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Proveedores;

import com.Beans.Proveedor;
import com.Beans.CondicionImpositiva;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class ProveedoresTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Cód", "Razón Social", "Documento", "Condición impositiva", "Ciudad"};
    //lista para a manipulacao do objeto
    private List<Proveedor> list;

    public ProveedoresTableModel(List<Proveedor> list) {
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
        Proveedor c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getRazon_social();
            case 2:
                return c.getDocumento();
            case 3:
                return c.getCondicionImpositiva();
            case 4:
                return c.getCiudad();
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
                return CondicionImpositiva.class;
            case 4:
                return String.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void agregar(Proveedor clientes) {
        list.add(clientes);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void agregar(List<Proveedor> clientes) {
        list.clear();
        list.addAll(clientes);
        this.fireTableDataChanged();
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Proveedor clientes) {
        list.set(row, clientes);
        this.fireTableRowsUpdated(row, row);
    }

    public Proveedor getProveedor(int row) {
        return list.get(row);
    }

}
