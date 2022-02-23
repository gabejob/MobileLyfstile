package com.example.lyfstile

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.ClassCastException
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager

import android.widget.TextView.OnEditorActionListener
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import android.widget.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class TextSubmitFragment : Fragment(), View.OnClickListener, OnDateSetListener {
    lateinit var dataPasser: PassData;
    var enterTxt : EditText ?= null;
    var isValid = false

    //Associate the callback with this Fragment
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataPasser = context as PassData
        } catch (e: ClassCastException) {
         }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_text_submit, container, false)
        enterTxt = view.findViewById(R.id.enter_box) as EditText

        setContent()

        //May look to move this into its own function for readability,
        //Makes keyboard disappear when enter/submit is fixed, still a little buggy
        if(tag != AGE) {
            enterTxt?.setOnEditorActionListener { view, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.keyCode == KEYCODE_ENTER) {
                    passData(enterTxt?.text.toString())
                    closeKeyboard()
                }
                false
            }
        }else
        {
        }

        return view
    }


    fun requestFocus()
    {
        enterTxt?.requestFocus()
    }
 /**
 *
 * Method for flow control:
 * for whatever reason, having two different listeners breaks everything,
 * so keep them separate
 *
 * */
    private fun setContent()
    {

      when(tag)
        {
            //
            AGE, WEIGHT, HEIGHT, SEX ->
            {
                enterTxt?.setOnClickListener(this)
            }
            //@todo
            SEX ->
            {
            }
            //Adds email req.
            EMAIL ->{ enterTxt?.addTextChangedListener(watcher) }
            //Adds password stars
            PASSWORD, PASSWORD_CONFIRMED -> { enterTxt?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD }

        }

    }



    /*
        Should be called like:
        <string>.isValidEmail() -> Returns -> T/F
        matching <***>@<***>.<***> pattern
     */
    private fun CharSequence?.isValidEmail() : Boolean
    {
        if(!isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches())
            return true
        return false

    }


 /**
  *
  *
  * Method to delegate click flow control
  *
  *
  * */
    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.enter_box ->
            {
                when(tag) {
                    AGE ->
                    {
                       showDatePickerDialog()
                    }
                    WEIGHT ->
                    {
                        showNumberPickerDialog(WEIGHT)
                    }
                    HEIGHT ->
                    {
                        showNumberPickerDialog(HEIGHT)
                    }
                    SEX ->
                    {
                        showSexPickerDialog()
                    }
                }
            }

    }
}

    private fun showSexPickerDialog() {

        val builder = AlertDialog.Builder(requireContext())
        var checked = 1

        var options = resources.getStringArray(R.array.sex_array) as Array<String>
        builder.setTitle("Choose Sex:")
            .setSingleChoiceItems(R.array.sex_array,checked,
                DialogInterface.OnClickListener{
                        _, which ->

                    //For some reason this is what I have to do to get this to work?
                    var s = options[which]
                    enterTxt?.setText(s)

                })
        builder.setPositiveButton(
            "OK"
        ) { _, _ ->
            passData(enterTxt?.text.toString())
            enterTxt?.clearFocus()
            closeKeyboard()
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun closeKeyboard()
    {
        var keyboard =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(enterTxt?.windowToken, 0)
    }
    /**
     *
     *
     * Method for creating weight/height onclick dialogs
     * @todo: maybe look to break this into smaller pieces
     *
     *
     * */
    private fun showNumberPickerDialog(type : String)
    {
        val inflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.fragment_picker, null)
        val pickerOne = dialogLayout.findViewById<NumberPicker>(R.id.number_picker_1)
        val pickerTwo = dialogLayout.findViewById<NumberPicker>(R.id.number_picker_2)
        val textOne = dialogLayout.findViewById<TextView>(R.id.text_1)
        val textTwo = dialogLayout.findViewById<TextView>(R.id.text_2)
        var textType1 = ""
        var textType2 = ""

        pickerOne.minValue=0
        pickerOne.maxValue=12
        pickerTwo.minValue=0
        pickerTwo.maxValue=12

        //Check for the type of text needed to display
        //@todo: add in metric system
        when(type)
        {
            WEIGHT ->
            {
                pickerOne.minValue=0
                pickerOne.maxValue=1000
                pickerTwo.minValue=0
                pickerTwo.maxValue=1000

                textOne?.text="Pounds"
                textTwo?.text="Ounces"

                textType1 = "lbs"
                textType2 = "oz"
            }
            HEIGHT ->
            {
                textOne?.text="Feet"
                textTwo?.text="Inches"

                textType1 = "ft"
                textType2 = "in"
            }
        }


        val builder = AlertDialog.Builder(requireContext())

        builder.setView(dialogLayout)
        builder.setTitle("Set Your $type")
        builder.setMessage("Choose $type:")


        builder.setPositiveButton(
            "OK"
        ) { dialog, which ->
            val weight = pickerOne.value.toString() + " "+textType1+", " + pickerTwo.value.toString() + " "+textType2
            enterTxt?.setText(weight)
            passData(enterTxt?.text.toString())
            enterTxt?.clearFocus()
        }
        builder.setNegativeButton(
            "CANCEL"
        ) { _, _ -> }

        // Create the AlertDialog object and return it
        builder.create()
        builder.show()
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            this,
            Calendar.getInstance()[Calendar.YEAR],
            Calendar.getInstance()[Calendar.MONTH],
            Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year : Int, month : Int, day: Int) {

        val date = "$month/$day/$year"
        enterTxt?.setText(date)
        passData(enterTxt?.text.toString())
        var keyboard =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(enterTxt?.windowToken, 0)
       // enterTxt?.clearFocus()

    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(sequence: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            var email = enterTxt?.text
            if (email.isValidEmail() && email?.length!! > 0)
            {
                //Need to look for some sort of success library here...
                isValid = true
            }
            else
            {
                enterTxt?.error = "Invalid Email!";
                isValid = false
            }
        }

    }

    fun passData(text : String)
    {
        var data : Data ?= null
        data = if(text.isEmpty())
            Data(tag.toString(), "Not Provided")
        else
            Data(tag.toString(), text)

        if (data != null) {
            dataPasser?.onDataPass(data)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TextSubmitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TextSubmitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




}