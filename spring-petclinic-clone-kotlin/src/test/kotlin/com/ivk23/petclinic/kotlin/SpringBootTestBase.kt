package com.ivk23.petclinic.kotlin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.ivk23.petclinic.kotlin.repository.OwnerRepository
import com.ivk23.petclinic.kotlin.repository.PetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["default", "metrics"])
abstract class SpringBootTestBase {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var ownerRepository: OwnerRepository

    @Autowired
    protected lateinit var petRepository: PetRepository

    // This might be interesting :)
    // Serializing LocalDate with Jackson! e.g.
    /*
        {
          "id" : null,
          "name" : "Barry",
          "type" : "Dog",
          "ownerId" : 1,
          "birthDate" : "2020-02-02",
          "visits" : [ ]
        }
    */
    protected val objectMapper: ObjectMapper = ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

}