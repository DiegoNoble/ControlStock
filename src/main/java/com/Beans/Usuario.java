
package com.Beans;

import java.io.Serializable;
import javax.persistence.*;

 @Entity
 @Table(name = "usuarios")
public class Usuario implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String pass;
    private String perfil;
    
    public Usuario(){
        
    }

    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    public String getPerfil() {
        return perfil;
    }

    
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    
    public String getPass() {
        return pass;
    }

   
    public void setPass(String pass) {
        this.pass = pass;
    }
}
