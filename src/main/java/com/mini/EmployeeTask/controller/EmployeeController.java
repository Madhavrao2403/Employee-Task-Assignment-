package com.mini.EmployeeTask.controller;

import com.mini.EmployeeTask.dto.EmployeeRequest;
import com.mini.EmployeeTask.dto.TaskResponse;
import com.mini.EmployeeTask.model.Employee;
import com.mini.EmployeeTask.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeRequest request) {
        Employee added = employeeService.createEmployee(request);
        URI location = URI.create("/employees/" + added.getId());
        return  ResponseEntity.created(location).body(added);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findTasksByEmployeeId(id));
    }
}
