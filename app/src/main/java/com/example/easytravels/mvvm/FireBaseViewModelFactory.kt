package com.example.easytravels.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FireBaseViewModelFactory(private val repository: FirebaseViewModelRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirebaseViewModel::class.java)){
            return FirebaseViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown view model class")
    }
}