package github.blokaly.springbootkotin.models

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class TodoCrud(@Qualifier("TodoDao") private val dao: TodoDao) : TodoDao by dao {
    fun update(todo: Todo, update: Todo): Todo {
        val updated = todo.copy(title = update.title, description = update.description, completed = update.completed)
        return save(updated)
    }
}