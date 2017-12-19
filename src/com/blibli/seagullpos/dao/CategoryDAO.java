package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.ProductCategoryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    Connection conn = null;
    PreparedStatement ps = null;

    public CategoryDAO(){
        try{
            conn = ConnectionManager.createConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<ProductCategoryModel> getAllCategory(){
        String query = "SELECT * FROM productcategory";
        List<ProductCategoryModel> listCategory = null;
        try {
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            listCategory = processAllRow(rs);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return listCategory;
    }

    public List<ProductCategoryModel> processAllRow(ResultSet rs) throws SQLException{
        List<ProductCategoryModel> listCategory = new ArrayList<>();
        while(rs.next()){
            ProductCategoryModel category = new ProductCategoryModel();
            category.setCategoryID(rs.getInt("productcategoryid"));
            category.setCategoryName(rs.getString("productcategoryname"));
            listCategory.add(category);
        }
        return listCategory;
    }
}
