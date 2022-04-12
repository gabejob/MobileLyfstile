package com.example.lyfstile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [UsernamePassScreenTest.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFrag : Fragment(), View.OnClickListener, PassData {
    //    private var dataList = HashMap<String, Data>()
    private var nextButton: Button? = null
    lateinit var viewModel: LyfViewModel
    private var passwordMatch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.view?.clearFocus()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        val emailEnterFragment = TextSubmitFragment()
        val passwordEnterFragment = TextSubmitFragment()
        val confirmPasswordEnterFragment = TextSubmitFragment()

        val fragtrans = childFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box, emailEnterFragment, EMAIL)
        fragtrans.replace(R.id.password_enter_box, passwordEnterFragment, PASSWORD)
        fragtrans.replace(
            R.id.confirm_password_enter_box,
            confirmPasswordEnterFragment,
            PASSWORD_CONFIRMED
        )

        fragtrans.commit()

        emailEnterFragment.value = viewModel.user.email
        passwordEnterFragment.value = viewModel.user.password
        confirmPasswordEnterFragment.value = viewModel.user.password

        nextButton = view.findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)

        return view
    }

    /*
        Should be called like:
        <string>.isValidEmail() -> Returns -> T/F
        matching <***>@<***>.<***> pattern
     */
    private fun CharSequence?.isValidEmail(): Boolean {
        if (!isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches())
            return true
        return false

    }

    override fun onResume() {
        super.onResume()
        view?.clearFocus()
    }

    override fun onClick(p0: View?) {
        val user = viewModel.user
        if (user.email.isNotBlank() && user.password.isNotBlank() ) {
            if (Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_registration_to_new_user_info)
                }
            } else {
                Toast.makeText(requireActivity(), "Invalid Email!", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(requireActivity(), "Please enter all forms!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDataPass(data: Data) {
        when (data.sender) {
            EMAIL -> viewModel.user.email = data.data.toString()
            PASSWORD -> viewModel.user.password = data.data.toString()
            PASSWORD_CONFIRMED -> {
                if(confirmPassword(data.data.toString())){
                    passwordMatch = true
                    nextButton?.isEnabled = true
                }else{
                    Toast.makeText(view?.context, "Passwords entered do not match!", Toast.LENGTH_SHORT)
                        .show()
                    nextButton?.isEnabled = false
                }
            }
        }
    }

    private fun confirmPassword(password: String): Boolean {
        return viewModel.user.password == password
    }
}