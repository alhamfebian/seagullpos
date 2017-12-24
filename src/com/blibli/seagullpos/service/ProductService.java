package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.ProductDAO;
import com.blibli.seagullpos.model.ProductModel;
import com.blibli.seagullpos.model.SummaryList;
import com.blibli.seagullpos.utility.RandomId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/product/*")
@MultipartConfig
public class ProductService extends HttpServlet {

    private ProductDAO productdao = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productdao = new ProductDAO();

        String productId = RandomId.generateProductId(16, 4, '-');
        String productName = request.getParameter("productName");
        int productCategoryId = Integer.parseInt(request.getParameter("productCategoryId"));
        int productStock = Integer.parseInt(request.getParameter("productStock"));
        int productPrice = Integer.parseInt(request.getParameter("productPrice"));
        Part filePart = request.getPart("productThumbnail");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        InputStream fileContent = null;



        try{
            File uploadDirectory = new File("D:Code/SeagullPOS/web/image/");
            File targetLocation = new File(uploadDirectory, fileName);
            fileContent = filePart.getInputStream();
            Files.copy(fileContent, targetLocation.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
        }
        ProductModel productModel = new ProductModel();
        productModel.setProductId(productId);
        productModel.setProductName(productName);
        productModel.setProductPrice(productPrice);
        productModel.setProductCategoryId(productCategoryId);
        productModel.setThumbnail(fileName);
        productModel.setProductStock(productStock);
        productModel.setProductLocation("A1");

        productdao.insertProduct(productModel);
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

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productdao = new ProductDAO();
        String productId = request.getParameter("id");
        String path = request.getParameter("file");
        productdao.deleteProduct(productId);

        File file = new File("D:Code/SeagullPOS/web/image/" + path);
        if(file.delete()){
            System.out.println("success");
        }else{
            System.out.println("failed");
        }

    }
}
