package github.blokaly.springbootkotin.controllers

import github.blokaly.springbootkotin.common.Endpoints
import github.blokaly.springbootkotin.common.toNullable
import github.blokaly.springbootkotin.models.Tutorial
import github.blokaly.springbootkotin.models.TutorialCrud
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Endpoints.TUTORIAL_ENDPOINT, produces = [MediaType.APPLICATION_JSON_VALUE])
class TutorialController(private val tutorialCrud: TutorialCrud) {

    @GetMapping
    fun getAllTutorials(@RequestParam(required = false) title: String?): ResponseEntity<List<Tutorial>?> {
        return try {
            val tutorials = if (title == null) tutorialCrud.findAll() else tutorialCrud.findByTitleContaining(title)
            if (tutorials.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else ResponseEntity(tutorials, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getTutorialById(@PathVariable("id") id: String): ResponseEntity<Tutorial?> {
        val data = tutorialCrud.findById(id).toNullable()
        return if (data != null) {
            ResponseEntity(data, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun createTutorial(@RequestBody tutorial: Tutorial): ResponseEntity<Tutorial?> {
        return try {
            val data = tutorialCrud.save(Tutorial(title = tutorial.title, description = tutorial.description, published = false))
            ResponseEntity(data, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }

    @PutMapping("/{id}")
    fun updateTutorial(@PathVariable("id") id: String, @RequestBody tutorial: Tutorial): ResponseEntity<Tutorial?> {
        val data = tutorialCrud.findById(id).toNullable()
        return if (data != null) {
            val updated = tutorialCrud.update(data, tutorial)
            ResponseEntity(updated, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTutorial(@PathVariable("id") id: String): ResponseEntity<HttpStatus?> {
        return try {
            tutorialCrud.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }

    @DeleteMapping
    fun deleteAllTutorials(): ResponseEntity<HttpStatus?> {
        return try {
            tutorialCrud.deleteAll()
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }

    @GetMapping("/published")
    fun findByPublished(): ResponseEntity<List<Tutorial>?> {
        return try {
            val tutorials = tutorialCrud.findByPublished(true)
            if (tutorials.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else ResponseEntity(tutorials, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }
}