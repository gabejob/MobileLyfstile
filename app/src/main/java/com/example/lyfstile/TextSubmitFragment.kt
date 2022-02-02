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
import java.lang.AssertionError
import java.lang.ClassCastException
import javax.crypto.BadPaddingException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TextSubmitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


/**
 *
 * Class representing data to be passed between fragments/activities
 *
 * MUST USE SENDER!
 *
 */
public class Data(_sender: String,_data: Map<String,String>)
{
    var sender: String = _sender

    var data: Map<String, String> = _data

}

class TextSubmitFragment : Fragment(), View.OnClickListener {

    var dataPasser : PassData ?= null;
    var editText : EditText ?= null;
    var submit : Button?= null;

    public interface PassData{
        fun onDataPass(data: Data)
    }


    //Associate the callback with this Fragment
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataPasser = context as PassData
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnDataPass")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_text_submit, container, false)
        submit = view.findViewById(R.id.button_submit) as Button
        editText = view.findViewById(R.id.frag_submit) as EditText
        submit!!.setOnClickListener(this)

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

        when(view?.id) {
            R.id.button_submit ->
            {
                val text = editText?.text.toString()
                val data = Data("frag_test", mapOf("frag" to text))

                print(data.data)
                dataPasser?.onDataPass(data)

            }
    }
}
}