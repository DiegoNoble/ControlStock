/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.Beans.Pedido;
import com.DAO.PedidoDAO;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbTest {

    private final static String XML_PEDIDOS = "./info-pedido.xml";

    public static void main(String[] args) throws JAXBException, IOException {

        // Primero rellenamos los objetos Java y generamos un XML
        PedidoDAO pedidoDAO = new PedidoDAO();
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        pedidos.add((Pedido) pedidoDAO.buscarPorID(Pedido.class, Integer.valueOf(338)));

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
            } finally {
                try {
                    w.close();
                } catch (Exception e) {
                }
            }
        }

        // Ahora leemos el XML e instanciamos las clases Java
        System.out.println("Salida desde el fichero XML: ");
        Unmarshaller um = context.createUnmarshaller();
        for (Pedido pedido : pedidos) {
        pedido = (Pedido) um.unmarshal(new FileReader(XML_PEDIDOS));

        for (int i = 0; i < pedido.getArticulosPedido().toArray().length; i++) {
            System.out.println("Articulo pedido " + (i + 1) + ": "
                    + pedido.getArticulosPedido().get(i).getArticulo() + " cantidad "
                    + pedido.getArticulosPedido().get(i).getCantPedida());
        }
    
        }
        
    }
}
