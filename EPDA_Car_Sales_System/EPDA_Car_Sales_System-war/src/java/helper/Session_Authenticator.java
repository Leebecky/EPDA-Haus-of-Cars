/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.MstMember;

/**
 *
 * @author leebe
 */
public class Session_Authenticator {

    /**
     * *
     * @param req
     * @return String Checks if the user is logged in and is an Admin. Returns
     * user to their home page if not Admin
     */
    public static String VerifyAdmin(HttpServletRequest req) {
        HttpSession ses = req.getSession(false);
        try {

            // Authenticating User Privileges
            if (ses.getAttribute("user") == null) {
                return "Login";
            } else {
                MstMember user = (MstMember) ses.getAttribute("user");
                if (user.getUserType().equals("Customer")) {
                    return "Home";
                } else if (user.getUserType().equals("Salesman")) {
                    return "Home";
                }
            }
            return "";
        } catch (Exception ex) {
            System.out.println("Session_Authenticator: VerifyUser: " + ex.getMessage());
            return "Login";
        }
    }

    /**
     * *
     * @param req
     * @return String Checks if the user is logged in and is a Salesman. Returns
     * user to their home page if not Salesman
     */
    public static String VerifySalesman(HttpServletRequest req) {
        HttpSession ses = req.getSession(false);
        try {

            // Authenticating User Privileges
            if (ses.getAttribute("user") == null) {
                return "Login";
            } else {
                MstMember user = (MstMember) ses.getAttribute("user");
                if (user.getUserType().equals("Customer")) {
                    return "Home";
                } else if (user.getUserType().equals("Admin")) {
                    return "Admin_Home";
                }
            }
            return "";
        } catch (Exception ex) {
            System.out.println("Session_Authenticator: VerifyUser: " + ex.getMessage());
            return "Login";
        }
    }

    /**
     * *
     * @param req
     * @return String Checks if the user is logged in and is a Customer. Returns
     * user to their home page if not Customer
     */
    public static String VerifyCustomer(HttpServletRequest req) {
        HttpSession ses = req.getSession(false);
        try {

            // Authenticating User Privileges
            if (ses.getAttribute("user") == null) {
                return "Login";
            } else {
                MstMember user = (MstMember) ses.getAttribute("user");
                if (user.getUserType().equals("Admin")) {
                    return "Admin_Home";
                } else if (user.getUserType().equals("Salesman")) {
                    return "Home";
                }
            }
            return "";
        } catch (Exception ex) {
            System.out.println("Session_Authenticator: VerifyUser: " + ex.getMessage());
            return "Login";
        }
    }
}
