package Utilidades;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class Utilidades {
    

    public static String getDataTelaToBD(String x) { //Este funcion modifica la fecha ingresada en el formato BR a formato USA compatible con el BD
        String dia, mes, ano = "";
        String data = "";
        dia = x.substring(0, 2);
        mes = x.substring(3, 5);
        ano = x.substring(6, 10);
        data = ano + "-" + mes + "-" + dia;
        return data;
    }
    
    public static String fechaBD(Date fechaSinFormatoBD){
        
        DateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");

        String date = formatoData.format(fechaSinFormatoBD);

        
        return date;
    }
    
    public static Date fechaPantalla(Date fechaSinFormatoBD){
        
        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = fechaSinFormatoBD;

        String date = formatoData.format(fecha);

        Date fechaBD = null;
        try {
            fechaBD = formatoData.parse(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error convirtiendo Fecha" + ex);
        }
        return fechaBD;
    }
    
    public static Date recibeDateRetornaDateBD(Date fechaSinFormatoBD){
        
        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = fechaSinFormatoBD;

        String date = formatoData.format(fecha);

        Date fechaBD = null;
        try {
            fechaBD = formatoData.parse(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error convirtiendo Fecha" + ex);
        }
        return fechaBD;
    }
    
    public static String fechaPantallaString(Date fechaSinFormatoBD){
        
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

        String date = formatoData.format(fechaSinFormatoBD);
       
        return date;
    }
    
    public static Date RecibeDateRetornaDateFormatoBD(Date fechaSinFormato){
        
    
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = fechaSinFormato;

        String date = formatoData.format(fecha);

        Date fechaBD = null;
        try {
            fechaBD = formatoData.parse(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error convirtiendo Fecha" + ex);
        }
        
        return fechaBD;
    }
    
    
    public static double Redondear(double numero,int digitos)

{
      int cifras=(int) Math.pow(10,digitos);

      return Math.rint(numero*cifras)/cifras;

}

    
    
}
