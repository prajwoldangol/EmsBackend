package com.prajwol.service;

import com.prajwol.Entity.EmsTask;
import com.prajwol.dto.EmsTaskDto;

import java.util.List;
import java.util.Optional;

public interface EmsTaskService {
    public EmsTask addTask(EmsTaskDto task);
    public EmsTask updateTask(String taskId, EmsTaskDto taskDto);
    public void deleteTask(String taskIdString);
    public List<EmsTask> getAllTasks();
    public Optional<EmsTask> getTaskById(String taskIdString);
    public List<EmsTask> getTasksByTaskOwner(String taskOwner);
    public List<EmsTask> getTasksByDepartment(String department);

}
