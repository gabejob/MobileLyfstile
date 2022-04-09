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
        view?.clearFocus()
    }

    private fun enableBackButton() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backButtonEnabled = true
                val fragMan = parentFragmentManager
                //val fragtrans = fragMan.beginTransaction()
                val count = fragMan?.backStackEntryCount
                val childCount = childFragmentManager.backStackEntryCount

                if (childCount == 0) {
                    super.setEnabled(false)
                    fragMan.popBackStack()
                    childFragmentManager.popBackStack()
/*                        fragMan.executePendingTransactions()
                        childFragmentManager.executePendingTransactions()*/

                    backButtonEnabled = false
                }else if(childCount > 1){
                    //fragMan?.popBackStack()
                    fragMan.popBackStackImmediate()
                    childFragmentManager.popBackStackImmediate()
                }
                updateScreenInfo(false)
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
/*        val extras = intent.extras
        user = extras?.get(USER_DATA) as User
        currentScreen.value = FIRST_LAST_NAME_SCREEN
        buildDataList(user!!)*/

        //fragTrans(FIRST_NAME, LAST_NAME)
        nextButton = view?.findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)

        if(currentScreen == COUNTRY_CITY_SCREEN){
            fragTrans(COUNTRY, CITY)
            val test = screenPrompts[COUNTRY_CITY_SCREEN]?.get(0)
            view?.findViewById<TextView>(R.id.wyn_textView)?.text =
                test
            view?.findViewById<TextView>(R.id.fn_textView)?.text = screenPrompts[COUNTRY_CITY_SCREEN]?.get(1)
            view?.findViewById<TextView>(R.id.ln_textView)?.text = screenPrompts[COUNTRY_CITY_SCREEN]?.get(2)
            nextButton?.isEnabled = true
        }else if(currentScreen == FIRST_LAST_NAME_SCREEN){
/*            currentScreen == AGE_SEX_SCREEN
            updateScreenInfo(false)*/
            fragTrans(FIRST_NAME, LAST_NAME)
            nextButton?.isEnabled = false
        }

        view.isFocusableInTouchMode = true
        view.isFocusable = true
        view.requestFocus()
        view.clearFocus()

        return view
    }

    /*    private fun buildDataList(user: User){
            if(user.firstName.isNullOrEmpty()) {
                nextButton?.isEnabled = false
            }else{

                dataList[FIRST_NAME] = (Data(FIRST_NAME,user.firstName))
                dataList[LAST_NAME] = (Data(LAST_NAME,user.lastName))
                dataList[COUNTRY] = (Data(COUNTRY,user.country))
                dataList[CITY] = (Data(CITY,user.city))
                dataList[AGE] = (Data(AGE,user.age))
                dataList[SEX] = (Data(SEX,user.sex))
                dataList[WEIGHT] = (Data(WEIGHT,user.weight))
                dataList[HEIGHT] = (Data(HEIGHT,user.height))

            }

        }*/
