package com.mistershorr.birthdaytracker

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import com.mistershorr.birthdaytracker.databinding.ActivityBirthdayDetailBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import com.backendless.exceptions.BackendlessFault

import com.backendless.async.callback.AsyncCallback

import com.backendless.Backendless

import android.R.string.no
import android.util.Log
import android.widget.Toast


class BirthdayDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityBirthdayDetailBinding
    lateinit var person : Person
    var personIsEditable = false
    var cal = Calendar.getInstance()

        companion object{
            val EXTRA_PERSON = "person"
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get the intent and fill in all the values
        person = intent.getParcelableExtra<Person>(EXTRA_PERSON) ?: Person()
        binding.editTextBirthdayDetailName.setText(person?.name)
        binding.editTextBirthdayDetailDesiredGift.setText(person?.giftwanted)
        binding.editTextBirthdayDetailBudget.setText(person?.giftbudget.toString())
        binding.checkBoxBirthdayDetailGiftBought.isChecked = person?.giftPurchased ?: false
        binding.textViewBirthdayDetailBirthdate.text = person?.birthday.toString()

        //save the object
        binding.buttonBirthdayDetailSave.setOnClickListener{



            person!!.name = binding.editTextBirthdayDetailName.text.toString()
            person!!.giftbudget = binding.editTextBirthdayDetailBudget.text.toString().toDouble()

            Backendless.Data.of(Person::class.java).save(person, object : AsyncCallback<Person?> {
                override fun handleResponse(response: Person?) {
                    // new Contact instance has been saved
                    Toast.makeText(this@BirthdayDetailActivity, "save successful", Toast.LENGTH_SHORT).show()

                }

                override fun handleFault(fault: BackendlessFault) {
                    Log.d("BrithdayActivity", "handleFault: ${fault.message}")
                }
            })}

        // datepicker code from https://www.tutorialkart.com/kotlin-android/android-datepicker-kotlin-example/
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        binding.textViewBirthdayDetailBirthdate.setOnClickListener {
            if(personIsEditable) {
                DatePickerDialog(this, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.textViewBirthdayDetailBirthdate.text = sdf.format(cal.getTime())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_birthday_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_item_birthday_detail_edit -> {
                toggleEditable()
                true
            }
            R.id.menu_item_birthday_delete->{
                deletefrombackendless()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun deletefrombackendless(){
        Backendless.Data.of(Person::class.java).remove(person,
        object : AsyncCallback<Long?>{
            override fun handleResponse(response: Long?) {
                finish()
            }

            override fun handleFault(fault: BackendlessFault) {
                Log.d("BirthdahDetail", "handleFault : ${fault.message}")
            }

        })
    }

    private fun toggleEditable() {
        if (personIsEditable) {
            personIsEditable = false
            binding.buttonBirthdayDetailSave.isEnabled = false
            binding.buttonBirthdayDetailSave.visibility = View.GONE
            binding.checkBoxBirthdayDetailGiftBought.isEnabled = false
            binding.editTextBirthdayDetailName.inputType = InputType.TYPE_NULL
            binding.editTextBirthdayDetailName.isEnabled = false
            binding.editTextBirthdayDetailDesiredGift.inputType = InputType.TYPE_NULL
            binding.editTextBirthdayDetailDesiredGift.isEnabled = false
            binding.editTextBirthdayDetailBudget.inputType = InputType.TYPE_NULL
            binding.editTextBirthdayDetailBudget.isEnabled = false
            binding.textViewBirthdayDetailBirthdate.isClickable = false
        } else {
            personIsEditable = true
            binding.buttonBirthdayDetailSave.isEnabled = true
            binding.buttonBirthdayDetailSave.visibility = View.VISIBLE
            binding.checkBoxBirthdayDetailGiftBought.isEnabled = true
            binding.editTextBirthdayDetailName.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextBirthdayDetailName.isEnabled = true
            binding.editTextBirthdayDetailDesiredGift.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextBirthdayDetailDesiredGift.isEnabled = true
            binding.editTextBirthdayDetailBudget.inputType = InputType.TYPE_NUMBER_VARIATION_NORMAL
            binding.editTextBirthdayDetailBudget.isEnabled = true
            binding.textViewBirthdayDetailBirthdate.isClickable = true
        }
    }

}