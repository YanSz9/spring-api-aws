package com.correa.springawsdeploy.controller;

import com.correa.springawsdeploy.Dtos.TaskDto;
import com.correa.springawsdeploy.entity.Task;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.correa.springawsdeploy.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/v1/task")
public class TaskController {

    private final TaskService taskService;
    private DynamoDbTemplate dynamoDbTemplate;

    public TaskController(DynamoDbTemplate dynamoDbTemplate, TaskService taskService) {
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.taskService = taskService;
    }
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
        Task createdTask = taskService.saveTask(taskDto.getDescription(), taskDto.getImageURl());
        return ResponseEntity.ok(createdTask);
    }
    @GetMapping("/getTaskById/{taskID}")
    public ResponseEntity<List<Task>> findTaskById(@PathVariable("taskID") UUID taskID) {
        List<Task> taskList = taskService.getTaskById(taskID);
        return ResponseEntity.ok(taskList);
    }
    @GetMapping("/getAllTasks")
    public ResponseEntity<List<Task>> listAllTasks() {
        List<Task> taskList = taskService.getAllTasks();
        return ResponseEntity.ok(taskList);
    }
    @PutMapping("/update/{taskID}")
    public ResponseEntity<Task> updateTask(@PathVariable("taskID") String taskID,
                                           @RequestBody TaskDto taskDto) {
        Task updatedTask = taskService.updateTask(taskID, taskDto.getDescription(), taskDto.getImageURl());
        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedTask);
    }
    @DeleteMapping("/delete/{taskID}")
    public ResponseEntity<Task> deleteTask(@PathVariable("taskID") String taskID) {
        Task deletedTask = taskService.deleteTask(taskID);
        if (deletedTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedTask);
    }
}



