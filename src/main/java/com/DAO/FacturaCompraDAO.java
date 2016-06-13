/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.FacturaCompra;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class FacturaCompraDAO extends DAOGenerico<Object> {

    public FacturaCompraDAO() {
    }

    
    public FacturaCompraDAO(FacturaCompra objeto) {

        super.objeto = objeto;

    }

public List buscaEntreFechas(Date desde, Date hasta) {

        List objetos = null;
        try {
            SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("select fc from FacturaCompra fc where fecha >= '" + formatoBD.format(desde) + "' and fecha <='" + formatoBD.format(hasta) + "'order by id desc");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
            e.printStackTrace();
        }
        return objetos;

    }
    
  

}
