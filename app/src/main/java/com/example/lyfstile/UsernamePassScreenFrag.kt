package com.example.lyfstile

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [UsernamePassScreenTest.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsernamePassScreenTest : Fragment(), View.OnClickListener, PassData {
    private var dataList = HashMap<String, Data>()
    private var nextButton: Button? = null
    lateinit var viewModel: LyfViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_username_pass_screen_test, container, false)

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

        nextButton = view.findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)
        nextButton?.isEnabled = false

        return view
    }

    override fun onClick(p0: View?) {
        if (dataList.size == 3) {
            if (dataList[PASSWORD]?.data == dataList[PASSWORD_CONFIRMED]?.data) {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_usernamePassScreenFrag_to_newUserInfoFrag)
                }
            } else {
                Toast.makeText(requireActivity(), "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireActivity(), "Please enter all forms!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDataPass(data: Data) {
        if (data.data.toString().isEmpty() || data.data == "Not Provided") {
            dataList.remove(data.sender)
            nextButton?.isEnabled = false
        } else {
            dataList[data.sender] = data
            if (dataList.size == 3) {
                nextButton?.isEnabled = true
            }
        }
    }
}