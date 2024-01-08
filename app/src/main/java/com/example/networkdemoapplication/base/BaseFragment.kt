package com.example.networkdemoapplication.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.networkdemoapplication.R
import com.example.networkdemoapplication.dagger.Injectable
import dagger.android.support.AndroidSupportInjection

open class BaseFragment : Fragment() {
    var activity: BasicActivity? = null

    override fun onAttach(context: Context) {
        if (this is Injectable) AndroidSupportInjection.inject(this)
        super.onAttach(context)
        activity = context as BasicActivity
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    open fun onBackPressed(): Boolean {
        return false
    }

     fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

     fun switchFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}