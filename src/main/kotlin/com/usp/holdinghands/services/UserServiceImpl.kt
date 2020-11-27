package com.usp.holdinghands.services

import com.usp.holdinghands.models.HelpType
import com.usp.holdinghands.models.User
import com.usp.holdinghands.models.dtos.UserDTO
import com.usp.holdinghands.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun createUser(userRequest: UserDTO): User {
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

        return userRepository.save(user)
    }

    override fun getUsers(): List<User> {
        return userRepository.findAll()
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
