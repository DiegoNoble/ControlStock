package com.Beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "factura_compra")
public class FacturaCompra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "Total")
    private Double total;
    private Double subtotal;
    private Double iva;

    @ManyToOne(targetEntity = Proveedor.class)
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;
    @Enumerated(EnumType.STRING)

    @Column(name = "nroDocumento")
    private String nroDocumento;

    @OneToMany(mappedBy = "facturaCompra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ArticulosCompra> articulosCompra;

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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public List<ArticulosCompra> getArticulosCompra() {
        return articulosCompra;
    }

    public void setArticulosCompra(List<ArticulosCompra> articulosCompra) {
        this.articulosCompra = articulosCompra;
    }

    @Override
    public String toString() {
        return "Documento: " + nroDocumento + ", Proveedor " + proveedor + "";
    }

}
