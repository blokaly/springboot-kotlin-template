package github.blokaly.springbootkotin.controllers

import github.blokaly.springbootkotin.common.Endpoints
import github.blokaly.springbootkotin.common.LoggerDelegate
import github.blokaly.springbootkotin.common.toNullable
import github.blokaly.springbootkotin.models.Todo
import github.blokaly.springbootkotin.models.TodoCrud
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Endpoints.TODO_ENDPOINT, produces = [MediaType.APPLICATION_JSON_VALUE])
class TodoController(private val todoCrud: TodoCrud) {

    private val logger by LoggerDelegate()

    @GetMapping
    fun getAllTodos(@RequestParam(required = false) title: String?): ResponseEntity<List<Todo>?> {
        return try {
            logger.info("getAllTodos, title:$title")
            val todos = if (title == null) todoCrud.findAll() else todoCrud.findByTitleContaining(title)
            if (todos.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else ResponseEntity(todos, HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error("getAllTodos error", ex)
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable("id") id: String): ResponseEntity<Todo?> {
        logger.info("getTodoById, id:$id")
        val data = todoCrud.findById(id).toNullable()
        return if (data != null) {
            ResponseEntity(data, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun createTodo(@RequestBody todo: Todo): ResponseEntity<Todo?> {
        return try {
            logger.info("createTodo, todo:$todo")
            val data = todoCrud.save(Todo(title = todo.title, description = todo.description, completed = false))
            ResponseEntity(data, HttpStatus.CREATED)
        } catch (ex: Exception) {
            logger.error("createTodo error", ex)
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }

    @PutMapping("/{id}")
    fun updateTodo(@PathVariable("id") id: String, @RequestBody todo: Todo): ResponseEntity<Todo?> {
        logger.info("updateTodo, id:$id, todo:$todo")
        val data = todoCrud.findById(id).toNullable()
        return if (data != null) {
            val updated = todoCrud.update(data, todo)
            ResponseEntity(updated, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable("id") id: String): ResponseEntity<HttpStatus?> {
        return try {
            logger.info("deleteTodo, id:$id")
            todoCrud.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: Exception) {
            logger.error("deleteTodo error", ex)
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }

    @DeleteMapping
    fun deleteAllTodos(): ResponseEntity<HttpStatus?> {
        return try {
            logger.info("deleteAllTodos")
            todoCrud.deleteAll()
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: Exception) {
            logger.error("deleteAllTodos error", ex)
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }

    @GetMapping("/completed")
    fun findByCompleted(): ResponseEntity<List<Todo>?> {
        return try {
            logger.info("findByCompleted")
            val todos = todoCrud.findByCompleted(true)
            if (todos.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else ResponseEntity(todos, HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error("findByCompleted error", ex)
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }
}