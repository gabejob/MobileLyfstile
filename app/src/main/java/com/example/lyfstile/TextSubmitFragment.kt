package com.example.lyfstile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Patterns
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
import android.widget.Toast


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class TextSubmitFragment : Fragment(), View.OnClickListener{
    lateinit var dataPasser: PassData;
    var enterTxt : EditText ?= null;
    var isValid = false

    //Really should only need to touch afterTextChanged -> Checks for valid email
    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(sequence: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            var email = enterTxt?.text
            if (email.isValidEmail() && email?.length!! > 0)
            {
                //Need to look for some sort of success library here...
                isValid = true
            }
            else
            {
                enterTxt?.error = "Invalid Email!";
                isValid = false
            }
        }

    }




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


        //Check for special input types
        if(tag?.lowercase()?.contains(PASSWORD.lowercase()) == true)
            enterTxt?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        if(tag?.lowercase()?.contains(EMAIL.lowercase()) == true)
            enterTxt?.addTextChangedListener(watcher)


        //May look to move this into its own function for readability,
        //Makes keyboard disappear when enter/submit is fixed, still a little buggy
        enterTxt?.setOnEditorActionListener { view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEND || keyEvent.keyCode == KEYCODE_ENTER) {
                val text = enterTxt?.text.toString()
                val data = Data(tag.toString(), text)
                print(data.data)
                dataPasser?.onDataPass(data)

                var keyboard =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(enterTxt?.windowToken, 0)
            }
            false
        }

        return view
    }

    /*
        Should be called like:
        <string>.isValidEmail() -> Returns -> T/F
        matching <***>@<***>.<***> pattern
     */
    private fun CharSequence?.isValidEmail() : Boolean
    {
        if(!isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches())
            return true
        return false

    }


    /*
        Not really in use currently, but if a button is desired, it can
        be added...
     */
    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.next_button ->
            {
                val text = enterTxt?.text.toString()
                val data = Data(tag.toString(), text)

                print(data.data)
                dataPasser?.onDataPass(data)

            }
    }
}



     fun onDataPass(data: Data) {

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
            TextSubmitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}