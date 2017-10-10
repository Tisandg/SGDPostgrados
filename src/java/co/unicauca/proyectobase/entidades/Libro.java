/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
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
@Table(name = "libro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Libro.findAll", query = "SELECT l FROM Libro l")
    , @NamedQuery(name = "Libro.findByPubIdentificador", query = "SELECT l FROM Libro l WHERE l.pubIdentificador = :pubIdentificador")
    , @NamedQuery(name = "Libro.findByLibTituloLibro", query = "SELECT l FROM Libro l WHERE l.libTituloLibro = :libTituloLibro")})
public class Libro implements Serializable {

    @Basic(optional = false)    
    @Size(min = 1, max = 30)
    @Column(name = "lib_isbn")
    private String libIsbn;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pub_identificador")
    private Integer pubIdentificador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "lib_titulo_libro")
    private String libTituloLibro;
    @JoinColumn(name = "pub_identificador", referencedColumnName = "pub_identificador", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Publicacion publicacion;

    public Libro() {
    }

    public Libro(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public Libro(Integer pubIdentificador, String libTituloLibro) {
        this.pubIdentificador = pubIdentificador;
        this.libTituloLibro = libTituloLibro;
    }

    public Integer getPubIdentificador() {
        return pubIdentificador;
    }

    public void setPubIdentificador(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public String getLibTituloLibro() {
        return libTituloLibro;
    }

    public void setLibTituloLibro(String libTituloLibro) {
        this.libTituloLibro = libTituloLibro;
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
        if (!(object instanceof Libro)) {
            return false;
        }
        Libro other = (Libro) object;
        if ((this.pubIdentificador == null && other.pubIdentificador != null) || (this.pubIdentificador != null && !this.pubIdentificador.equals(other.pubIdentificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.Libro[ pubIdentificador=" + pubIdentificador + " ]";
    }

    public String getLibIsbn() {
        return libIsbn;
    }

    public void setLibIsbn(String libIsbn) {
        this.libIsbn = libIsbn;
    }
    
}
