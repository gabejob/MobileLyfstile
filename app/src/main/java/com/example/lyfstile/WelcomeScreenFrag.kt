package com.example.lyfstile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeScreenFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeScreenFrag : Fragment(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
                Navigation.findNavController(view)
                    .navigate(R.id.action_testHomeScreenFrag_to_usernamePassScreenTest)
            }
            R.id.Log_in -> {

            }
        }
    }

}