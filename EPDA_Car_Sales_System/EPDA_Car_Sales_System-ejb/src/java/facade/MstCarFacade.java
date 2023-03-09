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
import javax.servlet.http.HttpServletRequest;
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
    
    public List<Object[]> rptCarCard(HttpServletRequest request) {
        try {   
            Query query = em.createNativeQuery("Select 'availableCars', Count(mst.carId) from MstCar mst Where mst.status = 'Available'"
                    + " Union "
                    + "Select 'bookedCars', Count(mst.carId) from MstCar mst Where mst.status = 'Booked'");

            Query query2 = em.createNativeQuery("Select 'bestSellingModel', m.MODEL from TxnSalesRecord txn Inner Join mstCar m on m.carId = txn.car_carId Where txn.orderStatus = 'Paid' Group By txn.salesman_userId, m.MODEL Order By Count(txn.salesId) desc, m.model").setMaxResults(1);

            List<Object[]> data = query.getResultList();
            data.addAll(query2.getResultList());

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptCarCard : " + e.getMessage());
            return null;
        }
    }
}
