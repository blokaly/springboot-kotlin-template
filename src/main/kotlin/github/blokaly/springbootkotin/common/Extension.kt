package github.blokaly.springbootkotin.common

import github.blokaly.springbootkotin.models.UserMessage
import github.blokaly.springbootkotin.models.UserMessageDto

fun UserMessage.toResponse() = UserMessageDto.Response(
    id = id, userName = userName, text = text
)