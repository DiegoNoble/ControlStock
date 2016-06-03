package com.Beans;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "articulos_pedido")
public class ArticulosPedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(targetEntity = Articulo.class)
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;

    @ManyToOne(targetEntity = Pedido.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    private Integer posicion;
    private Double cantPedida;
    private Double cantAtendida;
    private Double cantPendiente;
    private Double importePedido;
    private Double importeAtendido;
    private Double importePendiente;

    public ArticulosPedido() {
    }

    public ArticulosPedido(Articulo articulo, Double cantPedida, Double importePedido) {
        this.articulo = articulo;
        this.cantPedida = cantPedida;
        this.importePedido = importePedido;
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Double getCantPedida() {
        return cantPedida;
    }

    public void setCantPedida(Double cantPedida) {
        this.cantPedida = cantPedida;
    }

    public Double getCantAtendida() {
        return cantAtendida;
    }

    public void setCantAtendida(Double cantAtendida) {
        this.cantAtendida = cantAtendida;
    }

    public Double getCantPendiente() {
        return cantPendiente;
    }

    public void setCantPendiente(Double cantPendiente) {
        this.cantPendiente = cantPendiente;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Double getImportePedido() {
        return importePedido;
    }

    public void setImportePedido(Double importePedido) {
        this.importePedido = importePedido;
    }

    public Double getImporteAtendido() {
        return importeAtendido;
    }

    public void setImporteAtendido(Double importeAtendido) {
        this.importeAtendido = importeAtendido;
    }

    public Double getImportePendiente() {
        return importePendiente;
    }

    public void setImportePendiente(Double importePendiente) {
        this.importePendiente = importePendiente;
    }

}
