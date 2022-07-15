package com.catnip.ridehailing.repository.role

import com.catnip.ridehailing.component.db.DatabaseComponent
import com.catnip.ridehailing.model.entity.role.Role
import com.catnip.ridehailing.utils.ext.toResult
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.id.toId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class RoleRepositoryImpl(@Autowired private val databaseComponent: DatabaseComponent) : RoleRepository {
    override fun createRoles(roles: List<Role>): Result<Boolean> {
        return databaseComponent.rolesCollection().insertMany(roles.toMutableList()).wasAcknowledged().toResult()
    }

    override fun findRoleById(roleId: String): Result<Role> {
        return databaseComponent.rolesCollection().findOne(Role::_id eq ObjectId(roleId).toId()).toResult()
    }

    override fun findRoleByTag(roleTag: String): Result<Role> {
        return databaseComponent.rolesCollection().findOne(Role::roleTag eq roleTag).toResult()
    }

}

interface RoleRepository {
    fun createRoles(roles: List<Role>): Result<Boolean>
    fun findRoleById(roleId: String): Result<Role>
    fun findRoleByTag(roleTag: String): Result<Role>
}