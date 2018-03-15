package com.Beans;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "articulos_compra")
public class ArticulosCompra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Integer posicion;
    @Column(name = "valor")
    private Double valor;

    @Column(name = "cantidad")
    private Double cantidad;

    /*@ManyToOne(targetEntity = Articulo.class)
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;
    
    @ManyToOne(targetEntity = Articulo.class)
    @JoinColumn(name = "lote_articulo")
    private Articulo lote_articulo;
    */
    @ManyToOne
	@JoinColumns({
			@JoinColumn(name = "id_articulo", referencedColumnName = "id"),
			@JoinColumn(name = "lote_articulo", referencedColumnName = "lote"),
			})
	private Articulo articulo;
    
    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_factura_compra")
    private FacturaCompra facturaCompra;

    @ManyToOne
    private EquivalenciaUnidades equivalenciaUnidades;

    public ArticulosCompra() {
    }

    public ArticulosCompra(Integer posicion, Double valor, Double cantidad, Articulo articulo, EquivalenciaUnidades equivalenciaUnidades) {
        this.posicion = posicion;
        this.valor = valor;
        this.cantidad = cantidad;
        this.articulo = articulo;
        this.equivalenciaUnidades = equivalenciaUnidades;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
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

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public EquivalenciaUnidades getEquivalenciaUnidades() {
        return equivalenciaUnidades;
    }

    public void setEquivalenciaUnidades(EquivalenciaUnidades equivalenciaUnidades) {
        this.equivalenciaUnidades = equivalenciaUnidades;
    }

}
