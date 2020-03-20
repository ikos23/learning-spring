package com.ivk23.spring.data.hello.world.spring.config

import com.ivk23.spring.data.hello.world.model.Person
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringJUnitConfig(classes = [TestJdbcTemplateSpringConfig::class])
open class TestJdbcTemplateSpringConfigTest {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun contextTest() {
        assertNotNull(jdbcTemplate);
    }

    @Test
    fun simpleSelectTest() {
        val resultSet = HashSet<Person>(
            jdbcTemplate.query("select * from persons") { rs, _ ->
                Person(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"), rs.getDate("creation_date").toLocalDate()
                )
            })

        assertEquals(2, resultSet.size)

        val p1 = Person(
            "Donald",
            "Duck",
            "don@dd.com",
            LocalDate.of(2019, 5, 14)
        )

        val p2 = Person(
            "Tony",
            "Stark",
            "tony@start.com",
            LocalDate.of(2017, 1, 1)
        )

        resultSet.forEach(::println)

        assertTrue { resultSet.contains(p1) }
        assertTrue { resultSet.contains(p2) }
    }

    @Test
    fun crudTest() {
        // let's do insert
        val insert = "INSERT INTO PERSONS VALUES (3, 'User', 'From Test', 'created@by.unitest', '2020-02-12')";
        val updatedNo = jdbcTemplate.update(insert)
        assertEquals(1, updatedNo);

        // let's check count after this insert
        assertEquals(3, jdbcTemplate.queryForObject("select count(*) from persons", Int::class.java))

        // let's find person which does not exist
        assertThrows<EmptyResultDataAccessException> {
            jdbcTemplate.queryForObject("select * from persons where id=12345") { rs, _ ->
                Person(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"), rs.getDate("creation_date").toLocalDate()
                )
            }
        }

        // a little clean up
        assertEquals(1, jdbcTemplate.update("delete from persons where id=3"))
    }

}