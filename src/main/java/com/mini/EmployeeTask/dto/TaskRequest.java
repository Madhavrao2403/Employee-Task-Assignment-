package com.mini.EmployeeTask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskRequest {
    private String title;
    private String description;
    private Long employeeId;
}
