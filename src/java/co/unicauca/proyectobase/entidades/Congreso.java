package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa al congreso
 * @author 
 */
@Entity
@Table(name = "congreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Congreso.findAll", query = "SELECT c FROM Congreso c")
    , @NamedQuery(name = "Congreso.findByPubIdentificador", query = "SELECT c FROM Congreso c WHERE c.pubIdentificador = :pubIdentificador")
    , @NamedQuery(name = "Congreso.findByCongTituloPonencia", query = "SELECT c FROM Congreso c WHERE c.congTituloPonencia = :congTituloPonencia")
    , @NamedQuery(name = "Congreso.findByCongNombreEvento", query = "SELECT c FROM Congreso c WHERE c.congNombreEvento = :congNombreEvento")
    , @NamedQuery(name = "Congreso.findByCongTipoCongreso", query = "SELECT c FROM Congreso c WHERE c.congTipoCongreso = :congTipoCongreso")})
public class Congreso implements Serializable {

    // Versión de la base de datos
    private static final long serialVersionUID = 1L;
    // Clave identificadora de la publicación
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pub_identificador")
    private Integer pubIdentificador;
    // Fecha de inicio del congreso
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    // Fecha de finalización del congreso
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    // Doi del congreso
    @Basic(optional = true)
    @Size(max = 30)
    @Column(name = "cong_doi")
    private String congDoi;
    // ISSN del congreso
    @Basic(optional = true)    
    @Size(max = 30)
    @Column(name = "cong_issn")
    private String congIssn;
    // Título ponencia del congreso
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "cong_titulo_ponencia")
    private String congTituloPonencia;
    // Nombre del congreso
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cong_nombre_evento")
    private String congNombreEvento;
    // Tipo del congreso
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "cong_tipo_congreso")
    private String congTipoCongreso;
    // Publicación asociada al congreso
    @JoinColumn(name = "pub_identificador", referencedColumnName = "pub_identificador", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Publicacion publicacion;
    // Ciudad asociada al congreso
    @JoinColumn(name = "ciudad_id", referencedColumnName = "ciud_id")
    @ManyToOne
    private Ciudad ciudadId;

    /* Constructores */
    public Congreso() {
    }

    public Congreso(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public Congreso(Integer pubIdentificador, String congTituloPonencia, String congNombreEvento, String congTipoCongreso) {
        this.pubIdentificador = pubIdentificador;
        this.congTituloPonencia = congTituloPonencia;
        this.congNombreEvento = congNombreEvento;
        this.congTipoCongreso = congTipoCongreso;
    }

    /*Setters y Getters */
    public Integer getPubIdentificador() {
        return pubIdentificador;
    }

    public void setPubIdentificador(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public String getCongTituloPonencia() {
        return congTituloPonencia;
    }

    public void setCongTituloPonencia(String congTituloPonencia) {
        this.congTituloPonencia = congTituloPonencia;
    }

    public String getCongNombreEvento() {
        return congNombreEvento;
    }

    public void setCongNombreEvento(String congNombreEvento) {
        this.congNombreEvento = congNombreEvento;
    }

    public String getCongTipoCongreso() {
        return congTipoCongreso;
    }

    public void setCongTipoCongreso(String congTipoCongreso) {
        this.congTipoCongreso = congTipoCongreso;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
    
    public String getCongDoi() {
        return congDoi;
    }

    public void setCongDoi(String congDoi) {
        this.congDoi = congDoi;
    }

    public String getCongIssn() {
        return congIssn;
    }

    public void setCongIssn(String congIssn) {
        this.congIssn = congIssn;
    }

    public Ciudad getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Ciudad ciudadId) {
        this.ciudadId = ciudadId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pubIdentificador != null ? pubIdentificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Congreso)) {
            return false;
        }
        Congreso other = (Congreso) object;
        if ((this.pubIdentificador == null && other.pubIdentificador != null) || (this.pubIdentificador != null && !this.pubIdentificador.equals(other.pubIdentificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.Congreso[ pubIdentificador=" + pubIdentificador + " ]";
    }    
}
