package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.EmployeeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Connection connection = null;
    private PreparedStatement ps = null;

    public EmployeeModel authenticateUser(String email, String password){
        EmployeeModel employeeModel = null;
        try{
            connection = ConnectionManager.createConnection();
            final String query = "SELECT * FROM employee WHERE employeeemail = ? AND password = md5(?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            employeeModel = processEmployeeRow(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return employeeModel;
    }

    public List<EmployeeModel> processAllROw(ResultSet rs) throws SQLException{
        List<EmployeeModel> listEmployee = new ArrayList<>();
        while(rs.next()){
            EmployeeModel model = new EmployeeModel();
            model.setEmployeeEmail(rs.getString("employeeemail"));
            model.setEmployeeGender(rs.getString("employeegender"));
            model.setEmployeeId(rs.getString("employeeid"));
            model.setEmployeeName(rs.getString("employeename"));
            model.setEmployeePassword(rs.getString("password"));
            model.setEmployeeRole(rs.getString("role"));
            listEmployee.add(model);
        }
        return listEmployee;
    }

    public EmployeeModel processEmployeeRow(ResultSet rs) throws SQLException{
        EmployeeModel employeeModel = null;
        if(rs.next()){
            employeeModel = new EmployeeModel();
            employeeModel.setEmployeeEmail(rs.getString("employeeemail"));
            employeeModel.setEmployeeGender(rs.getString("employeegender"));
            employeeModel.setEmployeeId(rs.getString("employeeid"));
            employeeModel.setEmployeeName(rs.getString("employeename"));
            employeeModel.setEmployeePassword(rs.getString("password"));
            employeeModel.setEmployeeRole(rs.getString("role"));
        }
        return employeeModel;
    }

}
