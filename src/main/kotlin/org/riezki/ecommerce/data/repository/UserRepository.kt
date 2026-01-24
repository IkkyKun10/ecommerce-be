package org.riezki.ecommerce.data.repository

import org.riezki.ecommerce.data.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {

    fun findByPhoneNumber(phoneNumber: String) : UserEntity?
}