package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.EmployeeDAO;
import com.blibli.seagullpos.model.EmployeeModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import netscape.javascript.JSObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        String userJSON = new Gson().toJson(model);
        response.getWriter().write(userJSON);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dao = new EmployeeDAO();
        List<EmployeeModel> listAllUser = dao.getAllUser();
        String listUser = new Gson().toJson(listAllUser);
        PrintWriter pw = response.getWriter();
        pw.write(listUser);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        dao = new EmployeeDAO();

        String deleteId = request.getQueryString();

        dao.deleteUser(deleteId);
    }

}
