/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "cliente")
@XmlRootElement(name = "cliente")
//@XmlType(propOrder = {"Id_cliente", "razon_social"})
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
    @ManyToOne()
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;
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
    private Boolean activo = true;

    public Cliente() {
    }

    public Cliente(Integer Id_cliente, String nombre, Boolean activo) {
        this.Id_cliente = Id_cliente;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Cliente(Integer Id_cliente, String nombre, String documento, CondicionImpositiva condicionImpositiva, String direccion, Boolean activo) {
        this.Id_cliente = Id_cliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.documento = documento;
        this.condicionImpositiva = condicionImpositiva;
        this.activo = activo;
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
    @XmlTransient
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the telefono
     */
    @XmlTransient
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
    @XmlTransient
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
    @XmlTransient
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
    @XmlTransient
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public CondicionImpositiva getCondicionImpositiva() {
        return condicionImpositiva;
    }

    public void setCondicionImpositiva(CondicionImpositiva condicionImpositiva) {
        this.condicionImpositiva = condicionImpositiva;
    }

}
