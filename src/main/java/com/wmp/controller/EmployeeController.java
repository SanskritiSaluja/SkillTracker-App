package com.wmp.controller;

import com.wmp.exception.ResourceNotFoundException;
import com.wmp.model.Employee;
import com.wmp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

 // Get All Employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

 // Create a new Employee
    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
    	employee.setCreatedAt();
        return employeeRepository.save(employee);
    }
    
 // Get a Single Employee
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") long employeeId) {
        return employeeRepository.findById(employeeId);
    }

 // Update a Employee
    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable(value = "id") Long employeeId,
                                            @Valid @RequestBody Employee employeeDetails) {

        Employee employee = employeeRepository.findById(employeeId);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setAddress(employeeDetails.getAddress());
        employee.setCareerLevel(employeeDetails.getCareerLevel());
        employee.setCity(employeeDetails.getCity());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());
        employee.setDateOfJoin(employeeDetails.getDateOfJoin());
        employee.setState(employeeDetails.getState());
        employee.setZIPCode(employeeDetails.getZIPCode());

        Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }
    
 // Delete a Employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId);

        employeeRepository.delete(employee);

        return ResponseEntity.ok().build();
    }
}