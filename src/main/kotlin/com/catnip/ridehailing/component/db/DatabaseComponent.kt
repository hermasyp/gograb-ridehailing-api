package com.catnip.ridehailing.component.db

import com.catnip.ridehailing.model.entity.role.Role
import com.catnip.ridehailing.model.entity.user.User
import com.catnip.ridehailing.utils.constants.SystemEnv
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.springframework.stereotype.Component


@Component
class DatabaseComponent {
    private val databaseUrl = SystemEnv.DATABASE
    private val database: MongoClient = KMongo.createClient(databaseUrl)

    fun usersCollection(): MongoCollection<User> = database.getDatabase(COLLECTION_USER).getCollection()
    fun rolesCollection(): MongoCollection<Role> = database.getDatabase(COLLECTION_ROLE).getCollection()

    companion object {
        const val COLLECTION_USER = "users"
        const val COLLECTION_ROLE = "roles"
    }
}