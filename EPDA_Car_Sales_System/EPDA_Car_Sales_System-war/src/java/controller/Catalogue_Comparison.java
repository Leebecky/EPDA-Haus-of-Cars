/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MstCarFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MstCar;

/**
 *
 * @author leebe
 */
@WebServlet(name = "Catalogue_Comparison", urlPatterns = {"/Catalogue_Comparison"})
public class Catalogue_Comparison extends HttpServlet {

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
            String carId = request.getParameter("carId");

            ArrayList<String> comparisonList = (ArrayList<String>) request.getSession().getAttribute("comparison");
            if (comparisonList == null) {
                comparisonList = new ArrayList<>();
            }
            comparisonList.add(carId);

            request.getSession().setAttribute("comparison", comparisonList);

            response.sendRedirect("Catalogue_Cars");
        } catch (Exception ex) {
            System.out.println("Catalogue_Comparison: processRequest: " + ex.getMessage());
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
        RequestDispatcher rd = request.getRequestDispatcher("catalogue_comparison.jsp");

        ArrayList<String> comparisonList = (ArrayList<String>) request.getSession().getAttribute("comparison");
        if (comparisonList == null) {
            comparisonList = new ArrayList<>();
        }

        List<MstCar> carComparisonList = new ArrayList<>();
        for (String car : comparisonList) {
            carComparisonList.add(carFacade.find(car));
        }

        request.setAttribute("model", carComparisonList);
        rd.include(request, response);
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
