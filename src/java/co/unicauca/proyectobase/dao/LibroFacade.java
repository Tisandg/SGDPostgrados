package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Libro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Santiago Garcia
 */
@Stateless
public class LibroFacade extends AbstractFacade<Libro>{

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public LibroFacade() {
        super(Libro.class);
    }
    
    public List<Libro> findByTituloLibro(String tituloLibro)
    {
        Query query= em.createNamedQuery("Libro.findByLibTituloLibro");
        query.setParameter("libTituloLibro", tituloLibro);
        List<Libro> findLibro= query.getResultList();
        System.out.println("Datos de la lista"+ findLibro);
        return findLibro;
    }
}
