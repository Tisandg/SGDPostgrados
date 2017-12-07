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
 * Clase que define el identificador del grupo de tipos de usuario
 * @author Sahydo
 */
@Embeddable
public class GrupoTipoUsuarioPK implements Serializable {

    // Clave identificadora del grupo
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    // Identificador del tipo de usuario
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo")
    private int idTipo;
    // Identificador del usuario
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario")
    private int idUsuario;

    /* Constructores */
    public GrupoTipoUsuarioPK() {
    }

    public GrupoTipoUsuarioPK(int id, int idTipo, int idUsuario) {
        this.id = id;
        this.idTipo = idTipo;
        this.idUsuario = idUsuario;
    }

    /*Setters y Getters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idTipo;
        hash += (int) idUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoTipoUsuarioPK)) {
            return false;
        }
        GrupoTipoUsuarioPK other = (GrupoTipoUsuarioPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idTipo != other.idTipo) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK[ id=" + id + ", idTipo=" + idTipo + ", idUsuario=" + idUsuario + " ]";
    }
    
}
