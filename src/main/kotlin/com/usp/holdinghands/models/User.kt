package com.usp.holdinghands.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.management.monitor.StringMonitor
import javax.persistence.*
import kotlin.collections.ArrayList
import kotlin.jvm.Transient

enum class Gender {
    MALE, FEMALE, BOTH
}

enum class HelpType {
    TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5,
}

@Converter(autoApply = true)
object ListHelpTypesConverter : AttributeConverter<List<HelpType>, String?> {
    override fun convertToDatabaseColumn(attribute: List<HelpType>?): String? {
        if (attribute != null) {
            var value = ""
            for (helpType in attribute) value += helpType.name + ","
            return value.dropLast(1)
        }
        return null
    }

    override fun convertToEntityAttribute(dbData: String?): List<HelpType> {
        if (dbData != null) {
            val dbValues: List<String> =
                    dbData.split("\\s*,\\s*".toRegex()).toList()
            val enums: MutableList<HelpType> = ArrayList()
            for (s in dbValues) enums.add(HelpType.valueOf(s))
            return enums
        }
        return mutableListOf()
    }
}

@Entity
@Table(name = "users")
class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var userId: Long? = null,
        var name: String,
        @Transient var age: Int,
        @Transient var distance: Double,
        var helpTypes: String?,
        @Enumerated(EnumType.STRING) var gender: Gender,
        var profession: String,
//        var numberOfHelps: Int,
        @Column(unique = true) var email: String,
        @JsonIgnore var password: String,
        @Column(unique = true) var phone: String,
        var isHelper: Boolean,
        @Temporal(TemporalType.DATE) var birth: Calendar,
        @JsonIgnore var latitude: Double,
        @JsonIgnore var longitude: Double,
        var imageId: String? = null,
        var rating: Double = 5.0,
        @JsonIgnore var blocked: Boolean = false
)