//@todo
    fun onBackPressed() {
/*    val fragTrans = childFragmentManager
    val count = fragTrans?.backStackEntryCount
    fragTrans?.popBackStack()*/

        when (currentScreen) {
            "first_last_name" -> {
                //super.onBackPressed()
                //Fragment RegistrationFrag = new RegistrationFrag;

/*                val fragtrans = childFragmentManager.beginTransaction()
                fragtrans.replace(R.id.new_user_info, RegistrationFrag())
                fragtrans.commit()
                val fragTrans = parentFragment
                val count = fragTrans?.parentFragmentManager?.backStackEntryCount
                fragTrans?.parentFragmentManager?.popBackStack()

                //fragTrans?.parentFragmentManager?.beginTransaction()?.replace(R.id.new_user_info, RegistrationFrag())?.commit()
                super.onBackPressed()
                finish()*/
            }
            AGE_SEX_SCREEN -> {
                currentScreen = FIRST_LAST_NAME_SCREEN
                changeScreen(currentScreen)
                tag1 = FIRST_NAME
                tag2 = LAST_NAME
            }
            HEIGHT_WEIGHT_SCREEN -> {
                currentScreen = AGE_SEX_SCREEN
                changeScreen(currentScreen)
                tag1 = AGE
                tag2 = SEX
            }
            COUNTRY_CITY_SCREEN -> {
                currentScreen = HEIGHT_WEIGHT_SCREEN
                changeScreen(currentScreen)
                tag1 = HEIGHT
                tag2 = WEIGHT
            }
        }
        //fragTrans(tag1, tag2)
    }

    override fun onClick(view: View?) {
        if(dataList[FIRST_NAME]?.equals("") == true)
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
                if(!updateScreenInfo(true)){
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
                if(direction) {
                    currentScreen = AGE_SEX_SCREEN
                    changeScreen(currentScreen)
                    tag1 = AGE
                    tag2 = SEX
                }
            }
            AGE_SEX_SCREEN -> {
                if(direction) {
                    currentScreen = HEIGHT_WEIGHT_SCREEN
                    tag1 = HEIGHT
                    tag2 = WEIGHT
                }else{
                    currentScreen = FIRST_LAST_NAME_SCREEN
                    tag1 = FIRST_NAME
                    tag2 = LAST_NAME
                }
                changeScreen(currentScreen)
                nextButton?.isEnabled = true
            }
            HEIGHT_WEIGHT_SCREEN -> {
                if(direction) {
                    currentScreen = COUNTRY_CITY_SCREEN
                    tag1 = COUNTRY //Needs to enforce country auto-select
                    tag2 = CITY //Needs to enforce city auto-select
                }else{
                    currentScreen = AGE_SEX_SCREEN
                    tag1 = AGE //Needs to enforce numbers/date selector
                    tag2 = SEX //Needs to enforce dropdown
                }
                changeScreen(currentScreen)
                nextButton?.isEnabled = true
            }
            COUNTRY_CITY_SCREEN -> {
                if(direction) {
                    changeScreen(currentScreen)
                    return false
                }else{
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
        if(tag1 != "first_name") {
            fragtrans.addToBackStack(tag1)
        }

        val enterFragment = TextSubmitFragment();
        val enterFragment2 = TextSubmitFragment()

        if (dataList[tag1]?.data.toString() != "null") {
            enterFragment.value = dataList[tag1]?.data.toString()
        }else{
            enterFragment.value = ""
        }
        if (dataList[tag2]?.data.toString() != "null") {
            enterFragment2.value = dataList[tag2]?.data.toString()
        }else{
            enterFragment2.value = ""
        }

        fragtrans.replace(R.id.fn_enter_box, enterFragment, tag1)
        fragtrans.replace(R.id.ln_enter_box, enterFragment2, tag2)

/*        enterFragment2.fo
        enterFragment.requestFocus()*/
        view?.clearFocus()
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
//        if (data.data.toString().isEmpty()) {
//            dataList?.remove(data.sender)
//            nextButton?.isEnabled = false
//        } else {
//            dataList?.set(data.sender, data)
//            if (dataList!!.size % 2==0) {
//                nextButton?.isEnabled = true
//            }


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

/**
 *
 * All user data needs to be manually inserted into a user object
 *
 *
 */
/*private fun addToUserProfile() {

    user?.firstName = checkVal(FIRST_NAME)
    user?.lastName = checkVal(LAST_NAME)
    user?.birthday = checkVal(AGE)
    user?.sex = checkVal(SEX)
    user?.height = checkVal(HEIGHT)
    user?.weight = checkVal(WEIGHT)
    user?.country = checkVal(COUNTRY)
    user?.city = checkVal(CITY)
}

private fun checkVal(tag : String) : String
{
    if(dataList[tag]?.data.isNullOrEmpty())
        return "Not Provided"
    return dataList[tag]?.data.toString()
}*/
//private operator fun Any.setValue(
//    newUserInfoFrag: NewUserInfoFrag,
//    property: KProperty<*>,
//    lyfViewModel: LyfViewModel
//) {
//}
