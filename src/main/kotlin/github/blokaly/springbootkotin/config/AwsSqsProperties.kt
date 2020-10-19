package github.blokaly.springbootkotin.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("cloud.aws")
data class AwsSqsProperties(val credentials: AwsCredential,
                            val region: String,
                            val endpoint: String,
                            val messageGroup: String,
                            val messageQueue: String) {
    data class AwsCredential (val accessKey: String, val secretKey: String)
}

data class AwsSqsQueue(val name: String)