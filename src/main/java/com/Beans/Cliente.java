/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "cliente")

public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer Id_cliente;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "pais")
    private String pais;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "cel")
    private String cel;
    @Column(name = "email")
    private String email;
    @Column(name = "razon_social")
    private String razon_social;
    @Column(name = "documento")
    private String documento;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date Fecha_ingreso;
    @Enumerated(EnumType.STRING)
    private CondicionImpositiva condicionImpositiva;

    public Cliente() {
    }

    /**
     * @return the Id_cliente
     */
    public Integer getId_cliente() {
        return Id_cliente;
    }

    /**
     * @param Id_cliente the Id_cliente to set
     */
    public void setId_cliente(Integer Id_cliente) {
        this.Id_cliente = Id_cliente;
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

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the cel
     */
    public String getCel() {
        return cel;
    }

    /**
     * @param cel the cel to set
     */
    public void setCel(String cel) {
        this.cel = cel;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getRazon_social() {
        return razon_social;
    }

    /**
     * @param Razon_social the Razon_social to set
     */
    public void setRazon_social(String Razon_social) {
        this.razon_social = Razon_social;
    }

    /**
     * @return the rut
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param rut the rut to set
     */
    public void setDocumento(String rut) {
        this.documento = rut;
    }

    /**
     * @return the Fecha_ingreso
     */
    public Date getFecha_ingreso() {
        return Fecha_ingreso;
    }

    /**
     * @param Fecha_ingreso the Fecha_ingreso to set
     */
    public void setFecha_ingreso(Date Fecha_ingreso) {
        this.Fecha_ingreso = Fecha_ingreso;
    }

    public String toString() {
        return nombre;

    }

    public CondicionImpositiva getCondicionImpositiva() {
        return condicionImpositiva;
    }

    public void setCondicionImpositiva(CondicionImpositiva condicionImpositiva) {
        this.condicionImpositiva = condicionImpositiva;
    }

}
