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
    @ManyToOne(targetEntity = Articulos.class)
    @JoinColumn(name = "id_articulo")
    private Articulos articulo;

    @ManyToOne(targetEntity = Pedido.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    private Double cantPedida;
    private Double cantAtendida;
    private Double cantPendiente;

    public ArticulosPedido() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
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

}
