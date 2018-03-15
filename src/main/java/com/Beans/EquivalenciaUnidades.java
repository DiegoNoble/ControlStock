package com.Beans;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "equivalencia_unidades")
@XmlRootElement(name = "equivalenciaUnidades")
public class EquivalenciaUnidades implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /*@ManyToOne()
    private Articulo articulo;
     */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "articulo_id", referencedColumnName = "id")
        ,
			@JoinColumn(name = "lote_articulo", referencedColumnName = "lote"),})
    private Articulo articulo;

    @ManyToOne()
    private Unidad unidad;
    private Double factor_conversion;

    public EquivalenciaUnidades() {
    }

    public EquivalenciaUnidades(Articulo articulo) {
        this.articulo = articulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlTransient
    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Double getFactor_conversion() {
        return factor_conversion;
    }

    public void setFactor_conversion(Double factor_conversion) {
        this.factor_conversion = factor_conversion;
    }

    @Override
    public String toString() {
        return unidad.toString();
    }

}
