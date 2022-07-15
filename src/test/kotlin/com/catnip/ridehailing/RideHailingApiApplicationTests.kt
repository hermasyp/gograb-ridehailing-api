package com.catnip.ridehailing

import com.catnip.ridehailing.model.entity.role.Role
import com.catnip.ridehailing.model.entity.user.User
import com.catnip.ridehailing.repository.role.RoleRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
@SpringBootTest(classes = [RideHailingApiApplication::class])
class RideHailingApiApplicationTests {

    @Autowired
    private lateinit var roleRepository : RoleRepository
    @Test
    fun contextLoads() {
        val result = roleRepository.createRoles(mutableListOf(
                Role(roleName = "User", roleTag = "role_user"),
                Role(roleName = "Driver", roleTag = "role_driver")
        ))
        println(result.isSuccess)

    }

}
