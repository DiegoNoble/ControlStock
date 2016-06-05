/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.Pedido;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class PedidoDAO extends DAOGenerico<Object> {

    public PedidoDAO() {
    }

    public PedidoDAO(Pedido pedido) {

        super.objeto = pedido;

    }

    public List buscarIdsPedidos() {

        List ids = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("select id from Pedido");
            ids = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return ids;

    }

}
