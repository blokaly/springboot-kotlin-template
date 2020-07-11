package github.blokaly.springbootkotin.models

import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "Tutorial")
@TypeAlias("Tutorial")
data class Tutorial(
        @Id val id: String? = null,
        @Version val version: Long? = null,
        @CreatedDate val createdAt: LocalDateTime? = null,
        @LastModifiedDate var lastModified: LocalDateTime? = null,
        val title: String,
        val description: String,
        val published: Boolean
)