package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.GrupoTipoUsuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase utilizada para las operaciones sobre la tabla de la base de datos 
 * grupoTipoUsuario
 * @author Sahydo
 */
@Stateless
public class GrupoTipoUsuarioFacade extends AbstractFacade<GrupoTipoUsuario> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoTipoUsuarioFacade() {
        super(GrupoTipoUsuario.class);
    }
    public List<GrupoTipoUsuario> findAllByNombreUsuario(String nombreUsuario)
    {
        Query query = em.createNamedQuery("GrupoTipoUsuario.findByNombreUsuario");
        query.setParameter("nombreUsuario",nombreUsuario);
        List<GrupoTipoUsuario> findGrupo = query.getResultList();
        return findGrupo;
    }
    
}
