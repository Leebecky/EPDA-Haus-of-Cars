/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MstCustomerFacade;
import facade.MstMemberFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MstCustomer;
import model.MstMember;

/**
 *
 * @author leebe
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {
    
    @EJB
    MstMemberFacade memberFacade = new MstMemberFacade();
    @EJB
    MstCustomerFacade customerFacade = new MstCustomerFacade();

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
//        RequestDispatcher rd = request.getRequestDispatcher("register.jsp");

        try (PrintWriter out = response.getWriter()) {
            // Registration Verification
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String icNo = request.getParameter("icNo");
            String userRole = request.getParameter("userRole");
            
            MstMember member = memberFacade.getUser(email);
            if (member == null) {
                if (userRole.equals("Customer")) {
                    
                    MstCustomer cus = MstCustomer.createNewCustomer(username, email, password);
                    cus.setIcNo(icNo);
                    customerFacade.create(cus);

                    // On Success, Redirect to Customer Home
                    MstMember loggedInUser = memberFacade.getUser(email);
                    
                    HttpSession ses = request.getSession();
                    ses.setAttribute("msg", "User has been registered. Please wait for account to be approved");
//                    ses.setAttribute("user", loggedInUser);
                    
                    response.sendRedirect("Login");
                } else {
                    MstMember salesman = MstMember.createNewSalesman(username, email, password, "Pending");
                    salesman.setIcNo(icNo);
                    memberFacade.create(salesman);

                    // On Success, Redirect to Salesman Home
                    MstMember loggedInUser = memberFacade.getUser(email);
                    
                    HttpSession ses = request.getSession();
                    ses.setAttribute("msg", "User has been registered. Please wait for account to be approved");
//                    ses.setAttribute("user", loggedInUser);
                    
                    response.sendRedirect("Login");
                }
                
            } else {
                request.getSession().setAttribute("error", "This email already has an account!");
                response.sendRedirect("Register");
//                rd.forward(request, response);
                return;
            }
            
        } catch (Exception ex) {
            request.getSession().setAttribute("error", "Unexpected error occured: " + ex.getMessage());
            response.sendRedirect("Register");
            return;
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
        RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
        rd.include(request, response);
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
