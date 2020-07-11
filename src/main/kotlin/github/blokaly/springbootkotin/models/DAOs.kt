package github.blokaly.springbootkotin.models

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository("TutorialDao")
interface TutorialDao : MongoRepository<Tutorial, String> {
    fun findByPublished(published: Boolean): List<Tutorial>
    fun findByTitleContaining(title: String?): List<Tutorial>
}