package com.example.predictor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PredictorApplication

fun main(args: Array<String>) {
    runApplication<PredictorApplication>(*args)
}
