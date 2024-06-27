package com.correa.springawsdeploy.controller;

import com.correa.springawsdeploy.Dtos.DescriptionDto;
import com.correa.springawsdeploy.entity.Task;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.correa.springawsdeploy.service.TaskService;
import software.amazon.awssdk.enhanced.dynamodb.Key;

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
    public ResponseEntity<Void> createTask(@RequestBody DescriptionDto descriptionDto) {
        taskService.saveTask(descriptionDto);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> updateTask(@PathVariable("taskID") String taskID,
                                           @RequestBody DescriptionDto descriptionDto) {
        boolean updated = taskService.updateTask(taskID, descriptionDto.getDescription());

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete/{taskID}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskID") String taskID) {
        boolean deleted = taskService.deleteTask(taskID);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}



