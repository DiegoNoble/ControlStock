/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.ArticulosPedido;
import com.Beans.Pedido;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class ArticulosPedidoDAO extends DAOGenerico<Object> {

    public ArticulosPedidoDAO(ArticulosPedido objeto) {
        super.objeto = objeto;
    }

    public List<ArticulosPedido> buscarPorPedido(Pedido pedido) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from  ArticulosPedido where pedido.id = '" + pedido.getId() + "'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;
    }

}
