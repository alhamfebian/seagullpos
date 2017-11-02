package com.blibli.seagullpos.controller;

import com.blibli.seagullpos.dao.EmployeeDAO;
import com.blibli.seagullpos.model.EmployeeModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private EmployeeDAO dao = new EmployeeDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        EmployeeModel user = dao.authenticateUser(email, password);

        if(user != null){
            HttpSession session = request.getSession();
            session.setAttribute("user", user.getEmployeeName());
            session.setAttribute("email", user.getEmployeeEmail());
            session.setAttribute("role", user.getEmployeeRole());

            if(user.getEmployeeRole().equals("admin")){
                response.sendRedirect("dashboard.jsp");
            }else if(user.getEmployeeRole().equals("cashier")){
                response.sendRedirect("cashier.jsp");
            }
        }
        else{
            response.sendRedirect("login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
