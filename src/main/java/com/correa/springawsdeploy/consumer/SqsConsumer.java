package com.correa.springawsdeploy.consumer;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SqsConsumer {

    @SqsListener("task-queue")
    public void listen(String message) {
        System.out.println(message);
    }

}
