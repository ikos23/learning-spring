package com.ivk23.petclinic.kotlin.model

import javax.persistence.*
import javax.persistence.CascadeType.PERSIST
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "owners")
class Owner : Person() {

    @Column(name = "address")
    var address: String? = null

    @Column(name = "city")
    var city: String? = null

    @NotBlank
    @Column(name = "telephone")
    var telephone: String? = null

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    var pets: MutableSet<Pet> = HashSet()

    override fun toString(): String {
        return "Owner(pets=$pets, address=$address, city=$city, telephone=$telephone) ${super.toString()}"
    }

}