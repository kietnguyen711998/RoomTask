package com.kn.roomtask

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kn.roomtask.AppExecutorRoom.Companion.instance
import com.kn.roomtask.MyDatabase.Companion.getInstance
import com.kn.roomtask.entity.Company
import com.kn.roomtask.entity.Person

class RoomUserActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var personAdapter: RoomUserAdapter? = null
    var db: MyDatabase? = null
    var companies: MutableList<Company>? = null
    var persons: List<Person>? = null
    private var spinnerCompany: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_user)
        recyclerView = findViewById(R.id.rvPerson)
        spinnerCompany = findViewById(R.id.spinnerFilter)
        db = getInstance(applicationContext)
        companies = ArrayList()
        companies!!.add(Company(1, "Google Inc", "Mobile Developer"))
        companies!!.add(Company(2, "Facebook Inc", "Artificial intelligence"))
        companies!!.add(Company(3, "Apple Inc", "Marketing Manager"))
        companies!!.add(Company(4, "Goldman Sachs Bank", "Banking"))
        companies!!.add(Company(5, "MaxMobile Software", "CEO"))
        instance!!.diskIO().execute {
            if (db!!.personDao()!!.loadCompanyById(1) == null) {
                for (company in companies!!) {
                    db!!.personDao()!!.insertCompany(company)
                }
            }
            companies!!.add(Company(6, "All", ""))
        }
        initSpinner()

    }

    private fun initSpinner() {
        val companyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, companies!!)
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCompany!!.adapter = companyAdapter
        spinnerCompany!!.setSelection(5)
        spinnerCompany!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (companies!![i].companyName != "All") {
                    instance!!.diskIO().execute {
                        ///load company name
                        persons =
                            db!!.personDao()!!.loadAllPersonByCompany(companies!![i].companyId) as List<Person>?
                        runOnUiThread {
                            recyclerView!!.layoutManager =
                                LinearLayoutManager(this@RoomUserActivity)
                            recyclerView!!.itemAnimator = DefaultItemAnimator()
                            personAdapter = RoomUserAdapter(this@RoomUserActivity, persons!!)
                            recyclerView!!.adapter = personAdapter




                        }
                    }
                } else {
                    instance!!.diskIO().execute {
                        persons = db!!.personDao()!!.loadAllPersons() as List<Person>?
                        runOnUiThread {
                            recyclerView!!.layoutManager =
                                LinearLayoutManager(this@RoomUserActivity)
                            recyclerView!!.itemAnimator = DefaultItemAnimator()
                            personAdapter = RoomUserAdapter(this@RoomUserActivity, persons!!)
                            recyclerView!!.adapter = personAdapter
                        }
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_room, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                val intent = Intent(this@RoomUserActivity, AddUserRoomActivity::class.java)
                startActivityForResult(intent, ACTION_REQUEST_ADD)
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ACTION_REQUEST_ADD -> instance!!.diskIO().execute {
                    persons = db!!.personDao()!!.loadAllPersons() as List<Person>?
                    runOnUiThread {
                        //recyclerView!!.layoutManager = LinearLayoutManager(this@RoomUserActivity)
                        personAdapter = RoomUserAdapter(this@RoomUserActivity, persons!!)
                        recyclerView!!.adapter = personAdapter
                        spinnerCompany!!.setSelection(5)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val ACTION_REQUEST_ADD = 111
    }
}