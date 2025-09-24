package com.petition.sqs.sender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "adapter.sqs.capacidad")
public record SQSSenderCapacityProperties (
        String region,
        String queueUrl,
        String endpoint
){}
