/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sahydo
 */
@Embeddable
public class DoctoradoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "doc_identificador")
    private int docIdentificador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "doc_coo_identificador")
    private int docCooIdentificador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "doc_est_identificador")
    private int docEstIdentificador;

    public DoctoradoPK() {
    }

    public DoctoradoPK(int docIdentificador, int docCooIdentificador, int docEstIdentificador) {
        this.docIdentificador = docIdentificador;
        this.docCooIdentificador = docCooIdentificador;
        this.docEstIdentificador = docEstIdentificador;
    }

    public int getDocIdentificador() {
        return docIdentificador;
    }

    public void setDocIdentificador(int docIdentificador) {
        this.docIdentificador = docIdentificador;
    }

    public int getDocCooIdentificador() {
        return docCooIdentificador;
    }

    public void setDocCooIdentificador(int docCooIdentificador) {
        this.docCooIdentificador = docCooIdentificador;
    }

    public int getDocEstIdentificador() {
        return docEstIdentificador;
    }

    public void setDocEstIdentificador(int docEstIdentificador) {
        this.docEstIdentificador = docEstIdentificador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) docIdentificador;
        hash += (int) docCooIdentificador;
        hash += (int) docEstIdentificador;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DoctoradoPK)) {
            return false;
        }
        DoctoradoPK other = (DoctoradoPK) object;
        if (this.docIdentificador != other.docIdentificador) {
            return false;
        }
        if (this.docCooIdentificador != other.docCooIdentificador) {
            return false;
        }
        if (this.docEstIdentificador != other.docEstIdentificador) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.DoctoradoPK[ docIdentificador=" + docIdentificador + ", docCooIdentificador=" + docCooIdentificador + ", docEstIdentificador=" + docEstIdentificador + " ]";
    }
    
}
