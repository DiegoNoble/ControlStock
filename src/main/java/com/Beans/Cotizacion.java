/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "cotizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cotizacion.findAll", query = "SELECT c FROM Cotizacion c"),
    @NamedQuery(name = "Cotizacion.findById", query = "SELECT c FROM Cotizacion c WHERE c.id = :id"),
    @NamedQuery(name = "Cotizacion.findByPesos", query = "SELECT c FROM Cotizacion c WHERE c.pesos = :pesos"),
    @NamedQuery(name = "Cotizacion.findByDolares", query = "SELECT c FROM Cotizacion c WHERE c.dolares = :dolares"),
    @NamedQuery(name = "Cotizacion.findByReales", query = "SELECT c FROM Cotizacion c WHERE c.reales = :reales"),
    @NamedQuery(name = "Cotizacion.findByFecha", query = "SELECT c FROM Cotizacion c WHERE c.fecha = :fecha")})
public class Cotizacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "PESOS")
    private float pesos;
    @Basic(optional = false)
    @Column(name = "DOLARES")
    private float dolares;
    @Basic(optional = false)
    @Column(name = "REALES")
    private float reales;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Cotizacion() {
    }

    public Cotizacion(Integer id) {
        this.id = id;
    }

    public Cotizacion(Integer id, float pesos, float dolares, float reales, Date fecha) {
        this.id = id;
        this.pesos = pesos;
        this.dolares = dolares;
        this.reales = reales;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getPesos() {
        return pesos;
    }

    public void setPesos(float pesos) {
        this.pesos = pesos;
    }

    public float getDolares() {
        return dolares;
    }

    public void setDolares(float dolares) {
        this.dolares = dolares;
    }

    public float getReales() {
        return reales;
    }

    public void setReales(float reales) {
        this.reales = reales;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cotizacion)) {
            return false;
        }
        Cotizacion other = (Cotizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Beans.Cotizacion[ id=" + id + " ]";
    }
    
}
