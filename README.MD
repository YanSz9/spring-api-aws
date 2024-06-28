# Spring AWS Deploy 

## Information 
- **Title**: Spring Aws | Yan Corrêa
- **Version**: 1.0
- **Host**: 54.207.226.158
-- **SWAGGER URL**: http://54.207.226.158:8080/swagger-ui/index.html

## TO-DO List Api using Java 21, Docker, DynamoDb, CI/CD with GH Actions

## Endpoints 

- **Create a task:**

  ```
  curl -X POST -H "Content-Type: application/json" -d '{"description": "Tem vaga na verdinha?"}' 54.207.226.158/v1/task/createTask

  ```
- **Update a task:**

  ```
  curl -X PUT -H "Content-Type: application/json" -d '{"description": "Em? Tem vaga"}'  54.207.226.158/v1/task/updateTask/{taskID}

  ```

- **Get all tasks:**
  ```
  curl -X GET -H "Content-Type: application/json" -d  54.207.226.158/v1/task/getAllTask
  
  ```

- **Get a task by id:**
  ```
  curl -X GET -H "Content-Type: application/json" -d  54.207.226.158/v1/task/getTaskById/{taskID}
  
  ```
  
- **Delete a task by id:**
  ```
  curl -X DELETE -H "Content-Type: application/json" -d  54.207.226.158/v1/task/deleteTask/{taskID}
  
  ```