package com.abhishek.sampleapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhishek.sampleapp.models.AccountProperties
import com.abhishek.sampleapp.models.AuthToken

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@Database(entities = [AuthToken::class, AccountProperties::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAuthTokenDao() : AuthTokenDao

    abstract fun getAccountPropertiesDao() : AccountPropertiesDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }

}