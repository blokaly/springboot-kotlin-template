package github.blokaly.springbootkotin.models

sealed class UserMessageDto {
    data class Request(val userName: String, val text: String)
    data class Response(val id: String? = null, val userName: String? = null, val text: String? = null)
}