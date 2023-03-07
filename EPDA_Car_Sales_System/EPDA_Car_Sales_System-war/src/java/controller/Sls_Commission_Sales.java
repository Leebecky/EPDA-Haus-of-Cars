/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.TxnSalesRecordFacade;
import helper.Session_Authenticator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MstMember;
import model.TxnSalesRecord;

/**
 *
 * @author leebe
 */
@WebServlet(name = "Sls_Commission_Sales", urlPatterns = {"/Sls_Commission_Sales"})
public class Sls_Commission_Sales extends HttpServlet {
@EJB
TxnSalesRecordFacade salesFacade;
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
         RequestDispatcher rd = request.getRequestDispatcher("sls_commission_sales.jsp");
        try (PrintWriter out = response.getWriter()) {
            String auth = Session_Authenticator.VerifySalesman(request);
            
            if (auth != null && !auth.isEmpty()) {
                request.getSession().setAttribute("error", "Error: User not authenticated");
                response.sendRedirect(auth);
            }
            
            MstMember user = (MstMember)request.getSession().getAttribute("user");
            
            List<TxnSalesRecord> data = salesFacade.getPendingBookings();
            request.setAttribute("model", data);

            rd.include(request, response);
        } catch (Exception ex) {
            System.out.println("Sls_Manage_Sales: " + ex.getMessage());
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
//        RequestDispatcher rd = request.getRequestDispatcher("sls_commission_sales.jsp");
//
//        rd.include(request, response);
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
