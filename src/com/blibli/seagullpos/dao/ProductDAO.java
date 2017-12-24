package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.ProductCategoryModel;
import com.blibli.seagullpos.model.ProductModel;
import com.blibli.seagullpos.model.SummaryList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection connection = null;
    private PreparedStatement ps = null;

    private List<ProductModel> listProduct = null;
    private SummaryList<ProductModel> productSummaryList = null;

    public ProductDAO(){

    }

    public List<ProductModel> getAllProduct(int limit, int offset){
        String query = "SELECT p.*, pc.productcategoryname " +
                "FROM product p " +
                "JOIN productcategory pc " +
                "ON p.productcategoryid = pc.productcategoryid " +
                "LIMIT ? OFFSET ?";
        try {
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            listProduct = processAllRow(rs);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return listProduct;
    }

    public boolean insertProduct(ProductModel product){
        String query = "INSERT INTO product " +
                "(productid, productcategoryid, productName, productprice, productstock, productthumbnail, productlocation) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        boolean insertStatus = false;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, product.getProductId());
            ps.setInt(2, product.getProductCategoryId());
            ps.setString(3, product.getProductName());
            ps.setInt(4, product.getProductPrice());
            ps.setInt(5, product.getProductStock());
            ps.setString(6, product.getThumbnail());
            ps.setString(7, product.getProductLocation());

            insertStatus = ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return insertStatus;
    }

    public boolean updateProduct(ProductModel product){
        String query = "UPDATE product " +
                "SET " +
                "productid = ?, " +
                "productcategoryid = ?, " +
                "productname = ?, " +
                "productprice = ?, " +
                "productstock = ?, " +
                "productthumbnail = ?, " +
                "productlocation = ? " +
                "WHERE productid = ?";
        boolean updateStatus = false;
        try {
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, product.getProductId());
            ps.setInt(2, product.getProductCategoryId());
            ps.setString(3, product.getProductName());
            ps.setInt(4, product.getProductPrice());
            ps.setInt(5, product.getProductStock());
            ps.setString(6, product.getThumbnail());
            ps.setString(7, product.getProductLocation());
            ps.setString(8, product.getProductId());

            updateStatus = ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return updateStatus;
    }

    public boolean deleteProduct(String productId){
        String query = "DELETE FROM product WHERE productid = ?";
        boolean deleteStatus = false;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, productId);
            deleteStatus = ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return deleteStatus;
    }

    public List<ProductModel> getSearchProductList(String search, int limit, int offset) {
        String query = "SELECT p.*, pc.productcategoryname " +
                "FROM product p " +
                "JOIN productcategory pc " +
                "ON p.productcategoryid = pc.productcategoryid " +
                "WHERE p.productid LIKE ? OR p.productname LIKE ? OR p.productlocation LIKE ? OR pc.productcategoryname LIKE ? " +
                "LIMIT ? OFFSET ?";
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + search + "%");
            ps.setString(4, "%" + search + "%");
            ps.setInt(5, limit);
            ps.setInt(6, limit);

            ResultSet rs = ps.executeQuery();
            listProduct = processAllRow(rs);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return listProduct;
    }

    public int getTotalProduct(){
        String query = "SELECT COUNT(*) FROM product";
        int totalProduct = 0;
        try {
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                totalProduct = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return totalProduct;
    }

    public int getTotalSearchProduct(String search){
        String query = "SELECT COUNT(*) FROM product p " +
                "JOIN productcategory pc " +
                "ON p.productcategoryid = pc.productcategoryid " +
                "WHERE p.productid LIKE ? OR p.productname LIKE ? OR p.productlocation LIKE ? OR pc.productcategoryname LIKE ?";
        int totalSearchData = 0;

        try {
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + search + "%");
            ps.setString(4, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                totalSearchData = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return totalSearchData;
    }

    private List<ProductModel> processAllRow(ResultSet rs) throws SQLException{
        List<ProductModel> listProduct = new ArrayList<>();

        while(rs.next()){
            ProductModel product = new ProductModel();
            product.setProductId(rs.getString("productid"));
            product.setProductCategoryId(rs.getInt("productcategoryid"));
            product.setProductName(rs.getString("productname"));
            product.setProductPrice(rs.getInt("productprice"));
            product.setProductStock(rs.getInt("productstock"));
            product.setThumbnail(rs.getString("productthumbnail"));
            product.setProductCategoryName(rs.getString("productcategoryname"));
            product.setProductLocation(rs.getString("productlocation"));
            listProduct.add(product);
        }
        return listProduct;
    }

    public SummaryList<ProductModel> getProductSummaryList(int currentPage) {
        int limit = 10;
        int offset = (currentPage - 1) * 10;

        productSummaryList = new SummaryList<>();
        productSummaryList.setCurrentPage(currentPage);
        productSummaryList.setPageSize(limit);
        productSummaryList.setContent(getAllProduct(limit, offset));
        productSummaryList.setTotalRecords(getTotalProduct());
        return productSummaryList;
    }

    public SummaryList<ProductModel> getSearchProductSummaryList(int currentPage, String search) {
        int limit = 10;
        int offset = (currentPage - 1 ) * 10;

        productSummaryList = new SummaryList<>();
        productSummaryList.setCurrentPage(currentPage);
        productSummaryList.setPageSize(limit);
        productSummaryList.setContent(getSearchProductList(search, limit, offset));
        productSummaryList.setTotalRecords(getTotalSearchProduct(search));

        return productSummaryList;
    }
}
