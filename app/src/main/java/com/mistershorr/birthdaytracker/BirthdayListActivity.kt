package com.mistershorr.birthdaytracker

import android.content.Intent
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
    lateinit var userId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = intent.getStringExtra(LoginActivity.EXTRA_USER_ID)?: ""
        loadDataFromBackendless()
        binding.fabBirthdaylistCreateNew.setOnClickListener{
            val detailIntent = Intent(this,BirthdayDetailActivity::class.java)
            startActivity(detailIntent)
        }
    }
        override fun onStart(){
            super.onStart()
            loadDataFromBackendless()
        }
    private fun loadDataFromBackendless() {

        var whereClause = "ownerId = '$userId'"
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