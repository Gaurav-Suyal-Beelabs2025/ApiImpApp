package com.example.networkdemoapplication.views.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.networkdemoapplication.base.BaseFragment
import com.example.networkdemoapplication.dagger.Injectable
import com.example.networkdemoapplication.dagger.ViewModelProviderFactory
import com.example.networkdemoapplication.databinding.FragmentMobileNumberBinding
import com.example.networkdemoapplication.remote.NetworkState
import com.example.networkdemoapplication.util.*
import com.example.networkdemoapplication.viewModel.LogInViewModel
import com.example.networkdemoapplication.views.auth.data.AuthDeviceModel
import com.example.networkdemoapplication.views.auth.data.DeviceAuthData
import com.example.networkdemoapplication.views.auth.data.MobileNumberModel
import com.example.networkdemoapplication.views.auth.data.SendOtpData
import javax.inject.Inject

class MobileNumberFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private val viewModel: LogInViewModel by lazy {
        ViewModelProvider(this, providerFactory)[LogInViewModel::class.java]
    }
    private lateinit var _binding: FragmentMobileNumberBinding
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMobileNumberBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().let {
            SharedPref.init(it)
        }

        val context: Context = requireContext()
        val deviceId: String = Extension.getDeviceId(context)

        Log.d("deviceId", deviceId.toString())

        binding.verifyButton.setOnClickListener {
            val mobileNumber = binding.enterANumber.text.toString()
            if (mobileNumber.length < 10) {
                showToast("Please enter mobile number")
            } else {
                SharedPref.write(Constant.MOBILE_NUMBER, "+91-$mobileNumber")
                viewModel.onSendOtp(MobileNumberModel("+91-$mobileNumber"))
            }

        }

        val dvId = AuthDeviceModel(deviceId.toUpperCase())
        viewModel.onAuthDevice(dvId)


        viewModel.authDevice.observe(viewLifecycleOwner, ::onAuthDevice)
        viewModel.sendoOtp.observe(viewLifecycleOwner, ::onSendingOtp)

    }


    private fun onAuthDevice(state: NetworkState<DeviceAuthData>?) {
        if (state is NetworkState.Loading) {
            return
        }
        when (state) {
            is NetworkState.Success -> {
                token = "${state.data?.token}"
                SharedPref.write(Constant.TOKEN, "${state.data?.token}")

            }

            is NetworkState.Error -> {
                if (state.message == null) {
                    activity?.onFailure(binding.root, CONNECTION_ERROR)
                } else {
                    Toast.makeText(requireActivity(), state.message, Toast.LENGTH_LONG).show()
                }
            }
            is NetworkState.Failure -> {
                activity?.onFailure(binding.root, CONNECTION_ERROR)
            }
            else -> {
                Toast.makeText(requireActivity(), "Error occurred ", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun onSendingOtp(state: NetworkState<SendOtpData>?) {

        if (state is NetworkState.Loading) {
            return
        }
        when (state) {
            is NetworkState.Success -> {

                showToast("OTP Sent Successfully!!")
                switchFragment(OtpFragment())
            }

            is NetworkState.Error -> {
                if (state.message == null) {
                    activity?.onFailure(binding.root, CONNECTION_ERROR)
                } else {
                    Toast.makeText(requireActivity(), state.message, Toast.LENGTH_LONG).show()
                }
            }
            is NetworkState.Failure -> {
                activity?.onFailure(binding.root, CONNECTION_ERROR)
            }
            else -> {
                Toast.makeText(requireActivity(), "Error occurred ", Toast.LENGTH_LONG).show()
            }
        }

    }

}