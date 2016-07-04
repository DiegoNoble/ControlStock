package com.DAO;

import Utilidades.HibernateUtil;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

public class DAOGenerico<Objeto> {

    final Session seccion;
    Transaction transacion = null;
    Objeto objeto;

    public DAOGenerico(Objeto objeto) {

        this.seccion = HibernateUtil.getSeccion();
        this.objeto = objeto;

    }

    public DAOGenerico() {

        this.seccion = HibernateUtil.getSeccion();

    }

    public void guardar() throws Exception {

        transacion = seccion.beginTransaction();
        seccion.save(objeto);
        transacion.commit();

    }

    public void guardarList(List objetos) {

        try {
            transacion = seccion.beginTransaction();
            for (Object objeto : objetos) {
                seccion.saveOrUpdate(objeto);
                seccion.flush();
                seccion.clear();

            }
            transacion.commit();
            seccion.close();
        } catch (Exception e) {
            e.printStackTrace();
            transacion.rollback();
            JOptionPane.showMessageDialog(null, "Error al guardar registros" + e);
        }
    }

    public void actualiza() {

        try {
            transacion = seccion.beginTransaction();
            seccion.merge(objeto);
            transacion.commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            transacion.rollback();
        }

    }

    public void elimina() {

        try {
            transacion = seccion.beginTransaction();
            seccion.delete(objeto);
            seccion.flush();
            seccion.clear();
            transacion.commit();
            seccion.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar" + e, "Error", JOptionPane.ERROR_MESSAGE);
            transacion.rollback();
        }
    }

    public List BuscaTodos(Class classe) {

        List objetos = null;

        try {

            transacion = seccion.beginTransaction();

            Query query = seccion.createQuery("from " + classe.getName());
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar todos" + e);
        }
        return objetos;
    }

    public Objeto buscarPorID(Class classe, int id) {

        transacion = seccion.beginTransaction();

        objeto = (Objeto) seccion.get(classe, id);

        return objeto;

    }

    public List buscaEntreFechas(Class classe, String fechaInicial, String fechaFinal) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from " + classe.getName() + " where fecha between '" + fechaInicial + "' and '" + fechaFinal + "'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }

    public List buscaEntreFechasFacturasCredito(Class classe, Integer idProveedor, String fechaInicial, String fechaFinal, String tipo) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from " + classe.getName() + " where id_proveedor = '" + idProveedor + "' and fecha between '" + fechaInicial + "' and '" + fechaFinal + "' and tipo ='" + tipo + "'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }

    public List buscaEntreFechasFacturasCreditoClientes(Class classe, Integer idCliente, String fechaInicial, String fechaFinal, String tipo) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from " + classe.getName() + " where id_cliente = '" + idCliente + "' and fecha between '" + fechaInicial + "' and '" + fechaFinal + "' and tipo ='" + tipo + "'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }

    public List buscaTodosOrdena(Class classe, String campoOrden) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from " + classe.getName() + " order by " + campoOrden);
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }

    public List buscarPor(Class classe, String campoTabla, String filtro) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from  " + classe.getName() + " where " + campoTabla + " like '%" + filtro + "%' order by id desc");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;
    }

    public List buscarPor(Class classe, String campoTabla, Integer filtro) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from  " + classe.getName() + " where " + campoTabla + " like '%" + filtro + "%' order by id desc");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;
    }

    public List Login(String nombre, String pass) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from  Usuario where nombre= '" + nombre + "' and pass= '" + pass + "'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;
    }

    public List buscarPorOrdena(Class classe, String campoTabla, String filtro, String campoOrdena) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from  " + classe.getName() + " where " + campoTabla + " like '%" + filtro + "%' order by " + campoOrdena);
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;
    }

    public void eliminarPor(Class clase, String campoTabla, String filtro) {

        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("delete from  " + clase.getName() + " where " + campoTabla + "= '" + filtro + "'");
            query.executeUpdate();

            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }

    }

    public List buscarPorIDString(Class classe, String id) {

        List objeto = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from  " + classe.getName() + " where id like '%" + id + "%'");
            objeto = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objeto;
    }

    public List buscaCompras(String idArticulo, String fechaInicial, String fechaFinal) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("select a from ArticulosCompra a inner join a.facturaCompra as f where a.articulo.id='" + idArticulo + "' and f.fecha between '" + fechaInicial + "' and '" + fechaFinal + "'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }

    public List buscaVentas(String idArticulo, String fechaInicial, String fechaFinal) {

        List objetos = null;
        try {

            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("select a from ArticulosVenta a inner join a.factura as f where a.articulo.id='" + idArticulo + "' and f.fecha between '" + fechaInicial + "' and '" + fechaFinal + "'");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }

    public void informe() {

        transacion = seccion.beginTransaction();
        seccion.doWork(new Work() {

            @Override
            public void execute(Connection cnctn) throws SQLException {
                try {
                    HashMap parametros = new HashMap();
                    InputStream resource = getClass().getResourceAsStream("/com/Informes/listoArticulos.jasper");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, cnctn);
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        transacion.commit();
    }

    public void registraOActualiza() {

        try {
            transacion = seccion.beginTransaction();
            seccion.saveOrUpdate(objeto);
            transacion.commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            transacion.rollback();
        }

    }

}
