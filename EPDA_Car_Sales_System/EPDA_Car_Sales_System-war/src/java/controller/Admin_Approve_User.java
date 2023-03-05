/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MstMemberFacade;
import helper.Session_Authenticator;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
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
@WebServlet(name = "Admin_Approve_User", urlPatterns = {"/Admin_Approve_User"})
public class Admin_Approve_User extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            // Authenticating User Privileges
            String auth = Session_Authenticator.VerifyAdmin(request);
            if (!auth.isEmpty()) {
                response.sendRedirect(auth);
                return;
            }

            String userId = request.getParameter("approveUserId");

            MstMember member = memberFacade.find(userId);
            member.setStatus("Approved");
            memberFacade.edit(member);
           
            request.getSession().setAttribute("msg", "User successfully approved");
            response.sendRedirect("Admin_Manage_Users");
        } catch (Exception ex) {
            System.out.println("Admin_Approve_User: " + ex.getMessage());
            request.getSession().setAttribute("error", "Unexpected error occured: " + ex.getMessage());
            response.sendRedirect("Admin_Manage_Users");
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
