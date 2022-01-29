package com.example.lyfstile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LayoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
public class LayoutFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var forgotpass: Button? = null
    var dataPass : LayoutFragment.OnDataPass? = null

    override fun onClick(view: View?) {
        when(view?.id)
        {
            R.id.forgot_pass -> Toast.makeText(
                activity,
                "working",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    public interface OnDataPass{
        fun onDataPass(data : Array<String>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try
        {
            dataPass = context as OnDataPass
        } catch(e : ClassCastException)
        {
            throw ClassCastException("$context must implement OnDataPass")
        }
    }
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

        var view : View = inflater.inflate(R.layout.fragment_layout,container,false)

        forgotpass = view.findViewById(R.id.forgot_pass) as Button
        forgotpass!!.setOnClickListener(this);


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LayoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LayoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}