/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.ReportDAO;
import DAO.UserDAO;
import Model.User;
import Model.Report;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class ReportsManagementController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReportsManagementController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportsManagementController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("AdminReports.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String columnIndex = request.getParameter("columnIndex");
        String columnDirection = request.getParameter("columnDirection");
        ReportDAO dao = new ReportDAO();
        ArrayList<Report> list = setUserFullnameInReport(dao.loadReports(start, length, columnIndex, columnDirection));
        int recordsTotal = dao.getTotalRecords();
        int recordsFiltered = dao.getTotalRecords();
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("recordsTotal", recordsTotal);
        jsonObject.addProperty("recordsFiltered", recordsFiltered);
        jsonObject.add("data", gson.toJsonTree(list ));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
    }
    public ArrayList<Report> setUserFullnameInReport(ArrayList<Report> list) {
        UserDAO userDAO = new UserDAO();
        for (Report r : list) {
            String createdbyFullname = userDAO.getFullnameByUserid(r.getCreatedBy());
            String buyerFullname = userDAO.getFullnameByUserid(r.getBuyerId());
            String sellerbyFullname = userDAO.getFullnameByUserid(r.getSellerId());
            r.setCreatedByFullname(createdbyFullname);
            r.setBuyerFullname(buyerFullname);
            r.setSellerFullname(sellerbyFullname);
        }
        return list;
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
