package github.blokaly.springbootkotin.models

import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "Message")
@TypeAlias("Message")
data class UserMessage(
    @Id val id: String? = null,
    @Version val version: Long? = null,
    @CreatedDate val createdAt: LocalDateTime? = null,
    @LastModifiedDate var lastModified: LocalDateTime? = null,
    val userName:String,
    val text:String
)