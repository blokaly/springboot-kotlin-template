package github.blokaly.springbootkotin.models

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class TutorialCrud(@Qualifier("TutorialDao") private val dao: TutorialDao) : TutorialDao by dao {
    fun update(tutorial: Tutorial): Tutorial {
        val updated = tutorial.copy(title = tutorial.title, description = tutorial.description, published = tutorial.published)
        return save(updated)
    }
}