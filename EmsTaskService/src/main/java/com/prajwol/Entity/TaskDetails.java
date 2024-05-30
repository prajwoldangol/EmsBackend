package com.prajwol.Entity;

import com.prajwol.dto.TaskDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDetails {

    private String taskTitle;

    private String taskDescription;

    public TaskDetails(TaskDetailsDto taskDetailsDto) {
        this.taskTitle = taskDetailsDto.getTaskTitle();
        this.taskDescription = taskDetailsDto.getTaskDescription();
    }
}
