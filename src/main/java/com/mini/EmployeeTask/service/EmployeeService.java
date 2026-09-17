package com.mini.EmployeeTask.service;

import com.mini.EmployeeTask.dto.EmployeeRequest;
import com.mini.EmployeeTask.dto.TaskResponse;
import com.mini.EmployeeTask.model.Employee;
import com.mini.EmployeeTask.model.Task;
import com.mini.EmployeeTask.repository.EmployeeRepository;
import com.mini.EmployeeTask.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    public EmployeeService(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public Employee createEmployee(EmployeeRequest request){
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());

        employeeRepository.save(employee);
        return employee;
    }

    public List<TaskResponse> findTasksByEmployeeId(Long id){
        List<TaskResponse> taskResponseList = new ArrayList<>();
        for(Task task:taskRepository.findByEmployeeId(id)){
            taskResponseList.add(new TaskResponse(task.getId(),task.getTitle(),task.getDescription(),task.isStatus(),task.getEmployee().getId()));
        }
        return taskResponseList;
    }
}
