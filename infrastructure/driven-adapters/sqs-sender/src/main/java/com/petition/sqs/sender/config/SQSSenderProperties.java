package com.petition.sqs.sender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapter.sqs.notificaciones")
public record SQSSenderProperties(
     String region,
     String queueUrl,
     String endpoint){
}
