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

    public boolean existByEstCorreo(String estCorreo) {
        Query query = em.createNamedQuery("Estudiante.findByEstCorreo");
        query.setParameter("estCorreo", estCorreo);
        return !query.getResultList().isEmpty();
    }

    public boolean existByEstCodigo(String estCodigo) {
        Query query = em.createNamedQuery("Estudiante.findByEstCodigo");
        query.setParameter("estCodigo", estCodigo);
        return !query.getResultList().isEmpty();
    }

    public Estudiante buscarPorCodigoExceptoConId(String estCodigo, Integer id) {
        Query query = em.createNamedQuery("Estudiante.buscarPorCodigoExceptoConId");
        query.setParameter("estCodigo", estCodigo);
        query.setParameter("estIdentificador", id);

        List<Estudiante> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * Funcion para buscar un estudiante por su codigo
     * @param codigo
     * @return Estudiante
     */
    public Estudiante buscarPorCodigo(String codigo){
        Query query = em.createNamedQuery("Estudiante.buscarPorCodigo");
        query.setParameter("estCodigo", codigo);
        List<Estudiante> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
    
    public Estudiante buscarPorCorreoExceptoConId(String correo, Integer id) {
        Query query = em.createNamedQuery("Estudiante.buscarPorCorreoExceptoConId");
        query.setParameter("estCorreo", correo);
        query.setParameter("estIdentificador", id);

        List<Estudiante> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
    public List<Estudiante> findAllByString(String texto) {
        Query query = em.createNamedQuery("Estudiante.findAllByString");
        query.setParameter("texto", "%" + texto + "%");
        List<Estudiante> findEst = query.getResultList();
        return findEst;
    }
}
