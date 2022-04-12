package com.example.lyfstile

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

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
    private var user: User? = null
    private var nextButton: Button? = null
    private var backButtonEnabled = false
    private lateinit var viewModel: LyfViewModel
    private val screenPrompts = mapOf(
        FIRST_LAST_NAME_SCREEN to arrayOf("What's your name?", "First Name", "Last Name"),
        AGE_SEX_SCREEN to arrayOf("Tell us about you", AGE, SEX),
        HEIGHT_WEIGHT_SCREEN to arrayOf("Tell us about you", HEIGHT, WEIGHT),
        COUNTRY_CITY_SCREEN to arrayOf("Where are you from?", COUNTRY, CITY)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableBackButton()
    }

    override fun onResume() {
        super.onResume()

        if(viewModel.user.firstName.isNotBlank() && viewModel.user.lastName.isNotBlank()){
            nextButton?.isEnabled = true
        }

        view?.clearFocus()
    }

    private fun enableBackButton() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                backButtonEnabled = true
                val fragMan = parentFragmentManager
                val childCount = childFragmentManager.backStackEntryCount

                if (childCount == 0) {

                    findNavController().popBackStack()
                    backButtonEnabled = false

                } else if (childCount >= 1) {

                    fragMan.popBackStackImmediate()
                    childFragmentManager.popBackStackImmediate()
                    updateScreenInfo(false)

                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_user_info, container, false)
        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        nextButton = view?.findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)

        if (currentScreen == COUNTRY_CITY_SCREEN) {
            fragTrans(COUNTRY, CITY)
            val test = screenPrompts[COUNTRY_CITY_SCREEN]?.get(0)
            view?.findViewById<TextView>(R.id.wyn_textView)?.text =
                test
            view?.findViewById<TextView>(R.id.fn_textView)?.text =
                screenPrompts[COUNTRY_CITY_SCREEN]?.get(1)
            view?.findViewById<TextView>(R.id.ln_textView)?.text =
                screenPrompts[COUNTRY_CITY_SCREEN]?.get(2)
            nextButton?.isEnabled = true
        } else if (currentScreen == FIRST_LAST_NAME_SCREEN) {
            fragTrans(FIRST_NAME, LAST_NAME)
            nextButton?.isEnabled = false
        }

        view.isFocusableInTouchMode = true
        view.isFocusable = true
        view.requestFocus()
        view.clearFocus()

        return view
    }

    override fun onClick(view: View?) {
        if (dataList[FIRST_NAME]?.equals("") == true)
            nextButton?.isEnabled = false

        when (view?.id) {
            //Each time the next button is pressed, change tags and replace text fragments...
            R.id.next_button -> {

                view.isFocusable = true
                view.requestFocus()
                view.clearFocus()
                activity?.currentFocus?.clearFocus()

                if (!backButtonEnabled) {
                    enableBackButton()
                }
                if (!updateScreenInfo(true)) {
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_new_user_info_to_camera)
                    }
                }
            }
        }
    }

    /*
    * if direction == true then we are moving forwards in the screen menu
    * otherwise move backwards
     */
    private fun updateScreenInfo(direction: Boolean): Boolean {
        when (currentScreen) {
            FIRST_LAST_NAME_SCREEN -> {
                if (direction) {
                    currentScreen = AGE_SEX_SCREEN
                    changeScreen(currentScreen)
                    tag1 = AGE
                    tag2 = SEX
                }
            }
            AGE_SEX_SCREEN -> {
                if (direction) {
                    currentScreen = HEIGHT_WEIGHT_SCREEN
                    tag1 = HEIGHT
                    tag2 = WEIGHT
                } else {
                    currentScreen = FIRST_LAST_NAME_SCREEN
                    tag1 = FIRST_NAME
                    tag2 = LAST_NAME
                }
                changeScreen(currentScreen)
                nextButton?.isEnabled = true
            }
            HEIGHT_WEIGHT_SCREEN -> {
                if (direction) {
                    currentScreen = COUNTRY_CITY_SCREEN
                    tag1 = COUNTRY
                    tag2 = CITY
                } else {
                    currentScreen = AGE_SEX_SCREEN
                    tag1 = AGE
                    tag2 = SEX
                }
                changeScreen(currentScreen)
                nextButton?.isEnabled = true
            }
            COUNTRY_CITY_SCREEN -> {
                if (direction) {
                    changeScreen(currentScreen)
                    return false
                } else {
                    currentScreen = HEIGHT_WEIGHT_SCREEN
                    changeScreen(currentScreen)
                    tag1 = HEIGHT
                    tag2 = WEIGHT
                }
            }
        }
        fragTrans(tag1, tag2)
        return true
    }

    /**
     *
     * Fragment initialization...
     *
     */
    private fun fragTrans(tag1: String, tag2: String) {
        val fragtrans = childFragmentManager.beginTransaction()
        val count = childFragmentManager.backStackEntryCount
        if (tag1 != "first_name") {
            fragtrans.addToBackStack(tag1)
        }

        val enterFragment = TextSubmitFragment();
        val enterFragment2 = TextSubmitFragment()

        if (dataList[tag1]?.data.toString() != "null") {
            enterFragment.value = dataList[tag1]?.data.toString()
        } else {
            enterFragment.value = ""
        }
        if (dataList[tag2]?.data.toString() != "null") {
            enterFragment2.value = dataList[tag2]?.data.toString()
        } else {
            enterFragment2.value = ""
        }

        fragtrans.replace(R.id.fn_enter_box, enterFragment, tag1)
        fragtrans.replace(R.id.ln_enter_box, enterFragment2, tag2)

        view?.clearFocus()
        fragtrans.commit()

        when(tag1) {
            FIRST_NAME -> {
                enterFragment.value = viewModel.user.firstName
                enterFragment2.value = viewModel.user.lastName
            }
            AGE -> {
                enterFragment.value = viewModel.user.age
                enterFragment2.value = viewModel.user.sex
            }
            HEIGHT -> {
                enterFragment.value = viewModel.user.height
                enterFragment2.value = viewModel.user.weight
            }
            COUNTRY -> {
                enterFragment.value = viewModel.user.country
                enterFragment2.value = viewModel.user.city
            }
        }
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