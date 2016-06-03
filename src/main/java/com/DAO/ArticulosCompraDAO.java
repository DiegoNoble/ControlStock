
package com.DAO;

import Utilidades.HibernateUtil;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class ArticulosCompraDAO extends DAOGenerico<Object> {

    private final Session seccion;
    Transaction transacion = null;
    private Object objeto;

   
    public ArticulosCompraDAO () {

        this.seccion = HibernateUtil.getSeccion();
        
    }
    
    public ArticulosCompraDAO(Object objeto) {

        this.seccion = HibernateUtil.getSeccion();
        this.objeto = objeto;

    }
    
    public void calculaCostoMedio(String idArticulo, Double newValorCompraSinIva, Double newCantidad, Double oldValorCompraSinIva, Double oldCantidad) throws Exception{
     
            transacion = seccion.beginTransaction();
            
            seccion.createSQLQuery("update articulo set valor_compra =round((('"+newValorCompraSinIva+"' * '"+newCantidad+"') + ('"+oldValorCompraSinIva+"' * '"+oldCantidad+"')) / ('"+newCantidad+"' + '"+oldCantidad+"'),2) where id = '"+idArticulo+"'").executeUpdate();
            seccion.createSQLQuery("update articulo set valor_compra_impuesto=round(((('"+newValorCompraSinIva+"' * '"+newCantidad+"') + ('"+oldValorCompraSinIva+"' * '"+oldCantidad+"')) / ('"+newCantidad+"' + '"+oldCantidad+"')*1.22) ,2) where id = '"+idArticulo+"'").executeUpdate();
            
            if(newValorCompraSinIva!=oldValorCompraSinIva){
                
             JOptionPane.showMessageDialog(null, "Los valores de costo de: " + idArticulo + " cambiaron \n "
                    + "se recomienda actualizar los valores de venta y porcentajes de ganancia. ", "Atención!", JOptionPane.INFORMATION_MESSAGE);
            transacion.commit();
            }
      
    }
      
}
