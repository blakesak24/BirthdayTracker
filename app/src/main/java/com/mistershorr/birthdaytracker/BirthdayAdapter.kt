package com.mistershorr.birthdaytracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class BirthdayAdapter(var birthdayList: List<Person>) : RecyclerView.Adapter<BirthdayAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewName : TextView
        val textViewDaysUntil: TextView
        val layout : ConstraintLayout
        val checkBoxGiftBought : CheckBox

        init {
            textViewName = view.findViewById(R.id.textView_birthdayItem_name)
            textViewDaysUntil = view.findViewById(R.id.textView_birthdayItem_daysUntil)
            layout = view.findViewById(R.id.layout_birthdayItem)
            checkBoxGiftBought = view.findViewById(R.id.checkBox_birthdayItem_giftBought)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_birthday, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.layout.context
        val person = birthdayList[position]
        holder.textViewName.text = person.name
        person.birthday


    }

    override fun getItemCount(): Int {
        return birthdayList.size
    }
}