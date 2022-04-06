package com.mistershorr.birthdaytracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mistershorr.birthdaytracker.databinding.ActivityBirthdayListBinding
import com.backendless.exceptions.BackendlessFault

import com.backendless.async.callback.AsyncCallback

import com.backendless.Backendless
import com.backendless.persistence.DataQueryBuilder







class BirthdayListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBirthdayListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadDataFromBackendless()

    }

    private fun loadDataFromBackendless() {
            val objectId = Backendless.UserService.CurrentUser().objectId
        val whereClause = "ownerId = '$objectId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause


        Backendless.Data.of(Person::class.java).find(queryBuilder, object : AsyncCallback<List<Person?>?> {
            override fun handleResponse(foundPeople: List<Person?>?) {
                // every loaded object from the "Contact" table is now an individual java.util.Map
                Log.d("BirthdayList", "handleResponse: ${foundPeople}")
                val adapter = BirthdayAdapter(((foundPeople ?: listOf()) as List<Person>))
                binding.recyclerViewBirthdayListPeople.adapter = adapter
                binding.recyclerViewBirthdayListPeople.layoutManager= LinearLayoutManager(this@BirthdayListActivity)
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d("BirthdayList", "handleFault: ${fault.message}")
            }
        })

    }



}