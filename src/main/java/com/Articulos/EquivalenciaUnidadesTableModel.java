package com.Articulos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.Beans.EquivalenciaUnidades;
import com.Beans.Unidad;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class EquivalenciaUnidadesTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Unidad", "Factor", "Valor compra", "Valor venta", "Stock equivalente"};
    //lista para a manipulacao do objeto
    private List<EquivalenciaUnidades> list;
    JTextField txtTotal;

    public EquivalenciaUnidadesTableModel(List<EquivalenciaUnidades> list) {
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
        EquivalenciaUnidades c = list.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return c.getUnidad();
            case 1:
                return c.getFactor_conversion();
            case 2:
                return c.getValor_compra_equivalente();
            case 3:
                return c.getValor_venta_equivalente();
            case 4:
                return c.getStock();
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
                return Unidad.class;
            case 1:
                return Double.class;
            case 2:
                return Double.class;
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
        EquivalenciaUnidades c = list.get(rowIndex);

        switch (columnIndex) {

            case 0:
                c.setUnidad((Unidad) aValue);
                break;
            case 1:
                c.setFactor_conversion((Double) aValue);
                c.setValor_compra_equivalente((Double) c.getArticulo().getValor_compra() * c.getFactor_conversion());
                c.setValor_venta_equivalente((Double) c.getArticulo().getValor_venta() * c.getFactor_conversion());
                c.setStock((Double) c.getArticulo().getCantidad() / c.getFactor_conversion());
                fireTableDataChanged();
                break;

        }
        fireTableCellUpdated(rowIndex, columnIndex);
        //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return true;
        } else if (columnIndex == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void agregar(EquivalenciaUnidades articulosPedidos) {
        list.add(articulosPedidos);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void agregar(List<EquivalenciaUnidades> equivalenciaUnidades) {
        list.clear();
        list.addAll(equivalenciaUnidades);

        this.fireTableDataChanged();
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, EquivalenciaUnidades articulosPedidos) {
        list.set(row, articulosPedidos);
        this.fireTableRowsUpdated(row, row);
    }

    public EquivalenciaUnidades getCliente(int row) {
        return list.get(row);
    }

}
