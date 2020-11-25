package com.kn.roomtask

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kn.roomtask.AppExecutorRoom.Companion.instance
import com.kn.roomtask.MyDatabase.Companion.getInstance
import com.kn.roomtask.entity.Person

class RoomUserAdapter(var context: Context, private var personRoomList: List<Person>) :
    RecyclerView.Adapter<RoomUserAdapter.PersonViewHolder>() {
    private var companyName: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room_list, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.tvName.text = personRoomList[position].name
        holder.tvGender.text = personRoomList[position].gender
        holder.tvAddress.text = personRoomList[position].address
        instance!!.diskIO().execute {
            val db = getInstance(context)
            companyName =
                db!!.personDao()!!.loadCompanyNameById(personRoomList[position].personCompanyId)
            Log.d("xxx", "company " + companyName.toString())
            holder.tvCompany.text = companyName
        }

    }

    override fun getItemCount(): Int {
        return personRoomList.size
    }

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tvPersonName)
        var tvGender: TextView = itemView.findViewById(R.id.tvGender)
        var tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        var tvCompany: TextView = itemView.findViewById(R.id.tvCompany)
    }
}

