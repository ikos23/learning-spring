package com.ivk23.webflux.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringWebfluxHelloWorldApplication

fun main(args: Array<String>) {
	runApplication<SpringWebfluxHelloWorldApplication>(*args)
}
