package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que representa la actividad de práctica docente
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@Entity
@Table(name = "actividad_pd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadPd.findAll", query = "SELECT a FROM ActividadPd a"),
    @NamedQuery(name = "ActividadPd.findByIdActividad", query = "SELECT a FROM ActividadPd a WHERE a.idActividad = :idActividad"),
    @NamedQuery(name = "ActividadPd.findByNombreActividad", query = "SELECT a FROM ActividadPd a WHERE a.nombreActividad = :nombreActividad"),
    @NamedQuery(name = "ActividadPd.findByHorasAsignadas", query = "SELECT a FROM ActividadPd a WHERE a.horasAsignadas = :horasAsignadas"),
    @NamedQuery(name = "ActividadPd.findByFaseAjuste", query = "SELECT a FROM ActividadPd a WHERE a.faseAjuste = :faseAjuste"),
    @NamedQuery(name = "ActividadPd.findByMaximoHoras", query = "SELECT a FROM ActividadPd a WHERE a.maximoHoras = :maximoHoras")})
public class ActividadPd implements Serializable {

    // Versión de la base de datos
    private static final long serialVersionUID = 1L;
    // Clave identificadora de la actividad de práctica docente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad")
    private Integer idActividad;
    // Nombre de la actividad de práctica docente
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_actividad")
    private String nombreActividad;
    // Horas asignadas a la actividad de práctica docente
    @Column(name = "horas_asignadas")
    private Integer horasAsignadas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // fase de ajuste de la actividad de práctica docente
    @Column(name = "fase_ajuste")
    private Float faseAjuste;
    // Máximo de horas para la actividad de práctica docente
    @Column(name = "maximoHoras")
    private Integer maximoHoras;
    //Actividades asociadas a la práctica docente
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActividad")
    private List<PracticaDocente> practicaDocenteList;

    //constructores
    public ActividadPd() {
    }

    public ActividadPd(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public ActividadPd(Integer idActividad, String nombreActividad) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
    }

    //Getters y Setters
    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public Integer getHorasAsignadas() {
        return horasAsignadas;
    }

    public void setHorasAsignadas(Integer horasAsignadas) {
        this.horasAsignadas = horasAsignadas;
    }

    public Float getFaseAjuste() {
        return faseAjuste;
    }

    public void setFaseAjuste(Float faseAjuste) {
        this.faseAjuste = faseAjuste;
    }

    public Integer getMaximoHoras() {
        return maximoHoras;
    }

    public void setMaximoHoras(Integer maximoHoras) {
        this.maximoHoras = maximoHoras;
    }

    @XmlTransient
    public List<PracticaDocente> getPracticaDocenteList() {
        return practicaDocenteList;
    }

    public void setPracticaDocenteList(List<PracticaDocente> practicaDocenteList) {
        this.practicaDocenteList = practicaDocenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadPd)) {
            return false;
        }
        ActividadPd other = (ActividadPd) object;
        if ((this.idActividad == null && other.idActividad != null) || (this.idActividad != null && !this.idActividad.equals(other.idActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombreActividad;
    }

}
