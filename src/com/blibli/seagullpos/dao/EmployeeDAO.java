package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.EmployeeModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDAO {

    private Connection connection = null;
    private PreparedStatement ps = null;

    public EmployeeDAO(){
        try{
            connection = ConnectionManager.createConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public EmployeeModel authenticateUser(String email, String password){
        EmployeeModel employeeModel = null;
        try{
            final String query = "SELECT * FROM employee WHERE employeeemail = ? AND password = md5(?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            employeeModel = processEmployeeRow(rs);

            if(employeeModel != null) updateLoginTime(employeeModel);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return employeeModel;
    }

    public List<EmployeeModel> getAllUser(){
        List<EmployeeModel> listUser = null;

        try{
            String query = "SELECT * FROM employee";
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            listUser = processAllROw(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  listUser;
    }

    public void updateLoginTime(EmployeeModel employee){
        try{
            connection.setAutoCommit(false);
            String query = "UPDATE employee " +
                    "SET lastlogin = ?" +
                    "WHERE employeeid = ?";

            Timestamp newTime = new Timestamp(System.currentTimeMillis());

            ps = connection.prepareStatement(query);
            ps.setTimestamp(1, newTime);
            ps.setString(2, employee.getEmployeeId());

            ps.executeUpdate();
            connection.commit();
            employee.setLastLogin(newTime);
        }catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
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
            model.setLastLogin(rs.getTimestamp("lastlogin"));
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
            if(rs.getDate("lastlogin") != null)
                employeeModel.setLastLogin(rs.getTimestamp("lastlogin"));
            else employeeModel.setLastLogin(null);
            employeeModel.setEmployeeRole(rs.getString("role"));
        }
        return employeeModel;
    }

}
