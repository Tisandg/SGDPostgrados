/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public List<Usuario> findAllByNombreUsuario(String nombreUsuario)
    {
        Query query= em.createNamedQuery("Usuario.findByNombreUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);
        List<Usuario> findUsuario= query.getResultList();
        System.out.println("Datos de la lista"+ findUsuario);
        return findUsuario;
    }
    
}
