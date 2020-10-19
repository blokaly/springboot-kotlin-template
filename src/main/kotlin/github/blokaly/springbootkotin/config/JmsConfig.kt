package github.blokaly.springbootkotin.config

import com.amazon.sqs.javamessaging.ProviderConfiguration
import com.amazon.sqs.javamessaging.SQSConnectionFactory
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType
import org.springframework.jms.support.destination.DynamicDestinationResolver
import javax.jms.ConnectionFactory
import javax.jms.Session


@Configuration
@EnableJms
class JmsConfig(private val awsSqsProperties: AwsSqsProperties) {

    @Bean
    fun jmsConnectionFactory(): ConnectionFactory {
        val sqsClientBuilder: AmazonSQSClientBuilder = AmazonSQSClientBuilder.standard()
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(awsSqsProperties.credentials.accessKey, awsSqsProperties.credentials.secretKey)))
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(awsSqsProperties.endpoint, awsSqsProperties.region))
        return SQSConnectionFactory(ProviderConfiguration(), sqsClientBuilder)
    }

    @Bean
    fun jmsTemplate(connectionFactory:ConnectionFactory): JmsTemplate {
        val jmsTemplate = JmsTemplate(connectionFactory)
        jmsTemplate.messageConverter = messageConverter()
        return jmsTemplate
    }

    @Bean
    fun messageConverter(): MessageConverter {
        val builder = Jackson2ObjectMapperBuilder()
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY)
        return MappingJackson2MessageConverter().apply {
            setObjectMapper(builder.build())
            setTargetType(MessageType.TEXT)
            setTypeIdPropertyName("documentType")
        }
    }

    @Bean
    fun jmsListenerContainerFactory(connectionFactory:ConnectionFactory): DefaultJmsListenerContainerFactory? {
        val factory = DefaultJmsListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setDestinationResolver(DynamicDestinationResolver())
        factory.setConcurrency("3-10")
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE)
        factory.setMessageConverter(messageConverter())
        return factory
    }


    @Bean("awsSqsQueue")
    fun getAwsSqsQueue() = AwsSqsQueue(awsSqsProperties.messageQueue)
}