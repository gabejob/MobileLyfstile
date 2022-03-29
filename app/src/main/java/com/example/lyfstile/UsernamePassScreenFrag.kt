package com.example.lyfstile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider

/**
 * A simple [Fragment] subclass.
 * Use the [UsernamePassScreenTest.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsernamePassScreenTest : Fragment(), View.OnClickListener, PassData {
    private var dataList = HashMap<String, Data>()
    private var nextButton: Button? = null
    lateinit var viewModel: LyfViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

    }

    override fun onDataPass(data: Data) {
        var x = viewModel.test
        print(viewModel.test)
    }
}