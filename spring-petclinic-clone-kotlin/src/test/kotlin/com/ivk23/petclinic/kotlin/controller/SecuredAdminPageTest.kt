package com.ivk23.petclinic.kotlin.controller

import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SecuredAdminPageTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @WithMockUser(username = "baz", roles = ["ADMIN"])
    @Test
    fun adminPage() {
        mockMvc.get("/admin")
                .andExpect {
                    status { isOk }
                    view { name("adminConsole") }
                    model {
                        attribute("currentUser", equalTo("baz"))
                    }
                }
    }

    @WithMockUser(username = "lol", roles = ["USER"])
    @Test
    fun adminPage_Forbidden() {
        mockMvc.get("/admin")
                .andExpect {
                    status { isForbidden }
                }
    }

}