package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.EmployeeDAO;
import com.blibli.seagullpos.model.EmployeeModel;
import com.blibli.seagullpos.model.SummaryList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import netscape.javascript.JSObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user/*")
public class UserService extends HttpServlet {
    EmployeeDAO dao = null;


    private String generateID(String role){
        dao = new EmployeeDAO();

        String id = dao.getLastID(role);
        int idNumber = id == "" ? 0 : Integer.parseInt(id.substring(2, 6));
        String newId = role.equals("admin") ? String.format("AD%04d", ++idNumber) : String.format("CH%04d", ++idNumber);

        return newId;
    }

    private boolean isRoleChange(String id, String role){
        if(id.substring(0, 2).equals("CH") && role.equals("Admin")) return true;
        else if(id.substring(0, 2).equals("AD") && role.equals("Cashier")) return true;
        return false;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        dao = new EmployeeDAO();


        EmployeeModel model = new EmployeeModel();
        String fullName = request.getParameter("fullname");
        String email = request.getParameter("staffEmail");
        String password = request.getParameter("staffPassword");
        String gender = request.getParameter("gender");
        String selectedGender = gender.equals("Male") ? "Male" : "Female";
        String role = request.getParameter("role");
        String id = generateID(role);

        model.setEmployeeName(fullName);
        model.setEmployeeEmail(email);
        model.setEmployeePassword(password);
        model.setEmployeeGender(selectedGender);
        model.setEmployeeRole(role);
        model.setEmployeeId(id);


        dao.insertUser(model);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dao = new EmployeeDAO();
        SummaryList<EmployeeModel> employeeSummaryList = null;

        int page = 1;
        if(request.getParameterMap().containsKey("page"))
            page = Integer.parseInt(request.getParameter("page"));

        if(request.getPathInfo() == null){
            employeeSummaryList = dao.getEmployeeSummaryList(page);
        }
        else if(request.getPathInfo().equals("/search")){
            String query = request.getParameter("query");
            employeeSummaryList = dao.searchEmployeeSummaryList(query, page);

        }

        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String listUserJSON = gson.toJson(employeeSummaryList);
        PrintWriter pw = response.getWriter();
        pw.write(listUserJSON);

    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        dao = new EmployeeDAO();
        BufferedReader br = request.getReader();
        String json = br.readLine();
        EmployeeModel employee = new Gson().fromJson(json, EmployeeModel.class);

        if(request.getPathInfo().equals("/update/changepassword")){
            dao.changePassword(employee.getEmployeeId(), employee.getEmployeePassword());
        }
        else{
            String currentId = employee.getEmployeeId();
            String role = employee.getEmployeeRole();
            String newId = isRoleChange(currentId, role)
                    ? generateID(role.toLowerCase()) : employee.getEmployeeId();

            employee.setEmployeeId(newId);

            dao.updateUserGeneralData(employee, currentId);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        dao = new EmployeeDAO();

        String deleteId = request.getParameter("id");

        dao.deleteUser(deleteId);
    }

}
