/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.Remito;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
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
public class RemitoDAO extends DAOGenerico<Object> {

    public RemitoDAO() {

    }

    public RemitoDAO(Remito remito) {

        super.objeto = remito;

    }

    public void imprimeRemito(final Remito remito) {

        transacion = seccion.beginTransaction();
        seccion.doWork(new Work() {

            @Override
            public void execute(Connection cnctn) throws SQLException {
                try {
                    BufferedImage logo = ImageIO.read(getClass().getResource("/com/imagenes/logo.png"));
                    HashMap parametros = new HashMap();
                    parametros.put("idRemito", remito.getId());
                    parametros.put("logo", logo);
                    InputStream resource = getClass().getResourceAsStream("/com/Informes/Remito1.jasper");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, cnctn);
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        transacion.commit();
    }

    public List buscaEntreFechas(Date desde, Date hasta) {

        List objetos = null;
        try {
            SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
            transacion = seccion.beginTransaction();
            Query query = seccion.createQuery("from Remito where fecha >= '" + formatoBD.format(desde) + "' and fecha <='" + formatoBD.format(hasta) + "' order by id desc");
            objetos = query.list();
            transacion.commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al traer todos los registros" + e);
        }
        return objetos;

    }
    
    
}
