package com.example.lyfstile

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import java.lang.ClassCastException
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager

import android.widget.TextView.OnEditorActionListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class TextSubmitFragment(_isPassword: Boolean) : Fragment(), View.OnClickListener{

    lateinit var dataPasser: PassData;
    var enterTxt : EditText ?= null;
    var fragmentTag : String ?= null
    var isPassword = _isPassword

    //Associate the callback with this Fragment
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataPasser = context as PassData
        } catch (e: ClassCastException) {
           // throw ClassCastException("$context must implement OnDataPass")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_text_submit, container, false)
        enterTxt = view.findViewById(R.id.enter_box) as EditText
        if(isPassword)
            enterTxt?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD



        enterTxt?.setOnEditorActionListener(OnEditorActionListener { view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEND || keyEvent.keyCode == KEYCODE_ENTER){
                val text = enterTxt?.text.toString()
                val data = Data(fragmentTag.toString(), mapOf(fragmentTag.toString() to text))
                print(data.data)
                dataPasser?.onDataPass(data)

                var keyboard = getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(enterTxt?.windowToken,0)

            }
            false
        })
        fragmentTag = tag
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TextSubmitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TextSubmitFragment(true).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




    override fun onClick(view: View?) {

            val frag = childFragmentManager.fragments

        when(view?.id) {
            R.id.next_button ->
            {
                val text = enterTxt?.text.toString()
                val data = Data(fragmentTag.toString(), mapOf(fragmentTag.toString() to text))

                print(data.data)
                dataPasser?.onDataPass(data)

            }
    }
}

     fun onDataPass(data: Data) {

    }

}