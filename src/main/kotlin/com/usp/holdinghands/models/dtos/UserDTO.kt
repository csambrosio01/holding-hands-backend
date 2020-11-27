package com.usp.holdinghands.models.dtos

import com.usp.holdinghands.models.Address
import com.usp.holdinghands.models.Gender
import com.usp.holdinghands.models.HelpType
import java.util.*

class UserDTO(
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
