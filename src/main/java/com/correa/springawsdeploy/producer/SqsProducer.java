package com.correa.springawsdeploy.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SqsProducer {

    private final SqsAsyncClient sqsAsyncClient;
    private final String queueUrl = "https://sqs.sa-east-1.amazonaws.com/590183694484/task-queue";

    @Autowired
    public SqsProducer(SqsAsyncClient sqsAsyncClient) {
        this.sqsAsyncClient = sqsAsyncClient;
    }

    public void sendMessage(String message) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();
        sqsAsyncClient.sendMessage(sendMsgRequest);
    }
}
