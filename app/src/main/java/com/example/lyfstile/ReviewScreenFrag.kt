package com.example.lyfstile

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation


/**
 * A simple [Fragment] subclass.
 * Use the [ReviewScreenFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewScreenFrag : Fragment(), View.OnClickListener {
    private var createAccountButton: Button? = null
    private var editButton: Button? = null
    private lateinit var viewModel: LyfViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_review, container, false)
        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        createAccountButton = view.findViewById(R.id.create_button)
        createAccountButton?.setOnClickListener(this)

        editButton = view.findViewById(R.id.edit_button)
        editButton?.setOnClickListener(this)
        setInfoValues(view)
        return view
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.create_button -> {
                try {
                    viewModel.insert(requireContext(), viewModel.user)
                    view.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_review_to_homeScreenFrag)
                    }
                } catch (e: Exception) {
                    throw Exception("Unable to start the camera")
                }
            }
            R.id.edit_button -> {
                view?.let {
                }

            }
        }
    }
    private fun setInfoValues(view: View) {
        val user = viewModel!!.user
//            view.findViewById<ImageView>(R.id.imageView)?.setImageBitmap(dataList?.get(PROFILE_PIC)?.data as Bitmap)
//        view?.findViewById<EditText>(R.id.first_name_box)?.text = user.firstName as Editable

        view.findViewById<EditText>(R.id.first_name_box)?.setText(user.firstName)
        view.findViewById<EditText>(R.id.last_name_box)?.setText(user.lastName)
        view.findViewById<EditText>(R.id.age_box)?.setText(user.age)
        view.findViewById<EditText>(R.id.sex_box)?.setText(user.sex)
        view.findViewById<EditText>(R.id.height_box)?.setText(user.height)
        view.findViewById<EditText>(R.id.weight_box)?.setText(user.weight)
//        view?.findViewById<EditText>(R.id.last_name_box)?.text = user.lastName as Editable
//        view?.findViewById<EditText>(R.id.age_box)?.text = user.age as Editable
//        view?.findViewById<EditText>(R.id.sex_box)?.text = user.sex as Editable
//        view?.findViewById<EditText>(R.id.height_box)?.text = user.height as Editable
//        view?.findViewById<EditText>(R.id.weight_box)?.text = user.weight as Editable
    }
}