/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.Beans.ArticulosCompra;
import com.Beans.ArticulosPedido;
import com.Beans.EquivalenciaUnidades;
import com.DAO.ArticulosCompraDAO;
import com.DAO.ArticulosPedidoDAO;
import java.util.List;
import javax.swing.JOptionPane;

public class Actualiza {

    public void actualizaArticulosPedido() {
        try {
            ArticulosPedidoDAO articulosPedidoDAO = new ArticulosPedidoDAO();

            List<ArticulosPedido> articulosPedido = articulosPedidoDAO.BuscaTodos(ArticulosPedido.class);
            for (ArticulosPedido articuloPedido : articulosPedido) {
                for (EquivalenciaUnidades equivalencia : articuloPedido.getArticulo().getListEquivalencias()) {
                    if (equivalencia.getUnidad() == articuloPedido.getArticulo().getUnidad()) {
                        articuloPedido.setEquivalenciaUnidades(equivalencia);
                        ArticulosPedidoDAO dAO = new ArticulosPedidoDAO(articuloPedido);
                        dAO.actualiza();
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Articulos pedido actualizados correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
       public void actualizaArticulosCompra() {
        try {
            ArticulosPedidoDAO articulosPedidoDAO = new ArticulosPedidoDAO();

            List<ArticulosCompra> articulosCompras = articulosPedidoDAO.BuscaTodos(ArticulosCompra.class);
            for (ArticulosCompra articuloCompra : articulosCompras) {
                for (EquivalenciaUnidades equivalencia : articuloCompra.getArticulo().getListEquivalencias()) {
                    if (equivalencia.getUnidad() == articuloCompra.getArticulo().getUnidad()) {
                        articuloCompra.setEquivalenciaUnidades(equivalencia);
                        ArticulosCompraDAO dAO = new ArticulosCompraDAO(articuloCompra);
                        dAO.actualiza();
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Articulos compra actualizados correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
