package com.catnip.ridehailing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RideHailingApiApplication

fun main(args: Array<String>) {
    runApplication<RideHailingApiApplication>(*args)
}
