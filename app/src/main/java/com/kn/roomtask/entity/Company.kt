package com.kn.roomtask.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["company_id"], unique = true)], tableName = "company")
data class Company(
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "company_id") var companyId: Int,
    @field:ColumnInfo(name = "company_name") var companyName: String,
    @field:ColumnInfo(name = "field") var field: String
) {

    override fun toString(): String {
        return companyName
    }

}