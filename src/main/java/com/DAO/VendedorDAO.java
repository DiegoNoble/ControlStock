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
public class VendedorDAO extends DAOGenerico<Object> {

    public VendedorDAO() {

    }

    public VendedorDAO(Object objeto) {

        super.objeto = objeto;

    }

    public List buscarVendedor(String filtro) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("from Vendedor where nombre like '%" + filtro + "%'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }
}
