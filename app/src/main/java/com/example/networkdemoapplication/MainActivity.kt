package com.example.networkdemoapplication

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.networkdemoapplication.base.BasicActivity
import com.example.networkdemoapplication.dagger.Injectable
import com.example.networkdemoapplication.dagger.ViewModelProviderFactory
import com.example.networkdemoapplication.databinding.ActivityMainBinding
import com.example.networkdemoapplication.util.SharedPref
import com.example.networkdemoapplication.viewModel.TestViewModel
import com.example.networkdemoapplication.views.auth.MobileNumberFragment
import javax.inject.Inject

class MainActivity : BasicActivity(), Injectable {
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private val viewModel: TestViewModel by lazy {
        ViewModelProvider(this, providerFactory)[TestViewModel::class.java]
    }
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        SharedPref.init(applicationContext)
        val settings: SharedPreferences = applicationContext.getSharedPreferences(
            applicationContext.packageName,
            MODE_PRIVATE
        )
        settings.edit().commit()

            switchFragment(MobileNumberFragment())

    }

    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}