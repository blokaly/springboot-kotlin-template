package github.blokaly.springbootkotin

import github.blokaly.springbootkotin.common.MainLogging
import github.blokaly.springbootkotin.common.logger
import github.blokaly.springbootkotin.config.AwsSqsProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import javax.annotation.PreDestroy

fun main(args: Array<String>) {
    runApplication<ApplicationMain>(*args)
}

@SpringBootApplication
@EnableConfigurationProperties(AwsSqsProperties::class)
@EnableMongoRepositories
@EnableMongoAuditing
class ApplicationMain {
    @PreDestroy
    @Throws(Exception::class)
    fun onDestroy() {
        MainLogging.logger().info("Application terminated")
    }
}