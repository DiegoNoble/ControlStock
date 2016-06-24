package com.DAO;

import com.Beans.ArticulosCompra;
import javax.swing.JOptionPane;

public class ArticulosCompraDAO extends DAOGenerico<Object> {

    public ArticulosCompraDAO() {

    }

    public ArticulosCompraDAO(ArticulosCompra objeto) {

        super.objeto = objeto;
    }

    public void ajustaPrecioCosto(String idArticulo, Double newValorCompra) throws Exception {

        transacion = seccion.beginTransaction();

        seccion.createSQLQuery("update articulo set valor_compra ='" + newValorCompra + "' where id = '" + idArticulo + "'").executeUpdate();

        JOptionPane.showMessageDialog(null, "Los valores de costo de: " + idArticulo + " cambiaron \n "
                + "se recomienda actualizar los valores de venta y porcentajes de ganancia. ", "Atención!", JOptionPane.INFORMATION_MESSAGE);
        transacion.commit();
    }

}
