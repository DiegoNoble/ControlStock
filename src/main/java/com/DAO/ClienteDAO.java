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

    public List filtroInteligenteClientes2(Boolean situacion, String filtro) {

        List objetos = null;

        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("select new Cliente(c.id,c.nombre, c.documento, c.condicionImpositiva, c.direccion, c.activo) "
                    + "from Cliente c where (c.razon_social like '%" + filtro + "%' "
                    + "or c.nombre like '%" + filtro + "%' or c.id like '%" + filtro + "%') and c.activo = " + situacion + " ");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar todos" + e);
        }
        return objetos;
    }

    public List buscaIdNombreClientes() {

        List objetos = null;

        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("select new Cliente(c.id,c.nombre,c.activo)  from Cliente c where c.activo = true");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar todos" + e);
        }
        return objetos;
    }

}
