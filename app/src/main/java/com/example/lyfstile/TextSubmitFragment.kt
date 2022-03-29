package com.example.lyfstile

import android.R.attr.password
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.provider.Telephony.Carriers.PASSWORD
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.lyfstile.databinding.FragmentCreateNewUserBinding
import java.util.*
import java.util.regex.Pattern


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class TextSubmitFragment : Fragment(),  OnDateSetListener {
    lateinit var dataPasser: PassData;
    var enterTxt : EditText ?= null;
    var isValid = false
    var autoCompleteEnterTxt : AutoCompleteTextView ?= null;
    var value = ""
    var screenNumber = 0

    private lateinit var binding: FragmentCreateNewUserBinding
    private lateinit var newUserViewModel: NewUserViewModel

    //Associate the callback with this Fragment
/*    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataPasser = parentFragment as PassData
        } catch (e: ClassCastException) {
         }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View ?= null

        val binding = DataBindingUtil.inflate<FragmentCreateNewUserBinding>(inflater, R.layout.new_user_info, container, false)
        newUserViewModel = ViewModelProvider(this).get(NewUserViewModel::class.java)
        binding.viewModel = newUserViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        newUserViewModel.currentScreen.observe(viewLifecycleOwner, { changeScreen(screenNumber) })

        // AutoComplete is created differently, thus we have to check for the fields
        if(tag == COUNTRY || tag == CITY) {
            //view = inflater.inflate(R.layout.fragment_auto_complete_textview, container, false)

            autoCompleteEnterTxt = view?.findViewById(R.id.autoCompleteEnter_box) as AutoCompleteTextView
            if(!value.isNullOrEmpty()) {
                autoCompleteEnterTxt!!.setText(value)
            }

            createAdapter()

        }else{
            view = inflater.inflate(R.layout.fragment_text_submit, container, false)
            enterTxt = view.findViewById(R.id.enter_box) as EditText
            if(!value.isNullOrEmpty()) {
                enterTxt!!.setText(value)
            }
        }

        setContent()

        //May look to move this into its own function for readability,
        //Makes keyboard disappear when enter/submit is fixed, still a little buggy
        if(tag != AGE || tag != COUNTRY) {
            enterTxt?.setOnEditorActionListener { view, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent?.keyCode == KEYCODE_ENTER) {
                    passData(enterTxt?.text.toString())
                    closeKeyboard()
                }
                false
            }
        }else
        {
        }

        return binding.root
    }

    private fun changeScreen(screenNumber: Int) {

    }

    /**
     * Creates and adapter for the give tag.
     * This is used for the autocompletion of countries and cities
     */
    private fun createAdapter() {
        var array: Array<String>? = null
        array = if (tag == COUNTRY) {
            resources.getStringArray(R.array.countries_array)
        } else {
            resources.getStringArray(R.array.cities_array)
        }

        val adapter =
            ArrayAdapter<String>(activity as Context, android.R.layout.simple_list_item_1, array)
        autoCompleteEnterTxt!!.setAdapter(adapter)
        val context = activity as Context
        autoCompleteEnterTxt?.setOnItemClickListener { adapterView, view, i, l ->
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(autoCompleteEnterTxt?.windowToken, 0)
            passData(autoCompleteEnterTxt?.text.toString())
        }
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
            AGE ->
            {
                enterTxt?.clearFocus()
                enterTxt?.setOnFocusChangeListener { view, b -> if(b){ showDatePickerDialog() } }
                enterTxt?.hint = "MM/DD/YYYY"
            }
          COUNTRY ->
          {
              autoCompleteEnterTxt?.hint = "US"
          }
          CITY ->
          {
              autoCompleteEnterTxt?.hint = "Salt Lake City"
          }
          WEIGHT->
          {
              enterTxt?.clearFocus()
              enterTxt?.setOnFocusChangeListener { view, b -> if(b){ showNumberPickerDialog(WEIGHT) } }
              enterTxt?.hint = "xxx lbs, xxxx oz"
          }
          HEIGHT->
          {
              enterTxt?.clearFocus()
              enterTxt?.setOnFocusChangeListener { view, b -> if(b){ showNumberPickerDialog(HEIGHT) } }
              enterTxt?.hint = "xx ft, xx in"
          }
          SEX ->
          {
              enterTxt?.clearFocus()
              enterTxt?.setOnFocusChangeListener { view, b -> if(b){ showSexPickerDialog() } }
              enterTxt?.hint = "Sex"
          }
            //Adds email req.
            EMAIL ->{
                enterTxt?.addTextChangedListener(watcher)
                enterTxt?.hint = "example@example.com"}
            //Adds password stars
            PASSWORD, PASSWORD_CONFIRMED -> {
                enterTxt?.addTextChangedListener(watcher)
                enterTxt?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                enterTxt?.clearFocus()
            }
        }
        enterTxt?.clearFocus()
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

    /*
        Should be called like:
        <string>.isValidEmail() -> Returns -> T/F
        matching <***>@<***>.<***> pattern
     */
    private fun CharSequence?.isValidPassword() : Boolean
    {

        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        val pat = Pattern.compile(PASSWORD_PATTERN)
        if(!isNullOrEmpty() && pat.matcher(this).matches())
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
   /* override fun onClick(view: View?) {
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
*/
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
        closeKeyboard()
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
        pickerOne.value=6
        pickerTwo.minValue=0
        pickerTwo.maxValue=12
        pickerTwo.value=6
        //Check for the type of text needed to display
        //@todo: add in metric system
        when(type)
        {
            WEIGHT ->
            {
                pickerOne.minValue=0
                pickerOne.maxValue=1000
                pickerOne.value=150
                pickerTwo.minValue=0
                pickerTwo.maxValue=1000
                pickerTwo.value=0

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
        ) { _, _ ->
            val weight = pickerOne.value.toString() + " "+textType1+", " + pickerTwo.value.toString() + " "+textType2
            enterTxt?.setText(weight)
            passData(enterTxt?.text.toString())
            enterTxt?.clearFocus()
        }
        builder.setNegativeButton(
            "CANCEL"
        ) { _, _ -> }
        closeKeyboard()
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
        closeKeyboard()
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year : Int, month : Int, day: Int) {

        val date = "$month/$day/$year"
        enterTxt?.setText(date)
        passData(enterTxt?.text.toString())
        var keyboard =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(enterTxt?.windowToken, 0)
        enterTxt?.clearFocus()

    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(sequence: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            when (tag) {
                EMAIL -> {
                    var email = enterTxt?.text
                    if (email.isValidEmail() && email?.length!! > 0) {
                        //Need to look for some sort of success library here...
                        isValid = true
                    } else {
                        enterTxt?.error = "Invalid Email!";
                        isValid = false
                    }
                }
                PASSWORD ->
                {
                    var pass = enterTxt?.text
                    if (pass.isValidPassword() && pass?.length!! > 0) {
                        //Need to look for some sort of success library here...
                        isValid = true
                    } else {
                        enterTxt?.error = "Password must be at least 8 digits\nand contain at least on of each:\n-Uppercase letter\n-Special character (!,#,?)\n-Number (0-9)";
                        isValid = false
                    }
                }
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