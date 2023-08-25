package com.keycloak.apis;

import com.keycloak.entites.Employee;
import com.keycloak.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/registerEmployee")
    public Object registerNewEmployee(@RequestBody Employee employee){
        return employeeService.registerNewEmployee(employee);
    }

    @GetMapping("/test")
    public Object getEmployee(){
        return "test data";
    }
}
