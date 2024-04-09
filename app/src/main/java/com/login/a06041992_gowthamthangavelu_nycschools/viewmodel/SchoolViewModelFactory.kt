package com.login.a06041992_gowthamthangavelu_nycschools.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.login.a06041992_gowthamthangavelu_nycschools.data.repository.SchoolRepository


class SchoolViewModelFactory(private val repository: SchoolRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(SchoolViewModel::class.java)) {
            return SchoolViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}. This factory only supports SchoolViewModel.")
    }
}