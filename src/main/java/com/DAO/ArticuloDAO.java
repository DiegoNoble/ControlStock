/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import Utilidades.HibernateUtil;
import com.Beans.Articulos;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Diego
 */
public class ArticuloDAO extends DAOGenerico<Object> {
    
    private final Session seccion;
    Transaction transacion = null;
    private Object objeto;

   
    public ArticuloDAO () {

        this.seccion = HibernateUtil.getSeccion();
        
    }
    
    public ArticuloDAO(Object objeto) {

        this.seccion = HibernateUtil.getSeccion();
        this.objeto = objeto;

    }
    
    public Articulos buscaArtUnicoPorIDStr(String id) throws Exception{
        
            Articulos articulo = new Articulos();
            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from Articulos where id ='"+id+"'");
            articulo = (Articulos) query.uniqueResult();
            transacion.commit();
        
        return articulo;
        
        
    }
    
}
