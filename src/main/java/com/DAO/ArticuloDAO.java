/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.Articulo;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class ArticuloDAO extends DAOGenerico<Object> {

    public ArticuloDAO() {

    }

    public ArticuloDAO(Articulo objeto) {

        super.objeto = objeto;

    }

    public Articulo buscaArtUnicoPorIDStr(String id) throws Exception {

        Articulo articulo = new Articulo();
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from Articulos where id ='" + id + "'");
        articulo = (Articulo) query.uniqueResult();
        transacion.commit();

        return articulo;

    }

    public List<String> buscarIdsArticulos() throws Exception {
        List ids = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("Select id from Articulo");
        ids = query.list();
        transacion.commit();

        return ids;

    }

    public List<String> buscarNombresArticulos() throws Exception {
        List ids = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("Select nombre from Articulo ");
        ids = query.list();
        transacion.commit();

        return ids;

    }

}
