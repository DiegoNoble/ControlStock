package com.Beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "equivalencia_unidades")
public class EquivalenciaUnidades implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ManyToOne()
    private Articulo articulo;
    @ManyToOne()
    private Unidad unidad;
    private Double factor_conversion;
    private Double stock;
    private Double valor_venta_equivalente;
    private Double valor_compra_equivalente;

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

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getValor_venta_equivalente() {
        return valor_venta_equivalente;
    }

    public void setValor_venta_equivalente(Double valor_venta_equivalente) {
        this.valor_venta_equivalente = valor_venta_equivalente;
    }

    public Double getValor_compra_equivalente() {
        return valor_compra_equivalente;
    }

    public void setValor_compra_equivalente(Double valor_compra_equivalente) {
        this.valor_compra_equivalente = valor_compra_equivalente;
    }

    @Override
    public String toString() {
        return unidad.toString();
    }

}
