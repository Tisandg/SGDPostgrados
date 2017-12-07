/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa del programa de doctorado
 * @author Sahydo
 */
@Entity
@Table(name = "doctorado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Doctorado.findAll", query = "SELECT d FROM Doctorado d"),
    @NamedQuery(name = "Doctorado.findByDocIdentificador", query = "SELECT d FROM Doctorado d WHERE d.doctoradoPK.docIdentificador = :docIdentificador"),
    @NamedQuery(name = "Doctorado.findByDocCooIdentificador", query = "SELECT d FROM Doctorado d WHERE d.doctoradoPK.docCooIdentificador = :docCooIdentificador"),
    @NamedQuery(name = "Doctorado.findByDocEstIdentificador", query = "SELECT d FROM Doctorado d WHERE d.doctoradoPK.docEstIdentificador = :docEstIdentificador")})
public class Doctorado implements Serializable {

    // Versión de la base de datos
    private static final long serialVersionUID = 1L;
    // Clave identificadora del doctorado
    @EmbeddedId
    protected DoctoradoPK doctoradoPK;
    // Identificador del coordinador del doctorado
    @JoinColumn(name = "doc_coo_identificador", referencedColumnName = "coo_identificador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Coordinador coordinador;
    // Identificador del estudiante del doctorado
    @JoinColumn(name = "doc_est_identificador", referencedColumnName = "est_identificador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estudiante estudiante;

    /* Constructor */
    public Doctorado() {
    }

    public Doctorado(DoctoradoPK doctoradoPK) {
        this.doctoradoPK = doctoradoPK;
    }

    public Doctorado(int docIdentificador, int docCooIdentificador, int docEstIdentificador) {
        this.doctoradoPK = new DoctoradoPK(docIdentificador, docCooIdentificador, docEstIdentificador);
    }

    /* Getters y Setters */
    public DoctoradoPK getDoctoradoPK() {
        return doctoradoPK;
    }

    public void setDoctoradoPK(DoctoradoPK doctoradoPK) {
        this.doctoradoPK = doctoradoPK;
    }

    public Coordinador getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (doctoradoPK != null ? doctoradoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Doctorado)) {
            return false;
        }
        Doctorado other = (Doctorado) object;
        if ((this.doctoradoPK == null && other.doctoradoPK != null) || (this.doctoradoPK != null && !this.doctoradoPK.equals(other.doctoradoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.Doctorado[ doctoradoPK=" + doctoradoPK + " ]";
    }
    
}
