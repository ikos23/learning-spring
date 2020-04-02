package com.ivk23.webflux.spring.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Vendor {

    @Id
    var id: String? = null

    var firstName: String? = null

    var lastName: String? = null

}