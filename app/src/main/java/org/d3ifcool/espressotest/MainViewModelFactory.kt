package org.d3ifcool.espressotest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.espressotest.data.MahasiswaDao
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val dataSource: MahasiswaDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unable to Construct viewModel")
    }
}