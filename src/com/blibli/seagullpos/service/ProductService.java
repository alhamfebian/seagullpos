package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.ProductDAO;
import com.blibli.seagullpos.model.ProductModel;
import com.blibli.seagullpos.model.SummaryList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/product/*")
public class ProductService extends HttpServlet {

    private ProductDAO productdao = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productdao = new ProductDAO();
        String currPage = request.getParameter("page");
        int currentPage = (currPage == null) ? 1 : Integer.parseInt(currPage);
        SummaryList<ProductModel> productSummaryList = productdao.getProductSummaryList(currentPage);


        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String productJSON = gson.toJson(productSummaryList);
        PrintWriter pw = response.getWriter();
        pw.write(productJSON);
    }
}
