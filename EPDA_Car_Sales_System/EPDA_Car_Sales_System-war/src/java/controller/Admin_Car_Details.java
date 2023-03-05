/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MstCarFacade;
import helper.File_Handler;
import helper.Session_Authenticator;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MstCar;
import model.MstCustomer;
import model.MstMember;

/**
 *
 * @author leebe
 */
@WebServlet(name = "Admin_Car_Details", urlPatterns = {"/Admin_Car_Details"})

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class Admin_Car_Details extends HttpServlet {

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
        String mode = request.getParameter("mode");
        String id = request.getParameter("id");

        try (PrintWriter out = response.getWriter()) {

            // Form Attributes
            String brand = request.getParameter("brand");
            String model = request.getParameter("model");
            String colour = request.getParameter("colour");
            String transmissionType = request.getParameter("transmissionType");
            String price = request.getParameter("price");
            String capacity = request.getParameter("capacity");

            Double carPrice = 0.0;
            int carCapacity = 2;

            if (price != null && !price.isEmpty()) {
                carPrice = Double.parseDouble(price);
            }

            if (capacity != null && !capacity.isEmpty()) {
                carCapacity = Integer.parseInt(capacity);
            }

//            String icImage = request.getParameter("icImage");
//            String drivingLicense = request.getParameter("drivingLicense");
            String imagePath = File_Handler.UploadImage(request, "carImage", "car_image");

            // Updating Details
            MstCar car = new MstCar();

            if (mode.equals("Edit")) {
                car = carFacade.find(id);
            }

            car.setBrand(brand);
            car.setModel(model);
            car.setColour(colour);
            car.setTransmissionType(transmissionType);
            car.setPrice(carPrice);
            car.setCapacity(carCapacity);

            if (imagePath != null) {
                car.setCarImage(imagePath);
            }

            if (mode.equals("New")) {
                car.setStatus("Available");
                carFacade.create(car);

                request.getSession().setAttribute("msg", "Car successfully added!");
                response.sendRedirect("Admin_Manage_Cars");

            } else { // Edit

                carFacade.edit(car);

                request.getSession().setAttribute("msg", "Details successfully saved");
                response.sendRedirect("Admin_Car_Details?id=" + id + "&mode=" + mode);
            }
        } catch (Exception ex) {
            System.out.println("Admin_Car_Details: processRequest: " + ex.getMessage());
            request.getSession().setAttribute("error", "Unexpected error encountered: " + ex.getMessage());
            response.sendRedirect("Admin_Car_Details?id=" + id + "&mode=" + mode);
        }
    }

    protected void processImageUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String mode = request.getParameter("mode");

        try {

            // Upload File
            String filePath = File_Handler.UploadImage(request, "carImage", "car_image");

            if (filePath != null) {

                MstCar car = carFacade.find(id);
                car.setCarImage(filePath);
                carFacade.edit(car);
            } else {
                request.getSession().setAttribute("error", "Error occurred when saving image.");
                response.sendRedirect("Admin_Car_Details?id=" + id + "&mode=" + mode);
                return;
            }

            request.getSession().setAttribute("msg", "Image successfully updated");
            response.sendRedirect("Admin_Car_Details?id=" + id + "&mode=" + mode);

        } catch (Exception ex) {
            System.out.println("Admin_Car_Details: processRequest: " + ex.getMessage());
            request.getSession().setAttribute("error", "Unexpected error encountered: " + ex.getMessage());
            response.sendRedirect("Admin_Car_Details?id=" + id + "&mode=" + mode);
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
        RequestDispatcher rd = request.getRequestDispatcher("admin_car_details.jsp");

        String auth = Session_Authenticator.VerifyLogin(request);

        // Authenticating if user is logged in
        if (auth.isEmpty()) {
            response.sendRedirect("Login");
            return;
        }
        try {
            String mode = request.getParameter("mode");
            String id = request.getParameter("id");

            // New car
            if (mode.equals("New")) {
                request.setAttribute("model", new MstCar());
            } else { // Existing car
                MstCar data = carFacade.find(id);
                request.setAttribute("model", data);
            }

            request.setAttribute("id", id);
            request.setAttribute("mode", mode);
            rd.include(request, response);
        } catch (Exception ex) {
            System.out.println("Admin_Car_Details: doGet: " + ex.getMessage());
        }
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
        String requestType = request.getParameter("carUpdateType");
        if (requestType.equals("imageUpdate")) {
            processImageUpdate(request, response);
        } else {
            processRequest(request, response);
        }
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
