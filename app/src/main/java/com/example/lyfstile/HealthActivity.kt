package com.example.lyfstile

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class HealthActivity : AppCompatActivity(),
    View.OnClickListener, ActionbarFragment.ClickInterface, NumberPicker.OnValueChangeListener {

    private var viewModel : ViewModel ?= null
    private var user : User ?=null
    private var profilePic: Bitmap? = null
    private var dialog : Dialog ?= null
    // NOTE: Using the Harris-Benedict equation for BMR
    //Must be saved on state change
    var BMR : Double ?= null
    private var calorieReq : Double ?= null
    private var userActivityLevelConstant : Double ?= null

    //Constants:
    //Source: Mayo Clinic
    //https://www.mayoclinic.org/healthy-lifestyle/weight-loss/in-depth/calories/art-20048065
    private val CALORIE_CHANGE_LOW : Double = 500.0
    val CALORIE_CHANGE_HIGH : Double = 1000.0

    private val ACTIVITY_LEVEL_1 : Double = 1.2
    private val ACTIVITY_LEVEL_2 : Double = 1.375
    private val ACTIVITY_LEVEL_3 : Double = 1.55
    private val ACTIVITY_LEVEL_4 : Double = 1.725
    private val ACTIVITY_LEVEL_5 : Double = 1.9


    //HealthActivity
    //Must be saved on state change
    private var activityGoalText : TextView ?= null
    private var activityCalorieGoalText : TextView ?= null
    private var activityCurrentGoalText : TextView ?= null


    //All within goal fragment
    private var modifyGoals : Button ?= null
    private var modifyHeightWeight : Button ?= null
    private var goalText : TextView ?= null
    private var warningText : FloatingActionButton ?= null
    private var isToolTipShown : Boolean = false

    private var numberPicker : NumberPicker ?= null
    //Get all radio buttons to autofill selections
    private var radioGroupActivity : RadioGroup ?= null
    private var radioGroupGoal : RadioGroup ?= null
    private var okButtonDialog : Button ?= null
    private var cancelButtonDialog : Button ?= null

    //For INTERMEDIATE USE IN DIALOG!
    private var currGoal : String ?=null
    private var warningTooltipText : String ?= null
    private var currGoalVal : Int ?=null
    private var activityLevel : Int ?=null

/**
 *
 * Save values up changing state,
 * only save those that are necessary to recalculate BMR/tdee
 * */
    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        outState.putParcelable("usr_data",user)
        outState.putString("curr_goal", currGoal)
        currGoalVal?.let { outState.putInt("curr_goal_val", it) }
        activityLevel?.let { outState.putInt("activity_level", it) }
        userActivityLevelConstant?.let { outState.putDouble("activity_level_constant", it) }
    }

    /**
     *
     * Pull values from saved instance state,
     * update locals, then UPDATE DIALOG OPTIONS!
     * */

    private fun restoreInstanceState(savedInstanceState: Bundle)
    {
        super.onRestoreInstanceState(savedInstanceState)

        user = savedInstanceState.getParcelable("usr_data")
        currGoal = savedInstanceState.getString("curr_goal")
        currGoalVal = savedInstanceState.getInt("curr_goal_val")
        activityLevel = savedInstanceState.getInt("activity_level")
        userActivityLevelConstant = savedInstanceState.getDouble("activity_level_constant")
        pullExistingOptions()
        updateGoals()
    }

    /**
     * Launch point of activity
     * */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider( this).get(ViewModel::class.java)

        val actionbarFragment = ActionbarFragment()
        val extras = intent.extras
        user = extras?.get(USER_DATA) as User
        profilePic = intent.getParcelableExtra(PROFILE_PIC)

        setContentView(R.layout.activity_health)

        //Grab all of the dynamic pieces...
        activityCalorieGoalText =  findViewById(R.id.calorieGoal)
        activityCurrentGoalText = findViewById(R.id.currentGoal)
        activityGoalText = findViewById(R.id.activityGoal)
        warningText = findViewById(R.id.floatingActionButton)
        modifyGoals = findViewById(R.id.modifyGoalsButton)
        modifyGoals?.setOnClickListener(this)
        warningText?.setOnClickListener(this)
        findViewById<ImageView>(R.id.pfp_box)?.setImageBitmap(profilePic)
        //setup actionbar click interface
        actionbarFragment.bindClickInterface(this)

        //Check to see if we're purely restoring state
        if(savedInstanceState != null)
        {
            restoreInstanceState(savedInstanceState)
        }

        //initialize action bar
        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.action_bar_fragment,actionbarFragment,"action_bar")
        fragtrans.commit()
    }

    override fun onClick(view: View?)
    {
        when(view?.id)
        {
            R.id.modifyGoalsButton ->
            {
                showModifyGoalsDialog()
            }
            R.id.floatingActionButton ->
            {
                if(isToolTipShown) {
                    warningText?.tooltipText = null
                    isToolTipShown = false
                }
                else {
                    warningText?.tooltipText = warningTooltipText
                    warningText?.performLongClick()
                    isToolTipShown=true
                }
            }
            R.id.cancel ->{dialog?.hide()}
            R.id.ok ->
            {
                updateGoals()
                dialog?.hide()
            }

        }
    }
    /**
     *
     * Note! Here be dragons! Debugger does not work here!
     *
     * Function to handle all radio button clicks, delegates workflow for both sets; activity level, goal
     *
     * */
    fun onRadioButtonClicked(view: View)
    {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                //===For goals===
                R.id.radio_lw ->
                    if (checked) {
                        currGoal = "Lose"
                        numberPicker?.visibility = View.VISIBLE
                    }
                R.id.radio_mw ->
                    if (checked) {
                        currGoal = "Maintain"
                        numberPicker?.visibility = View.GONE
                    }
                R.id.radio_gw ->
                    if (checked) {
                        currGoal = "Gain"
                        numberPicker?.visibility = View.VISIBLE
                    }

                //===For activity levels===
                R.id.radio_not_active ->
                    if (checked) {
                        activityLevel = 1
                        userActivityLevelConstant = ACTIVITY_LEVEL_1
                    }
                R.id.radio_mildly_active ->
                    if (checked) {
                        activityLevel = 2
                        userActivityLevelConstant = ACTIVITY_LEVEL_2
                    }
                R.id.radio_active ->
                    if (checked) {
                        activityLevel = 3
                        userActivityLevelConstant = ACTIVITY_LEVEL_3
                    }
                R.id.radio_very_active ->
                    if (checked) {
                        activityLevel = 4
                        userActivityLevelConstant = ACTIVITY_LEVEL_4
                    }
                R.id.radio_extremely_active ->
                    if (checked) {
                        activityLevel = 5
                        userActivityLevelConstant = ACTIVITY_LEVEL_5
                    }
            }
            currGoalVal?.let { updateDialogGoalText(it, currGoal.toString()) }
        }
    }

    /**
     *
     * Listener for number picker in dialog
     * updates shown text
     * */
    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int)
    {
        currGoalVal = newVal
        updateDialogGoalText(newVal, currGoal.toString())
    }

    /**
     *
     * Initializer for the "modify goals" button, handles all setup
     * */
    @SuppressLint("InflateParams")
    private fun showModifyGoalsDialog()
    {
        dialog = Dialog(this,R.style.AlertDialogCustom)

        dialog?.setTitle("Choose Goal:")
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(R.layout.fragment_modify_goals)

        //grab positive/negative buttons
        okButtonDialog = dialog?.findViewById(R.id.ok)
        cancelButtonDialog = dialog?.findViewById(R.id.cancel)
        okButtonDialog?.setOnClickListener(this)
        cancelButtonDialog?.setOnClickListener(this)

        //grab number picker
        numberPicker = dialog?.findViewById(R.id.number_picker_1)

        //Setup number picker values, should initially be invisible
        numberPicker?.setOnValueChangedListener(this)
        numberPicker?.maxValue=5
        numberPicker?.minValue=0
        numberPicker?.visibility = GONE

        //setup dynamic text box and radio buttons
        goalText = dialog?.findViewById(R.id.goalText)
        radioGroupActivity = dialog?.findViewById(R.id.radio_group_activity)
        radioGroupGoal = dialog?.findViewById(R.id.radio_group_goal)

        pullExistingOptions()
        dialog?.show()
    }

    /**
     *
     * Method to update dialog options from pre-existing data
     * needs to be migrated to user goals addition
     *
     * */
    private fun pullExistingOptions()
    {
        if(currGoalVal != null)
            numberPicker?.value = currGoalVal as Int
        else
            numberPicker?.value = 0
        if(currGoal != null)
        {
            when(currGoal)
            {
                "Maintain" ->
                {
                  radioGroupGoal?.check(R.id.radio_mw)
                }
                "Gain" ->
                {
                    radioGroupGoal?.check(R.id.radio_gw)
                    numberPicker?.visibility = View.VISIBLE

                }
                "Lose" ->
                {
                    radioGroupGoal?.check(R.id.radio_lw)
                    numberPicker?.visibility = View.VISIBLE
                }
            }
            currGoalVal?.let { updateDialogGoalText(it, currGoal.toString()) }
        }
        else
        {
             updateDialogGoalText(0, "")
        }

        if(activityLevel!=null)
        {
            when(activityLevel)
            {
                1 ->
                {
                    radioGroupActivity?.check(R.id.radio_not_active)
                }
                2 ->
                {
                    radioGroupActivity?.check(R.id.radio_mildly_active)
                }
                3 ->
                {
                    radioGroupActivity?.check(R.id.radio_active)
                }
                4 ->
                {
                    radioGroupActivity?.check(R.id.radio_very_active)
                }
                5 ->
                {
                    radioGroupActivity?.check(R.id.radio_extremely_active)
                }
            }
        }
    }


    /**
     *
     * Used in tandem with the number picker listener,
     * changes dialog goal text
     * */
    private fun updateDialogGoalText(newVal : Int, currentGoal : String)
    {
        if(numberPicker?.isVisible == true) {
            goalText?.text = getString(R.string.goal_option, currentGoal, newVal)
        }
        else
            goalText?.text = "Maintain Current Weight"

    }

    /**
     *
     * Function to handle all text changes on the health activity screen
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateGoals()
    {
        //The amount to maintain a weight at a certain activity level
        calorieReq = calculateDifference(userActivityLevelConstant?.let {
            calculateTDEE(
                calculateBMR(
                    user?.sex.toString(),
                    getYears(user?.birthday), getHeight(user?.height), getWeight(user?.weight)
                ), it
            )
        }, currGoal, currGoalVal)

        activityCalorieGoalText?.text = getString(R.string.calorie_goal, calorieReq)


        //Warning text handling
        if (currGoalVal!! > 2) {
            when(currGoal) {
                "Lose"-> {
                    warningTooltipText = HtmlCompat.fromHtml(
                        getString(R.string.warning_losing, currGoal, currGoalVal),
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    ).toString()
                    warningText?.visibility=VISIBLE
                }
                "Gain"-> {
                warningTooltipText = HtmlCompat.fromHtml(
                    getString(R.string.warning_gaining, currGoal, currGoalVal),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                ).toString()
                    warningText?.visibility=VISIBLE
                }
                else ->
                {
                    warningText?.visibility=GONE
                }
            }
        }
        else
        {
            warningText?.visibility=GONE
            warningText?.tooltipText = ""
        }

        //Current goal text handling
        if(currGoal=="Maintain")
            activityCurrentGoalText?.text = getString(
                R.string.current_goal,
                getString(
                    R.string.maintain_weight
                )
            )
        else
            activityCurrentGoalText?.text = getString(
                R.string.current_goal,
                getString(R.string.goal_option,
                currGoal, currGoalVal
                )
            )

        //Activity goal handling
        when(activityLevel)
        {
            1->{activityGoalText?.text = getString(R.string.activity_goal,"25 minutes")}
            2->{activityGoalText?.text = getString(R.string.activity_goal, "35 minutes")}
            3->{activityGoalText?.text = getString(R.string.activity_goal,"45 minutes")}
            4->{activityGoalText?.text = getString(R.string.activity_goal,"1 hour")}
            5->{activityGoalText?.text = getString(R.string.activity_goal, "1.5 hours")}
        }
    }

    /**
     *
     * Determines the calorie deficit/overage required to gain or lose weight
     * */
    private fun calculateDifference(tdee : Double?, goal : String?, goalVal : Int?) : Double
    {
        when(goal)
        {
            "Lose" ->
            {
                if (tdee != null && goalVal != null) {
                    return tdee - (CALORIE_CHANGE_LOW*goalVal)
                }
            }

            "Gain" ->
            {
                if (tdee != null && goalVal != null) {
                    return tdee + (CALORIE_CHANGE_LOW*goalVal)
                }
            }

            "Maintain" -> {return tdee as Double}
        }

        return tdee as Double
    }

    /**
     *
     * Function to parse incoming birthday input
     * */
    private fun getYears(birthday: String?): Int
    {
        if(birthday == "Not Provided")
            return 2022
        val currYear = Calendar.getInstance().get(Calendar.YEAR)
        val split = birthday?.split('/')
        val birthYear = split?.get(2)?.toInt() ?: 0

       return currYear-birthYear
    }

    /**
     *
     * Function to parse incoming weight input
     * */
    private fun getWeight(weight: String?) : Double
    {
        if(weight == "Not Provided")
            return 0.0
        val split = weight?.split(' ')
        val weightInKG: Double = if(weight?.contains("lbs") == true) {
            val lbs = split?.get(0)?.toDouble()
            val oz = split?.get(2)?.toDouble()
            if(lbs!= null && oz != null)
                (lbs /2.205)+(oz/ 35.274)
            else
                0.0
        } else {
            val kg = split?.get(0)?.toDouble()
            val g = split?.get(2)?.toDouble()
            if(kg!= null && g != null)
                (kg)+(g/ 1000)
            else
                0.0
        }
        return  weightInKG
    }

    /**
     *
     * Function to parse incoming height input
     * */
    private fun getHeight(height: String?) : Double
    {
        if(height=="Not Provided")
            return 0.0
        val split = height?.split(' ')
        val heightInCM: Double = if(height?.contains("ft")== true)
        {
            val ft = split?.get(0)?.toDouble()
            val inchs = split?.get(2)?.toDouble()
            if(ft != null && inchs != null)
                (ft * 30.48)+(inchs*2.54)
            else
                0.0

        }else
        {
            val m = split?.get(0)?.toDouble()
            val cm = split?.get(2)?.toDouble()
            if(m != null && cm != null)
                (m /100)+(cm)
            else
                0.0
        }
        return heightInCM
    }
    /**
     *
     * Function to calculate the Total daily energy expenditure
     *
     * */
    private fun calculateTDEE(bmr : Double?, activityLevel : Double) : Double
    {
        if (bmr != null) {
            return bmr*activityLevel
        }
        return 0.0
    }

    /**
     *
     * Function to obtain the Basic metabolic rate
     *
     * */
    private fun calculateBMR(sex : String?, age: Int?, height : Double?, weight : Double?) : Double
    {
        if(weight != null && age != null && height != null) {
            when (sex?.uppercase()) {
                "MALE" -> {
                    //WEIGHT MUST BE IN KG, HEIGHT MUST BE IN CM, AGE MUST BE IN YEARS
                    return (66.5 + (13.75 * weight) + (5.003 * height)) - (6.755 * age)
                }
                "FEMALE" -> {
                    //WEIGHT MUST BE IN KG, HEIGHT MUST BE IN CM, AGE MUST BE IN YEARS
                    return (655.1 + (9.563 * weight) + (1.850 * height)) - (4.676 * age)
                }
            }
        }
        return 0.0
    }

    /**
     *
     * Action bar related listeners.
     * */
    override fun actionButtonClicked(id: Int)
    {
        val temp = User()
        temp.tempConstruct()
        when(id)
        {
            R.id.health ->
            {}
            R.id.hiker ->
            {
                val mapScreen = Intent(this, MapActivity::class.java)
                mapScreen.putExtra(USER_DATA, user)
                mapScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(mapScreen)
            }
            R.id.weather ->
            {
                val weatherScreen = Intent(this, WeatherActivity::class.java)
                weatherScreen.putExtra(USER_DATA, user)
                weatherScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(weatherScreen)
            }
            R.id.home ->
            {
                val homeScreen = Intent(this, HomeScreen::class.java)
                homeScreen.putExtra(USER_DATA, user)
                homeScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(homeScreen)
            }
            R.id.settings ->
            {
                val settingsScreen = Intent(this, SettingsActivity::class.java)
                settingsScreen.putExtra(USER_DATA, user)
                settingsScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(settingsScreen)
            }
        }
    }
}