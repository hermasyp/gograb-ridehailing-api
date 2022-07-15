package com.catnip.ridehailing.repository.user

import com.catnip.ridehailing.component.db.DatabaseComponent
import com.catnip.ridehailing.entity.user.User
import com.catnip.ridehailing.component.config.exception.AppException
import com.catnip.ridehailing.utils.ext.toResult
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.id.toId
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(@Autowired private val databaseComponent: DatabaseComponent) : UserRepository {
    override fun createUser(user: User): Result<Boolean> {
        val isUserExist = getUserByUsername(user.username).isSuccess
        return if (isUserExist) {
            throw AppException("User with username ${user.username} is Already Exist")
        } else {
            databaseComponent.usersCollection().insertOne(user).wasAcknowledged().toResult()
        }
    }

    override fun getUserById(id: String): Result<User> {
        return databaseComponent.usersCollection().findOne(User::_id eq ObjectId(id).toId()).toResult("User with id $id cannot be found !")
    }

    override fun getUserByUsername(username: String): Result<User> {
        return databaseComponent.usersCollection().findOne(User::username eq username).toResult("User with username $username cannot be found !")
    }

    override fun updateUser(userId: String, user: User): Result<User> {
        val isUserExist = getUserByUsername(user.username).isSuccess
        return if (isUserExist) {
            databaseComponent.usersCollection().findOneAndUpdate(User::_id eq ObjectId(userId).toId(), set(User::username setTo user.username, User::password setTo user.password)).toResult()
        } else {
            throw AppException("User with id $userId does not exist !")
        }
    }

}

interface UserRepository {
    fun createUser(user: User): Result<Boolean>
    fun getUserById(id: String): Result<User>
    fun getUserByUsername(username: String): Result<User>
    fun updateUser(userId: String, user: User): Result<User>
}