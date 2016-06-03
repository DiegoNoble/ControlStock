
package com.Beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "articulo")
public class Articulos implements Serializable {
    
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_compra")
    private Double valorCompraSinImpuesto;
    @Column(name = "ganancia")
    private Double ganancia;
    @Column(name = "valor_venta")
    private Double valor_venta;
    @Column(name = "descuento")
    private Double descuento;
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "ganancia_descuento")
    private Double ganancia_descuento;
    @ManyToOne (targetEntity=Categoria.class)
    @JoinColumn(name="categoria")
    private Categoria categoria;
    @ManyToOne (targetEntity=Unidad.class)
    @JoinColumn(name="unidad")
    private Unidad unidad;
    private Double iva;
    @Column(name = "valor_compra_impuesto")
    private Double valorCompraConImpuesto;
    

    public Articulos() {
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

    /**
     * @return the valor_compra
     */
    public Double getValor_compra() {
        return valorCompraSinImpuesto;
    }

    /**
     * @param valor_compra the valor_compra to set
     */
    public void setValor_compra(Double valor_compra) {
        this.valorCompraSinImpuesto = valor_compra;
    }

    /**
     * @return the ganancia
     */
    public Double getGanancia() {
        return ganancia;
    }

    /**
     * @param ganancia the ganancia to set
     */
    public void setGanancia(Double ganancia) {
        this.ganancia = ganancia;
    }

    /**
     * @return the valor_venta
     */
    public Double getValor_venta() {
        return valor_venta;
    }

    /**
     * @param valor_venta the valor_venta to set
     */
    public void setValor_venta(Double valor_venta) {
        this.valor_venta = valor_venta;
    }

    /**
     * @return the descuento
     */
    public Double getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the cantidad
     */
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

    /**
     * @return the ganancia_descuento
     */
    public Double getGanancia_descuento() {
        return ganancia_descuento;
    }

    /**
     * @param ganancia_descuento the ganancia_descuento to set
     */
    public void setGanancia_descuento(Double ganancia_descuento) {
        this.ganancia_descuento = ganancia_descuento;
    }

    /**
     * @return the categoria
     */
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
    
}
