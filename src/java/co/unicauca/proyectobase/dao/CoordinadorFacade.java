package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Coordinador;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase utilizada para las operaciones sobre la tabla de la base de datos coordinador
 * @author Sahydo
 */
@Stateless
public class CoordinadorFacade extends AbstractFacade<Coordinador> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CoordinadorFacade() {
        super(Coordinador.class);
    }
    
}
