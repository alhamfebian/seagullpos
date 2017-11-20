package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.ProductDAO;
import com.blibli.seagullpos.model.ProductModel;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductService")
public class ProductService extends HttpServlet {

    private ProductDAO productdao = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productdao = new ProductDAO();

        List<ProductModel> listProduct = productdao.getAllProduct();

        String productJSON = new Gson().toJson(listProduct);
        PrintWriter pw = response.getWriter();

        pw.write(productJSON);
    }
}
