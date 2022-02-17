package com.example.lyfstile

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PickerFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val numberPicker = NumberPicker(it)
            numberPicker.maxValue=12
            numberPicker.minValue=1


            val builder = AlertDialog.Builder(it)

            builder.setView(numberPicker)
            builder.setTitle("Set Your Weight")
            builder.setMessage("Choose Weight:")
            builder.setPositiveButton(
                "OK"
            ) { dialog, which ->
                //code go here
            }
            builder.setNegativeButton(
                "CANCEL"
            ) { dialog, which ->

            }


            // Create the AlertDialog object and return it
            builder.create()
            return builder.show()
        } ?: throw IllegalStateException("Activity cannot be null")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picker, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PickerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}