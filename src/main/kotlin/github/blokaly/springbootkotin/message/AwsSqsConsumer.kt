package github.blokaly.springbootkotin.message

import github.blokaly.springbootkotin.common.LoggerDelegate
import github.blokaly.springbootkotin.models.ToDoMessage
import org.springframework.jms.annotation.JmsListener
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class AwsSqsConsumer {
    private val logger by LoggerDelegate()

    @JmsListener(destination = "#{awsSqsQueue.name}")
    fun listenQueueTest(@Payload message: Message<ToDoMessage>) {
        logger.info("receiving ${message.payload}")
    }
}