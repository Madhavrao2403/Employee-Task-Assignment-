package com.mini.EmployeeTask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private boolean status;
    private Long employeeId;
}
