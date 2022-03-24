package com.mistershorr.birthdaytracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mistershorr.birthdaytracker.databinding.ActivityBirthdayListBinding
import com.backendless.exceptions.BackendlessFault

import com.backendless.async.callback.AsyncCallback

import com.backendless.Backendless




class BirthdayListActivity : AppCompatActivity() {

    lateinit var binding : ActivityBirthdayListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadDataFromBackendless()

    }

    private fun loadDataFromBackendless() {

        Backendless.Data.of(Person::class.java).find(object : AsyncCallback<List<Person?>?> {
            override fun handleResponse(foundPeople: List<Person?>?) {
                // every loaded object from the "Contact" table is now an individual java.util.Map
                Log.d("BirthdayList", "handleResponse: ${foundPeople}")
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d("BirthdayList", "handleFault: ${fault.message}")
            }
        })

    }



}