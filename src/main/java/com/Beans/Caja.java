
package com.Beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "caja")

public class Caja implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String concepto;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    @ManyToOne (targetEntity=Rubros.class)
    @JoinColumn(name="id_rubro")
    
    private Rubros rubro;
    private double entrada_reales;
    private double salida_reales;
    private double entrada_dolares;
    private double salida_dolares;
    private double entrada_pesos;
    private double salida_pesos;
    
    public Caja (){
        
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the rubro
     */
    public Rubros getRubro() {
        return rubro;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(Rubros rubro) {
        this.rubro = rubro;
    }

    /**
     * @return the entrada_reales
     */
    public double getEntrada_reales() {
        return entrada_reales;
    }

    /**
     * @param entrada_reales the entrada_reales to set
     */
    public void setEntrada_reales(double entrada_reales) {
        this.entrada_reales = entrada_reales;
    }

    /**
     * @return the salida_reales
     */
    public double getSalida_reales() {
        return salida_reales;
    }

    /**
     * @param salida_reales the salida_reales to set
     */
    public void setSalida_reales(double salida_reales) {
        this.salida_reales = salida_reales;
    }

    /**
     * @return the entrada_dolares
     */
    public double getEntrada_dolares() {
        return entrada_dolares;
    }

    /**
     * @param entrada_dolares the entrada_dolares to set
     */
    public void setEntrada_dolares(double entrada_dolares) {
        this.entrada_dolares = entrada_dolares;
    }

    /**
     * @return the salida_dolares
     */
    public double getSalida_dolares() {
        return salida_dolares;
    }

    /**
     * @param salida_dolares the salida_dolares to set
     */
    public void setSalida_dolares(double salida_dolares) {
        this.salida_dolares = salida_dolares;
    }

    /**
     * @return the entrada_pesos
     */
    public double getEntrada_pesos() {
        return entrada_pesos;
    }

    /**
     * @param entrada_pesos the entrada_pesos to set
     */
    public void setEntrada_pesos(double entrada_pesos) {
        this.entrada_pesos = entrada_pesos;
    }

    /**
     * @return the salida_pesos
     */
    public double getSalida_pesos() {
        return salida_pesos;
    }

    /**
     * @param salida_pesos the salida_pesos to set
     */
    public void setSalida_pesos(double salida_pesos) {
        this.salida_pesos = salida_pesos;
    }
}
