package com.example.lyfstile

import android.content.Context
import android.graphics.BitmapFactory
import android.hardware.*
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.compose.ui.res.fontResource
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [HomeScreenFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreenFrag : Fragment(), ActionbarFragment.ClickInterface {
    lateinit var viewModel: LyfViewModel
    lateinit var mSensorManager: SensorManager
    lateinit var mLinearAccelerometer: Sensor
    private val mThreshold = 0.5
    private var last_x: Double = 0.0
    private var last_y: Double = 0.0
    private var last_z: Double = 0.0
    private var now_x: Double = 0.0
    private var now_y: Double = 0.0
    private var now_z: Double = 0.0
    private var steps = 0
    private var mNotFirstTime = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        viewModel.getUser(requireActivity(), viewModel.user.email)!!.observe(viewLifecycleOwner) {
            viewModel.user = User(it)
        }


        val bmgFragment = BMIFragment()
        val actionbarFragment = ActionbarFragment()
        val stepsFragment = StepsFragment()

        viewModel.steps?.observe(viewLifecycleOwner){ steps ->
            stepsFragment.stepsBox?.text = steps.toString() + " Steps" //viewModel.steps?.value.toString()
        }

        val fragtrans = childFragmentManager.beginTransaction()
        fragtrans.replace(R.id.bmi_frag, bmgFragment, BMI)
        fragtrans.replace(R.id.account_text, stepsFragment, "Steps")
        fragtrans.replace(R.id.action_bar_fragment, actionbarFragment, ACTION_BAR)
        fragtrans.commit()
        actionbarFragment.bindClickInterface(this)

        view.findViewById<ImageView>(R.id.pfp_box)?.setImageBitmap(
            BitmapFactory.decodeByteArray(
                viewModel.user.pfp,
                0,
                viewModel.user.pfp.size
            )
        )

        mSensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //Get the default light sensor
        mLinearAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)


        return view
    }

    private val mListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent) {

            //Get the acceleration rates along the y and z axes
            now_x = sensorEvent.values[0].toDouble()
            now_y = sensorEvent.values[1].toDouble()
            now_z = sensorEvent.values[2].toDouble()
            if (mNotFirstTime) {
                val dx: Double = Math.abs(last_x - now_x)
                val dy: Double = Math.abs(last_y - now_y)
                val dz: Double = Math.abs(last_z - now_z)

                //Check if the values of acceleration have changed on any pair of axes
                if (dx > mThreshold && dy > mThreshold ||
                    dx > mThreshold && dz > mThreshold ||
                    dy > mThreshold && dz > mThreshold
                ) {

                    // Start step counter
                    val rnd = Random()
                    viewModel.steps?.value = viewModel.steps?.value?.plus(1)
                    //steps = rnd.nextInt()
                    print("***********************$rnd***********************")
                }
            }
            last_x = now_x
            last_y = now_y
            last_z = now_z
            mNotFirstTime = true
        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    }


    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(
            mListener,
            mLinearAccelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(mListener)
    }

    override fun actionButtonClicked(id: Int) {
        when (id) {
            R.id.health -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_homeScreenFrag_to_healthFrag)
                }
            }
            R.id.hiker -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_homeScreenFrag_to_mapsFrag)
                }
            }
            R.id.weather -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_homeScreenFrag_to_weatherFrag)
                }
            }
            R.id.settings -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_homeScreenFrag_to_settingsFrag)
                }
            }
        }
    }
}