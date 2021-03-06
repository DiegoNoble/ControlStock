/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pedidos;

import com.Beans.SituacionPedido;
import com.Beans.Pedido;
import com.Beans.Vendedor;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class PedidosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Situción", "Cod.", "Fecha", "Cliente", "Ciudad", "Vendedor", "Importe atendido","Total pedido"};
    //lista para a manipulacao do objeto
    private List<Pedido> list;

    public PedidosTableModel(List<Pedido> list) {
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
        Pedido c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getEstadoPedido();
            case 1:
                return c.getId();
            case 2:
                return c.getFecha();
            case 3:
                return c.getCliente().getNombre();
            case 4:
                return c.getCliente().getCiudad();
            case 5:
                return c.getVendedor();
            case 6:
                return c.getImporteAtendido();
            case 7:
                return c.getImporteTotal();
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
                return SituacionPedido.class;
            case 1:
                return Integer.class;
            case 2:
                return Date.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return Vendedor.class;
            case 6:
                return Double.class;
            case 7:
                return Double.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void agregar(Pedido pedidos) {
        list.add(pedidos);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void agregar(List<Pedido> pedidos) {
        list.clear();

        list.addAll(pedidos);

        this.fireTableDataChanged();
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Pedido pedidos) {
        list.set(row, pedidos);
        this.fireTableRowsUpdated(row, row);
    }

    public Pedido getCliente(int row) {
        return list.get(row);
    }

}
