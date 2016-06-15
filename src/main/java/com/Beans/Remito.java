package com.Beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "remito")
public class Remito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Double importeRemito;
    private Double descuento =0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    @Enumerated(EnumType.STRING)
    private TipoRemito tipoRemito;
    private Boolean anulado = false;

    public Remito() {
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

    public Double getImporteAtendido() {
        return importeRemito;
    }

    public void setImporteRemito(Double importeAtendido) {
        this.importeRemito = importeAtendido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public TipoRemito getTipoRemito() {
        return tipoRemito;
    }

    public void setTipoRemito(TipoRemito tipoRemito) {
        this.tipoRemito = tipoRemito;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
    

}
