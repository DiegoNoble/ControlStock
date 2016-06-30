/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.Transportista;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class TransportistaDAO extends DAOGenerico<Object> {

    public TransportistaDAO() {

    }

    public TransportistaDAO(Transportista objeto) {

        super.objeto = objeto;

    }

    public List buscarTransportista(String filtro) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("from Transportista where nombre like '%" + filtro + "%'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }
}
