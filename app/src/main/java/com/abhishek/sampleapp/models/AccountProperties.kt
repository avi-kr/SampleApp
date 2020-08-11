package com.abhishek.sampleapp.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@Parcelize
@Entity(tableName = "account_properties")
data class AccountProperties(
    @SerializedName("pk")
    @Expose
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    var pk: Int,
    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    var email: String,
    @SerializedName("username")
    @Expose
    @ColumnInfo(name = "username")
    var username: String
) : Parcelable