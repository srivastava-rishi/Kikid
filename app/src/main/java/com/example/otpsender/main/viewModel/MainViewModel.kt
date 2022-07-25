package com.example.otpsender.main.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otpsender.data.database.MessageDatabase
import com.example.otpsender.data.entity.MessageEntity
import com.example.otpsender.retrofit.UserNetworkEntity
import com.example.otpsender.retrofit.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val repository: UserRepository
             ): ViewModel() {

    private val logTag = "@MainViewModel"

    private val _response = MutableLiveData<List<UserNetworkEntity>>()

    val responseUserDetail: LiveData<List<UserNetworkEntity>>
        get() = _response

    init {
        getAllUsers()
    }

    private fun getAllUsers() = viewModelScope.launch {

        repository.getUser().let { response ->

            if (response.isSuccessful){
                _response.postValue(response.body())
            }else{
                Log.d(logTag, "error: ${response.errorBody()} ")
            }

        }
    }

}