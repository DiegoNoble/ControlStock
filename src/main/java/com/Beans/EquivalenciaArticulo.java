package com.Beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "equivalencia_articulo")
public class EquivalenciaArticulo implements Serializable {

    @Id
    private String id;
    @ManyToOne()
    private Articulo articulo;
    @ManyToOne()
    private Unidad unidad;

    private Double factor_conversion;
    private Double stock;
    private Double valor_venta;
    private Double valor_compra;

    public EquivalenciaArticulo() {
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

    public Double getValor_venta() {
        return valor_venta;
    }

    public void setValor_venta(Double valor_venta) {
        this.valor_venta = valor_venta;
    }

    public Double getValor_compra() {
        return valor_compra;
    }

    public void setValor_compra(Double valor_compra) {
        this.valor_compra = valor_compra;
    }

}
