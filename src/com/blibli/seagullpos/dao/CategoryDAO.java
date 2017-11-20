package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.CategoryModel;

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

    public List<CategoryModel> getAllCategory(){
        String query = "SELECT * FROM productcategory";
        List<CategoryModel> listCategory = null;
        try {
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            listCategory = processAllRow(rs);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return listCategory;
    }

    public List<CategoryModel> processAllRow(ResultSet rs) throws SQLException{
        List<CategoryModel> listCategory = new ArrayList<>();
        while(rs.next()){
            CategoryModel category = new CategoryModel();
            category.setCategoryID(rs.getInt("productcategoryid"));
            category.setCategoryName(rs.getString("productcategoryname"));
            listCategory.add(category);
        }
        return listCategory;
    }
}
