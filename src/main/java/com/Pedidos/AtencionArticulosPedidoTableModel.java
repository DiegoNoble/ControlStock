/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pedidos;

import com.Beans.Articulo;
import com.Beans.ArticulosPedido;
import com.Beans.Unidad;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class AtencionArticulosPedidoTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Pos", "Cod.", "Nombre", "Unidad", "Cant pedida", "Cant atendida", "Unitario", "% Bonif", "Sub Total"};
    //lista para a manipulacao do objeto
    private List<ArticulosPedido> list;
    JTextField txtTotal;

    public AtencionArticulosPedidoTableModel(List<ArticulosPedido> list, JTextField txtTotal) {
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
                return c.getEquivalenciaUnidades().getUnidad();
            case 4:
                return c.getCantPedida();
            case 5:
                return c.getCantAtendida();
            case 6:
                return c.getArticulo().getValor_venta() * c.getEquivalenciaUnidades().getFactor_conversion();
            case 7:
                return c.getBonificacion();
            case 8:
                return c.getImporteAtendido();

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
            case 7:
                return Double.class;
            case 8:
                return Double.class;

            default:
                return null;
        }
    }

    Double calculaTotalAtendido(ArticulosPedido a) {

        try {
            Double total = (a.getArticulo().getValor_venta() * a.getEquivalenciaUnidades().getFactor_conversion()) * a.getCantAtendida();
            if (a.getBonificacion() == 100.00) {
                return 0.0;
           
            } else {

                Double totalBonificado = (total / (1 + (a.getBonificacion() / 100)));
                return totalBonificado;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ArticulosPedido c = list.get(rowIndex);

        switch (columnIndex) {

            case 5:
                c.setCantAtendida((Double) aValue);
                c.setImporteAtendido(calculaTotalAtendido(c));
                break;

            case 7:

                c.setBonificacion((Double) aValue);
                c.setImporteAtendido(calculaTotalAtendido(c));
                break;

        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 5) {
            return true;
        } else if (columnIndex == 7) {
            return true;
        } else {
            return false;
        }

    }

    public void agregar(ArticulosPedido articulosPedidos) {
        list.add(articulosPedidos);
        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
        CalculaTotalPedido();
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
        CalculaTotalPedido();
    }

    public void atualizar(int row, ArticulosPedido articulosPedidos) {
        list.set(row, articulosPedidos);
        this.fireTableRowsUpdated(row, row);
        CalculaTotalPedido();
    }

    public ArticulosPedido getCliente(int row) {
        return list.get(row);
    }

    public void CalculaTotalPedido() {
        Double total = 0.0;
        for (ArticulosPedido articulosPedido : list) {
            if (articulosPedido.getImportePedido() != null) {
                total = total + articulosPedido.getImporteAtendido();
            }
        }
        //BigDecimal to = new BigDecimal

        //DecimalFormat formato = new DecimalFormat("#,##");
        //formato.setRoundingMode(RoundingMode.CEILING);
        txtTotal.setText(String.format("%.2f", total));

    }

}
