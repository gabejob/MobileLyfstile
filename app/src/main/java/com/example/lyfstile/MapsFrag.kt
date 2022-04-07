package com.example.lyfstile

import android.content.Intent
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [MapsFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapsFrag : Fragment(), ActionbarFragment.ClickInterface, LocationListener,  View.OnClickListener {
    lateinit var viewModel: LyfViewModel
    private var longitude = 0.0
    private var latitude = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

        val actionbarFragment = ActionbarFragment()

        val fragtrans = childFragmentManager.beginTransaction()
        fragtrans.replace(R.id.action_bar_fragment, actionbarFragment, ACTION_BAR)
        fragtrans.commit()
        actionbarFragment.bindClickInterface(this)

        val mapsHikingButton = view.findViewById<Button>(R.id.Maps_Hiking)
        mapsHikingButton.setOnClickListener(this)
        val mapsParksButton = view.findViewById<Button>(R.id.Maps_Parks)
        mapsParksButton.setOnClickListener(this)
        val mapsGymsButton = view.findViewById<Button>(R.id.Maps_Gym)
        mapsGymsButton.setOnClickListener(this)
        val mapsRestaurantsButton = view.findViewById<Button>(R.id.Maps_Restaurants)
        mapsRestaurantsButton.setOnClickListener(this)

        return view
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.Maps_Hiking -> {
                val searchURI = Uri.parse("geo:$latitude,$longitude?q=Hiking")
                val mapIntent = Intent(Intent.ACTION_VIEW, searchURI)
                startActivity(mapIntent)
            }
            R.id.Maps_Parks -> {
                val searchURI = Uri.parse("geo:$latitude,$longitude?q=Parks")
                val mapIntent = Intent(Intent.ACTION_VIEW, searchURI)
                startActivity(mapIntent)
            }
            R.id.Maps_Gym -> {
                val searchURI = Uri.parse("geo:$latitude,$longitude?q=Gym")
                val mapIntent = Intent(Intent.ACTION_VIEW, searchURI)
                startActivity(mapIntent)
            }
            R.id.Maps_Restaurants -> {
                val searchURI = Uri.parse("geo:$latitude,$longitude?q=Restaurants")
                val mapIntent = Intent(Intent.ACTION_VIEW, searchURI)
                startActivity(mapIntent)
            }
        }
    }

    override fun actionButtonClicked(id: Int) {
        when (id) {
            R.id.health -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_mapsFrag_to_healthFrag)
                }
            }
            R.id.weather -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_mapsFrag_to_weatherFrag)
                }
            }
            R.id.settings -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_mapsFrag_to_settingsFrag)
                }
            }
            R.id.home -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_mapsFrag_to_homeScreenFrag)
                }
            }
        }
    }

}