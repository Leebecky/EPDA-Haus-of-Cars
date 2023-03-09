/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MstCustomerFacade;
import facade.MstMemberFacade;
import helper.File_Handler;
import helper.Session_Authenticator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.MstCustomer;
import model.MstMember;

/**
 *
 * @author leebe
 */
@WebServlet(name = "User_Profile", urlPatterns = {"/User_Profile"})

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class User_Profile extends HttpServlet {

    @EJB
    MstCustomerFacade cusFacade;

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
        String mode = request.getParameter("mode");
        String id = request.getParameter("id");

        try (PrintWriter out = response.getWriter()) {

            // Form Attributes
            String username = request.getParameter("username");
            String fullname = request.getParameter("fullname");
            String gender = request.getParameter("gender");
            String userRole = request.getParameter("userRole");
            String icNo = request.getParameter("icNo");
            String telNo = request.getParameter("telNo");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String email = request.getParameter("email");

//            String icImage = request.getParameter("icImage");
//            String drivingLicense = request.getParameter("drivingLicense");
            String icImagePath = "";
            String drivingLicensePath = "";

            if (userRole.equals("Customer")) {

                icImagePath = File_Handler.UploadImage(request, "icImage", "ic");
                drivingLicensePath = File_Handler.UploadImage(request, "drivingLicense", "driving_license");
            }

            // Updating Profile
            if (mode.equals("New")) {
                if (userRole.equals("Customer")) {
                    MstCustomer cust = MstCustomer.createNewCustomer(username, email, username + "-" + icNo);
                    cust.setUsername(username);
                    cust.setFullname(fullname);
                    cust.setGender(gender);
                    cust.setUserType(userRole);
                    cust.setIcNo(icNo);
                    cust.setTelNo(telNo);
                    cust.setDob(LocalDate.parse(dob));
                    cust.setAddress(address);
                    cust.setStatus("Approved");

                    if (icImagePath != null) {
                        cust.setIcImage(icImagePath);
                    }

                    if (drivingLicensePath != null) {
                        cust.setDrivingLicense(drivingLicensePath);
                    }

                    cusFacade.create(cust);
                } else { // Salesman & Admin
                    MstMember member = MstMember.createNewSalesman(username, email, username + "-" + icNo, "Approved");
                    member.setUsername(username);
                    member.setFullname(fullname);
                    member.setGender(gender);
                    member.setUserType(userRole);
                    member.setIcNo(icNo);
                    member.setTelNo(telNo);
                    member.setDob(LocalDate.parse(dob));
                    member.setAddress(address);

                    memberFacade.create(member);
                }

                request.getSession().setAttribute("msg", "User successfully added!");
                response.sendRedirect("Admin_Manage_Users");

            } else { // Edit
                if (userRole.equals("Customer")) {
                    MstCustomer cust = cusFacade.find(id);
                    cust.setUsername(username);
                    cust.setFullname(fullname);
                    cust.setGender(gender);
                    cust.setUserType(userRole);
                    cust.setIcNo(icNo);
                    cust.setTelNo(telNo);
                    cust.setDob(LocalDate.parse(dob));
                    cust.setAddress(address);

                    if (icImagePath != null) {
                        cust.setIcImage(icImagePath);
                    }

                    if (drivingLicensePath != null) {
                        cust.setDrivingLicense(drivingLicensePath);
                    }

                    cusFacade.edit(cust);
                } else { // Salesman & Admin
                    MstMember member = memberFacade.find(id);
                    member.setUsername(username);
                    member.setFullname(fullname);
                    member.setGender(gender);
                    member.setIcNo(icNo);
                    member.setTelNo(telNo);
                    member.setDob(LocalDate.parse(dob));
                    member.setAddress(address);

                    memberFacade.edit(member);
                }

                request.getSession().setAttribute("msg", "Profile successfully saved");
                response.sendRedirect("User_Profile?id=" + id + "&mode=" + mode);
            }
        } catch (Exception ex) {
            System.out.println("User_Profile: processRequest: " + ex.getMessage());
            request.getSession().setAttribute("error", "Unexpected error encountered: " + ex.getMessage());
            response.sendRedirect("User_Profile?id=" + id + "&mode=" + mode);
        }
    }

    protected void processImageUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String mode = request.getParameter("mode");

        try {

            // Upload File
            String filePath = File_Handler.UploadImage(request, "profileImage", "profile");

            if (filePath != null) {

                MstMember user = memberFacade.find(id);
                user.setProfileImage(filePath);
                memberFacade.edit(user);
            } else {
                request.getSession().setAttribute("error", "Error occurred when saving image.");
                response.sendRedirect("User_Profile?id=" + id + "&mode=" + mode);
                return;
            }

            request.getSession().setAttribute("msg", "Image successfully updated");
            response.sendRedirect("User_Profile?id=" + id + "&mode=" + mode);

        } catch (Exception ex) {
            System.out.println("User_Profile: processRequest: " + ex.getMessage());
            request.getSession().setAttribute("error", "Unexpected error encountered: " + ex.getMessage());
            response.sendRedirect("User_Profile?id=" + id + "&mode=" + mode);
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

        try {
            RequestDispatcher rd = request.getRequestDispatcher("user_profile.jsp");

            String auth = Session_Authenticator.VerifyLogin(request);

            // Authenticating if user is logged in
            if (auth.isEmpty()) {
                response.sendRedirect("Login");
                return;
            }

            String mode = request.getParameter("mode");
            String id = request.getParameter("id");

            // New User
            if (mode.equals("New")) {
                request.setAttribute("model", new MstMember());
            } else { // Existing user
                if (auth.equals("Customer")) {
                    MstCustomer cus = cusFacade.find(id);
                    request.setAttribute("model", cus);
                } else {
                    MstMember member = memberFacade.find(id);
                    request.setAttribute("model", member);
                }
            }

            request.setAttribute("id", id);
            request.setAttribute("mode", mode);
            rd.include(request, response);
        } catch (Exception ex) {
            System.out.println("User_Profile: doGet: " + ex.getMessage());

        }
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
        String requestType = request.getParameter("profileUpdateType");
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
