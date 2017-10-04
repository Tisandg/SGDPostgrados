/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Libro;
import co.unicauca.proyectobase.entidades.Revista;
import com.openkm.sdk4j.exception.ExceptionHelper;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sahydo
 */
@Stateless
public class RevistaFacade extends AbstractFacade<Revista> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RevistaFacade() {
        super(Revista.class);
    }

    public Revista findByDoiRevista(String doi) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Revista findByTituloArticulo(String titulo) {
        //System.out.println("Titulo: "+tituloLibro);
        Query query= em.createNamedQuery("Revista.findByTituloArticulo");
        query.setParameter("revTitulo", titulo);
        List<Revista> resultList= query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
    
}
