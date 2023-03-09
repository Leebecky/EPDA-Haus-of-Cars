/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MstCarFacade;
import facade.MstCustomerFacade;
import facade.TxnSalesRecordFacade;
import helper.Session_Authenticator;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MstCar;
import model.MstCustomer;
import model.MstMember;
import model.TxnSalesRecord;

/**
 *
 * @author leebe
 */
@WebServlet(name = "Booking_Details", urlPatterns = {"/Booking_Details"})
public class Booking_Details extends HttpServlet {

    @EJB
    TxnSalesRecordFacade salesFacade;

    @EJB
    MstCarFacade carFacade;

    @EJB
    MstCustomerFacade customerFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void updateCustomerBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String salesId = request.getParameter("id");
            String carRate = request.getParameter("carRating");
            String slsRate = request.getParameter("salesmanRating");
            String customerReview = request.getParameter("customerReview");

            float carRating = 0;
            float salesmanRating = 0;

            if (carRate != null && !carRate.isEmpty()) {
                carRating = Float.parseFloat(carRate);
            }

            if (slsRate != null && !slsRate.isEmpty()) {
                salesmanRating = Float.parseFloat(slsRate);
            }

            TxnSalesRecord booking = salesFacade.find(salesId);

            if (booking != null) {

                booking.setCarRating(carRating);
                booking.setSalesmanRating(salesmanRating);
                booking.setCustomerReview(customerReview);

                salesFacade.edit(booking);

                request.getSession().setAttribute("msg", "Reviews saved!");
            } else {
                request.getSession().setAttribute("error", "Record not found!");
            }

            response.sendRedirect("Customer_Booking");

        } catch (Exception ex) {
            System.out.println("updateCustomerBooking: " + ex.getMessage());
        }
    }

    protected void updateSalesRecord(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String salesId = request.getParameter("id");
            String salesmanComments = request.getParameter("salesmanComments");

            TxnSalesRecord booking = salesFacade.find(salesId);

            if (booking != null) {

                booking.setSalesmanComments(salesmanComments);
                salesFacade.edit(booking);

                request.getSession().setAttribute("msg", "Comment saved!");
            } else {
                request.getSession().setAttribute("error", "Record not found!");
            }

            response.sendRedirect("Sls_Manage_Sales");
        } catch (Exception ex) {
            System.out.println("updateSalesRecord: " + ex.getMessage());
        }
    }

    protected void createSalesRecord(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String salesId = request.getParameter("id");

            MstMember salesman = (MstMember) request.getSession().getAttribute("user");

            String selectedCustomer = request.getParameter("customerSelection");
            String selectedCar = request.getParameter("carSelection");
            String salesmanComments = request.getParameter("salesmanComments");

            TxnSalesRecord booking = new TxnSalesRecord();
            MstCar car = carFacade.find(selectedCar);
            MstCustomer customer = customerFacade.find(selectedCustomer);

            if (customer == null) {
                request.getSession().setAttribute("error", "Customer not found!");
                response.sendRedirect("Sls_Manage_Sales");
                return;
            }

            if (car != null) {

                car.setStatus("Booked");

                booking.setTotalPayable(car.getPrice() + 15);
                booking.setSalesman(salesman);
                booking.setCustomer(customer);
                booking.setCar(car);
                booking.setSalesDate(LocalDate.now());
                booking.setOrderStatus("Booked");
                booking.setSalesmanComments(salesmanComments);
                salesFacade.create(booking);
                carFacade.edit(car);
                
                request.getSession().setAttribute("msg", "Booking has been saved!");
            } else {
                request.getSession().setAttribute("error", "Car not found!");
            }

            response.sendRedirect("Sls_Manage_Sales");
        } catch (Exception ex) {
            System.out.println("updateSalesRecord: " + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("booking_details.jsp");
        try {
            String auth = Session_Authenticator.VerifyLogin(request);

            if (auth.isEmpty()) {
                request.getSession().setAttribute("error", "Error: User not authorised!");
                response.sendRedirect("Login");
                return;
            }

            String salesId = request.getParameter("id");
            String mode = request.getParameter("mode");

            TxnSalesRecord data = new TxnSalesRecord();

            if (mode != "New") {
                data = salesFacade.find(salesId);
            } else {
                data.setOrderStatus("Pending");
                data.setSalesDate(LocalDate.now());
            }

            request.setAttribute("model", data);
            request.setAttribute("id", salesId);
            request.setAttribute("mode", mode);
            request.setAttribute("bookingFee", TxnSalesRecord.getBookingFee());

            rd.include(request, response);
        } catch (Exception ex) {
            System.out.println("Booking_Details: " + ex.getMessage());
        }
//        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mode = request.getParameter("mode");
        String auth = Session_Authenticator.VerifyLogin(request);

        if (auth.equals("Customer")) {
            updateCustomerBooking(request, response);
        } else if (mode.equals("New")) {
            createSalesRecord(request, response);
        } else {
            updateSalesRecord(request, response);
        }

//        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
