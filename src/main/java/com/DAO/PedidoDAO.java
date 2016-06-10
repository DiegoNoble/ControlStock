/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.Pedido;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Query;
import org.hibernate.jdbc.Work;

/**
 *
 * @author Diego
 */
public class PedidoDAO extends DAOGenerico<Object> {

    public PedidoDAO() {
    }

    public PedidoDAO(Pedido pedido) {

        super.objeto = pedido;

    }

    public List buscarIdsPedidos() {

        List ids = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("select id from Pedido");
            ids = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return ids;

    }
    
     public List buscaEntreFechas(Date desde, Date hasta) {

        List objetos = null;
        try {
            SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from Pedido where fecha >= '" + formatoBD.format(desde) + "' and fecha <='" + formatoBD.format(hasta) + "'order by id desc");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }
    
    

}
