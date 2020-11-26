package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.Address
import org.springframework.data.repository.CrudRepository

interface AddressRepository : CrudRepository<Address, Long>
