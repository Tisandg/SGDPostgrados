package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 *
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@Entity
@Table(name = "practica_docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PracticaDocente.findAll", query = "SELECT p FROM PracticaDocente p"),
    @NamedQuery(name = "PracticaDocente.misPracticas", query = "SELECT p FROM PracticaDocente p "),    
    
    @NamedQuery(name = "PracticaDocente.buscarPracticasByNombreUsuario", query = "select pr from PracticaDocente pr where pr.pubIdentificador IN (SELECt p.pubIdentificador FROM Publicacion p where P.pubEstIdentificador = (select e.estIdentificador from Estudiante e where e.estUsuario = :nombreUsuario))"),
        
    @NamedQuery(name = "PracticaDocente.findByPubIdentificador", query = "SELECT p FROM PracticaDocente p WHERE p.pubIdentificador = :pubIdentificador"),
    @NamedQuery(name = "PracticaDocente.findByFechaInicio", query = "SELECT p FROM PracticaDocente p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "PracticaDocente.findByFechaTerminacion", query = "SELECT p FROM PracticaDocente p WHERE p.fechaTerminacion = :fechaTerminacion"),
    @NamedQuery(name = "PracticaDocente.findByLugarPractica", query = "SELECT p FROM PracticaDocente p WHERE p.lugarPractica = :lugarPractica"),
        
    @NamedQuery(name = "PracticaDocente.findByLugarPracticaLike", query = "SELECT p FROM PracticaDocente p WHERE p.lugarPractica like :lugarPractica"),        
    
    @NamedQuery(name = "PracticaDocente.findIdTipoDocumento", query = "SELECT td FROM TipoDocumento td WHERE td.nombre like 'Practica docente'")
                         
})
public class PracticaDocente implements Serializable {
//@NamedQuery(name = "PracticaDocente.findByNombreAutor", query = "SELECT p FROM PracticaDocente p WHERE p.lugarPractica like :lugarPractica"),
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pub_identificador")
    private Integer pubIdentificador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_terminacion")
    @Temporal(TemporalType.DATE)
    private Date fechaTerminacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "lugar_practica")
    private String lugarPractica;
    @JoinColumn(name = "pub_identificador", referencedColumnName = "pub_identificador", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Publicacion publicacion;

    public PracticaDocente() {
    }

    public PracticaDocente(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public PracticaDocente(Integer pubIdentificador, Date fechaInicio, Date fechaTerminacion, String lugarPractica) {
        this.pubIdentificador = pubIdentificador;
        this.fechaInicio = fechaInicio;
        this.fechaTerminacion = fechaTerminacion;
        this.lugarPractica = lugarPractica;
    }

    public Integer getPubIdentificador() {
        return pubIdentificador;
    }

    public void setPubIdentificador(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }
    
     public String getFechaIn() {        
        return new SimpleDateFormat("dd-MMMM-yyyy").format(fechaInicio);
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }
    
     public String getFechaTer() {
        return new SimpleDateFormat("dd-MMMM-yyyy").format(fechaTerminacion);
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public String getLugarPractica() {
        return lugarPractica;
    }

    public void setLugarPractica(String lugarPractica) {
        this.lugarPractica = lugarPractica;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
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
        if (!(object instanceof PracticaDocente)) {
            return false;
        }
        PracticaDocente other = (PracticaDocente) object;
        if ((this.pubIdentificador == null && other.pubIdentificador != null) || (this.pubIdentificador != null && !this.pubIdentificador.equals(other.pubIdentificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.PracticaDocente[ pubIdentificador=" + pubIdentificador + " ]";
    }

}

