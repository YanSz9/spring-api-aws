package com.correa.springawsdeploy.service;

import com.correa.springawsdeploy.entity.Task;
import com.correa.springawsdeploy.producer.SqsProducer;
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
    private final SqsProducer sqsProducer;


    private TaskService taskService;
    public TaskService(DynamoDbTemplate dynamoDbTemplate, SqsProducer sqsProducer) {
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.sqsProducer = sqsProducer;
    }

    public Task saveTask(String description, String imageURL) {
        UUID taskID = UUID.randomUUID();

        Task task = new Task();
        task.setTaskID(taskID);
        task.setDescription(description);
        task.setImageURl(imageURL);;

        dynamoDbTemplate.save(task);
        sqsProducer.sendMessage("Task created: " + taskID);
        return task;
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

    public Task updateTask(String taskID, String description, String imageURL) {
        var key = Key.builder()
                .partitionValue(taskID)
                .build();
        var task = dynamoDbTemplate.load(key, Task.class);

        task.setDescription(description);
        task.setImageURl(imageURL);
        dynamoDbTemplate.save(task);
        sqsProducer.sendMessage("Task updated: " + taskID);

        return task;
    }

    public Task deleteTask(String taskID) {
        var key = Key.builder()
                .partitionValue(taskID)
                .build();
        var task = dynamoDbTemplate.load(key, Task.class);

        dynamoDbTemplate.delete(task);

        return task;
    }
}





