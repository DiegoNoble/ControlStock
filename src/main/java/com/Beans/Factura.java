
package com.Beans;

import com.Beans.MonedaEnum;
import com.Beans.TipoDocumentoEnum;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "factura")
public class Factura implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfactura")
    private Integer idfactura;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Total")
    private Double total;
    @ManyToOne (targetEntity=Cliente.class)
    @JoinColumn(name="id_cliente")
    private Cliente cliente;
    @Enumerated(EnumType.STRING)
    @Column(name="tipo")
    private TipoDocumentoEnum tipo;
    @Column(name="moneda")
    @Enumerated(EnumType.STRING)
    private MonedaEnum moneda;
    @Column(name ="nro")
    private Integer NroDocumento;

    
      public Factura() {
    }

    public Factura(Integer idfactura) {
        this.idfactura = idfactura;
    }
   
        
    public Integer getNroDocumento() {
        return NroDocumento;
    }

    public void setNroDocumento(Integer NroDocumento) {
        this.NroDocumento = NroDocumento;
    }
       
    public MonedaEnum getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedaEnum moneda) {
        this.moneda = moneda;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

   
    public Cliente getCliente() {
        return cliente;
    }

    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoDocumentoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumentoEnum tipo) {
        this.tipo = tipo;
    }
    
}
