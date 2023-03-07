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
import javax.persistence.TypedQuery;
import model.MstCar;

/**
 *
 * @author leebe
 */
@Stateless
public class MstCarFacade extends AbstractFacade<MstCar> {

    @PersistenceContext(unitName = "EPDA_Car_Sales_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MstCarFacade() {
        super(MstCar.class);
    }
 
    public List<MstCar> getAvailableCars() {
        try {

            TypedQuery query = em.createQuery("select m from MstCar m where m.status = 'Available'", MstCar.class);
            List<MstCar> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("MstCarFacade - getAvailableCars : " + e.getMessage());
            return null;
        }
    }
}
