package com.example.lyfstile

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeScreenFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeScreenFrag : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome_screen, container, false)

        val createAccountButton = view.findViewById<Button>(R.id.new_user)
        createAccountButton.setOnClickListener(this)
        val existingAccountButton = view.findViewById<Button>(R.id.Log_in)
        existingAccountButton.setOnClickListener(this)
        return view;
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.new_user -> {
                view.let {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_welcome_screen_to_registration)
                }
            }
            R.id.Log_in -> {
                view.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_welcome_to_loginScreenFrag)
                }
            }
        }
    }
}