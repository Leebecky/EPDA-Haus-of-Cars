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
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MstCar;
import model.TxnSalesRecord;

/**
 *
 * @author leebe
 */
@WebServlet(name = "Sls_Booking_Paid", urlPatterns = {"/Sls_Booking_Paid"})
public class Sls_Booking_Paid extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {

            // Authenticating User Privileges
            String auth = Session_Authenticator.VerifySalesman(request);
            if (!auth.isEmpty()) {
                request.getSession().setAttribute("error", "Error: User not authenticated");
                response.sendRedirect(auth);
                return;
            }

            String salesId = request.getParameter("salesId");
//            System.out.println("SALES: "+salesId);
            
            TxnSalesRecord sales = salesFacade.find(salesId);
            sales.setOrderStatus("Paid");
            
//            System.out.println("Sales: " + sales);
//            System.out.println("Sales: " + sales.getCar());
            
            MstCar car = sales.getCar();
            car.setStatus("Paid");
            
            salesFacade.edit(sales);
            carFacade.edit(car);
            
            request.getSession().setAttribute("msg", "Booking has been paid");
            response.sendRedirect("Sls_Manage_Sales");
            
        } catch (Exception ex) {
            System.out.println("Sls_Manage_Sales_Cancel: " + ex.getMessage());
            request.getSession().setAttribute("error", "Unexpected error occured: " + ex.getMessage());
            response.sendRedirect("Sls_Manage_Sales");
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
