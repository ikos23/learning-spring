package com.ivk23.petclinic.kotlin.model

import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.validation.constraints.NotBlank


@MappedSuperclass
open class Person : BaseEntity() {

    @NotBlank
    @Column(name = "first_name")
    open var firstName: String? = null

    @NotBlank
    @Column(name = "last_name")
    open var lastName: String? = null

    override fun toString(): String {
        return "Person(firstName=$firstName, lastName=$lastName)"
    }

}