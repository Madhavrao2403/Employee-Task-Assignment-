package com.mini.EmployeeTask.service;

import com.mini.EmployeeTask.dto.TaskRequest;
import com.mini.EmployeeTask.dto.TaskResponse;
import com.mini.EmployeeTask.model.Employee;
import com.mini.EmployeeTask.model.Task;
import com.mini.EmployeeTask.repository.EmployeeRepository;
import com.mini.EmployeeTask.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    public TaskResponse createTask(TaskRequest request){
        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(()->new RuntimeException("Employee Not Found"));
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(true);
        task.setEmployee(employee);

        taskRepository.save(task);
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.isStatus());
        response.setEmployeeId(task.getEmployee().getId());
        return response;
    }
}
