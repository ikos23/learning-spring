package com.ivk23.webflux.spring.bootstrap

import com.ivk23.webflux.spring.document.Category
import com.ivk23.webflux.spring.document.Vendor
import com.ivk23.webflux.spring.repository.CategoryRepository
import com.ivk23.webflux.spring.repository.VendorRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("dev")
@Component
class TestDataBootstrap(private val vr: VendorRepository,
                        private val cr: CategoryRepository) : CommandLineRunner {


    companion object {
        val log: Logger = LoggerFactory
                .getLogger(TestDataBootstrap::class.java)
    }

    override fun run(vararg args: String?) {
        vr.deleteAll().block()
        cr.deleteAll().block()

        vr.save(createVendor("Donald", "Duck")).block()
        vr.save(createVendor("Tony", "Stark")).block()

        cr.save(createCategory("Fruits")).block()
        cr.save(createCategory("Vegetables")).block()
        cr.save(createCategory("Eggs")).block()
        cr.save(createCategory("Bread")).block()

        log.info("Test Data Created Successfully ^_^")
    }

    private fun createVendor(firstName: String, lastName: String): Vendor {
        val vendor = Vendor()
        vendor.firstName = firstName
        vendor.lastName = lastName
        return vendor
    }

    private fun createCategory(description: String): Category {
        val categ = Category()
        categ.description = description
        return categ
    }
}