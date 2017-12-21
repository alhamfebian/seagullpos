package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.ProductCategoryModel;
import com.blibli.seagullpos.model.SummaryList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDAO {
    private Connection connection;
    private PreparedStatement ps;
    private List<ProductCategoryModel> listProductCategory = null;
    private SummaryList<ProductCategoryModel> productCategorySummaryList = null;

    public ProductCategoryDAO(){}

    public List<ProductCategoryModel> getAllCategory(int limit, int offset){
        String query = "SELECT * FROM productcategory LIMIT ? OFFSET ?";

        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();
            listProductCategory = processProductCategoryRow(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listProductCategory;
    }

    public boolean insertCategory(ProductCategoryModel productCategory){
        String query = "INSERT INTO productcategory (productcategoryname) VALUES (?)";
        boolean insertStatus = false;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, productCategory.getCategoryName());
            insertStatus = ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return insertStatus;
    }

    public boolean updateProductCategory(ProductCategoryModel productCategory){
        String query = "UPDATE productcategory " +
                "SET " +
                "productcategoryname = ? " +
                "WHERE " +
                "productcategoryid = ?";
        boolean updateStatus = false;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);

            ps.setString(1, productCategory.getCategoryName());
            ps.setInt(2, productCategory.getCategoryId());
            updateStatus = ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return updateStatus;
    }

    public boolean deleteProductCategory(int productCategoryId){
        String query = "DELETE FROM productcategory WHERE productcategoryid = ?";
        boolean deleteStatus = false;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);

            ps.setInt(1, productCategoryId);
            deleteStatus = ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return deleteStatus;
    }

    public int getTotalCategory(){
        String query = "SELECT COUNT(*) FROM productcategory";
        int totalData = 0;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                totalData = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return totalData;
    }

    public List<ProductCategoryModel> searchProductCategory(String search, int limit, int offset) {
        String query = "SELECT * FROM productcategory WHERE productcategoryname LIKE ? LIMIT ? OFFSET ?";

        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + search + "%");
            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            listProductCategory = processProductCategoryRow(rs);

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listProductCategory;
    }

    public int getTotalSearchProductCategoy(String search){
        String query = "SELECT COUNT(*) FROM productcategory WHERE productcategoryname LIKE ?";
        int totalData = 0;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + search + "%");

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                totalData = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return totalData;
    }

    public List<ProductCategoryModel> processProductCategoryRow(ResultSet rs) throws SQLException{
        List<ProductCategoryModel> productCategoryModelList = new ArrayList<>();
        ProductCategoryModel category = null;

        while(rs.next()){
            category = new ProductCategoryModel();
            category.setCategoryId(rs.getInt(1));
            category.setCategoryName(rs.getString(2));
            productCategoryModelList.add(category);
        }

        return productCategoryModelList;
    }

    public SummaryList<ProductCategoryModel> getProductCategorySummaryList(int currentPage) {
        int limit = 10;
        int offset = (currentPage - 1) * 10;
        productCategorySummaryList = new SummaryList<>();
        productCategorySummaryList.setCurrentPage(currentPage);
        productCategorySummaryList.setContent(getAllCategory(limit, offset));
        productCategorySummaryList.setTotalRecords(getTotalCategory());
        productCategorySummaryList.setPageSize(limit);
        return productCategorySummaryList;
    }

    public SummaryList<ProductCategoryModel> getSearchProductCategory(int currentPage, String search) {
        int limit = 10;
        int offset = (currentPage - 1) * 10;
        productCategorySummaryList = new SummaryList<>();
        productCategorySummaryList.setPageSize(limit);
        productCategorySummaryList.setCurrentPage(currentPage);
        productCategorySummaryList.setContent(searchProductCategory(search, limit, offset));
        productCategorySummaryList.setTotalRecords(getTotalSearchProductCategoy(search));
        return productCategorySummaryList;
    }
}
