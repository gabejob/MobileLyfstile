package com.example.lyfstile

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [NewUserInfoFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewUserInfoFrag : Fragment(), PassData, View.OnClickListener {
    private var dataList = HashMap<String, Data>()
    private var currentScreen = "first_last_name"
    private var tag1 = ""
    private var tag2 = ""
    private var nextButton: Button? = null
    private lateinit var viewModel: LyfViewModel
    private val screenPrompts = mapOf(
        FIRST_LAST_NAME_SCREEN to arrayOf("What's your name?", FIRST_NAME, LAST_NAME),
        AGE_SEX_SCREEN to arrayOf("Tell us about you", AGE, SEX),
        HEIGHT_WEIGHT_SCREEN to arrayOf("Tell us about you", HEIGHT, WEIGHT),
        COUNTRY_CITY_SCREEN to arrayOf("Where are you from?", COUNTRY, CITY)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_user_info, container, false)
        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        fragTrans(FIRST_NAME, LAST_NAME)
        nextButton = view?.findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)

        nextButton?.isEnabled = false
        return view
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            //Each time the next button is pressed, change tags and replace text fragments...
            R.id.next_button -> {
                activity?.currentFocus?.clearFocus()
                when (currentScreen) {
                    FIRST_LAST_NAME_SCREEN -> {
                        currentScreen = AGE_SEX_SCREEN
                        changeScreen(currentScreen)
                        tag1 = AGE //Needs to enforce numbers/date selector
                        tag2 = SEX //Needs to enforce dropdown
                    }
                    AGE_SEX_SCREEN -> {
                        currentScreen = HEIGHT_WEIGHT_SCREEN
                        changeScreen(currentScreen)
                        tag1 = HEIGHT //Needs to enforce dropdowns
                        tag2 = WEIGHT //Needs to enforce dropdowns
                    }
                    HEIGHT_WEIGHT_SCREEN -> {
                        currentScreen = COUNTRY_CITY_SCREEN
                        changeScreen(currentScreen)
                        tag1 = COUNTRY //Needs to enforce country auto-select
                        tag2 = CITY //Needs to enforce city auto-select
                    }
                    COUNTRY_CITY_SCREEN -> {
                        view?.let {
                            Navigation.findNavController(it)
                                .navigate(R.id.action_new_user_info_to_camera)
                        }
                    }
                }
                fragTrans(tag1, tag2)
            }
        }
    }

    /**
     *
     * Fragment initialization...
     *
     */
    private fun fragTrans(tag1: String, tag2: String) {
        val fragtrans = childFragmentManager.beginTransaction()

        val enterFragment = TextSubmitFragment();
        val enterFragment2 = TextSubmitFragment()

        if (dataList[tag1]?.data.toString().isNotEmpty()) {
            enterFragment.value = dataList[tag1]?.data.toString()
        }
        if (dataList[tag2]?.data.toString().isNotEmpty()) {
            enterFragment2.value = dataList[tag2]?.data.toString()
        }

        fragtrans.replace(R.id.fn_enter_box, enterFragment, tag1)
        fragtrans.replace(R.id.ln_enter_box, enterFragment2, tag2)

        enterFragment.requestFocus()
        fragtrans.commit()
    }

    override fun onDataPass(data: Data) {
        when (data.sender) {
            FIRST_NAME -> {
                viewModel.user.firstName = data.data.toString()
                if (viewModel.user.firstName.isNotBlank() && viewModel.user.lastName.isNotBlank())
                    nextButton?.isEnabled = true
            }
            LAST_NAME -> {
                viewModel.user.lastName = data.data.toString()
                if (viewModel.user.firstName.isNotBlank() && viewModel.user.lastName.isNotBlank())
                    nextButton?.isEnabled = true
            }
            AGE -> viewModel.user.age = data.data.toString()
            SEX -> viewModel.user.sex = data.data.toString()
            HEIGHT -> viewModel.user.height = data.data.toString()
            WEIGHT -> viewModel.user.weight = data.data.toString()
            COUNTRY -> viewModel.user.country = data.data.toString()
            CITY -> viewModel.user.city = data.data.toString()
        }
    }


    /**
     *
     *
     * Function to handle all swaps between prompts/fragments
     *
     *
     */
    private fun changeScreen(screenName: String) {
        try {
            view?.findViewById<TextView>(R.id.wyn_textView)?.text =
                screenPrompts[screenName]?.get(0)
            view?.findViewById<TextView>(R.id.fn_textView)?.text = screenPrompts[screenName]?.get(1)
            view?.findViewById<TextView>(R.id.ln_textView)?.text = screenPrompts[screenName]?.get(2)
        } catch (e: Exception) {
            throw Exception("Unable to load next screen")
        }
    }
}