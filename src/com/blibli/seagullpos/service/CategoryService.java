package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.CategoryDAO;
import com.blibli.seagullpos.model.CategoryModel;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CategoryService")
public class CategoryService extends HttpServlet {
    private CategoryDAO categoryDAO = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        categoryDAO = new CategoryDAO();

        List<CategoryModel> listCategory = categoryDAO.getAllCategory();

        String listCategoryJSON = new Gson().toJson(listCategory);
        PrintWriter pw = response.getWriter();

        pw.write(listCategoryJSON);
    }
}
