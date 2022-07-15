package com.catnip.ridehailing.service.role

import com.catnip.ridehailing.entity.role.Role
import com.catnip.ridehailing.repository.role.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(@Autowired private val roleRepository: RoleRepository) : RoleServices{
    override fun findRoleById(roleId: String): Result<Role> {
        return roleRepository.findRoleById(roleId)
    }

    override fun findRoleByTag(roleTag: String): Result<Role> {
        return roleRepository.findRoleByTag(roleTag)
    }
}

interface RoleServices {
    fun findRoleById(roleId : String) : Result<Role>
    fun findRoleByTag(roleTag : String) : Result<Role>
}