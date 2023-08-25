package com.keycloak.service;

import com.keycloak.entites.Employee;
import com.keycloak.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Object registerNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
