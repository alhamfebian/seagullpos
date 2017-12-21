package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.ProductCategoryDAO;
import com.blibli.seagullpos.model.ProductCategoryModel;
import com.blibli.seagullpos.model.SummaryList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/productcategory/*")
public class ProductCategoryService extends HttpServlet {

    ProductCategoryDAO productCategoryDAO = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productCategoryDAO = new ProductCategoryDAO();

        String categoryName = request.getParameter("categoryName");
        ProductCategoryModel productCategory = new ProductCategoryModel();
        productCategory.setCategoryName(categoryName);
        System.out.println(productCategory.getCategoryName());
        productCategoryDAO.insertCategory(productCategory);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        productCategoryDAO = new ProductCategoryDAO();
        String currPage = request.getParameter("page");
        int currentPage = currPage == null ? 1 : Integer.parseInt(currPage);
        SummaryList<ProductCategoryModel> productCategorySummaryList = productCategoryDAO.getProductCategorySummaryList(currentPage);
        PrintWriter pw = response.getWriter();

        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        String productCategoryJSON = gson.toJson(productCategorySummaryList);

        pw.write(productCategoryJSON);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productCategoryDAO = new ProductCategoryDAO();
        BufferedReader br = request.getReader();
        String json = br.readLine();
        ProductCategoryModel productCategory = new Gson().fromJson(json, ProductCategoryModel.class);
        productCategoryDAO.updateProductCategory(productCategory);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productCategoryDAO = new ProductCategoryDAO();
        int productCategoryId = Integer.parseInt(request.getParameter("id"));
        productCategoryDAO.deleteProductCategory(productCategoryId);
    }
}
