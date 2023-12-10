package mara.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MaraServerApplication

fun main(args: Array<String>) {
    runApplication<MaraServerApplication>(*args)
}
