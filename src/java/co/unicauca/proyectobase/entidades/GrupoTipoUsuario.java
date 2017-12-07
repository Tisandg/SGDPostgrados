/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa un grupo con el mismo tipo de usuario
 * @author Sahydo
 */
@Entity
@Table(name = "grupo_tipo_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoTipoUsuario.findAll", query = "SELECT g FROM GrupoTipoUsuario g"),
    @NamedQuery(name = "GrupoTipoUsuario.findById", query = "SELECT g FROM GrupoTipoUsuario g WHERE g.grupoTipoUsuarioPK.id = :id"),
    @NamedQuery(name = "GrupoTipoUsuario.findByIdTipo", query = "SELECT g FROM GrupoTipoUsuario g WHERE g.grupoTipoUsuarioPK.idTipo = :idTipo"),
    @NamedQuery(name = "GrupoTipoUsuario.findByIdUsuario", query = "SELECT g FROM GrupoTipoUsuario g WHERE g.grupoTipoUsuarioPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "GrupoTipoUsuario.findByNombreUsuario", query = "SELECT g FROM GrupoTipoUsuario g WHERE g.nombreUsuario = :nombreUsuario")})
public class GrupoTipoUsuario implements Serializable {

    // Versión de la base de datos
    private static final long serialVersionUID = 1L;
    // Clave identificadora del grupo
    @EmbeddedId
    protected GrupoTipoUsuarioPK grupoTipoUsuarioPK;
    // Nombre del grupo de usuarios
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    // Identificador del tipo de usuario
    @JoinColumn(name = "id_tipo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoUsuario tipoUsuario;
    // Identificador del usuario
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    /* Constructores */
    public GrupoTipoUsuario() {
    }

    public GrupoTipoUsuario(GrupoTipoUsuarioPK grupoTipoUsuarioPK) {
        this.grupoTipoUsuarioPK = grupoTipoUsuarioPK;
    }

    public GrupoTipoUsuario(GrupoTipoUsuarioPK grupoTipoUsuarioPK, String nombreUsuario) {
        this.grupoTipoUsuarioPK = grupoTipoUsuarioPK;
        this.nombreUsuario = nombreUsuario;
    }

    public GrupoTipoUsuario(int id, int idTipo, int idUsuario) {
        this.grupoTipoUsuarioPK = new GrupoTipoUsuarioPK(id, idTipo, idUsuario);
    }

    /*Setters y Getters */
    public GrupoTipoUsuarioPK getGrupoTipoUsuarioPK() {
        return grupoTipoUsuarioPK;
    }

    public void setGrupoTipoUsuarioPK(GrupoTipoUsuarioPK grupoTipoUsuarioPK) {
        this.grupoTipoUsuarioPK = grupoTipoUsuarioPK;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoTipoUsuarioPK != null ? grupoTipoUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoTipoUsuario)) {
            return false;
        }
        GrupoTipoUsuario other = (GrupoTipoUsuario) object;
        if ((this.grupoTipoUsuarioPK == null && other.grupoTipoUsuarioPK != null) || (this.grupoTipoUsuarioPK != null && !this.grupoTipoUsuarioPK.equals(other.grupoTipoUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.GrupoTipoUsuario[ grupoTipoUsuarioPK=" + grupoTipoUsuarioPK + " ]";
    }
    
}
