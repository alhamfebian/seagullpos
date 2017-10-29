package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.EmployeeDAO;
import com.blibli.seagullpos.model.EmployeeModel;

public class EmployeeService {
    EmployeeDAO dao = new EmployeeDAO();

    public EmployeeModel aunthenticateService(String email, String password){
        return dao.authenticateUser(email, password);
    }
}
