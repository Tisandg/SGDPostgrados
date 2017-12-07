package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que representa el tipo de documento
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@Entity
@Table(name = "tipo_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDocumento.findAll", query = "SELECT t FROM TipoDocumento t"),
    @NamedQuery(name = "TipoDocumento.findByIdentificador", query = "SELECT t FROM TipoDocumento t WHERE t.identificador = :identificador"),
    @NamedQuery(name = "TipoDocumento.findByNombre", query = "SELECT t FROM TipoDocumento t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoDocumento.findByCreditos", query = "SELECT t FROM TipoDocumento t WHERE t.creditos = :creditos"),
    @NamedQuery(name = "TipoDocumento.findByMaxCreditos", query = "SELECT t FROM TipoDocumento t WHERE t.maxCreditos = :maxCreditos"),
    @NamedQuery(name = "TipoDocumento.findByMinCreditos", query = "SELECT t FROM TipoDocumento t WHERE t.minCreditos = :minCreditos")})
public class TipoDocumento implements Serializable {

    @OneToMany(mappedBy = "idTipoDocumento")
    private List<Publicacion> publicacionList;

    // Versión de la base de datos
    private static final long serialVersionUID = 1L;
    // Clave identificadora del tipo de documento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "identificador")
    private Integer identificador;
    // Nombre del tipo de documento
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    // Créditos del tipo de documento
    @Basic(optional = false)
    @NotNull
    @Column(name = "creditos")
    private int creditos;
    // Número máximo de créditos del tipo de documento
    @Column(name = "max_creditos")
    private Integer maxCreditos;
    // Número mínimo de créditos del tipo de documento
    @Column(name = "min_creditos")
    private Integer minCreditos;
    //Correquisitos asociados al tipo de documento
    @OneToMany(mappedBy = "correquisitos")
    private List<TipoDocumento> tipoDocumentoList;
    @JoinColumn(name = "correquisitos", referencedColumnName = "identificador")
    @ManyToOne
    private TipoDocumento correquisitos;

    /* Constructores */
    public TipoDocumento() {
    }

    public TipoDocumento(Integer identificador) {
        this.identificador = identificador;
    }

    public TipoDocumento(Integer identificador, String nombre, int creditos) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    /* Getters y Setters */
    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public Integer getMaxCreditos() {
        return maxCreditos;
    }

    public void setMaxCreditos(Integer maxCreditos) {
        this.maxCreditos = maxCreditos;
    }

    public Integer getMinCreditos() {
        return minCreditos;
    }

    public void setMinCreditos(Integer minCreditos) {
        this.minCreditos = minCreditos;
    }

    @XmlTransient
    public List<TipoDocumento> getTipoDocumentoList() {
        return tipoDocumentoList;
    }

    public void setTipoDocumentoList(List<TipoDocumento> tipoDocumentoList) {
        this.tipoDocumentoList = tipoDocumentoList;
    }

    public TipoDocumento getCorrequisitos() {
        return correquisitos;
    }

    public void setCorrequisitos(TipoDocumento correquisitos) {
        this.correquisitos = correquisitos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identificador != null ? identificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDocumento)) {
            return false;
        }
        TipoDocumento other = (TipoDocumento) object;
        if ((this.identificador == null && other.identificador != null) || (this.identificador != null && !this.identificador.equals(other.identificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.TipoDocumento[ identificador=" + identificador + " ]";
    }

    @XmlTransient
    public List<Publicacion> getPublicacionList() {
        return publicacionList;
    }

    public void setPublicacionList(List<Publicacion> publicacionList) {
        this.publicacionList = publicacionList;
    }

}
