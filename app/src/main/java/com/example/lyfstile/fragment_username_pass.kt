package com.example.lyfstile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class fragment_username_pass : Fragment() {

    companion object {
        fun newInstance() = fragment_username_pass()
    }

    private lateinit var viewModel: FragmentUsernamePassViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(FragmentUsernamePassViewModel::class.java)

        return inflater.inflate(R.layout.fragment_username_pass_fragment, container, false)
    }

/*    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }*/

}