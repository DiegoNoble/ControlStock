package com.Beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "articulo")

public class Articulo implements Serializable {

    @EmbeddedId
    private ArticuloId articuloId;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor_venta")
    private Double valor_venta;
    @Column(name = "valor_compra")
    private Double valor_compra;
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "categoria")
    private Categoria categoria;
    @ManyToOne(targetEntity = Unidad.class)
    @JoinColumn(name = "unidad")
    private Unidad unidad;
    private Double iva = 21.0;
    @OneToMany(mappedBy = "articulo")
    private List<EquivalenciaUnidades> listEquivalencias;
    private String activo = "Activo";

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vencimiento;

    public Articulo() {
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

    public Double getValor_venta() {
        return valor_venta;
    }

    /**
     * @param valor_venta the valor_venta to set
     */
    public void setValor_venta(Double valor_venta) {
        this.valor_venta = valor_venta;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    @XmlTransient
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
    @XmlTransient
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
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
    @XmlTransient
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
    @XmlTransient
    public Double getValor_compra() {
        return valor_compra;
    }

    /**
     * @param valor_compra_impuesto the valor_compra_impuesto to set
     */
    public void setValor_compra(Double valor_compra_impuesto) {
        this.valor_compra = valor_compra_impuesto;
    }

    /**
     * @return the unidad
     */
    @XmlTransient
    public Unidad getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public ArticuloId getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(ArticuloId articuloId) {
        this.articuloId = articuloId;
    }

    @XmlTransient
    public List<EquivalenciaUnidades> getListEquivalencias() {
        return listEquivalencias;
    }

    public void setListEquivalencias(List<EquivalenciaUnidades> listEquivalencias) {
        this.listEquivalencias = listEquivalencias;
    }

    @Override
    public String toString() {
        if (vencimiento != null) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");
            return articuloId.getId() + " Lote:" + articuloId.getLote() + " Venc. " + formato.format(vencimiento);
        } else {
            return articuloId.getId() + " Lote:" + articuloId.getLote();
        }
    }

}
