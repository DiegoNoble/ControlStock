/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "unidad")

public class Unidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public Unidad() {
    }

    public Unidad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   @Override
    public String toString() {
        return descripcion;
    }
    
}
