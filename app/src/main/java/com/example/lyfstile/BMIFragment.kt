package com.example.lyfstile

import android.icu.util.MeasureUnit
import android.icu.util.MeasureUnit.*
import android.os.Build
import android.os.Bundle
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import kotlin.math.pow

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [bmiActivity.newInstance] factory method to
 * create an instance of this fragment.
 */
class BMIFragment : Fragment(), PassData, View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var bmiBox : TextView ?= null

    private val bmiFactorImperial = 703
    private val bmiFactorMetric = 10000

    //User input --> Maybe we don't have people enter in custom height, so no entry fields
    //Some of these may be unused, but left in for simplicity

        private var feet = 6.0
        private var inches = 1.1

        private var meters = 5.0
        private var km = 5.0


        private var pounds = 181.0
        private var ounces = 0.0

        private var kg = 4.0
        private var grams = 4.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var bundle = this.arguments
        var height = bundle?.getString("height")
        var weight = bundle?.getString("weight")
        if (height != null && weight != null) {
            parseBundleData(height,weight)
        }
        var view = inflater.inflate(R.layout.fragment_bmi, container, false)
        var goButton = view.findViewById<Button>(R.id.go_button)

        goButton.setOnClickListener(this)
        bmiBox = view.findViewById<TextView>(R.id.bmi_text)

        return view
    }

    private fun parseBundleData(height : String, weight : String)
    {
        var heightSplit = height.split(' ')
        var weightSplit = weight.split(' ')

        feet = heightSplit[0].toDouble()
        inches = heightSplit[2].toDouble()

        pounds = weightSplit[0].toDouble()
        ounces = weightSplit[2].toDouble()

    }

    override fun onDataPass(data: Data) {
        TODO("Not yet implemented")
    }



    /*
    *
    * weight: MUST BE IN EITHER (KG) OR (OZ)
    * height: MUST BE IN (CM) OR (IN)
    *
    */
    private fun calculateBMI(weight : Double, height : Double, bmiFactor : Int) : Double
    {
        return (weight / height.pow(2.0)) *bmiFactor
    }

    /*
    *
    * Turn kg  into oz for calculation formula
    *
    * */

    private fun weightToImperial(kg : Double, grams : Double) : Double
    {
      return (((kg*1000)+grams)*28.35)
    }
    /*
    *
    * Turn lbs into kg for calculation formula
    *
    * */
    private fun weightToMetric(pounds : Double, ounces : Double) : Double
    {
        return (((ounces)+pounds*16)/35.274)
    }
    /*
    *
    * Turn meters/cm into in for calculation formula
    *
    * */
    private fun heightToImperial(meters: Double, cm : Double) : Double
    {
        return (((meters*100)+cm)/2.54)
    }
    /*
    *
    * Turn feet/inches to cm for calculation formula
    *
    * */
    private fun heightToMetric(feet : Double, inches : Double) : Double
    {
        return (((feet*12)+inches)*2.54)

    }


    /*
*
* Turn feet/inches to cm for calculation formula
*
* */
    private fun crunchImperialHeight(feet : Double, inches : Double) : Double
    {
        return ((feet*12)+inches)

    }
    /*
*
* Turn feet/inches to cm for calculation formula
*
* */
    private fun crunchImperialWeight(pounds : Double, ounces : Double) : Double
    {
        return if(ounces>0)
            ((ounces/16)+pounds)
        else
            (pounds)

    }
    /*
*
* Turn feet/inches to cm for calculation formula
*
* */
    private fun crunchMetric(feet : Double, inches : Double) : Double
    {
        return 1.0 //TODO

    }


    override fun onClick(view: View?) {

        if(view?.id == R.id.go_button)
        {
            var newBMI = calculateBMI(crunchImperialWeight(pounds,ounces),
                crunchImperialHeight(feet,inches),bmiFactorImperial)



            bmiBox?.text = getBMIIndicator(newBMI)
        }


    }

    private fun getBMIIndicator(newBMI: Double): Spanned {

        if(newBMI<18.5)
            return  HtmlCompat.fromHtml(getString(R.string.bmi_underweight, newBMI), HtmlCompat.FROM_HTML_MODE_COMPACT)

        if(newBMI>18.5 && newBMI < 25)
            return  HtmlCompat.fromHtml(getString(R.string.bmi_normal, newBMI), HtmlCompat.FROM_HTML_MODE_COMPACT)

        if(newBMI>25 && newBMI<30)
            return  HtmlCompat.fromHtml(getString(R.string.bmi_overweight, newBMI), HtmlCompat.FROM_HTML_MODE_COMPACT)
        return  HtmlCompat.fromHtml(getString(R.string.bmi_obese, newBMI), HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment bmiActivity.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BMIFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




}