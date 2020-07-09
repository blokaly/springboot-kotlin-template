package github.blokaly.springbootkotin.services

import github.blokaly.springbootkotin.models.UserMessage
import github.blokaly.springbootkotin.models.UserMessageCrud
import org.springframework.stereotype.Service

@Service
class UserMessageService(private val userMessageCrud: UserMessageCrud) {
    fun saveUserMessage(userName: String, text: String) = userMessageCrud.insert(UserMessage(userName = userName, text = text))
    fun findMessageById(messageId: String) = userMessageCrud.findOneById(messageId)
}