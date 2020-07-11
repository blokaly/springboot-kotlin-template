package github.blokaly.springbootkotin.controllers

import github.blokaly.springbootkotin.common.Endpoints
import github.blokaly.springbootkotin.common.LoggerDelegate
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

    private val logger by LoggerDelegate()

    @GetMapping
    fun getAllTutorials(@RequestParam(required = false) title: String?): ResponseEntity<List<Tutorial>?> {
        return try {
            logger.info("getAllTutorials, title:$title")
            val tutorials = if (title == null) tutorialCrud.findAll() else tutorialCrud.findByTitleContaining(title)
            if (tutorials.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else ResponseEntity(tutorials, HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error("getAllTutorials error", ex)
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getTutorialById(@PathVariable("id") id: String): ResponseEntity<Tutorial?> {
        logger.info("getTutorialById, id:$id")
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
            logger.info("createTutorial, tutorial:$tutorial")
            val data = tutorialCrud.save(Tutorial(title = tutorial.title, description = tutorial.description, published = false))
            ResponseEntity(data, HttpStatus.CREATED)
        } catch (ex: Exception) {
            logger.error("createTutorial error", ex)
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }

    @PutMapping("/{id}")
    fun updateTutorial(@PathVariable("id") id: String, @RequestBody tutorial: Tutorial): ResponseEntity<Tutorial?> {
        logger.info("updateTutorial, id:$id, tutorial:$tutorial")
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
            logger.info("deleteTutorial, id:$id")
            tutorialCrud.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: Exception) {
            logger.error("deleteTutorial error", ex)
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }

    @DeleteMapping
    fun deleteAllTutorials(): ResponseEntity<HttpStatus?> {
        return try {
            logger.info("deleteAllTutorials")
            tutorialCrud.deleteAll()
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: Exception) {
            logger.error("deleteAllTutorials error", ex)
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }

    @GetMapping("/published")
    fun findByPublished(): ResponseEntity<List<Tutorial>?> {
        return try {
            logger.info("findByPublished")
            val tutorials = tutorialCrud.findByPublished(true)
            if (tutorials.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else ResponseEntity(tutorials, HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error("findByPublished error", ex)
            ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }
}