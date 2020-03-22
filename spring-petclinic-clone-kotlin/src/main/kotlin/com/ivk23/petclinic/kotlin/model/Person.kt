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


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Person) return false
        if (!super.equals(other)) return false

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Person(id=$id, firstName=$firstName, lastName=$lastName)"
    }

}