/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import facade.MstCarFacade;
import facade.MstMemberFacade;
import facade.TxnSalesRecordFacade;
import helper.Session_Authenticator;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MstMember;

/**
 *
 * @author leebe
 */
@WebServlet(name = "Admin_Home", urlPatterns = {"/Admin_Home"})
public class Admin_Home extends HttpServlet {

    @EJB
    TxnSalesRecordFacade salesFacade;

    @EJB
    MstMemberFacade memberFacade;

    @EJB
    MstCarFacade carFacade;

    String[] monthList = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

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
            RequestDispatcher rd = request.getRequestDispatcher("admin_home.jsp");
            // Authenticating User Privileges
            String auth = Session_Authenticator.VerifyAdmin(request);
            if (!auth.isEmpty()) {
                response.sendRedirect(auth);
                return;
            }

            // Init Report Filters
            if (request.getSession().getAttribute("toDate") == null) {
                request.getSession().setAttribute("toDate", LocalDate.now());
                request.getSession().setAttribute("currentMonth", monthList[LocalDate.now().getMonthValue() - 1]);
                request.getSession().setAttribute("currentYear", LocalDate.now().getYear());
            }

            if (request.getSession().getAttribute("fromDate") == null) {
                request.getSession().setAttribute("fromDate", LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
            }

//            report_salesByBrand(request, response);
            rd.include(request, response);
        } catch (Exception ex) {
            System.out.println("Admin_Home: processRequest: " + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Sales Report Actions.">
    protected void report_salesByBrand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> data = salesFacade.getSalesByBrand();

            List<String> brandList = new ArrayList<>();
            List<Integer> dataList = new ArrayList<>();

            for (int i = 0; i < data.size(); i++) {
                brandList.add(data.get(i)[0].toString());
                dataList.add(Integer.parseInt(data.get(i)[1].toString()));
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(dataList));
            jsonObj.add("label", new Gson().toJson(brandList));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: report_salesByBrand: " + ex.getMessage());
        }
    }

    protected void rpt_SalesPerMonth(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = salesFacade.rptSalesPerMonth(request);

            List<String> labels = new ArrayList<>();
            List<Double> data = new ArrayList<>();

            for (int i = 0; i < rpt.size(); i++) {
                int month = Integer.parseInt(rpt.get(i)[0].toString());
                labels.add(monthList[month - 1]);
                data.add(Double.parseDouble(rpt.get(i)[1].toString()));
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_SalesPerMonth: " + ex.getMessage());
        }
    }

    protected void rpt_BookingsByStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = salesFacade.rptBookingsByStatus(request);

            List<String> labels = new ArrayList<>();
            List<Integer> data = new ArrayList<>();

