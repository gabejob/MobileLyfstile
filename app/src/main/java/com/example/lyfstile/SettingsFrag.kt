package com.example.lyfstile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFrag : Fragment(), View.OnClickListener {
    lateinit var viewModel: LyfViewModel
    private var cancelButton: Button? = null
    private var saveButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        cancelButton = view.findViewById(R.id.settings_cancel)
        cancelButton?.setOnClickListener(this)

        saveButton = view.findViewById(R.id.settings_save)
        saveButton?.setOnClickListener(this)

        setInfoValues(view)
        return view
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.settings_save -> {
                try {
                    viewModel.update(requireContext(), viewModel.user)
                    Toast.makeText(requireActivity(), "Information was saved", Toast.LENGTH_SHORT)
                        .show()
                    view.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_settingsFrag_to_homeScreenFrag)
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        "Information was NOT saved",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            R.id.settings_cancel -> {
                try {
                    Toast.makeText(
                        requireActivity(),
                        "Information was NOT saved",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    view.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_settingsFrag_to_homeScreenFrag)
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        "Information was NOT saved",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun setInfoValues(view: View) {
        val user = viewModel.user

        view.findViewById<ImageView>(R.id.settings_imageView)
            ?.setImageBitmap(BitmapFactory.decodeByteArray(user.pfp, 0, user.pfp.size))
        view.findViewById<EditText>(R.id.settings_first_name_box)?.setText(user.firstName)
        view.findViewById<EditText>(R.id.settings_last_name_box)?.setText(user.lastName)
        view.findViewById<EditText>(R.id.settings_age_box)?.setText(user.age)
        view.findViewById<EditText>(R.id.settings_sex_box)?.setText(user.sex)
        view.findViewById<EditText>(R.id.settings_height_box)?.setText(user.height)
        view.findViewById<EditText>(R.id.settings_weight_box)?.setText(user.weight)
    }
}