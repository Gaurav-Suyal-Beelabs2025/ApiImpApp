package com.example.networkdemoapplication.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkdemoapplication.apiService.ApiService
import com.example.networkdemoapplication.model.TestData
import com.example.networkdemoapplication.remote.NetworkState
import com.example.networkdemoapplication.util.coroutineErrorHandler
import kotlinx.coroutines.launch
import javax.inject.Inject


class TestViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _test = MutableLiveData<NetworkState<TestData>>()
    val test: MutableLiveData<NetworkState<TestData>> get() = _test

    fun onTest() {
        _test.value = NetworkState.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.test()
                if (response.isSuccessful) {
                    val responseData = response.body()
                    _test.postValue(NetworkState.Success(responseData?.data))
                } else {
                    _test.postValue(NetworkState.Error("Network request failed"))
                }

                Log.d("responceData activity",response.toString() +"    "+_test.value.toString())

            } catch (ex: Exception) {
                _test.value = NetworkState.Error(ex.message ?: "An error occurred")
            }
        }
    }
}