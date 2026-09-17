package com.mini.EmployeeTask.controller;

import com.mini.EmployeeTask.dto.TaskRequest;
import com.mini.EmployeeTask.dto.TaskResponse;
import com.mini.EmployeeTask.model.Task;
import com.mini.EmployeeTask.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> addTask(@RequestBody TaskRequest request) {
        TaskResponse task = taskService.createTask(request);
        URI location = URI.create("/tasks/" + task.getId());
        return  ResponseEntity.created(location).body(task);
    }
}
