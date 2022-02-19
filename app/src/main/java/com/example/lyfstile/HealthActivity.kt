package com.example.lyfstile

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible

class HealthActivity : AppCompatActivity(),
    View.OnClickListener, ActionbarFragment.ClickInterface, NumberPicker.OnValueChangeListener {

    var modifyGoals : Button ?= null
    var modifyHeightWeight : Button ?= null
    var goalText : TextView ?= null
    var dialogLayout : View ?= null
    private var numberPicker : NumberPicker ?= null
    private var currGoal : String ?=null
    private var currVal : Int ?=null
    private var activityLevel : Int ?=null


    //Get all radio buttons to autofill selections
    private var radioGroupActivity : RadioGroup ?= null
    private var radioGroupGoal : RadioGroup ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health)
        var actionbarFragment = ActionbarFragment()

        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.action_bar_fragment,actionbarFragment,"action_bar")
        fragtrans.commit()

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
                    }
                R.id.radio_mildly_active ->
                    if (checked) {
                        activityLevel = 2
                    }
                R.id.radio_active ->
                    if (checked) {
                        activityLevel = 3
                    }
                R.id.radio_very_active ->
                    if (checked) {
                        activityLevel = 4
                    }
            }
            currVal?.let { updateDialogGoalText(it, currGoal.toString()) }
        }
    }
    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        currVal = newVal
        updateDialogGoalText(newVal, currGoal.toString())
    }
    private fun showModifyGoalsDialog() {
        val builder = AlertDialog.Builder(this)

        var options = resources.getStringArray(R.array.sex_array) as Array<String>
        val inflater = layoutInflater
        dialogLayout= inflater.inflate(R.layout.fragment_modify_goals, null)
        numberPicker = dialogLayout?.findViewById(R.id.number_picker_1)

        numberPicker?.setOnValueChangedListener(this)
        numberPicker?.maxValue=5
        numberPicker?.minValue=0
        numberPicker?.visibility = View.GONE
        goalText = dialogLayout?.findViewById(R.id.goalText)
        radioGroupActivity = dialogLayout?.findViewById(R.id.radio_group_activity)
        radioGroupGoal = dialogLayout?.findViewById(R.id.radio_group_goal)


        builder.setTitle("Choose Goal:")
        builder.setView(dialogLayout)
        builder.setPositiveButton(
            "OK"
        ) { _, _ -> }
        builder.setNegativeButton("Cancel", null)

        pullExistingOptions()
        builder.show()
    }

    private fun pullExistingOptions() {
        if(currVal != null)
            numberPicker?.value = currVal as Int
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
                }
                "Lost" ->
                {
                    radioGroupGoal?.check(R.id.radio_lw)
                }
            }
        }
        else
        {
            updateDialogGoalText(0,"Maintain")

        }

    }

    override fun actionButtonClicked(id: Int) {
    }

    private fun updateDialogGoalText(newVal : Int, currentGoal : String)
    {
        if(numberPicker?.isVisible == true)
            goalText?.text = getString(R.string.goal_option,currentGoal,newVal)
        else
            goalText?.text = "Maintain Current Weight"

    }

    private fun updateCalorieGoal()
    {}
    private fun updateCurrentGoal()
    {}
    private fun updateActivityGoal()
    {}




}