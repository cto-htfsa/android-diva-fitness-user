package com.htf.diva.dashboard.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.htf.diva.R
import com.htf.diva.databinding.FragmentMembershipBinding
import com.htf.diva.databinding.FragmentUpcomingBookingBinding
import kotlinx.android.synthetic.main.fragment_membership.*
import kotlinx.android.synthetic.main.fragment_membership.view.*

class MembershipFragment : Fragment(),View.OnClickListener {

    private lateinit var currActivity: Activity
    lateinit var binding: FragmentMembershipBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_membership, container, false)
        setListener()
        selectedTab(0)

        return binding.root

    }
    private fun setListener() {
        binding.root.llUpcomingBooking.setOnClickListener(this)
        binding.root.llCompletedBooking.setOnClickListener(this)

    }
    private fun changeFragment(s: String, fragment: Fragment) {
        val fragmentManager = childFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.commit()
    }

    private fun selectedTab(selected: Int) {
        binding.root.llUpcomingBooking.setBackgroundResource(R.drawable.rect_gray_solid)
        binding.root.llCompletedBooking.setBackgroundResource(R.drawable.rect_gray_solid)

        when (selected) {
            0 -> {
                changeFragment("", UpComingBookingFragment())
                binding.root.llUpcomingBooking.setBackgroundResource(R.drawable.rect_white_solid)
            }
            1 -> {
                changeFragment("", CompletedBookingFragment())
                binding.root.llCompletedBooking.setBackgroundResource(R.drawable.rect_white_solid)
            }
        }
    }
    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.llUpcomingBooking -> {
                selectedTab(0)
            }
            R.id.llCompletedBooking -> {
                selectedTab(1)
            }

        }
    }
}