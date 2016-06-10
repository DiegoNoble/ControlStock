/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.Cliente;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class ClienteDAO extends DAOGenerico<Object> {

    public ClienteDAO() {

    }

    public ClienteDAO(Cliente objeto) {

        super.objeto = objeto;

    }

    public List buscarCliente(String filtro) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("from Cliente where nombre like '%" + filtro + "%' or razon_social like'%" + filtro + "%'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }
}
