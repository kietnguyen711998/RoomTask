package com.kn.roomtask.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kn.roomtask.entity.Company
import com.kn.roomtask.entity.Person

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun loadAllPersons(): List<Person?>?

    @Query("SELECT * FROM person WHERE person_company_id = :companyId")
    fun loadAllPersonByCompany(companyId: Int): List<Person?>?

    @Insert
    fun insertPerson(person: Person?)

    @Insert
    fun insertCompany(company: Company?)

    @Query("SELECT * FROM COMPANY WHERE company_id = :id")
    fun loadCompanyById(id: Int): Company?

    @Query("SELECT company_name FROM COMPANY")
    fun loadAllCompanyName(): List<String?>?

    @Query("SELECT company_id FROM COMPANY WHERE company_name = :companyName")
    fun getCompanyIdByName(companyName: String?): Int

    @Query("SELECT company_name FROM COMPANY WHERE company_id = :id")
    fun loadCompanyNameById(id: Int): String?
}