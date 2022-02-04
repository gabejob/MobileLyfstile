package com.example.lyfstile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import java.lang.AssertionError
import java.lang.ClassCastException
import javax.crypto.BadPaddingException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class TextSubmitFragment : Fragment(), View.OnClickListener{

    lateinit var dataPasser: PassData;
    var enter_email : EditText ?= null;
    var next : Button?= null;
    var fragment_tag : String ?= null


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
        next = view.findViewById(R.id.next_button) as Button
        enter_email = view.findViewById(R.id.enter_box) as EditText
        next!!.setOnClickListener(this)

        fragment_tag = tag
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
            TextSubmitFragment().apply {
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
                val text = enter_email?.text.toString()
                val data = Data(fragment_tag.toString(), mapOf(fragment_tag.toString() to text))

                print(data.data)
                dataPasser?.onDataPass(data)

            }
    }
}

     fun onDataPass(data: Data) {

    }
}