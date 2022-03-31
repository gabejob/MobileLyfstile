package com.example.lyfstile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import java.lang.Exception
import kotlin.reflect.KProperty

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    private var viewModel : LyfViewModel by viewModels()
    private val screenPrompts = mapOf(
        FIRST_LAST_NAME_SCREEN to arrayOf("What's your name?", FIRST_NAME, LAST_NAME),
        AGE_SEX_SCREEN to arrayOf("Tell us about you", AGE, SEX),
        HEIGHT_WEIGHT_SCREEN to arrayOf("Tell us about you", HEIGHT, WEIGHT),
        COUNTRY_CITY_SCREEN to arrayOf("Where are you from?", COUNTRY, CITY)
    )
    var user:User? = User()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_user_info, container, false)
        viewModel = ViewModelProvider(this)[LyfViewModel::class.java]

        fragTrans(FIRST_NAME, LAST_NAME)
        nextButton = view?.findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)

        nextButton?.isEnabled = false
        return view
    }
//@todo
//    override fun onBackPressed() {
//        when (currentScreen) {
//            "first_last_name" -> {
//                super.onBackPressed()
//                finish()
//            }
//            AGE_SEX_SCREEN -> {
//                currentScreen = FIRST_LAST_NAME_SCREEN
//                changeScreen(currentScreen)
//                tag1 = FIRST_NAME
//                tag2 = LAST_NAME
//            }
//            HEIGHT_WEIGHT_SCREEN -> {
//                currentScreen = AGE_SEX_SCREEN
//                changeScreen(currentScreen)
//                tag1 = AGE
//                tag2 = SEX
//            }
//            COUNTRY_CITY_SCREEN -> {
//                currentScreen = HEIGHT_WEIGHT_SCREEN
//                changeScreen(currentScreen)
//                tag1 = HEIGHT
//                tag2 = WEIGHT
//            }
//        }
//        fragTrans(tag1, tag2)
//    }

    override fun onClick(view: View?) {

        if(dataList?.get(FIRST_NAME)?.equals("") == true)
            nextButton?.isEnabled = false

        when(view?.id) {
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
                        addToUserProfile()
                        view?.let {
                            Navigation.findNavController(it)
                                .navigate(R.id.action_newUserInfoFrag_to_cameraFrag)
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
    private fun fragTrans(tag1 : String, tag2 : String) {

        val fragtrans = childFragmentManager.beginTransaction()

        var enterFragment = TextSubmitFragment();
        val enterFragment2 = TextSubmitFragment()

        if (!dataList?.get(tag1)?.data.toString().isNullOrEmpty()) {
            enterFragment.value = dataList?.get(tag1)?.data.toString()
        }
        if(!dataList?.get(tag2)?.data.toString().isNullOrEmpty()){
            enterFragment2.value = dataList?.get(tag2)?.data.toString()
        }

        fragtrans.replace(R.id.fn_enter_box, enterFragment, tag1)
        fragtrans.replace(R.id.ln_enter_box, enterFragment2, tag2)

        enterFragment.requestFocus()
        fragtrans.commit()

    }

    /**
     *
     * All user data needs to be manually inserted into a user object
     *
     *
     */
    private fun addToUserProfile() {
        //testing

        user?.email = "test"
        user?.password = "testpass"
        user?.firstName = dataList[FIRST_NAME]?.data.toString()
        user?.lastName = dataList[LAST_NAME]?.data.toString()
        user?.birthday = dataList[AGE]?.data.toString()
        user?.sex = dataList[SEX]?.data.toString()
        user?.height = dataList[HEIGHT]?.data.toString()
        user?.weight = dataList[WEIGHT]?.data.toString()
        user?.country = dataList[COUNTRY]?.data.toString()
        user?.city = dataList[CITY]?.data.toString()



        user?.let { viewModel.update(requireActivity(), it) }
    }

    override fun onDataPass(data: Data) {
        if (data.data.toString().isEmpty()) {
            dataList?.remove(data.sender)
            nextButton?.isEnabled = false
        } else {
            dataList?.set(data.sender, data)
            if (dataList!!.size % 2==0) {
                nextButton?.isEnabled = true
            }
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
            view?.findViewById<TextView>(R.id.wyn_textView)?.text = screenPrompts[screenName]?.get(0)
            view?.findViewById<TextView>(R.id.fn_textView)?.text = screenPrompts[screenName]?.get(1)
            view?.findViewById<TextView>(R.id.ln_textView)?.text = screenPrompts[screenName]?.get(2)
        } catch (e: Exception) {
            throw Exception("Unable to load next screen")
        }
    }

}

private operator fun Any.setValue(newUserInfoFrag: NewUserInfoFrag, property: KProperty<*>, lyfViewModel: LyfViewModel) {

}
