/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import Utilidades.HibernateUtil;
import com.Beans.Cliente;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Diego
 */
public class FacturaVentaDAO extends DAOGenerico<Object> {

    private final Session seccion;
    Transaction transacion = null;
    private Object objeto;

    public FacturaVentaDAO(Object objeto) {

        this.seccion = HibernateUtil.getSeccion();
        this.objeto = objeto;

    }

    public FacturaVentaDAO() {

        this.seccion = HibernateUtil.getSeccion();

    }

    public List buscaPorNroFactura(String idFactura) throws Exception {

        List objectos = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from Factura where idFactura = " + idFactura + "");
        objectos = query.list();
        transacion.commit();

        return objectos;
    }
    
    public List buscaPorNroFacturaVentaCredito(String idFactura) throws Exception {

        List objectos = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from Factura where idFactura = " + idFactura + " and tipo = 'CREDITO'" );
        objectos = query.list();
        transacion.commit();

        return objectos;
    }

    public void eliminaFactura() throws Exception {

        transacion = seccion.beginTransaction();
        seccion.delete(objeto);
        transacion.commit();

    }
    
    public List buscaPorCliente(Integer IdCliente) throws Exception{
        
        List objectos = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from Factura where cliente = " + IdCliente + "");
        objectos = query.list();
        transacion.commit();

        return objectos;
        
    }
    
    public List buscaPorClienteCredito(Integer IdCliente) throws Exception{
        
        List objectos = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from Factura where cliente = " + IdCliente + " and tipo = 'CREDITO'");
        objectos = query.list();
        transacion.commit();

        return objectos;
        
    }
    
    public List buscaEntreFechasFacturasCredito(Class classe, String fechaInicial, String fechaFinal, String tipo) {
        
        List objetos = null;
        try {
            
            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from "+classe.getName()+" where fecha between '" + fechaInicial + "' and '" + fechaFinal + "' and tipo ='"+tipo+"'");
            objetos = query.list();
            transacion.commit();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros"+e);
        } 
        return objetos;

    }
    
    public List buscaEntreFechasPorProveedor(Cliente cliente, String fechaInicial, String fechaFinal) throws Exception{
        
        List objetos = null;
       
            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from Factura where cliente = "+cliente.getId_cliente()+" and fecha between '" + fechaInicial + "' and '" + fechaFinal + "'");
            objetos = query.list();
            transacion.commit();
       
        return objetos;

    }
}

