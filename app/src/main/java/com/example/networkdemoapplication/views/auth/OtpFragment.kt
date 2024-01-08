package com.example.networkdemoapplication.views.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.networkdemoapplication.base.BaseFragment
import com.example.networkdemoapplication.dagger.Injectable
import com.example.networkdemoapplication.dagger.ViewModelProviderFactory
import com.example.networkdemoapplication.databinding.FragmentOtpFragmentBinding
import com.example.networkdemoapplication.remote.NetworkState
import com.example.networkdemoapplication.util.CONNECTION_ERROR
import com.example.networkdemoapplication.util.Constant
import com.example.networkdemoapplication.util.SharedPref
import com.example.networkdemoapplication.viewModel.LogInViewModel
import com.example.networkdemoapplication.views.auth.data.LoginData
import com.example.networkdemoapplication.views.auth.data.VerifyOtpModel
import javax.inject.Inject

class OtpFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private val viewModel : LogInViewModel by lazy {
        ViewModelProvider(this, providerFactory)[LogInViewModel::class.java]
    }
    private lateinit var _binding: FragmentOtpFragmentBinding
    val binding get() =  _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOtpFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.verifyOtpBtn.setOnClickListener {
            val mobileNumber = SharedPref.read(Constant.MOBILE_NUMBER, null)
            val otp = binding.otpBox.text.toString()

            if (binding.otpBox.length() >= 6){
                viewModel.onVerifyOtp(VerifyOtpModel(mobileNumber?:"", otp))
            }else{
                showToast("Enter Valid OTP !!")
            }
        }




      viewModel.verifyOtp.observe(viewLifecycleOwner, :: onVerfyingOtp)

    }


    private fun onVerfyingOtp(state: NetworkState<LoginData>?) {

        if (state is NetworkState.Loading) {
            return
        }
        when (state) {
            is NetworkState.Success -> {

                showToast("OTP Verified !!")
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