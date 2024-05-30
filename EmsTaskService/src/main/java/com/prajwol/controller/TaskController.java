package com.prajwol.controller;

import com.prajwol.Entity.EmsTask;
import com.prajwol.dto.EmsTaskDto;
import com.prajwol.service.EmsTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
public class TaskController {
    private EmsTaskService taskService;

    @Autowired
    public TaskController(EmsTaskService taskService) {
        this.taskService = taskService;
    }

        @GetMapping("/hello")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test passed" );
    }

    @PostMapping("/")
    public ResponseEntity<EmsTask> addTask(@RequestBody EmsTaskDto task) {
        return ResponseEntity.ok(taskService.addTask(task));
    }
}
