package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Archivo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase utilizada para las operaciones sobre la tabla de la base de datos archivo
 * @author Sahydo
 */
@Stateless
public class ArchivoFacade extends AbstractFacade<Archivo> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoFacade() {
        super(Archivo.class);
    }
    
}
