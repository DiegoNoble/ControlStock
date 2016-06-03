/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import Utilidades.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Diego
 */
public class ArticulosPedidoDAO extends DAOGenerico<Object> {

    private final Session seccion;
    Transaction transacion = null;
    private Object objeto;

   
    public ArticulosPedidoDAO () {

        this.seccion = HibernateUtil.getSeccion();
        
    }
    
    public ArticulosPedidoDAO(Object objeto) {

        this.seccion = HibernateUtil.getSeccion();
        this.objeto = objeto;

    }     
}
