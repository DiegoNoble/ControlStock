package com.Beans;

import java.io.Serializable;
import javax.persistence.*;

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
    private Double valorCompraConImpuesto;
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
    private Double iva;

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

 
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

 
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
    public Double getValor_compra_impuesto() {
        return valorCompraConImpuesto;
    }

    /**
     * @param valor_compra_impuesto the valor_compra_impuesto to set
     */
    public void setValor_compra_impuesto(Double valor_compra_impuesto) {
        this.valorCompraConImpuesto = valor_compra_impuesto;
    }

    /**
     * @return the unidad
     */
    public Unidad getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return id;
    }

}
