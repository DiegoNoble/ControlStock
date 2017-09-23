/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class LeeProperties {

    private String usr;
    private String psw;
    private String driver;
    private String url;
    private Properties props;

    public LeeProperties() {
        getProperties();
    }

    private void getProperties() {
        try {
            props = new Properties();
            InputStream datos = this.getClass().getClassLoader().getResourceAsStream("META-INF/application.properties");
            props.load(datos);
            this.setUsr(props.getProperty("jdbc.user"));
            this.setPsw(props.getProperty("jdbc.pass"));
            this.setDriver(props.getProperty("jdbc.driver"));
            this.setUrl(props.getProperty("jdbc.url"));
        } catch (IOException ex) {
            Logger.getLogger(LeeProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUsr() {

        return usr;
    }

    private void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPsw() {

        return psw;
    }

    private void setPsw(String psw) {
        this.psw = psw;
    }

    public String getDriver() {

        return driver;
    }

    private void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {

        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

}
