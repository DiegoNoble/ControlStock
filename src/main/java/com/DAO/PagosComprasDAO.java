
package com.DAO;

import Utilidades.HibernateUtil;
import com.Beans.MonedaEnum;
import com.Beans.Proveedor;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class PagosComprasDAO extends DAOGenerico<Object> {
    
    private final Session seccion;
    Transaction transacion = null;

   
    public PagosComprasDAO() {

        this.seccion = HibernateUtil.getSeccion();
        
    }

    public List buscaPagosPorProveedoryMoneda(Proveedor proveedor, MonedaEnum moneda) throws Exception {

        List objectos = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("select distinct a.pago from CompraPago a where a.factura.proveedor.id ='"+proveedor.getId()+"' and a.factura.moneda = '"+moneda+"'");
        objectos = query.list();
        transacion.commit();

        return objectos;
    }
    
    
}
