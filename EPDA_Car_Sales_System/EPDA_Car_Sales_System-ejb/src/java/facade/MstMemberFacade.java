/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import model.MstMember;

/**
 *
 * @author leebe
 */
@Stateless
public class MstMemberFacade extends AbstractFacade<MstMember> {

    @PersistenceContext(unitName = "EPDA_Car_Sales_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MstMemberFacade() {
        super(MstMember.class);
    }

    public MstMember getUser(String email) {
        try {

            TypedQuery<MstMember> query = em.createQuery("select m from MstMember m where m.email = :mail", MstMember.class);
            query.setParameter("mail", email);
            MstMember data = query.getSingleResult();

            return data;
        } catch (Exception e) {
            System.out.println("MstMemberFacade - getUser : " + e.getMessage());
            return null;
        }
    }

    public MstMember loginUser(String email, String password) {
        try {

            TypedQuery<MstMember> query = em.createQuery("select m from MstMember m where m.email = :mail and m.password = :pass", MstMember.class);
            query.setParameter("mail", email);
            query.setParameter("pass", password);
            MstMember data = query.getSingleResult();

            return data;
        } catch (Exception e) {
            System.out.println("MstMemberFacade - LoginUser : " + e.getMessage());
            return null;
        }
    }

}
