package github.blokaly.springbootkotin.models

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class UserMessageCrud(@Qualifier("UserMessageDao") private val dao: UserMessageDao) : UserMessageDao by dao