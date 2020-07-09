package github.blokaly.springbootkotin.models

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository("UserMessageDao")
interface UserMessageDao : MongoRepository<UserMessage, String> {
    fun findOneById(id: String): UserMessage?
}