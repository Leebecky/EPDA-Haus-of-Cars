/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MstMemberFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MstMember;

/**
 *
 * @author leebe
 */
@WebServlet(name = "Reset_Password", urlPatterns = {"/Reset_Password"})
public class Reset_Password extends HttpServlet {

    @EJB
    MstMemberFacade memberFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //TODO IMPLEMENT
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("resetEmail");

            MstMember user = memberFacade.getUser(email);

            if (user != null) {
                if (user.getStatus().equals("Pending")) {
                    request.getSession().setAttribute("error", "User has not been approved yet. Please wait to be approved");
                } else {
                    user.setPassword(user.getUsername() + "-" + user.getIcNo());
                    memberFacade.edit(user);
                    request.getSession().setAttribute("msg", "Password has been reset to the default combination: [username]-[ic number]");
                }
            } else {
                request.getSession().setAttribute("error", "No users with this email!");
            }
            response.sendRedirect("Login");
//            rd.forward(request, response);
        } catch (Exception ex) {
            System.out.println("Reset_Password: " + ex.getMessage());
            request.getSession().setAttribute("error", "Unexpected error encountered");
            response.sendRedirect("Login");
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
