package com.Beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "mov_stock")
public class MovStock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Double cantidadMov;
    private Double saldoStock;

    @ManyToOne(targetEntity = Remito.class)
    @JoinColumn(name = "id_remito")
    private Remito remito;

    @ManyToOne(targetEntity = Articulo.class)
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;

    @ManyToOne(targetEntity = FacturaCompra.class)
    @JoinColumn(name = "id_factura_compra")
    private FacturaCompra facturaCompra;

    public MovStock() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getCantidadMov() {
        return cantidadMov;
    }

    public void setCantidadMov(Double cantidadMov) {
        this.cantidadMov = cantidadMov;
    }

    public Double getSaldoStock() {
        return saldoStock;
    }

    public void setSaldoStock(Double saldoStock) {
        this.saldoStock = saldoStock;
    }

    public Remito getRemito() {
        return remito;
    }

    public void setRemito(Remito remito) {
        this.remito = remito;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public FacturaCompra getFacturaCompra() {
        return facturaCompra;
    }

    public void setFacturaCompra(FacturaCompra facturaCompra) {
        this.facturaCompra = facturaCompra;
    }

}
