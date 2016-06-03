
package com.Beans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

@Entity
@Table (name ="compras_pagos") 
 
public class CompraPago implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne (targetEntity=FacturaCompra.class)
    @JoinColumn(name="id_factura")
    private FacturaCompra factura;
    @ManyToOne (targetEntity=PagoCompra.class)
    @JoinColumn(name="id_pago")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private PagoCompra pago;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FacturaCompra getFactura() {
        return factura;
    }

    public void setFactura(FacturaCompra factura) {
        this.factura = factura;
    }

    public PagoCompra getPago() {
        return pago;
    }

    public void setPago(PagoCompra pago) {
        this.pago = pago;
    }
}
