package com.abhishek.paidcourseapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhishek.paidcourseapp.models.AccountProperties

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@Dao
interface AccountPropertiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(accountProperties: AccountProperties) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(accountProperties: AccountProperties) : Long

    @Query("SELECT * From account_properties Where pk = :pk")
    fun searchByPk(pk: Int) : AccountProperties?

    @Query("SELECT * From account_properties Where email = :email")
    fun searchByEmail(email: String) : AccountProperties?
}