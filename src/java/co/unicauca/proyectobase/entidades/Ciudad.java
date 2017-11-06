/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sperez
 */
@Entity
@Table(name = "ciudad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c"),
    @NamedQuery(name = "Ciudad.findByCiudId", query = "SELECT c FROM Ciudad c WHERE c.ciudId = :ciudId"),
    @NamedQuery(name = "Ciudad.findByCiudNombre", query = "SELECT c FROM Ciudad c WHERE c.ciudNombre = :ciudNombre")})
public class Ciudad implements Serializable {

    @OneToMany(mappedBy = "ciudadId")
    private List<Congreso> congresoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ciud_id")
    private Integer ciudId;
    @Size(max = 40)
    @Column(name = "ciud_nombre")
    private String ciudNombre;
    @OneToMany(mappedBy = "ciudadId")
    private List<Libro> libroList;
    @JoinColumn(name = "pais_id", referencedColumnName = "pais_id")
    @ManyToOne(optional = false)
    private Pais paisId;

    public Ciudad() {
    }

    public Ciudad(Integer ciudId) {
        this.ciudId = ciudId;
    }

    public Integer getCiudId() {
        return ciudId;
    }

    public void setCiudId(Integer ciudId) {
        this.ciudId = ciudId;
    }

    public String getCiudNombre() {
        return ciudNombre;
    }

    public void setCiudNombre(String ciudNombre) {
        this.ciudNombre = ciudNombre;
    }

    @XmlTransient
    public List<Libro> getLibroList() {
        return libroList;
    }

    public void setLibroList(List<Libro> libroList) {
        this.libroList = libroList;
    }

    public Pais getPaisId() {
        return paisId;
    }

    public void setPaisId(Pais paisId) {
        this.paisId = paisId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciudId != null ? ciudId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.ciudId == null && other.ciudId != null) || (this.ciudId != null && !this.ciudId.equals(other.ciudId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.Ciudad[ ciudId=" + ciudId + " ]";
    }

    @XmlTransient
    public List<Congreso> getCongresoList() {
        return congresoList;
    }

    public void setCongresoList(List<Congreso> congresoList) {
        this.congresoList = congresoList;
    }
    
}
