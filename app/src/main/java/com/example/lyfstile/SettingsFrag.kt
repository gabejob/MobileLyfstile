package com.example.lyfstile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFrag : Fragment(), ActionbarFragment.ClickInterface {
    lateinit var viewModel: LyfViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        val actionbarFragment = ActionbarFragment()

        val fragtrans = childFragmentManager.beginTransaction()
        fragtrans.replace(R.id.action_bar_fragment, actionbarFragment, ACTION_BAR)
        fragtrans.commit()
        actionbarFragment.bindClickInterface(this)
        return view
    }

    override fun actionButtonClicked(id: Int) {
        when (id) {
            R.id.health -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_settingsFrag_to_healthFrag)
                }
            }
            R.id.weather -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_settingsFrag_to_weatherFrag)
                }
            }
            R.id.hiker -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_settingsFrag_to_mapsFrag)
                }
            }
            R.id.home -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_settingsFrag_to_homeScreenFrag)
                }
            }
        }
    }
}