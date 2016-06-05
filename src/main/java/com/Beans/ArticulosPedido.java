package com.Beans;

import java.io.Serializable;
import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;
    
    private Integer posicion;
    private Double cantPedida;
    private Double cantAtendida = 0.0;
    private Double cantPendiente;
    private Double importePedido;
    private Double importeAtendido = 0.0;
    private Double importePendiente;

    public ArticulosPedido() {
    }

    public ArticulosPedido(Integer posicion, Articulo articulo, Double cantPedida) {
        this.posicion = posicion;
        this.cantPedida = cantPedida;
        this.articulo = articulo;
    }

    public ArticulosPedido(Integer posicion, Double cantPedida) {
        this.posicion = posicion;
        this.cantPedida = cantPedida;
    }

    public ArticulosPedido(Integer posicion, Articulo articulo, Double cantPedida, Double importePedido) {
        this.posicion = posicion;
        this.articulo = articulo;
        this.cantPedida = cantPedida;
        this.cantPendiente = cantPedida;
        this.importePedido = importePedido;
        this.importePendiente = importePedido;
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

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
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
