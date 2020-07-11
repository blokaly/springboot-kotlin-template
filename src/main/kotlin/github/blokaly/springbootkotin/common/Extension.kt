package github.blokaly.springbootkotin.common

import java.util.*

fun <T : Any> Optional<T>.toNullable(): T? = orElse(null)