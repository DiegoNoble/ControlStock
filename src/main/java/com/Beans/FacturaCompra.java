
package com.Beans;

import com.Beans.EstadoEnum;
import com.Beans.MonedaEnum;
import com.Beans.TipoDocumentoEnum;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "factura_compra")
public class FacturaCompra implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfactura")
    private Integer idfactura;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "fechaVencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Column(name = "Total")
    private Double total;
    @ManyToOne (targetEntity=Proveedor.class)
    @JoinColumn(name="id_proveedor")
    private Proveedor proveedor;
    @Enumerated(EnumType.STRING)
    @Column(name="tipo")
    private TipoDocumentoEnum tipo;
    @Column(name ="nro")
    private Integer nroFactura;
    @Column(name ="serie")
    private String serieFactura;
    @Column(name="moneda")
    @Enumerated(EnumType.STRING)
    private MonedaEnum moneda;
    @Column(name="estado")
    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public MonedaEnum getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedaEnum moneda) {
        this.moneda = moneda;
    }
    
    public Integer getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(Integer nroFactura) {
        this.nroFactura = nroFactura;
    }

    public String getSerieFactura() {
        return serieFactura;
    }

    public void setSerieFactura(String serieFactura) {
        this.serieFactura = serieFactura;
    }
    public FacturaCompra() {
    }

    public FacturaCompra(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public Integer getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * @return the proveedor
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the tipo
     */
    public TipoDocumentoEnum getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoDocumentoEnum tipo) {
        this.tipo = tipo;
    }

   
}
