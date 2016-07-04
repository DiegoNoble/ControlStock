package com.Beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "reparto")
public class Reparto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transportista")
    private Transportista transportista;

    @OneToMany(mappedBy = "reparto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Remito> remitos;

    @Enumerated(EnumType.STRING)
    private SituacionReparto situacionReparto;

    private String observaciones;
    private Double porcentageComision;
    private Double comision;
    private Double totalRepartoSinIVA;

    public Reparto() {

    }

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

    public Transportista getTransportista() {
        return transportista;
    }

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public List<Remito> getRemitos() {
        return remitos;
    }

    public void setRemitos(List<Remito> remitos) {
        this.remitos = remitos;
    }

    public SituacionReparto getSituacionReparto() {
        return situacionReparto;
    }

    public void setSituacionReparto(SituacionReparto situacionReparto) {
        this.situacionReparto = situacionReparto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getPorcentageComision() {
        return porcentageComision;
    }

    public void setPorcentageComision(Double porcentageComision) {
        this.porcentageComision = porcentageComision;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Double getTotalRepartoSinIVA() {
        return totalRepartoSinIVA;
    }

    public void setTotalRepartoSinIVA(Double totalRepartoSinIVA) {
        this.totalRepartoSinIVA = totalRepartoSinIVA;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
