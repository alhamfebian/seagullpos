package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;

    public ProductDAO(){
        try{
            conn = ConnectionManager.createConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<ProductModel> getAllProduct(){
        String query = "SELECT * FROM product";
        List<ProductModel> listProduct = null;
        try{
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            listProduct = processAllRow(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(conn != null) ConnectionManager.closeConnection(conn);
                if(ps != null) ps.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return listProduct;
    }

    private List<ProductModel> processAllRow(ResultSet rs) throws SQLException{
        List<ProductModel> listProduct = new ArrayList<>();

        while(rs.next()){
            ProductModel product = new ProductModel();
            product.setProductID(rs.getString("productid"));
            product.setProductCategoryID(rs.getInt("productcategoryid"));
            product.setProductName(rs.getString("productname"));
            product.setProductPrice(rs.getInt("productprice"));
            product.setProductStock(rs.getInt("productstock"));
            product.setThumbnail(rs.getString("productthumbnail"));

            listProduct.add(product);
        }
        return listProduct;
    }

}
