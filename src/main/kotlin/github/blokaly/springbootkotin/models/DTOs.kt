package github.blokaly.springbootkotin.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.joda.time.DateTime

data class ToDoMessage(val title: String,
                       val description: String,
                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                       val timeStamp:DateTime)