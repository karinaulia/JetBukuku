package com.bangkit.jetbukuku.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.jetbukuku.data.BookRepository
import com.bangkit.jetbukuku.ui.screen.detail.DetailViewModel
import com.bangkit.jetbukuku.ui.screen.home.HomeViewModel
import com.bangkit.jetbukuku.ui.screen.library.LibraryViewModel
import com.bangkit.jetbukuku.ui.screen.profile.ProfileViewModel

class ViewModelFactory(private val repository: BookRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            return LibraryViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}