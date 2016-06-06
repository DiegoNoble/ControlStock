package com.Beans;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "articulos_compra")
public class ArticulosCompra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorConIva")
    private Double valorConIva;

    @Column(name = "cantidad")
    private Double cantidad;

    @ManyToOne(targetEntity = Articulo.class)
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;
    @ManyToOne(targetEntity = FacturaCompra.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "id_factura")
    private FacturaCompra facturaCompra;

    public ArticulosCompra() {
    }

    public ArticulosCompra(Integer id) {
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

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the facturaCompra
     */
    public FacturaCompra getFacturaCompra() {
        return facturaCompra;
    }

    /**
     * @param facturaCompra the facturaCompra to set
     */
    public void setFacturaCompra(FacturaCompra facturaCompra) {
        this.facturaCompra = facturaCompra;
    }

    public Double getValorConIva() {
        return valorConIva;
    }

    /**
     * @param valorConIva the valorConIva to set
     */
    public void setValorConIva(Double valorConIva) {
        this.valorConIva = valorConIva;
    }

}
