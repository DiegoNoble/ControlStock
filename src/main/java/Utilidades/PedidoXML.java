/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.Beans.Pedido;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class PedidoXML {

    Pedido pedidoSeleccionado;
    private static String XML_PEDIDOS = "";

    public void exportarPedidoXML(Component component, Pedido pedidoSeleccionado) {

        try {
            this.pedidoSeleccionado = pedidoSeleccionado;
            ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
            pedidos.add(pedidoSeleccionado);
            JFileChooser directorio = new JFileChooser();
            //buscarFoto.setCurrentDirectory(new File("C:/Fotos Socios/"));
            directorio.setDialogTitle("Exportar XML");
            //directorio.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
            directorio.showSaveDialog(component);
            //String foto = "C:/Fotos Socios/" + buscarFoto.getSelectedFile().getName();
            XML_PEDIDOS = directorio.getSelectedFile().getPath().concat(".xml");

            // Creamos el contexto e instanciamos el marshaller
            JAXBContext context = JAXBContext.newInstance(Pedido.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            for (Pedido pedido : pedidos) {
                m.marshal(pedido, System.out);

                Writer w = null;
                try {
                    w = new FileWriter(XML_PEDIDOS);
                    m.marshal(pedido, w);
                    JOptionPane.showMessageDialog(null, "XML Generado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(PedidoXML.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        w.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(PedidoXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void importarPedidoXML() {

        try {
            JAXBContext context = JAXBContext.newInstance(Pedido.class);
            System.out.println("Salida desde el fichero XML: ");
            Unmarshaller um = context.createUnmarshaller();
            //for (Pedido pedido : pedidos) {
            Pedido pedidoXML = (Pedido) um.unmarshal(new FileReader(XML_PEDIDOS));
        } catch (JAXBException ex) {
            Logger.getLogger(PedidoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PedidoXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
