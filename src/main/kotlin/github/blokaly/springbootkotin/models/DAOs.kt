package github.blokaly.springbootkotin.models

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository("TodoDao")
interface TodoDao : MongoRepository<Todo, String> {
    fun findByCompleted(completed: Boolean): List<Todo>
    fun findByTitleContaining(title: String?): List<Todo>
}