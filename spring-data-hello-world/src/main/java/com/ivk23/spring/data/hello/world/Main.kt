package com.ivk23.spring.data.hello.world

import com.ivk23.spring.data.hello.world.model.Person
import com.ivk23.spring.data.hello.world.spring.config.SimpleSpringConfig
import com.ivk23.spring.data.hello.world.spring.config.TestJdbcTemplateSpringConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.jdbc.core.JdbcTemplate

fun main() {
    val ctx = AnnotationConfigApplicationContext(
        SimpleSpringConfig::class.java,
        TestJdbcTemplateSpringConfig::class.java
    )

    println("Available beans: ${ctx.beanDefinitionNames.size} .")
    println("--------------------------------------------------")

    ctx.beanDefinitionNames.forEach { println(it) }

    val jdbcTemplate = ctx.getBean(JdbcTemplate::class.java)

    val resultList = jdbcTemplate.query("select * from persons") { rs, _ ->
        val firstName = rs.getString("first_name")
        val lastName = rs.getString("last_name")
        val userName = rs.getString("username")
        val created = rs.getDate("creation_date").toLocalDate()
        Person(firstName, lastName, userName, created)
    }

    println(">>> ${resultList.size} persons found.")
    resultList.forEach(::println)

}