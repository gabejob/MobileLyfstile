package com.example.lyfstile

import android.icu.util.MeasureUnit.*
import android.os.Bundle
import android.text.Spanned
import android.view.*
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlin.math.pow

/**
 * A simple [Fragment] subclass.
 * Use the [bmiActivity.newInstance] factory method to
 * create an instance of this fragment.
 */
class BMIFragment() : Fragment(), View.OnClickListener {
    lateinit var viewModel: LyfViewModel

    private var bmiBox: TextView? = null

    private val bmiFactorImperial = 703
    private val bmiFactorMetric = 10000

    //User input --> Maybe we don't have people enter in custom height, so no entry fields
    //Some of these may be unused, but left in for simplicity

    private var feet = 0.0
    private var inches = 0.0

    private var meters = 0.0
    private var km = 0.0


    private var pounds = 0.0
    private var ounces = 0.0

    private var kg = 0.0
    private var grams = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        val height = viewModel.user.height
        val weight = viewModel.user.weight
        if (height != "Not Provided" && weight != "Not Provided") {
            parseBundleData(height, weight)
        }
        val view = inflater.inflate(R.layout.fragment_bmi, container, false)

        bmiBox = view.findViewById<TextView>(R.id.bmi_text)
        update()

        return view
    }

    private fun parseBundleData(height: String, weight: String) {
        val heightSplit = height.split(' ')
        val weightSplit = weight.split(' ')

        feet = heightSplit[0].toDouble()
        inches = heightSplit[2].toDouble()

        pounds = weightSplit[0].toDouble()
        ounces = weightSplit[2].toDouble()
    }


    /*
    *
    * weight: MUST BE IN EITHER (KG) OR (OZ)
    * height: MUST BE IN (CM) OR (IN)
    *
    */
    private fun calculateBMI(weight: Double, height: Double, bmiFactor: Int): Double {
        return (weight / height.pow(2.0)) * bmiFactor
    }

    /*
    *
    * Turn kg  into oz for calculation formula
    *
    * */

    private fun weightToImperial(kg: Double, grams: Double): Double {
        return (((kg * 1000) + grams) * 28.35)
    }

    /*
    *
    * Turn lbs into kg for calculation formula
    *
    * */
    private fun weightToMetric(pounds: Double, ounces: Double): Double {
        return (((ounces) + pounds * 16) / 35.274)
    }

    /*
    *
    * Turn meters/cm into in for calculation formula
    *
    * */
    private fun heightToImperial(meters: Double, cm: Double): Double {
        return (((meters * 100) + cm) / 2.54)
    }

    /*
    *
    * Turn feet/inches to cm for calculation formula
    *
    * */
    private fun heightToMetric(feet: Double, inches: Double): Double {
        return (((feet * 12) + inches) * 2.54)

    }


    /*
*
* Turn feet/inches to cm for calculation formula
*
* */
    private fun crunchImperialHeight(feet: Double, inches: Double): Double {
        return ((feet * 12) + inches)

    }

    /*
*
* Turn feet/inches to cm for calculation formula
*
* */
    private fun crunchImperialWeight(pounds: Double, ounces: Double): Double {
        return if (ounces > 0)
            ((ounces / 16) + pounds)
        else
            (pounds)

    }

    /*
*
* Turn feet/inches to cm for calculation formula
*
* */
    private fun crunchMetric(feet: Double, inches: Double): Double {
        return 1.0 //TODO

    }

    private fun update() {

        val newBMI = calculateBMI(
            crunchImperialWeight(pounds, ounces),
            crunchImperialHeight(feet, inches), bmiFactorImperial
        )

        bmiBox?.text = getBMIIndicator(newBMI)
    }

    override fun onClick(view: View?) {
        /* if(view?.id == R.id.go_button)
         {
             var newBMI = calculateBMI(crunchImperialWeight(pounds,ounces),
                 crunchImperialHeight(feet,inches),bmiFactorImperial)



             bmiBox?.text = getBMIIndicator(newBMI)
         }*/
    }

    private fun getBMIIndicator(newBMI: Double): Spanned {
        if (newBMI < 18.5)
            return HtmlCompat.fromHtml(
                getString(R.string.bmi_underweight, newBMI),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )

        if (newBMI > 18.5 && newBMI < 25)
            return HtmlCompat.fromHtml(
                getString(R.string.bmi_normal, newBMI),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )

        if (newBMI > 25 && newBMI < 30)
            return HtmlCompat.fromHtml(
                getString(R.string.bmi_overweight, newBMI),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )

        if (newBMI > 30)
            return HtmlCompat.fromHtml(
                getString(R.string.bmi_obese, newBMI),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        return HtmlCompat.fromHtml(
            getString(R.string.not_provided),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
    }
}