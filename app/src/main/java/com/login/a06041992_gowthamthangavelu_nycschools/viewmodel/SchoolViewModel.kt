package com.login.a06041992_gowthamthangavelu_nycschools.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.login.a06041992_gowthamthangavelu_nycschools.data.model.SatScore
import com.login.a06041992_gowthamthangavelu_nycschools.data.model.School
import com.login.a06041992_gowthamthangavelu_nycschools.data.repository.SchoolRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class SchoolViewModel(private val repository: SchoolRepository) : ViewModel() {
    private val _schools = MutableLiveData<List<School>>()
    val schools: LiveData<List<School>> = _schools

    private val _schoolDetails = MutableLiveData<School?>()
    val schoolDetails: LiveData<School?> = _schoolDetails

    private val _satScores = MutableLiveData<SatScore?>()
    val satScores: LiveData<SatScore?> = _satScores


    private val coroutineExceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, exception ->
    }

    init {
        loadSchools()
    }
    //Fetch All Schools
    fun loadSchools() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val schoolsList = repository.getSchools()
            _schools.postValue(schoolsList)
        }
    }

    //Fetch School details
    fun fetchSchoolDetails(dbn: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val details = repository.getSchoolDetails(dbn)
            _schoolDetails.postValue(details)
        }
    }

    //Fetch Sat scores
    fun fetchSatScoresForSchool(dbn: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val scores = repository.getSatScoresForSchool(dbn)
            _satScores.postValue(scores)
        }
    }
}