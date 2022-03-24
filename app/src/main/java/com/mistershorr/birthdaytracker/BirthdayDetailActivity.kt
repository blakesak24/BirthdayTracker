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

class BirthdayDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityBirthdayDetailBinding
    var personIsEditable = false
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            else -> super.onOptionsItemSelected(item)
        }
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
            binding.editTextBirthdayDetailName.isEnabled = false
            binding.editTextBirthdayDetailDesiredGift.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextBirthdayDetailDesiredGift.isEnabled = false
            binding.editTextBirthdayDetailBudget.inputType = InputType.TYPE_NUMBER_VARIATION_NORMAL
            binding.editTextBirthdayDetailBudget.isEnabled = false
            binding.textViewBirthdayDetailBirthdate.isClickable = true
        }
    }

}