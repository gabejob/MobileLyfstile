package com.example.lyfstile

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import java.lang.Thread.sleep

/**
 * A simple [Fragment] subclass.
 * Use the [LoginScreenFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginScreenFrag : Fragment(), View.OnClickListener, PassData {
    private lateinit var viewModel: LyfViewModel
    private var login: Button? = null
    private var forgot: Button? = null
    private var email = ""
    private var password = ""
    private var userEntity: List<UserEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_screen, container, false)
        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        val emailEnterFragment = TextSubmitFragment()
        val passwordEnterFragment = TextSubmitFragment()

        val fragtrans = childFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box, emailEnterFragment, EMAIL)
        fragtrans.replace(R.id.password_enter_box, passwordEnterFragment, PASSWORD)

        fragtrans.commit()

        viewModel.allUsers(requireActivity())!!.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                userEntity = it
            }
        }

        login = view.findViewById<Button>(R.id.Login_button)
        forgot = view.findViewById<Button>(R.id.forgot_pass)

        login?.setOnClickListener(this)
        forgot?.setOnClickListener(this)

        return view
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.Login_button -> {
                if (email.isNotBlank() && password.isNotBlank()) {
                    val cred = verifyCredentials(email, password)
                    sleep(1000)
                    if (cred) {
                        view.let {
                            Navigation.findNavController(it)
                                .navigate(R.id.action_loginScreenFrag_to_homeScreenFrag)
                        }
                    } else {
                        Toast.makeText(requireActivity(), "Incorrect Password!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(requireActivity(), "Please enter all forms!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            R.id.forgot_pass -> {
                Toast.makeText(requireActivity(), "Not Implemented", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyCredentials(email: String, password: String): Boolean {
        var isVerified = false
        val foundUser = userEntity.find {
            it.email == email && it.password == password
        }
            if (foundUser != null) {
                login?.isEnabled = true
                isVerified = true
                viewModel.getUser(requireActivity(), email)!!.observe(viewLifecycleOwner){
                    viewModel.user = User(it)
                }
            }
        return isVerified
    }

    override fun onDataPass(data: Data) {
        when (data.sender) {
            EMAIL -> email = data.data.toString()
            PASSWORD -> password = data.data.toString()
        }
    }
}