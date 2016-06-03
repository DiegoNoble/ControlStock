/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import Utilidades.HibernateUtil;
import com.Beans.Proveedor;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Diego
 */
public class ProveedorDAO extends DAOGenerico<Object> {
    
    private final Session seccion;
    Transaction transacion = null;
    private Object objeto;
    
    public ProveedorDAO(Object objeto) {

        this.seccion = HibernateUtil.getSeccion();
        this.objeto = objeto;

    }
    public ProveedorDAO() {

        this.seccion = HibernateUtil.getSeccion();
        
    }
    
   

    
}
