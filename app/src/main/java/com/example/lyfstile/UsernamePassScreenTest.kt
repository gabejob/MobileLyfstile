package com.example.lyfstile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UsernamePassScreenTest.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsernamePassScreenTest : Fragment(), View.OnClickListener, PassData {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var dataList = HashMap<String, Data>()
    private var nextButton: Button? = null
    lateinit var viewModel : LyfViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_username_pass_screen_test, container, false)

        viewModel = ViewModelProvider( requireActivity())[LyfViewModel::class.java]


        val emailEnterFragment = TextSubmitFragment()
        val passwordEnterFragment = TextSubmitFragment()
        val confirmPasswordEnterFragment = TextSubmitFragment()

        val fragtrans = childFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box, emailEnterFragment, EMAIL)
        fragtrans.replace(R.id.password_enter_box, passwordEnterFragment, PASSWORD)
        fragtrans.replace(R.id.confirm_password_enter_box, confirmPasswordEnterFragment,PASSWORD_CONFIRMED)

        fragtrans.commit()

        nextButton = view.findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)
        nextButton?.isEnabled = false

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UsernamePassScreenTest.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UsernamePassScreenTest().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(p0: View?) {

    }

    override fun onDataPass(data: Data) {
        var x = viewModel.test
        print(viewModel.test)
    }
}