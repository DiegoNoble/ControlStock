package com.Beans;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "articulo")
public class Articulo implements Serializable {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor_venta")
    private Double valor_venta;
    @Column(name = "valor_compra")
    private Double valor_compra;
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "categoria")
    private Categoria categoria;
    @ManyToOne(targetEntity = Unidad.class)
    @JoinColumn(name = "unidad")
    private Unidad unidad;
    private Double iva = 21.0;

    @ManyToOne()
    @JoinColumn(name = "unidad_mayor_id")
    private Unidad unidad_mayor;
    private Double factor_conversion;
    private Double stock_mayor_unidad;

    public Articulo() {
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getValor_venta() {
        return valor_venta;
    }

    /**
     * @param valor_venta the valor_venta to set
     */
    public void setValor_venta(Double valor_venta) {
        this.valor_venta = valor_venta;
    }

    @XmlTransient
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
        this.stock_mayor_unidad = cantidad / factor_conversion;
    }

    /**
     * @return the descripcion
     */
    @XmlTransient
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the iva
     */
    @XmlTransient
    public Double getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(Double iva) {
        this.iva = iva;
    }

    /**
     * @return the valor_compra_impuesto
     */
    @XmlTransient
    public Double getValor_compra() {
        return valor_compra;
    }

    /**
     * @param valor_compra_impuesto the valor_compra_impuesto to set
     */
    public void setValor_compra(Double valor_compra_impuesto) {
        this.valor_compra = valor_compra_impuesto;
    }

    /**
     * @return the unidad
     */
    @XmlTransient
    public Unidad getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Unidad getUnidad_mayor() {
        return unidad_mayor;
    }

    public void setUnidad_mayor(Unidad unidad_mayor_id) {
        this.unidad_mayor = unidad_mayor_id;
    }

    public Double getStock_mayor_unidad() {
        return stock_mayor_unidad;
    }

    public void setStock_mayor_unidad(Double stock_mayor_unidad) {
        this.stock_mayor_unidad = stock_mayor_unidad;

    }

    public Double getFactor_conversion() {
        return factor_conversion;
    }

    public void setFactor_conversion(Double factor_conversion) {
        this.factor_conversion = factor_conversion;
    }

    @Override
    public String toString() {
        return id;
    }

}
