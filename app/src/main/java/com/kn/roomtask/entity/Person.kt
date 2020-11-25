package com.kn.roomtask.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["company_id"],
            childColumns = ["person_company_id"],
            onDelete = ForeignKey.NO_ACTION
        )]
)
data class Person(
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "address") var address: String? = null,
    @ColumnInfo(name = "gender") var gender: String? = null,
    @ColumnInfo(name = "person_company_id") var personCompanyId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var personId = 0
}