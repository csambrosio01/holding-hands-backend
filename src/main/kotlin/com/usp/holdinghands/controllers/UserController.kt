package com.usp.holdinghands.controllers

import com.usp.holdinghands.models.HelpType
import com.usp.holdinghands.models.User
import com.usp.holdinghands.models.UserRequest
import com.usp.holdinghands.repositories.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/user")
class UserController(val userRepository: UserRepository) {

    @PostMapping("/create")
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<Any> {
        val user = User(
                name = userRequest.name,
                helpTypes = convertToDatabaseColumn(userRequest.helpTypes),
                gender = userRequest.gender,
                profession = userRequest.profession,
                email = userRequest.email,
                password = userRequest.password,
                phone = userRequest.phone,
                isHelper = userRequest.isHelper,
                birth = userRequest.birth,
                address = userRequest.address
        )
        return try{
            ResponseEntity(userRepository.save(user), HttpStatus.OK)
        }
        catch (e: DataIntegrityViolationException){
            ResponseEntity("Duplicate value", HttpStatus.BAD_REQUEST)
        }
    }

    private fun convertToDatabaseColumn(attribute: List<HelpType>?): String? {
        if (attribute != null && attribute.isNotEmpty()) {
            var value = ""
            for (helpType in attribute) value += helpType.name + ","
            return value.dropLast(1)
        }
        return null
    }
}
