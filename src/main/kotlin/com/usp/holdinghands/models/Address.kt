package com.usp.holdinghands.models

import javax.persistence.*

@Entity
@Table(name = "addresses")
class Address(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        var zipCode: String,
        var address: String,
        var number: String,
        var complement: String? = null,
        val neighborhood: String,
        val city: String,
        val state: String,
        val country: String = "Brasil"
)
