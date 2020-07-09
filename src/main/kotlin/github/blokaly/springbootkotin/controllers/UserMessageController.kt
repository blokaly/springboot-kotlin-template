package github.blokaly.springbootkotin.controllers

import github.blokaly.springbootkotin.common.Endpoints
import github.blokaly.springbootkotin.common.LoggerDelegate
import github.blokaly.springbootkotin.common.toResponse
import github.blokaly.springbootkotin.models.UserMessageDto
import github.blokaly.springbootkotin.services.UserMessageService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping(Endpoints.USER_MESSAGE_ENDPOINT, produces = [MediaType.APPLICATION_JSON_VALUE])
class UserMessageController(private val userMessageService: UserMessageService) {
    private val logger by LoggerDelegate()

    @PostMapping
    fun create(@Valid @RequestBody request: UserMessageDto.Request): ResponseEntity<UserMessageDto.Response> {
        return try {
            val message = userMessageService.saveUserMessage(request.userName, request.text)
            ResponseEntity(message.toResponse(), HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error("Error saving user message", ex)
            ResponseEntity(UserMessageDto.Response(userName = request.userName, text = request.text), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @GetMapping(Endpoints.USER_MESSAGE_ID)
    fun queryTemplateById(@NotBlank @PathVariable messageId: String): ResponseEntity<UserMessageDto.Response> {
        val message = userMessageService.findMessageById(messageId)
        return if (message != null) {
            ResponseEntity(message.toResponse(), HttpStatus.OK)
        } else {
            ResponseEntity(UserMessageDto.Response(id = messageId), HttpStatus.NOT_FOUND)
        }
    }
}