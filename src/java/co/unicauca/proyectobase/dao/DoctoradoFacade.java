package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Doctorado;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase utilizada para las operaciones sobre la tabla de la base de datos doctorado
 * @author Sahydo
 */
@Stateless
public class DoctoradoFacade extends AbstractFacade<Doctorado> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DoctoradoFacade() {
        super(Doctorado.class);
    }
    
}
