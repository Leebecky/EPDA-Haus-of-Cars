/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletRequest;
import model.MstCar;
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
            System.out.println("MstMemberFacade - loginUser : " + e.getMessage());
            return null;
        }
    }

    public List<MstMember> getAllUsers(String userType) {
        try {

            TypedQuery<MstMember> query = null;

            if (userType.isEmpty()) {
                query = em.createQuery("select m from MstMember m order by m.userType, m.fullname", MstMember.class);
            } else {
                query = em.createQuery("select m from MstMember m where m.userType = :role order by m.fullname", MstMember.class);
                query.setParameter("role", userType);
            }

            List<MstMember> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("MstMemberFacade - getAllUsers : " + e.getMessage());
            return null;
        }
    }

    public List<MstMember> getAvailableCustomers() {
        try {

            TypedQuery query = em.createQuery("select m from MstMember m where m.userType = 'Customer' and m.status = 'Approved'", MstMember.class);
            List<MstMember> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("MstCarFacade - getAvailableCars : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> rptCustomerCard(HttpServletRequest request) {
        try {   
            Query query = em.createNativeQuery("Select 'approvedCustomers', Count(mst.userId) from MstMember mst Where mst.status = 'Approved' and mst.userType = 'Customer'"
                    + " Union "
                    + "Select 'pendingCustomers', Count(mst.userId) from MstMember mst Where mst.status = 'Pending' and mst.userType = 'Customer'");

            Query query2 = em.createNativeQuery("Select 'highestSpender', m.fullname from TxnSalesRecord txn Inner Join mstMember m on m.userId = txn.customer_userId Where txn.orderStatus = 'Paid' Group By txn.CUSTOMER_USERID, m.fullname Order By Sum(txn.totalPayable) desc").setMaxResults(1);

            List<Object[]> data = query.getResultList();
            data.addAll(query2.getResultList());

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptCustomerCard : " + e.getMessage());
            return null;
        }
    }
    
    public List<Object[]> rptSalesmanCard(HttpServletRequest request) {
        try {   
            Query query = em.createNativeQuery("Select 'approvedSalesman', Count(mst.userId) from MstMember mst Where mst.status = 'Approved' and mst.userType = 'Salesman'"
                    + " Union "
                    + "Select 'pendingSalesman', Count(mst.userId) from MstMember mst Where mst.status = 'Pending' and mst.userType = 'Salesman'");

            Query query2 = em.createNativeQuery("Select 'bestService', m.fullname from TxnSalesRecord txn Inner Join mstMember m on m.userId = txn.salesman_userId Where txn.orderStatus = 'Paid' Group By txn.salesman_userId, m.fullname Order By Avg(txn.salesmanRating) desc").setMaxResults(1);

            List<Object[]> data = query.getResultList();
            data.addAll(query2.getResultList());

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptSalesmanCard : " + e.getMessage());
            return null;
        }
    }
}
