package com.login.a06041992_gowthamthangavelu_nycschools.data.network

import com.login.a06041992_gowthamthangavelu_nycschools.data.model.SatScore
import com.login.a06041992_gowthamthangavelu_nycschools.data.model.School
import retrofit2.http.GET


interface ApiService {
    @GET("s3k6-pzi2.json")
    suspend fun getSchools(): List<School>

    @GET("f9bf-2cp4.json")
    suspend fun getSatScores(): List<SatScore>
}