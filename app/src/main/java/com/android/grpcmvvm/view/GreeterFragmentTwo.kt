package com.android.grpcmvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.android.grpcmvvm.R
import com.android.grpcmvvm.di.Injector
import kotlinx.android.synthetic.main.fragment_greeter_fragment_two.*

class GreeterFragmentTwo : Fragment() {

    private lateinit var greeterViewModel: GreeterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_greeter_fragment_two, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        greeterViewModel = activity?.run {
            ViewModelProvider(this, Injector.provideGreeterViewModelFactory()).get(GreeterViewModel::class.java)
        } ?: throw Exception("Activity not found")

        greeterViewModel.greeting.observe(viewLifecycleOwner, Observer { greeting ->
            fragment2ResultView.text = greeting
        })

        fragment2SendBtn.setOnClickListener {
            greeterViewModel.sayHello(fragment2InputField.text.toString())
        }
    }
}
