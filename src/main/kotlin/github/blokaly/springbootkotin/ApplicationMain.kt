package github.blokaly.springbootkotin

import github.blokaly.springbootkotin.common.MainLogging
import github.blokaly.springbootkotin.common.logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

fun main(args: Array<String>) {
    runApplication<ApplicationMain>(*args)
}

@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing
@EnableScheduling
class ApplicationMain {
    @PreDestroy
    @Throws(Exception::class)
    fun onDestroy() {
        MainLogging.logger().info("Application terminated")
    }
}