package com.example.lyfstile

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import java.util.*

class HealthActivity : AppCompatActivity(),
    View.OnClickListener, ActionbarFragment.ClickInterface, NumberPicker.OnValueChangeListener {

    private var user : User ?=null

    //Storage values
    var currentGoalString : String ?= null
    var calorieGoalString : String ?= null
    var activityGoalString : String ?= null

    // NOTE: Using the Harris-Benedict equation for BMR
    var BMR : Double ?= null
    var calorieReq : Double ?= null
    var userActivityLevelConstant : Double ?= null

    //Constants:
    //Source: Mayo Clinic
    //https://www.mayoclinic.org/healthy-lifestyle/weight-loss/in-depth/calories/art-20048065
    val CALORIE_CHANGE_LOW : Double = 500.0
    val CALORIE_CHANGE_HIGH : Double = 1000.0
    val ACTIVITY_LEVEL_1 : Double = 1.2
    val ACTIVITY_LEVEL_2 : Double = 1.375
    val ACTIVITY_LEVEL_3 : Double = 1.55
    val ACTIVITY_LEVEL_4 : Double = 1.725
    val ACTIVITY_LEVEL_5 : Double = 1.9



    //HealthActivity
    var activityGoalText : TextView ?= null
    var activityCalorieGoalText : TextView ?= null
    var activityCurrentGoalText : TextView ?= null


    //All within goal fragment
    var modifyGoals : Button ?= null
    var modifyHeightWeight : Button ?= null
    var goalText : TextView ?= null
    var warningText : TextView ?= null

    var dialogLayout : View ?= null
    private var numberPicker : NumberPicker ?= null
    //Get all radio buttons to autofill selections
    private var radioGroupActivity : RadioGroup ?= null
    private var radioGroupGoal : RadioGroup ?= null

    //For INTERMEDIATE USE IN DIALOG!
    private var currGoal : String ?=null
    private var currGoalVal : Int ?=null
    private var activityLevel : Int ?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var actionbarFragment = ActionbarFragment()

        var extras = intent.extras
        user = extras?.get(USER_DATA) as User
        setContentView(R.layout.activity_health)


        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.action_bar_fragment,actionbarFragment,"action_bar")
        fragtrans.commit()

        activityCalorieGoalText =  findViewById(R.id.calorieGoal)
        activityCurrentGoalText = findViewById(R.id.currentGoal)
        activityGoalText = findViewById(R.id.activityGoal)
        warningText = findViewById(R.id.warning)
        modifyGoals = findViewById(R.id.modifyGoalsButton)
        modifyHeightWeight = findViewById(R.id.modifyHeightWeight)
        modifyHeightWeight?.setOnClickListener(this)
        modifyGoals?.setOnClickListener(this)
        actionbarFragment.bindClickInterface(this)

    }

    override fun onClick(view: View?) {
        when(view?.id)
        {
            R.id.modifyGoalsButton ->
            {
                showModifyGoalsDialog()
            }
            R.id.modifyHeightWeight ->
            {

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
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                //===For goals===
                R.id.radio_lw ->
                    if (checked) {
                       // Toast.makeText(this, "made it here!", Toast.LENGTH_SHORT).show()
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
    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        currGoalVal = newVal
        updateDialogGoalText(newVal, currGoal.toString())
    }
    private fun showModifyGoalsDialog() {
        val builder = AlertDialog.Builder(this)

        //Get dialog view and number picker
        val inflater = layoutInflater
        dialogLayout= inflater.inflate(R.layout.fragment_modify_goals, null)
        numberPicker = dialogLayout?.findViewById(R.id.number_picker_1)

        //Setup number picker values, should initially be invisible
        numberPicker?.setOnValueChangedListener(this)
        numberPicker?.maxValue=5
        numberPicker?.minValue=0
        numberPicker?.visibility = View.GONE

        //setup dynamic text box and radio buttons
        goalText = dialogLayout?.findViewById(R.id.goalText)
        radioGroupActivity = dialogLayout?.findViewById(R.id.radio_group_activity)
        radioGroupGoal = dialogLayout?.findViewById(R.id.radio_group_goal)


        builder.setTitle("Choose Goal:")
        builder.setView(dialogLayout)
        builder.setPositiveButton(
            "OK"
        ) { _, _ ->

            updateGoals()

        }
        builder.setNegativeButton("Cancel", null)


        pullExistingOptions()
        builder.show()
    }

    /**
     *
     *
     * Method to update dialog options from pre-existing data
     * needs to be migrated to user goals addition
     *
     * */
    private fun pullExistingOptions() {
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


    private fun updateDialogGoalText(newVal : Int, currentGoal : String)
    {
        if(numberPicker?.isVisible == true) {
            goalText?.text = getString(R.string.goal_option, currentGoal, newVal)
        }
        else
            goalText?.text = "Maintain Current Weight"

    }

    private fun updateGoals() {
        //The amount to maintain a weight at a certain activity level
        calorieReq = calculateDifference(userActivityLevelConstant?.let {
            calculateTDEE(
                calculateBMR(
                    user?.sex.toString(),
                    getYears(user?.birthday), getHeight(user?.height), getWeight(user?.weight)
                ), it
            )
        }, currGoal, currGoalVal);

        activityCalorieGoalText?.text = getString(R.string.calorie_goal, calorieReq)

        if (currGoalVal!! > 2) {
            when(currGoal) {
                "Lose"-> {
                    val warning = HtmlCompat.fromHtml(
                        getString(R.string.warning_losing, currGoal, currGoalVal),
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                    warningText?.text = warning
                }
                "Gain"-> {
                val warning = HtmlCompat.fromHtml(
                    getString(R.string.warning_gaining, currGoal, currGoalVal),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
                warningText?.text = warning
            }
            }
        }
        else
        {
            warningText?.text = ""
        }

        activityCurrentGoalText?.text = getString(
            R.string.current_goal,
            getString(
                R.string.goal_option,
                currGoal, currGoalVal
            )
        )
        activityGoalText?.text = getString(R.string.activity_goal)

    }

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

    private fun getYears(birthday: String?): Int {
        var currYear = Calendar.getInstance().get(Calendar.YEAR);

        var split = birthday?.split('/')
        var birthYear = split?.get(2)?.toInt() ?: 0

       return currYear-birthYear
    }

    private fun getWeight(weight: String?) : Double
    {
        var split = weight?.split(' ')
        var weightInKG = 0.0

        weightInKG = if(weight?.contains("lbs") == true) {
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

    private fun getHeight(height: String?) : Double
    {
        var split = height?.split(' ')
        var heightInCM = 0.0

        heightInCM = if(height?.contains("ft")== true)
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
        return 0.0;
    }



    override fun actionButtonClicked(id: Int) {
    }


}