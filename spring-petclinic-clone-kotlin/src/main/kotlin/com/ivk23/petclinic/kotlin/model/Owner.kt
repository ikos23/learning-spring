package com.ivk23.petclinic.kotlin.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "owners")
@ApiModel(description = "Owner v1 model.")
class Owner : Person() {

    @Column(name = "address")
    var address: String? = null

    @Column(name = "city")
    var city: String? = null

    @NotBlank
    @Column(name = "telephone")
    var telephone: String? = null

    @ApiModelProperty(hidden = true)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    var pets: MutableSet<Pet> = HashSet()

    override fun toString(): String {
        return "Owner(pets=$pets, address=$address, city=$city, telephone=$telephone) ${super.toString()}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Owner) return false
        if (!super.equals(other)) return false

        if (address != other.address) return false
        if (city != other.city) return false
        if (telephone != other.telephone) return false
        if (pets != other.pets) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (telephone?.hashCode() ?: 0)
        result = 31 * result + pets.hashCode()
        return result
    }


}