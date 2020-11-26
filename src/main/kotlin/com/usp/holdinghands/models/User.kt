package com.usp.holdinghands.models

import java.util.Date
import javax.management.monitor.StringMonitor
import javax.persistence.*

enum class Gender {
    MALE, FEMALE, BOTH
}

enum class HelpType {
    TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5,
}

@Converter(autoApply = true)
class ListHelpTypesConverter : AttributeConverter<List<HelpType>, String?> {
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
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        var name: String,
//        var age: Int,
//        var distance: Double,
        var helpTypes: String?,
        @Enumerated(EnumType.STRING) var gender: Gender,
        var profession: String,
//        var numberOfHelps: Int,
        var email: String,
        var password: String,
        var phone: String,
        var isHelper: Boolean,
        @Temporal(TemporalType.DATE) var birth: Date,
        @OneToOne(cascade = [CascadeType.ALL]) val address: Address,
        var imageId: String? = null,
        var rating: Double = 5.0
)

class UserRequest(
        var name: String,
        var helpTypes: List<HelpType>,
        var gender: Gender,
        var profession: String,
        var birth: Date,
        var email: String,
        var phone: String,
        var password: String,
        var isHelper: Boolean,
        var address: Address
)
