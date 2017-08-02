/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Estudiante;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author Sahydo
 */
@Stateless
public class EstudianteFacade extends AbstractFacade<Estudiante> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteFacade() {
        super(Estudiante.class);
    }
    public boolean findByEstCorreo(String estCorreo){
        Query query = em.createNamedQuery("Estudiante.findByEstCorreo");
        query.setParameter("estCorreo", estCorreo);
        return !query.getResultList().isEmpty();
    }
    
    public boolean findByEstCodigo(String estCodigo){
            Query query = em.createNamedQuery("Estudiante.findByEstCodigo");
            query.setParameter("estCodigo", estCodigo);
            return !query.getResultList().isEmpty();
    }
    
    public List<Estudiante> findAllByString(String texto)
    {
        Query query = em.createNamedQuery("Estudiante.findAllByString");
        query.setParameter("texto", "%" + texto + "%");
        List<Estudiante> findEst = query.getResultList();
        return findEst;
    }
}
