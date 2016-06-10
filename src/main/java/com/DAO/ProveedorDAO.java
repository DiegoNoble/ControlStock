/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class ProveedorDAO extends DAOGenerico<Object> {

    public ProveedorDAO(Object objeto) {

        super.objeto = objeto;

    }

    public ProveedorDAO() {

    }

     public List buscarProveedor(String filtro) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("from Proveedor where nombre like '%" + filtro + "%' or razon_social like'%" + filtro + "%'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }
}
