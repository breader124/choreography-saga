package com.breader.order.infrastructure.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.ContainerCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AwsServicesConfiguration {

    @Value("${aws.endpointUrl}")
    private String awsEndpointUrl;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    @Profile("LOCAL")
    public DynamoDBMapper localDynamoDBMapper() {
        AWSStaticCredentialsProvider anonymousCredProvider = new AWSStaticCredentialsProvider(new AnonymousAWSCredentials());
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
            awsEndpointUrl,
            awsRegion
        );
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(anonymousCredProvider)
            .withEndpointConfiguration(endpointConfiguration)
            .build();
        return new DynamoDBMapper(dynamoDB);
    }

    @Bean
    @Profile("!LOCAL")
    public DynamoDBMapper dynamoDBMapper() {
        AWSCredentialsProvider credentialsProvider = new ContainerCredentialsProvider();
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .withCredentials(credentialsProvider)
            .build();
        return new DynamoDBMapper(dynamoDB);
    }

}
