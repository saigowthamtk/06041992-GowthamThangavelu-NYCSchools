package com.login.a06041992_gowthamthangavelu_nycschools.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.login.a06041992_gowthamthangavelu_nycschools.R
import com.login.a06041992_gowthamthangavelu_nycschools.data.model.SatScore
import com.login.a06041992_gowthamthangavelu_nycschools.data.model.School
import com.login.a06041992_gowthamthangavelu_nycschools.data.repository.SchoolRepository
import com.login.a06041992_gowthamthangavelu_nycschools.databinding.ActivitySchoolDetailsBinding
import com.login.a06041992_gowthamthangavelu_nycschools.util.NetworkUtils
import com.login.a06041992_gowthamthangavelu_nycschools.viewmodel.SchoolViewModel
import com.login.a06041992_gowthamthangavelu_nycschools.viewmodel.SchoolViewModelFactory


class SchoolDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchoolDetailsBinding
    private lateinit var viewModel: SchoolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Checking for network connectivity before proceeding
        if (!NetworkUtils.isOnline(this)) {
            showSnackbar(R.string.no_internet)
            return
        }

        // Initialize the ViewModel
        val factory = SchoolViewModelFactory(SchoolRepository())
        viewModel = ViewModelProvider(this, factory).get(SchoolViewModel::class.java)

        // Extract the DBN from the intent
        val dbn = intent.getStringExtra("DBN")
        if (dbn != null) {
            // Fetch and observe school details and SAT scores
            fetchAndObserveSchoolDetails(dbn)
            fetchAndObserveSatScores(dbn)
        } else {
            showSnackbar(R.string.no_dbn_details)
        }
    }

    private fun fetchAndObserveSchoolDetails(dbn: String) {
        viewModel.fetchSchoolDetails(dbn)
        viewModel.schoolDetails.observe(this) { school ->
            progressBarHide()
            school?.let {
                displaySchoolDetails(it) } ?: Log.d(TAG, getString(R.string.no_school_details))
        }
    }

    private fun fetchAndObserveSatScores(dbn: String) {
        viewModel.fetchSatScoresForSchool(dbn)

        viewModel.satScores.observe(this) { result ->
            progressBarHide()
            result?.let {
                displaySatScores(it)
            } ?: run {
                Log.d(TAG, getString(R.string.no_sat_score))
            }
        }
    }
    private fun displaySchoolDetails(school: School) {
        with(binding) {
            textViewSchoolName.text = school.school_name
        }
    }

    private fun displaySatScores(satScore: SatScore) {
        with(binding) {

            textViewDBN.text = getString(R.string.dbn_format, satScore.dbn)
            textViewNumTestTakers.text = getString(R.string.num_test_takers_format, satScore.num_of_sat_test_takers)
            progressBarSatReading.progress = satScore.sat_critical_reading_avg_score.toIntOrNull() ?: 0
            progressBarSatMath.progress = satScore.sat_math_avg_score.toIntOrNull() ?: 0
            progressBarSatWriting.progress = satScore.sat_writing_avg_score.toIntOrNull() ?: 0

            scoreSatReading.text = satScore.sat_critical_reading_avg_score
            scoreSatMath.text = satScore.sat_math_avg_score
            scoreSatWriting.text = satScore.sat_writing_avg_score
        }
    }

    private fun showSnackbar(messageResId: Int) {
        Snackbar.make(binding.root, messageResId, Snackbar.LENGTH_LONG).show()
    }

    private fun progressBarHide(){
        binding.progressBarDetails.visibility=View.GONE
        binding.contentLayout.visibility=View.VISIBLE
    }
}