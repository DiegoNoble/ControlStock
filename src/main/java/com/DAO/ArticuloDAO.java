/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.Articulo;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;

/**
 *
 * @author Diego
 */
public class ArticuloDAO extends DAOGenerico<Object> {

    public ArticuloDAO() {

    }

    public ArticuloDAO(Articulo objeto) {

        super.objeto = objeto;

    }

    public Articulo buscaArtUnicoPorIDStr(String id) throws Exception {

        Articulo articulo = new Articulo();
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("from Articulos where id ='" + id + "'");
        articulo = (Articulo) query.uniqueResult();
        transacion.commit();

        return articulo;

    }

    public List<String> buscarIdsArticulos() throws Exception {
        List ids = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("Select id from Articulo");
        ids = query.list();
        transacion.commit();

        return ids;

    }

    public List<String> buscarNombresArticulos() throws Exception {
        List ids = null;
        transacion = seccion.beginTransaction();
        Query query = seccion.createQuery("Select nombre from Articulo ");
        ids = query.list();
        transacion.commit();

        return ids;

    }

    public List BuscaTodosPorSituacion(String situacion) {

        List objetos = null;

        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("from Articulo a where a.activo = '" + situacion + "'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar todos" + e);
        }
        return objetos;
    }

    public List BuscaPorCodigo(String codigo, String situacion) {

        List objetos = null;

        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("from Articulo a where a.activo = '" + situacion + "' and a.articuloId.id like '%" + codigo + "%'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar todos" + e);
        }
        return objetos;
    }

    public List<Articulo> BuscarArticulosArtivosAgotados() {

        List objetos = null;

        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("from Articulo a where a.activo = 'Activo' and a.cantidad <=0");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar todos" + e);
        }
        return objetos;
    }

}
