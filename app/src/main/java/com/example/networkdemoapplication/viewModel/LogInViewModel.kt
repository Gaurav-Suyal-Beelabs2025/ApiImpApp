package com.example.networkdemoapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkdemoapplication.apiService.ApiService
import com.example.networkdemoapplication.remote.NetworkState
import com.example.networkdemoapplication.util.coroutineErrorHandler
import com.example.networkdemoapplication.views.auth.data.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class LogInViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private  val _authDevice = MutableLiveData<NetworkState<DeviceAuthData>?>()
    val authDevice: MutableLiveData<NetworkState<DeviceAuthData>?> get() = _authDevice

    private  val _sendOtp = MutableLiveData<NetworkState<SendOtpData>?>()
    val sendoOtp: MutableLiveData<NetworkState<SendOtpData>?> get() = _sendOtp

    private  val _verifyOtp = MutableLiveData<NetworkState<LoginData>?>()
    val verifyOtp: MutableLiveData<NetworkState<LoginData>?> get() = _verifyOtp


    fun onAuthDevice(authDeviceModel: AuthDeviceModel){
        _authDevice.value = NetworkState.Loading()

        viewModelScope.launch {
            try {
                val response = apiService.authDevice(authDeviceModel)
                _authDevice.value = NetworkState.Success(response.data, response.status, response.message)

            }catch (ex:Exception){
                _authDevice.value = coroutineErrorHandler(ex)
            }
        }
    }


    fun onSendOtp(mobileNumberModel: MobileNumberModel){
        _sendOtp.value = NetworkState.Loading()

        viewModelScope.launch {
            try {
                val response = apiService.sendOtp(mobileNumberModel)
                _sendOtp.value = NetworkState.Success(response.data, response.status, response.message)

            }catch (ex:Exception){
                _sendOtp.value = coroutineErrorHandler(ex)
            }
        }
    }


    fun onVerifyOtp(VerifyOtpModel: VerifyOtpModel){
        _verifyOtp.value = NetworkState.Loading()

        viewModelScope.launch {
            try {
                val response = apiService.verifyOtp(VerifyOtpModel)
                _verifyOtp.value = NetworkState.Success(response.data, response.status, response.message)

            }catch (ex:Exception){
                _verifyOtp.value = coroutineErrorHandler(ex)
            }
        }
    }

}