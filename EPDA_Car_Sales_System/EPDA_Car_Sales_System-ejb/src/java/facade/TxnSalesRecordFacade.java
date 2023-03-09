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
import javax.servlet.http.HttpServletRequest;
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

    public List<TxnSalesRecord> getSalesBySalesman(String salesmanId) {
        try {

            Query query = em.createQuery("select m from TxnSalesRecord m where m.salesman.userId = :salesmanId", TxnSalesRecord.class);
            query.setParameter("salesmanId", salesmanId);
            List<TxnSalesRecord> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - getSalesBySalesman : " + e.getMessage());
            return null;
        }
    }

    public List<TxnSalesRecord> getPendingBookings() {
        try {

            Query query = em.createQuery("select m from TxnSalesRecord m where m.orderStatus = 'Pending Salesman'", TxnSalesRecord.class);
            List<TxnSalesRecord> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - getPendingBookings : " + e.getMessage());
            return null;
        }
    }

    public Boolean hasTransactions(String userId) {
        try {

            Query query = em.createQuery("Select m from TxnSalesRecord m Where m.salesman.userId = :id or m.customer.userId = :id");
            query.setParameter("id", userId);
            List<Object[]> data = query.getResultList();

            if (data != null && data.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - getSalesByBrand : " + e.getMessage());
            return null;
        }
    }
    
     public Boolean hasCarTransactions(String carId) {
        try {

            Query query = em.createQuery("Select m from TxnSalesRecord m Where m.car.carId = :id");
            query.setParameter("id", carId);
            List<Object[]> data = query.getResultList();

            if (data != null && data.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - getSalesByBrand : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> getSalesByBrand() {
        try {

            Query query = em.createQuery("Select txn.car.brand, Count(txn.car.brand) as CarsSold from TxnSalesRecord txn Group By txn.car.brand");
            List<Object[]> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - getSalesByBrand : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> rptBookingsByStatus(HttpServletRequest request) {
        try {

            LocalDate toDate = (LocalDate) request.getSession().getAttribute("toDate");
            LocalDate fromDate = (LocalDate) request.getSession().getAttribute("fromDate");

            Query query = em.createQuery("Select txn.orderStatus, Count(txn.orderStatus) from TxnSalesRecord txn Where txn.salesDate >= :fromDate and txn.salesDate <= :toDate Group By txn.orderStatus");
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);

            List<Object[]> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptBookingsByStatus : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> rptSalesPerMonth(HttpServletRequest request) {
        try {

            LocalDate toDate = (LocalDate) request.getSession().getAttribute("toDate");
            int year = toDate.getYear();

            Query query = em.createNativeQuery("Select Month(txn.salesDate), SUM(txn.totalPayable) from txnSalesRecord txn Where txn.orderStatus = 'Paid' and Year(txn.salesDate) = ? Group By Month(txn.salesDate)");
            query.setParameter(1, year);

            List<Object[]> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptSalesPerMonth : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> rptSalesCard(HttpServletRequest request) {
        try {

            LocalDate toDate = (LocalDate) request.getSession().getAttribute("toDate");
            int month = toDate.getMonthValue();

            Query query = em.createNativeQuery("Select 'totalSalesCard', Sum(txn.totalPayable) from TxnSalesRecord txn Where Month(txn.salesDate) = ? and txn.orderStatus = 'Paid'"
                    + " Union "
                    + "Select 'totalPaidBookingsCard', Count(txn.salesId) from TxnSalesRecord txn Where Month(txn.salesDate) = ? and txn.orderStatus = 'Paid'"
                    + " Union "
                    + "Select 'avgSalesCard', Avg(txn.totalPayable) from TxnSalesRecord txn Where Month(txn.salesDate) = ? and txn.orderStatus = 'Paid'");
            query.setParameter(1, month);
            query.setParameter(2, month);
            query.setParameter(3, month);

            List<Object[]> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptSalesCard : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> rptBookingsByCustomers(HttpServletRequest request, String customerName) {
        try {
            LocalDate toDate = (LocalDate) request.getSession().getAttribute("toDate");
            LocalDate fromDate = (LocalDate) request.getSession().getAttribute("fromDate");

            Query query = em.createQuery("Select Count(txn.salesId), txn.orderStatus from TxnSalesRecord txn Where txn.customer.fullname = :name and txn.salesDate >= :fromDate and txn.salesDate <= :toDate Group By txn.orderStatus Order by txn.orderStatus");
            query.setParameter("name", customerName);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            List<Object[]> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptBookingsByCustomers : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> rptSalesmanSales(HttpServletRequest request, String orderStatus) {
        try {
            LocalDate toDate = (LocalDate) request.getSession().getAttribute("toDate");
            LocalDate fromDate = (LocalDate) request.getSession().getAttribute("fromDate");

            Query query = em.createQuery("Select txn.salesman.fullname, Count(txn.salesId) from TxnSalesRecord txn Where txn.orderStatus = :status and txn.salesDate >= :fromDate and txn.salesDate <= :toDate Group By txn.salesman.fullname Order by txn.salesman.fullname");
            query.setParameter("status", orderStatus);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            List<Object[]> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptSalesmanSales : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> rptSalesmanAvgRating(HttpServletRequest request) {
        try {
            LocalDate toDate = (LocalDate) request.getSession().getAttribute("toDate");
            LocalDate fromDate = (LocalDate) request.getSession().getAttribute("fromDate");

            Query query = em.createQuery("Select txn.salesman.fullname, Avg(txn.salesmanRating) from TxnSalesRecord txn Where txn.orderStatus = 'Paid' and txn.salesDate >= :fromDate and txn.salesDate <= :toDate and txn.salesman Is Not Null Group By txn.salesman.fullname Order by txn.salesman.fullname");
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            List<Object[]> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptSalesmanAvgRating : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> rptCarColours(HttpServletRequest request) {
        try {
            LocalDate toDate = (LocalDate) request.getSession().getAttribute("toDate");
            LocalDate fromDate = (LocalDate) request.getSession().getAttribute("fromDate");

            Query query = em.createQuery("Select txn.car.colour, Count(txn.salesId) from TxnSalesRecord txn Where txn.orderStatus = 'Paid' and txn.salesDate >= :fromDate and txn.salesDate <= :toDate and txn.salesman Is Not Null Group By txn.car.colour Order by txn.car.colour");
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            List<Object[]> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptCarColours : " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> rptCarSalesByBrand(HttpServletRequest request) {
        try {
            LocalDate toDate = (LocalDate) request.getSession().getAttribute("toDate");
            LocalDate fromDate = (LocalDate) request.getSession().getAttribute("fromDate");

            Query query = em.createQuery("Select txn.car.brand, Count(txn.salesId), Avg(txn.carRating) from TxnSalesRecord txn Where txn.orderStatus = 'Paid' and txn.salesDate >= :fromDate and txn.salesDate <= :toDate Group By txn.car.brand Order by txn.car.brand");
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            List<Object[]> data = query.getResultList();

            return data;
        } catch (Exception e) {
            System.out.println("TxnSalesRecordFacade - rptCarSalesByBrand : " + e.getMessage());
            return null;
        }
    }

}
