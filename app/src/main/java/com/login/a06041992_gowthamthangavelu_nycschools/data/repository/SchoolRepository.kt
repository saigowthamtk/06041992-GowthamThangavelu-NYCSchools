package com.login.a06041992_gowthamthangavelu_nycschools.data.repository


import com.login.a06041992_gowthamthangavelu_nycschools.data.model.SatScore
import com.login.a06041992_gowthamthangavelu_nycschools.data.model.School
import com.login.a06041992_gowthamthangavelu_nycschools.data.network.RetrofitInstance

class SchoolRepository() {

     val apiService = RetrofitInstance.api

    //Get All School
    suspend fun getSchools(): List<School> {
        return apiService.getSchools()
    }

    //Get School details based on dbn
    suspend fun getSchoolDetails(dbn: String): School? = apiService.getSchools().find { it.dbn == dbn }

    //Get SAT score based on dbn
    suspend fun getSatScoresForSchool(dbn: String): SatScore? = apiService.getSatScores().find { it.dbn == dbn }

}