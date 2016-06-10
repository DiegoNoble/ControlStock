package com.Beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Double importeAtendido = 0.0;
    private Double importeTotal;
    private Double importePendiente = importeTotal;

    @ManyToOne(targetEntity = Cliente.class)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne(targetEntity = Vendedor.class)
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ArticulosPedido> articulosPedido;

    @Enumerated(EnumType.STRING)
    private SituacionPedido estadoPedido;

    private String observaciones;

    public Pedido() {

    }

    public Pedido(Date fecha, Cliente cliente, Vendedor vendedor, SituacionPedido estadoPedido, List<ArticulosPedido> articulosPedido) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.estadoPedido = estadoPedido;
        this.articulosPedido = articulosPedido;
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

    public Double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public SituacionPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(SituacionPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public List<ArticulosPedido> getArticulosPedido() {
        return articulosPedido;
    }

    public void setArticulosPedido(List<ArticulosPedido> articulosPedido) {
        this.articulosPedido = articulosPedido;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
