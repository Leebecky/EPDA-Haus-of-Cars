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
import model.TxnSalesRecord;

/**
 *
 * @author leebe
 */
@Stateless
public class TxnSalesRecordFacade extends AbstractFacade<TxnSalesRecord> {

    @PersistenceContext(unitName = "EPDA_Car_Sales_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TxnSalesRecordFacade() {
        super(TxnSalesRecord.class);
    }
    
    public List<TxnSalesRecord> getSalesByCustomer(String customerId) {
        try {

            Query query = em.createQuery("select m from TxnSalesRecord m where m.customer.userId = :customerId", TxnSalesRecord.class);
            query.setParameter("customerId", customerId);
            List<TxnSalesRecord> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - getSalesByCustomer : " + e.getMessage());
            return null;
        }
    }
}
