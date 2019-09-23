package com.android.grpcmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.grpcmvvm.R
import com.android.grpcmvvm.adapters.GreeterFragmentsViewPagerAdapter
import kotlinx.android.synthetic.main.activity_greeter.*

class GreeterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greeter)

        val greeterFragmentsViewPagerAdapter = GreeterFragmentsViewPagerAdapter(supportFragmentManager)
        greeterViewPager.adapter = greeterFragmentsViewPagerAdapter

        greeterTabLayout.setupWithViewPager(greeterViewPager)
    }
}
