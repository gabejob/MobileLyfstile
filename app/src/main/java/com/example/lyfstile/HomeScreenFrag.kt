package com.example.lyfstile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [HomeScreenFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreenFrag : Fragment(), ActionbarFragment.ClickInterface {
    lateinit var viewModel: LyfViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        viewModel.getUser(requireActivity(), viewModel.user.email)!!.observe(viewLifecycleOwner){
            viewModel.user = User(it)
        }

        val bmgFragment = BMIFragment()
        val actionbarFragment = ActionbarFragment()

        val fragtrans = childFragmentManager.beginTransaction()
        fragtrans.replace(R.id.bmi_frag, bmgFragment, BMI)
        fragtrans.replace(R.id.action_bar_fragment, actionbarFragment, ACTION_BAR)
        fragtrans.commit()
        actionbarFragment.bindClickInterface(this)

        view.findViewById<ImageView>(R.id.pfp_box)?.setImageBitmap(BitmapFactory.decodeByteArray(viewModel.user.pfp, 0,viewModel.user.pfp.size))
        return view
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