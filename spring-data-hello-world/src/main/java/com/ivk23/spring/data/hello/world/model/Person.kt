package com.ivk23.spring.data.hello.world.model

import java.time.LocalDate

data class Person(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val creationDate: LocalDate
)