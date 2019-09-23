package com.android.grpcmvvm.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.android.grpcmvvm.view.GreeterFragmentOne
import com.android.grpcmvvm.view.GreeterFragmentTwo

class GreeterFragmentsViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> GreeterFragmentOne()
            else -> GreeterFragmentTwo()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Fragment 1"
            else -> "Fragment 2"
        }
    }
}