package com.ivk23.spring.data.hello.world.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@Configuration
open class TestJdbcTemplateSpringConfig {

    @Bean
    open fun dataSource(): DataSource {
        val dbBuilder = EmbeddedDatabaseBuilder()
        val db = dbBuilder.setType(EmbeddedDatabaseType.H2)
            .generateUniqueName(true)
            .addScript("db/test-schema.sql")
            .addScript("db/test-data.sql")
            .build()
        return db
    }

    @Bean
    open fun jdbcTemplate(): JdbcTemplate {
        return JdbcTemplate(dataSource());
    }

}