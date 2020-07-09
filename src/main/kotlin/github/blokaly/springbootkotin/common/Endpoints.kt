package github.blokaly.springbootkotin.common

object Endpoints {
    private const val API_ROOT_PATH = "/api/v1"
    const val USER_MESSAGE_ENDPOINT = "$API_ROOT_PATH/message"
    const val USER_MESSAGE_ID = "/{messageId}"
}