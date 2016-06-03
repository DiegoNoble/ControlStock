
package com.Beans;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "articulos_venta")
public class ArticulosVenta implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name ="cantidad")
    private Double cantidad;
    @ManyToOne (targetEntity=Articulo.class)
    @JoinColumn(name="id_articulo")
    private Articulo articulo;
    @ManyToOne (targetEntity=Factura.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name="id_factura")
    private Factura factura;
    @Column(name ="descuento")
    private String descuento;
    @Column(name ="valorSinIva")
    private Double valorSinIva;
    @Column(name ="valorConIva")
    private Double valorConIva;

    public Double getValorSinIva() {
        return valorSinIva;
    }

    public void setValorSinIva(Double valorSinIva) {
        this.valorSinIva = valorSinIva;
    }

    public Double getValorConIva() {
        return valorConIva;
    }

    public void setValorConIva(Double valorConIva) {
        this.valorConIva = valorConIva;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }
    
    public ArticulosVenta() {
    }

    public ArticulosVenta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    /**
     * @param articulo the articulo to set
     */
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    /**
     * @return the factura
     */
    public Factura getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(Factura factura) {
        this.factura = factura;
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

     
    
}
