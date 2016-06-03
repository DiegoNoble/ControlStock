
package Utilidades;

import com.Beans.CompraPago;
import com.Beans.PagoCompra;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class CreaTablas {
    
    public static void main (String args[]){
    
    AnnotationConfiguration cfg = new AnnotationConfiguration().configure();
     //<-- Especifico la clase a generar
    cfg.addAnnotatedClass(PagoCompra.class); 
    cfg.addAnnotatedClass(CompraPago.class);
    SchemaExport se = new SchemaExport(cfg);
    se.create(true,true);
    
     Session seccion = HibernateUtil.getSeccion();
     Transaction transacion = seccion.beginTransaction();
     
     Query elimina_Factura = seccion.createSQLQuery("CREATE TRIGGER `control_stock`.`elimina_Factura` "
             + "AFTER DELETE ON `control_stock`.`articulos_venta` FOR EACH ROW "
             + "update articulo set cantidad = cantidad + old.cantidad where id = old.id_articulo");
     
     Query baja_stock = seccion.createSQLQuery("CREATE TRIGGER `control_stock`.`baja_Stock` "
             + "AFTER INSERT ON `control_stock`.`articulos_venta` FOR EACH ROW "
             + "update articulo set cantidad = cantidad - new.cantidad where id = new.id_articulo");
     
     Query compra_articulos = seccion.createSQLQuery("CREATE TRIGGER `control_stock`.`compraArticulo` "
             + "AFTER INSERT ON `control_stock`.`articulos_compra` FOR EACH ROW update articulo "
             + "set cantidad = cantidad + new.cantidad where id = new.id_articulo");
     
     Query elimina_compra_articulo = seccion.createSQLQuery("CREATE TRIGGER `control_stock`.`eliminaCompraArticulo` "
             + "AFTER DELETE ON `control_stock`.`articulos_compra` FOR EACH ROW update articulo "
             + "set cantidad = cantidad - old.cantidad where id = old.id_articulo");
     
     elimina_Factura.executeUpdate();
     baja_stock.executeUpdate();
     compra_articulos.executeUpdate();
     elimina_compra_articulo.executeUpdate();
     
     transacion.commit();
     seccion.close();
    }
}