            for (int i = 0; i < rpt.size(); i++) {
                labels.add(rpt.get(i)[0].toString());
                data.add(Integer.parseInt(rpt.get(i)[1].toString()));
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_BookingsByStatus: " + ex.getMessage());
        }
    }

    protected void rpt_SalesCards(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = salesFacade.rptSalesCard(request);

            List<String> labels = new ArrayList<>();
            List<Double> data = new ArrayList<>();

            for (int i = 0; i < rpt.size(); i++) {
                labels.add(rpt.get(i)[0].toString());
                data.add(Double.parseDouble(rpt.get(i)[1].toString()));
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_SalesCards: " + ex.getMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Report Filter.">
    protected void rpt_setFilters(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession ses = request.getSession();

            String filterToDate = request.getParameter("rpt_toDateFilter");
            String filterFromDate = request.getParameter("rpt_fromDateFilter");

            LocalDate toDate = LocalDate.now();
            LocalDate fromDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());

            if (filterToDate != null && !filterToDate.isEmpty()) {
                toDate = LocalDate.parse(filterToDate);
            }

            if (filterFromDate != null && !filterFromDate.isEmpty()) {
                fromDate = LocalDate.parse(filterFromDate);
            }

            ses.setAttribute("currentMonth", monthList[toDate.getMonthValue() - 1]);
            ses.setAttribute("currentYear", toDate.getYear());
            ses.setAttribute("toDate", toDate);
            ses.setAttribute("fromDate", fromDate);

            response.sendRedirect("Admin_Home");

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_setFilters: " + ex.getMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Customer Report Actions.">
    protected void rpt_BookingsByCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            List<MstMember> customers = memberFacade.getAvailableCustomers();

            // Retrieve list of distinct customers that are approved
            List<String> customerNames = new ArrayList<>();
            for (MstMember user : customers) {
                customerNames.add(user.getFullname());
            }

            JsonArrayBuilder jsonRptArray = Json.createArrayBuilder();
            JsonArrayBuilder jsonOutputArray = Json.createArrayBuilder();
            JsonObjectBuilder json = Json.createObjectBuilder();

            jsonOutputArray.add(new Gson().toJson(customerNames));

            // Retrieve Data
            for (String name : customerNames) {
                List<Object[]> rptData = salesFacade.rptBookingsByCustomers(request, name);
                List<String> orderStatus = new ArrayList<>();
                List<Integer> orderData = new ArrayList<>();
//                System.out.println("DATA: " + new Gson().toJson(rptData));
                //  Count | Status
                for (int i = 0; i < rptData.size(); i++) {
                    orderStatus.add(rptData.get(i)[1].toString());
                    orderData.add(Integer.parseInt(rptData.get(i)[0].toString()));
                }

                json.add("name", name);

                if (orderStatus.contains("Booked")) {
                    int index = orderStatus.indexOf("Booked");
                    json.add("Booked", orderData.get(index));
                } else {
                    json.add("Booked", 0);
                }

                if (orderStatus.contains("Paid")) {
                    int index = orderStatus.indexOf("Paid");
                    json.add("Paid", orderData.get(index));
                } else {
                    json.add("Paid", 0);
                }

                if (orderStatus.contains("Cancelled")) {
                    int index = orderStatus.indexOf("Cancelled");
                    json.add("Cancelled", orderData.get(index));
                } else {
                    json.add("Cancelled", 0);
                }

                if (orderStatus.contains("Pending Salesman")) {
                    int index = orderStatus.indexOf("Pending Salesman");
                    json.add("Pending Salesman", orderData.get(index));
                } else {
                    json.add("Pending Salesman", 0);
                }

                jsonRptArray.add(json);

            }

            jsonOutputArray.add(jsonRptArray);
            out.print(jsonOutputArray.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_BookingsByCustomers: " + ex.getMessage());
        }
    }

    protected void rpt_customerCards(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = memberFacade.rptCustomerCard(request);

            List<String> labels = new ArrayList<>();
            List<String> data = new ArrayList<>();

            for (int i = 0; i < rpt.size(); i++) {
                labels.add(rpt.get(i)[0].toString());
                data.add(rpt.get(i)[1].toString());
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_CustomerCards: " + ex.getMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Salesman Report Actions">
    protected void rpt_SalesmanSales(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = salesFacade.rptSalesmanSales(request, "Paid");

            List<String> labels = new ArrayList<>();
            List<Integer> data = new ArrayList<>();

            // Name | Count
            for (int i = 0; i < rpt.size(); i++) {
                labels.add(rpt.get(i)[0].toString());
                data.add(Integer.parseInt(rpt.get(i)[1].toString()));
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_SalesmanSales: " + ex.getMessage());
        }
    }

    protected void rpt_SalesmanCancelled(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = salesFacade.rptSalesmanSales(request, "Cancelled");

            List<String> labels = new ArrayList<>();
            List<Integer> data = new ArrayList<>();

            for (int i = 0; i < rpt.size(); i++) {
                labels.add(rpt.get(i)[0].toString());
                data.add(Integer.parseInt(rpt.get(i)[1].toString()));
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_SalesmanCancelled: " + ex.getMessage());
        }
    }

    protected void rpt_salesmanAvgRating(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = salesFacade.rptSalesmanAvgRating(request);

            List<String> labels = new ArrayList<>();
            List<Double> data = new ArrayList<>();

            // Name | Count
            for (int i = 0; i < rpt.size(); i++) {
                labels.add(rpt.get(i)[0].toString());
                data.add(Double.parseDouble(rpt.get(i)[1].toString()));
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_salesmanAvgRating: " + ex.getMessage());
        }
    }

    protected void rpt_salesmanCards(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = memberFacade.rptSalesmanCard(request);

            List<String> labels = new ArrayList<>();
            List<String> data = new ArrayList<>();

            for (int i = 0; i < rpt.size(); i++) {
                labels.add(rpt.get(i)[0].toString());
                data.add(rpt.get(i)[1].toString());
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_CustomerCards: " + ex.getMessage());
        }
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Car Reports">
    protected void rpt_carColours(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = salesFacade.rptCarColours(request);

            List<String> labels = new ArrayList<>();
            List<Integer> data = new ArrayList<>();

            // Colour | Count
            for (int i = 0; i < rpt.size(); i++) {
                labels.add(rpt.get(i)[0].toString());
                data.add(Integer.parseInt(rpt.get(i)[1].toString()));
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_carColours: " + ex.getMessage());
        }
    }

    protected void rpt_carSales(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rptCarSales = salesFacade.rptCarSalesByBrand(request);

            List<String> labels = new ArrayList<>();
//            List<Integer> data = new ArrayList<>();

            JsonObjectBuilder jsonSales = Json.createObjectBuilder();
            JsonObjectBuilder jsonRating = Json.createObjectBuilder();
            JsonArrayBuilder salesDataset = Json.createArrayBuilder();
            JsonArrayBuilder ratingDataset = Json.createArrayBuilder();

            // Label | Sum Sales | Avg Rating
            for (int i = 0; i < rptCarSales.size(); i++) {
                labels.add(rptCarSales.get(i)[0].toString());
//                data.add(Integer.parseInt(rpt.get(i)[1].toString()));
                jsonSales.add("brand", rptCarSales.get(i)[0].toString());
                jsonSales.add("data", Double.parseDouble(rptCarSales.get(i)[1].toString()));
                salesDataset.add(jsonSales);

                jsonRating.add("brand", rptCarSales.get(i)[0].toString());
                jsonRating.add("data", Float.parseFloat(rptCarSales.get(i)[2].toString()));
                ratingDataset.add(jsonRating);
            }

            JsonObjectBuilder jsonObject = Json.createObjectBuilder();

            jsonObject.add("labels", new Gson().toJson(labels));
            jsonObject.add("sales", salesDataset);
            jsonObject.add("rating", ratingDataset);

            out.print(jsonObject.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_carSales: " + ex.getMessage());
        }
    }

    protected void rpt_carCards(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> rpt = carFacade.rptCarCard(request);

            List<String> labels = new ArrayList<>();
            List<String> data = new ArrayList<>();

            for (int i = 0; i < rpt.size(); i++) {
                labels.add(rpt.get(i)[0].toString());
                data.add(rpt.get(i)[1].toString());
            }

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();

            jsonObj.add("data", new Gson().toJson(data));
            jsonObj.add("labels", new Gson().toJson(labels));

            out.print(jsonObj.build().toString());

        } catch (Exception ex) {
            System.out.println("Admin_Home: rpt_carCards: " + ex.getMessage());
        }
    }

    // </editor-fold>
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

        String report = request.getParameter("report");
        switch (report) {
            case "filter":
                rpt_setFilters(request, response);
                break;
            case "rpt_salesPerMonth":
                rpt_SalesPerMonth(request, response);
                break;
            case "rpt_BookingsByStatus":
                rpt_BookingsByStatus(request, response);
                break;
            case "rpt_SalesCards":
                rpt_SalesCards(request, response);
                break;
            case "rpt_BookingsByCustomers":
                rpt_BookingsByCustomers(request, response);
                break;
            case "rpt_customerCards":
                rpt_customerCards(request, response);
                break;
            case "rpt_salesmanSales":
                rpt_SalesmanSales(request, response);
                break;
            case "rpt_salesmanCancelled":
                rpt_SalesmanCancelled(request, response);
                break;
            case "rpt_salesmanAvgRating":
                rpt_salesmanAvgRating(request, response);
                break;
            case "rpt_salesmanCards":
                rpt_salesmanCards(request, response);
                break;
            case "rpt_carColours":
                rpt_carColours(request, response);
                break;
            case "rpt_carSales":
                rpt_carSales(request, response);
                break;
            case "rpt_carCards":
                rpt_carCards(request, response);
                break;
            default:
                processRequest(request, response);
                break;
        }
//        processRequest(request, response);
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
