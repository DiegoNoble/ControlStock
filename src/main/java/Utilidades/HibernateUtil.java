/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;


public class HibernateUtil {

    private static SessionFactory fabricaDeSecciones;
    
    static {
        try {
            
            fabricaDeSecciones = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error" +ex);
        }
    }
    
    public static Session getSeccion() {
        return fabricaDeSecciones.openSession();
    }
}
