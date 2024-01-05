package com.example.networkdemoapplication

 import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.networkdemoapplication.base.BasicActivity
import com.example.networkdemoapplication.dagger.Injectable
import com.example.networkdemoapplication.dagger.ViewModelProviderFactory
import com.example.networkdemoapplication.databinding.ActivityMainBinding
 import com.example.networkdemoapplication.model.TestData
 import com.example.networkdemoapplication.remote.NetworkState
import com.example.networkdemoapplication.util.CONNECTION_ERROR
 import com.example.networkdemoapplication.viewModel.TestViewModel
 import javax.inject.Inject

class MainActivity : BasicActivity() ,Injectable{
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private val viewModel: TestViewModel by lazy {
        ViewModelProvider(this, providerFactory)[TestViewModel::class.java]
    }
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.onTest()
        viewModel.test.observe(this,::onGetTest)



    }

    private fun onGetTest(state: NetworkState<TestData>?) {

        if (state is NetworkState.Loading) {
            return
        }
        when (state) {
            is NetworkState.Success -> {
            Log.d("responceData activity",state.toString())
                binding.txtHello.text=state.type
                binding.txtHello2.text=state.activity
                binding.txtHello3.text="Rajeev Ranjan"
                binding.txtHello4.text="Brajesh Kumar"
            }

            is NetworkState.Error -> {
                if (state.message == null) {
                    onFailure(binding.root, CONNECTION_ERROR)
                } else {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
            is NetworkState.Failure -> {
                onFailure(binding.root, CONNECTION_ERROR)
            }
            else -> {
                Toast.makeText(this, "Error occurred ", Toast.LENGTH_LONG).show()
            }
        }

    }
}