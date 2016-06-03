
package com.Beans;

import com.Beans.EstadoEnum;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.*;


  
@Entity
@Table (name ="pagos") 
public class PagoCreditosVenta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar fecha;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha_pago;
    private Double valor;
    @ManyToOne (targetEntity=CreditosVenta.class)
    @JoinColumn(name="id_credito")
    private CreditosVenta credito;
    private int nro_cuota;
    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private EstadoEnum estado;
    

    public PagoCreditosVenta(){
        
    }
    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

    
    public Double getValor() {
        return valor;
    }

    
    public void setValor(Double valor) {
        this.valor = valor;
    }

   
    public CreditosVenta getCredito() {
        return credito;
    }

    
    public void setCredito(CreditosVenta credito) {
        this.credito = credito;
    }

   
    public Calendar getFecha() {
        return fecha;
    }

    
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    
    public int getNro_cuota() {
        return nro_cuota;
    }

    
    public void setNro_cuota(int nro_cuota) {
        this.nro_cuota = nro_cuota;
    }

   
    public Date getFecha_pago() {
        return fecha_pago;
    }

  
    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

          
    
}
