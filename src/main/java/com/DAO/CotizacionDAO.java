
package com.DAO;

import Utilidades.HibernateUtil;
import com.Beans.Cotizacion;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class CotizacionDAO extends DAOGenerico<Object> {
    
    private final Session seccion;
    Transaction transacion = null;

   
    public CotizacionDAO() {

        this.seccion = HibernateUtil.getSeccion();
        
    }
    
    public Object BuscarUltimaCotizacion() {
        
        Cotizacion retorno = null;
        try {
            
            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("SELECT c FROM Cotizacion c where c.id =(select max(c.id) FROM Cotizacion as c)");
            retorno = (Cotizacion) query.uniqueResult();
            transacion.commit();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros"+e);
        } 
        return retorno;

    }
    
}
