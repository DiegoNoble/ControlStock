/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.FacturaCompra;
import com.Beans.MonedaEnum;
import com.Beans.Proveedor;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class FacturaCompraDAO extends DAOGenerico<Object> {

    public FacturaCompraDAO(FacturaCompra objeto) {

        super.objeto = objeto;

    }

    public List buscaPorNroFactura(String nroFactura) throws Exception {

        List objectos = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from FacturaCompra where nroFactura = " + nroFactura + "");
        objectos = query.list();
        transacion.commit();

        return objectos;
    }

    public void eliminaFactura() throws Exception {

        transacion = seccion.beginTransaction();
        seccion.delete(objeto);
        transacion.commit();

    }

    public List buscaPorProveedor(Integer IdProveedor) throws Exception {

        List objectos = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from FacturaCompra where proveedor = " + IdProveedor + "");
        objectos = query.list();
        transacion.commit();

        return objectos;

    }

    public List buscaFacturasPendientes(Proveedor proveedor) throws Exception {

        List objectos = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from FacturaCompra where estado = 'PENDIENTE' and proveedor = " + proveedor.getId() + "");
        objectos = query.list();
        transacion.commit();

        return objectos;
    }

    public List buscaFacturasPendientesPorMoneda(Proveedor proveedor, MonedaEnum moneda) throws Exception {

        List objectos = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from FacturaCompra where estado = 'PENDIENTE' and proveedor = " + proveedor.getId() + " and moneda ='" + moneda + "'");
        objectos = query.list();
        transacion.commit();

        return objectos;
    }

    
    public List buscaEntreFechasPorProveedor(Proveedor proveedor, String fechaInicial, String fechaFinal) throws Exception {

        List objetos = null;

        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from FacturaCompra where proveedor = " + proveedor.getId() + " and fecha between '" + fechaInicial + "' and '" + fechaFinal + "'");
        objetos = query.list();
        transacion.commit();

        return objetos;

    }

    public List buscaProximosVencimientos() throws Exception {

        List objetos = null;

        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from FacturaCompra where datediff(fechaVencimiento,curdate()) < 5 and estado = 'PENDIENTE'");
        objetos = query.list();
        transacion.commit();

        return objetos;

    }

    public FacturaCompra buscaPorNroFacturaYSerie(String nroFactura, String serie) throws Exception {

        FacturaCompra factura = new FacturaCompra();
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from FacturaCompra where nroFactura = " + nroFactura + " and serieFactura = '" + serie + "'");
        factura = (FacturaCompra) query.uniqueResult();
        transacion.commit();

        return factura;
    }

}
