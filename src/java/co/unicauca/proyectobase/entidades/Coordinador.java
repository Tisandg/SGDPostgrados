/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sahydo
 */
@Entity
@Table(name = "coordinador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordinador.findAll", query = "SELECT c FROM Coordinador c"),
    @NamedQuery(name = "Coordinador.findByCooIdentificador", query = "SELECT c FROM Coordinador c WHERE c.cooIdentificador = :cooIdentificador"),
    @NamedQuery(name = "Coordinador.findByCooNombre", query = "SELECT c FROM Coordinador c WHERE c.cooNombre = :cooNombre"),
    @NamedQuery(name = "Coordinador.findByCooContrasena", query = "SELECT c FROM Coordinador c WHERE c.cooContrasena = :cooContrasena"),
    @NamedQuery(name = "Coordinador.findByCooCorreo", query = "SELECT c FROM Coordinador c WHERE c.cooCorreo = :cooCorreo"),
    @NamedQuery(name = "Coordinador.findByCooUsuario", query = "SELECT c FROM Coordinador c WHERE c.cooUsuario = :cooUsuario")})
public class Coordinador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coo_identificador")
    private Integer cooIdentificador;
    @Size(max = 45)
    @Column(name = "coo_nombre")
    private String cooNombre;
    @Size(max = 40)
    @Column(name = "coo_contrasena")
    private String cooContrasena;
    @Size(max = 30)
    @Column(name = "coo_correo")
    private String cooCorreo;
    @Size(max = 20)
    @Column(name = "coo_usuario")
    private String cooUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coordinador")
    private List<Doctorado> doctoradoList;

    public Coordinador() {
    }

    public Coordinador(Integer cooIdentificador) {
        this.cooIdentificador = cooIdentificador;
    }

    public Integer getCooIdentificador() {
        return cooIdentificador;
    }

    public void setCooIdentificador(Integer cooIdentificador) {
        this.cooIdentificador = cooIdentificador;
    }

    public String getCooNombre() {
        return cooNombre;
    }

    public void setCooNombre(String cooNombre) {
        this.cooNombre = cooNombre;
    }

    public String getCooContrasena() {
        return cooContrasena;
    }

    public void setCooContrasena(String cooContrasena) {
        this.cooContrasena = cooContrasena;
    }

    public String getCooCorreo() {
        return cooCorreo;
    }

    public void setCooCorreo(String cooCorreo) {
        this.cooCorreo = cooCorreo;
    }

    public String getCooUsuario() {
        return cooUsuario;
    }

    public void setCooUsuario(String cooUsuario) {
        this.cooUsuario = cooUsuario;
    }

    @XmlTransient
    public List<Doctorado> getDoctoradoList() {
        return doctoradoList;
    }

    public void setDoctoradoList(List<Doctorado> doctoradoList) {
        this.doctoradoList = doctoradoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cooIdentificador != null ? cooIdentificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coordinador)) {
            return false;
        }
        Coordinador other = (Coordinador) object;
        if ((this.cooIdentificador == null && other.cooIdentificador != null) || (this.cooIdentificador != null && !this.cooIdentificador.equals(other.cooIdentificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.Coordinador[ cooIdentificador=" + cooIdentificador + " ]";
    }
    
}
