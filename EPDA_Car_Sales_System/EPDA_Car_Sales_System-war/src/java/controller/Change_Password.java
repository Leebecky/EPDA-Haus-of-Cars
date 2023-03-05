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
@WebServlet(name = "Change_Password", urlPatterns = {"/Change_Password"})
public class Change_Password extends HttpServlet {

    @EJB
    private MstMemberFacade memberFacade;

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
        String id = request.getParameter("userId");
        
        try (PrintWriter out = response.getWriter()) {
            String password = request.getParameter("password");

            MstMember user = memberFacade.find(id);
            user.setPassword(password);
            memberFacade.edit(user);
            
 request.getSession().setAttribute("msg", "Password successfully updated");
            response.sendRedirect("User_Profile?id=" + id + "&mode=Edit");
//            rd.forward(request, response);
        } catch (Exception ex) {
            System.out.println("Change_Password: processRequest: " + ex.getMessage());
            request.getSession().setAttribute("error", "Unexpected error occurred: " + ex.getMessage());
            response.sendRedirect("User_Profile?id=" + id + "&mode=Edit");

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
