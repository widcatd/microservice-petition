package com.petition.sqs.listener.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.metrics.LoggingMetricPublisher;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class SQSConfigTest {

    @InjectMocks
    private SQSConfig sqsConfig;

    @Mock
    private SqsAsyncClient sqsAsyncClient;

    @Mock
    private SQSStateResponseProperties sqsStateResponseProperties;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        when(sqsStateResponseProperties.region()).thenReturn("us-east-1");
        when(sqsStateResponseProperties.queueUrl()).thenReturn("http://localhost:4566/00000000000/queue-sqs");
        when(sqsStateResponseProperties.waitTimeSeconds()).thenReturn(20);
        when(sqsStateResponseProperties.maxNumberOfMessages()).thenReturn(10);
        when(sqsStateResponseProperties.numberOfThreads()).thenReturn(1);
    }

    @Test
    void configSQSListenerIsNotNull() {
        assertThat(sqsConfig.sqsListener(sqsAsyncClient, sqsStateResponseProperties, message -> Mono.empty())).isNotNull();
    }

    @Test
    void configSqsIsNotNull() {
        var loggingMetricPublisher = LoggingMetricPublisher.create();
        assertThat(sqsConfig.configSqs(sqsStateResponseProperties, loggingMetricPublisher)).isNotNull();
    }

    @Test
    void configSqsWhenEndpointIsNotNull() {
        var loggingMetricPublisher = LoggingMetricPublisher.create();
        when(sqsStateResponseProperties.endpoint()).thenReturn("http://localhost:4566");
        assertThat(sqsConfig.configSqs(sqsStateResponseProperties, loggingMetricPublisher)).isNotNull();
    }

    @Test
    void resolveEndpointIsNull() {
        assertThat(sqsConfig.resolveEndpoint(sqsStateResponseProperties)).isNull();
    }
}
