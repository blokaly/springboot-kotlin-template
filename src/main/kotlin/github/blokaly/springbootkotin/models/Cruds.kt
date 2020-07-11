package github.blokaly.springbootkotin.models

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class TutorialCrud(@Qualifier("TutorialDao") private val dao: TutorialDao) : TutorialDao by dao {
    fun update(tutorial: Tutorial, update: Tutorial): Tutorial {
        val updated = tutorial.copy(title = update.title, description = update.description, published = update.published)
        return save(updated)
    }
}