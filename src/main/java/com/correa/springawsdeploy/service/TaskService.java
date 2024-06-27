package com.correa.springawsdeploy.service;



import com.correa.springawsdeploy.Dtos.DescriptionDto;
import com.correa.springawsdeploy.entity.Task;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final DynamoDbTemplate dynamoDbTemplate;

    public TaskService(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    public void saveTask(DescriptionDto descriptionDto) {
        UUID taskID = UUID.randomUUID();

        Task task = new Task();
        task.setTaskID(taskID);
        task.setDescription(descriptionDto.getDescription());

        dynamoDbTemplate.save(task);
    }

    public List<Task> getTaskById(UUID taskID) {
        var key = Key.builder().partitionValue(taskID.toString()).build();
        var condition = QueryConditional.keyEqualTo(key);
        var query = QueryEnhancedRequest.builder().queryConditional(condition).build();
        var taskList = dynamoDbTemplate.query(query, Task.class);
        return taskList.items().stream().toList();
    }

    public List<Task> getAllTasks() {
        var scanRequest = ScanEnhancedRequest.builder().build();
        var tasks = dynamoDbTemplate.scan(scanRequest, Task.class);
        return tasks.items().stream().toList();
    }

    public boolean updateTask(String taskID, String description) {
        var key = Key.builder()
                .partitionValue(taskID)
                .build();
        var task = dynamoDbTemplate.load(key, Task.class);

        if (task == null) {
            return false;
        }

        task.setDescription(description);
        dynamoDbTemplate.save(task);

        return true;
    }

    public boolean deleteTask(String taskID) {
        var key = Key.builder()
                .partitionValue(taskID)
                .build();
        var task = dynamoDbTemplate.load(key, Task.class);

        if (task == null) {
            return false;
        }

        dynamoDbTemplate.delete(task);

        return true;
    }
}





