/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Hospital;
import entity.Hospital_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author inftel09
 */
@Stateless
@Path("entity.hospital")
public class HospitalFacadeREST extends AbstractFacade<Hospital> {

    @PersistenceContext(unitName = "hospitalPU")
    private EntityManager em;

    public HospitalFacadeREST() {
        super(Hospital.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Hospital entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Hospital entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Hospital find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({/*MediaType.APPLICATION_XML, */MediaType.APPLICATION_JSON})
    public List<Hospital> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("bytype/{type}")
    @Produces({/*MediaType.APPLICATION_XML, */MediaType.APPLICATION_JSON})
    public List<Hospital> findAllByType(@PathParam("type") String type) {
        javax.persistence.criteria.CriteriaBuilder b = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery  cq = b.createQuery();
        
        ParameterExpression<String> p = b.parameter(String.class, "type");
        
        Root<Hospital> hospitalRoot = cq.from( Hospital.class );
        
        cq.select( hospitalRoot );
        cq.where( b.equal( hospitalRoot.get( Hospital_.tipo ), p ) );
        return getEntityManager().createQuery(cq).setParameter(p, type).getResultList();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Hospital> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
