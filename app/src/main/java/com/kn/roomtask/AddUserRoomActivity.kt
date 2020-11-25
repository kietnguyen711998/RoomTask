package com.kn.roomtask

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.kn.roomtask.AppExecutorRoom.Companion.instance
import com.kn.roomtask.MyDatabase.Companion.getInstance
import com.kn.roomtask.entity.Person
import kotlinx.android.synthetic.main.activity_add_user_room.*

class AddUserRoomActivity : AppCompatActivity() {
    private var db: MyDatabase? = null
    private var companies: List<String?>? = null
    private var companyName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user_room)
        init()
    }

    private fun init() {
        db = getInstance(applicationContext)
        companies = ArrayList()
        instance!!.diskIO().execute {
            companies = db!!.personDao()!!.loadAllCompanyName()
            initSpinner()
        }
        val genders = ArrayList<String>()
        genders.add("Male")
        genders.add("Female")
        genders.add("Other")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender!!.adapter = genderAdapter
        btnAddPersonItem!!.setOnClickListener {
            val name = edtPersonName!!.text.toString().trim { it <= ' ' }
            val address = edtAddress!!.text.toString().trim { it <= ' ' }
            val gender = spinnerGender!!.selectedItem.toString()
            companyName = spinnerCompany.selectedItem.toString()
            if (companyName != null && name != "" && address != "" && gender!= "") {
                instance!!.diskIO().execute {
                    val companyId = db!!.personDao()!!.getCompanyIdByName(companyName)
                    db!!.personDao()!!.insertPerson(Person(name, address, gender, companyId))
                }
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                edtPersonName!!.setText("")
                edtAddress!!.setText("")
            }
        }
    }

    private fun initSpinner() {
        val companyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, companies!!)
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCompany!!.adapter = companyAdapter
    }
}