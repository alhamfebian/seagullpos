package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.EmployeeDAO;
import com.blibli.seagullpos.model.EmployeeModel;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/user")
public class UserService extends HttpServlet {
    EmployeeDAO dao = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dao = new EmployeeDAO();
        List<EmployeeModel> listAllUser = dao.getAllUser();
        String listUser = new Gson().toJson(listAllUser);
        PrintWriter pw = response.getWriter();
        pw.write(listUser);
    }
}
