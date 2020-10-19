package github.blokaly.springbootkotin.message

import com.amazon.sqs.javamessaging.SQSMessagingClientConstants
import com.fasterxml.jackson.databind.ObjectMapper
import github.blokaly.springbootkotin.common.LoggerDelegate
import github.blokaly.springbootkotin.config.AwsSqsProperties
import github.blokaly.springbootkotin.models.ToDoMessage
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.Resource
import javax.jms.Message


@Component
class AwsSqsProducer(private val awsSqsProperties: AwsSqsProperties,
                     @Resource private val jmsTemplate: JmsTemplate,
                     @Resource private val objectMapper: ObjectMapper) {

    private val logger by LoggerDelegate()

    private fun <MESSAGE : Any> send(queue: String, payload: MESSAGE) {
        jmsTemplate.send(queue) { session ->
            try {
                val createMessage: Message = session.createTextMessage(objectMapper.writeValueAsString(payload))
                createMessage.setStringProperty(SQSMessagingClientConstants.JMSX_GROUP_ID, awsSqsProperties.messageGroup)
                createMessage.setStringProperty(SQSMessagingClientConstants.JMS_SQS_DEDUPLICATION_ID, UUID.randomUUID().toString())
                createMessage.setStringProperty("documentType", payload::class.qualifiedName)
                createMessage
            } catch (e: Exception) {
                logger.error("Fail to send message {}", payload)
                throw RuntimeException(e)
            } catch (e: Error) {
                logger.error("Fail to send message {}", payload)
                throw RuntimeException(e)
            }
        }
    }

    fun send(message: ToDoMessage) {
        send(awsSqsProperties.messageQueue, message)
    }
}