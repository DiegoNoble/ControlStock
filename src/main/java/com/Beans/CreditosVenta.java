
package com.Beans;

import com.Beans.Factura;
import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table (name ="creditos") 
public class CreditosVenta implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double valorCredito;
    private Double entrega;
    private int nroCuotas;
    @OneToOne (targetEntity=Factura.class)
    @JoinColumn(name="id_factura")
    private Factura factura;

    public CreditosVenta(){
        
    }
    
    public int getId() {
        return id;
    }

   
    public void setId(int id) {
        this.id = id;
    }

    
    public Double getValorCredito() {
        return valorCredito;
    }

   
    public void setValorCredito(Double valorCredito) {
        this.valorCredito = valorCredito;
    }

   
    public Double getEntrega() {
        return entrega;
    }

   
    public void setEntrega(Double entrega) {
        this.entrega = entrega;
    }

    
    public int getNroCuotas() {
        return nroCuotas;
    }

    
    public void setNroCuotas(int nroCuotas) {
        this.nroCuotas = nroCuotas;
    }

    
    public Factura getFactura() {
        return factura;
    }

    
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    }
