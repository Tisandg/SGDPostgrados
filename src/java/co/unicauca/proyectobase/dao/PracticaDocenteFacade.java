package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.PracticaDocente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@Stateless
public class PracticaDocenteFacade extends AbstractFacade<PracticaDocente> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PracticaDocenteFacade() {
        super(PracticaDocente.class);
    }

}
