package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import javax.enterprise.inject.Default;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL7
 */
@Entity
@Table(name = "capitulo_libro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CapituloLibro.findAll", query = "SELECT c FROM CapituloLibro c")
    , @NamedQuery(name = "CapituloLibro.findByPubIdentificador", query = "SELECT c FROM CapituloLibro c WHERE c.pubIdentificador = :pubIdentificador")
    , @NamedQuery(name = "CapituloLibro.findByCaplibTituloLibro", query = "SELECT c FROM CapituloLibro c WHERE c.caplibTituloLibro = :caplibTituloLibro")
    , @NamedQuery(name = "CapituloLibro.findByCaplibTituloCapitulo", query = "SELECT c FROM CapituloLibro c WHERE c.caplibTituloCapitulo = :caplibTituloCapitulo")})
public class CapituloLibro implements Serializable {

    @Basic(optional = true)    
    @Size(max = 30)
    @Column(name = "caplib_isbn")
    private String caplibIsbn;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pub_identificador")
    private Integer pubIdentificador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "caplib_titulo_libro")
    private String caplibTituloLibro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "caplib_titulo_capitulo")
    private String caplibTituloCapitulo;
    @JoinColumn(name = "pub_identificador", referencedColumnName = "pub_identificador", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Publicacion publicacion;

    public CapituloLibro() {
    }

    public CapituloLibro(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public CapituloLibro(Integer pubIdentificador, String caplibTituloLibro, String caplibTituloCapitulo) {
        this.pubIdentificador = pubIdentificador;
        this.caplibTituloLibro = caplibTituloLibro;
        this.caplibTituloCapitulo = caplibTituloCapitulo;
    }

    public Integer getPubIdentificador() {
        return pubIdentificador;
    }

    public void setPubIdentificador(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public String getCaplibTituloLibro() {
        return caplibTituloLibro;
    }

    public void setCaplibTituloLibro(String caplibTituloLibro) {
        this.caplibTituloLibro = caplibTituloLibro;
    }

    public String getCaplibTituloCapitulo() {
        return caplibTituloCapitulo;
    }

    public void setCaplibTituloCapitulo(String caplibTituloCapitulo) {
        this.caplibTituloCapitulo = caplibTituloCapitulo;
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
        if (!(object instanceof CapituloLibro)) {
            return false;
        }
        CapituloLibro other = (CapituloLibro) object;
        if ((this.pubIdentificador == null && other.pubIdentificador != null) || (this.pubIdentificador != null && !this.pubIdentificador.equals(other.pubIdentificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.CapituloLibro[ pubIdentificador=" + pubIdentificador + " ]";
    }

    public String getCaplibIsbn() {
        return caplibIsbn;
    }

    public void setCaplibIsbn(String caplibIsbn) {
        this.caplibIsbn = caplibIsbn;
    }
    
}
