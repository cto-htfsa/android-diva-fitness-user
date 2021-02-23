package com.htf.diva.dashboard.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.htf.diva.R
import com.htf.diva.models.Banner


class BannerFragment : Fragment() {
    private lateinit var currActivity: Activity
    private lateinit var rootView:View

    companion object {
        fun create(storeBanner: Banner): BannerFragment {
            val fragment = BannerFragment()
            val bundle = Bundle()
            bundle.putSerializable("storeBanner", storeBanner)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_banner, container, false)
        currActivity=requireActivity()
        return rootView
    }

}