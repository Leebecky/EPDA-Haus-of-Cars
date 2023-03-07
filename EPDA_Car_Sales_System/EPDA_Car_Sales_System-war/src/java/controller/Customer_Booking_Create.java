/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MstCarFacade;
import facade.TxnSalesRecordFacade;
import helper.Session_Authenticator;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MstCar;
import model.MstCustomer;
import model.TxnSalesRecord;

/**
 *
 * @author leebe
 */
@WebServlet(name = "Customer_Create_Booking", urlPatterns = {"/Customer_Booking_Create"})
public class Customer_Booking_Create extends HttpServlet {

    @EJB
    TxnSalesRecordFacade salesFacade;

    @EJB
    MstCarFacade carFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//         RequestDispatcher rd = request.getRequestDispatcher("catalogue_cars.jsp");

        try (PrintWriter out = response.getWriter()) {

            String auth = Session_Authenticator.VerifyCustomer(request);
            if (auth != null && !auth.isEmpty()) {
                request.getSession().setAttribute("error", "Please login as a customer to create a booking!");
                response.sendRedirect("Login");
                return;
            }

            String carId = request.getParameter("carId");
            MstCar car = carFacade.find(carId);

            MstCustomer customer = (MstCustomer) request.getSession().getAttribute("user");

            if (car != null) {

                car.setStatus("Booked");

                TxnSalesRecord booking = new TxnSalesRecord();
                booking.setCar(car);
                booking.setOrderStatus("Pending Salesman");
                booking.setSalesDate(LocalDateTime.now());
                booking.setCustomer(customer);
                booking.setCarRating(0);
                booking.setSalesmanRating(0);
                booking.setTotalPayable(car.getPrice() + TxnSalesRecord.getBookingFee());

                carFacade.edit(car);
                salesFacade.create(booking);
                request.getSession().setAttribute("msg", "Booking created");
            } else {
                request.getSession().setAttribute("error", "Car not found");
            }

            response.sendRedirect("Catalogue_Cars");

        } catch (Exception ex) {
            System.out.println("Customer_Booking_Create: " + ex.getMessage());
            request.getSession().setAttribute("error", "Unexpected error occurred: " + ex.getMessage());
            response.sendRedirect("Catalogue_Cars");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
