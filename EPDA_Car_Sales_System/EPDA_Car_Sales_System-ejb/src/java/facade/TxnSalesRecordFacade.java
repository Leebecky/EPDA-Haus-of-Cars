/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
}
