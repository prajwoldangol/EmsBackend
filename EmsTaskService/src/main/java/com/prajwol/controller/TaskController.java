package com.prajwol.controller;

import com.prajwol.Entity.EmsTask;
import com.prajwol.dto.EmsTaskDto;
import com.prajwol.service.EmsTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TaskController {
    private EmsTaskService taskService;

    @Autowired
    public TaskController(EmsTaskService taskService) {
        this.taskService = taskService;
    }
    @PostMapping
    public ResponseEntity<EmsTask> addTask(@RequestBody EmsTaskDto task) {
        return ResponseEntity.ok(taskService.addTask(task));
    }
    @PutMapping("/{taskId}")
    public ResponseEntity<EmsTask> updateTask( @PathVariable String taskId, @RequestBody EmsTaskDto task) {
        return ResponseEntity.ok(taskService.updateTask(taskId, task));
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EmsTask>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
    @GetMapping("/{taskId}")
    public ResponseEntity<EmsTask> getTaskById(@PathVariable String taskId) {
        Optional<EmsTask> task = taskService.getTaskById(taskId);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/owner/{taskOwner}")
    public ResponseEntity<List<EmsTask>> getTasksByTaskOwner(@PathVariable String taskOwner) {
        return ResponseEntity.ok(taskService.getTasksByTaskOwner(taskOwner));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmsTask>> getTasksByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(taskService.getTasksByDepartment(department));
    }
}
