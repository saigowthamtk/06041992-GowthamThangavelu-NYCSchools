package com.login.a06041992_gowthamthangavelu_nycschools

import com.login.a06041992_gowthamthangavelu_nycschools.data.model.School
import com.login.a06041992_gowthamthangavelu_nycschools.data.network.ApiService
import com.login.a06041992_gowthamthangavelu_nycschools.data.repository.SchoolRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class SchoolRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var schoolRepository: SchoolRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        schoolRepository = SchoolRepository()
    }

    @Test
    fun `fetch schools successfully`() = runBlocking {
        val expectedSchools = listOf(School("01", "Test School", "Manhattan", "Test overview"))
        `when`(apiService.getSchools()).thenReturn(expectedSchools)

        val schools = schoolRepository.getSchools()

        assertEquals(expectedSchools, schools)
    }
}