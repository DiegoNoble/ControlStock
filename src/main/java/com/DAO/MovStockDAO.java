/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.Articulo;
import com.Beans.MovStock;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class MovStockDAO extends DAOGenerico<Object> {

    public MovStockDAO() {
    }

    public MovStockDAO(MovStock objeto) {
        super.objeto = objeto;
    }

    public List buscaMovArticuloEntreFechas(Articulo articulo, Date fechaInicial, Date fechaFinal) {

        List objetos = null;
        try {
            SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from MovStock  where id_articulo = '" + articulo.getArticuloId().getId() + "' "
                    + "and lote_articulo = '" + articulo.getArticuloId().getLote()+ "'"
                    + "and fecha between '" + formatoBD.format(fechaInicial) + "' "
                    + "and '" + formatoBD.format(fechaFinal) + "' order by id desc, fecha desc" );
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
            
        }
        return objetos;

    }
}
