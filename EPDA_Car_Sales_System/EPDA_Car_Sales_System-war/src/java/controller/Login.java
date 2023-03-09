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
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

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
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            MstMember member = memberFacade.loginUser(email, password);

            if (member == null) {
                request.getSession().setAttribute("error", "No account with these credentials found!");
                response.sendRedirect("Login");
//                rd.forward(request, response);
                return;
            } else {
                if (member.getStatus().equals("Pending")) {
                    request.getSession().setAttribute("error", "User has not been approved yet. Please wait to be approved");
                    response.sendRedirect("Login");
                    return;
                }
                
                if (member.getStatus().equals("Inactive")) {
                    request.getSession().setAttribute("error", "Account has been deactivated. Please contact the Admin to reactivate your account.");
                    response.sendRedirect("Login");
                    return;
                }
                

                // On Success, Redirect to Customer Home
                HttpSession ses = request.getSession();

                member.setPassword("");
                ses.setAttribute("user", member);
                if (member.getUserType().equals("Admin")) {
                    response.sendRedirect("Admin_Home");
                } else if (member.getUserType().equals("Salesman")) {
                    response.sendRedirect("Sls_Manage_Sales");
                } else {
                    response.sendRedirect("Catalogue_Cars");
                }

                return;
            }

        } catch (Exception ex) {
            request.getSession().setAttribute("error", "Unexpected error occured: " + ex.getMessage());
            response.sendRedirect("Login");
//            rd.forward(request, response);
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
//        processRequest(request, response);
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
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
