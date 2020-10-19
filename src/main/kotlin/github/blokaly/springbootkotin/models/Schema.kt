package github.blokaly.springbootkotin.models

import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "Todo")
@TypeAlias("Todo")
data class Todo(
        @Id val id: String? = null,
        @Version val version: Long? = null,
        @CreatedDate val createdAt: LocalDateTime? = null,
        @LastModifiedDate var lastModified: LocalDateTime? = null,
        val title: String,
        val description: String,
        val completed: Boolean
)