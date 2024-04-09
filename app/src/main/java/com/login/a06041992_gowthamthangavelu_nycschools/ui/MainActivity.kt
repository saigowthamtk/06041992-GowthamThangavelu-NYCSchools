package com.login.a06041992_gowthamthangavelu_nycschools.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.login.a06041992_gowthamthangavelu_nycschools.R
import com.login.a06041992_gowthamthangavelu_nycschools.data.model.School
import com.login.a06041992_gowthamthangavelu_nycschools.data.repository.SchoolRepository
import com.login.a06041992_gowthamthangavelu_nycschools.databinding.ActivityMainBinding
import com.login.a06041992_gowthamthangavelu_nycschools.ui.adapter.SchoolAdapter
import com.login.a06041992_gowthamthangavelu_nycschools.util.NetworkUtils
import com.login.a06041992_gowthamthangavelu_nycschools.viewmodel.SchoolViewModel
import com.login.a06041992_gowthamthangavelu_nycschools.viewmodel.SchoolViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SchoolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewModel()
        checkInternetAndLoadData()
    }

    /**
     * Initializes the ViewModel for this activity.
     */
    private fun initializeViewModel() {
        val factory = SchoolViewModelFactory(SchoolRepository())
        viewModel = ViewModelProvider(this, factory)[SchoolViewModel::class.java]
    }

    /**
     * Checks internet connectivity and attempts to load data if connected.
     * Shows an error message otherwise.
     */
    private fun checkInternetAndLoadData() {
        if (NetworkUtils.isOnline(this)) {
            observeSchoolData()
        } else {
            showError(getString(R.string.no_internet))
        }
    }

    /**
     * Observes the schools LiveData from the ViewModel and updates UI accordingly.
     */
    private fun observeSchoolData() {
        viewModel.schools.observe(this) { schools ->
            if (schools.isNotEmpty()) {
                setupRecyclerView(schools)
            } else {
                // Handle empty data scenario, possibly due to API issues.
                showError(getString(R.string.no_data))
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    /**
     * Shows a Snackbar with the provided error message.
     */
    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Sets up the RecyclerView with the list of schools.
     */
    private fun setupRecyclerView(schools: List<School>) {
        with(binding.schoolsRecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = SchoolAdapter(schools) { school ->
                navigateToSchoolDetails(school.dbn)
            }
        }
    }

    /**
     * Navigates to SchoolDetailsActivity, passing the DBN of the selected school.
     */
    private fun navigateToSchoolDetails(dbn: String) {
        val intent = Intent(this, SchoolDetailsActivity::class.java).apply {
            putExtra("DBN", dbn)
        }
        startActivity(intent)
    }
}