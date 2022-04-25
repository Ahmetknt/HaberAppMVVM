package com.ahmetkanat.habermvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahmetkanat.habermvvm.R
import com.ahmetkanat.habermvvm.databinding.FragmentDetayBinding
import com.ahmetkanat.habermvvm.roomdb.HaberDAO
import com.ahmetkanat.habermvvm.roomdb.HaberDatabase
import com.ahmetkanat.habermvvm.viewmodel.DetayViewModel
import com.squareup.picasso.Picasso

class DetayFragment : Fragment() {

    private lateinit var viewModel : DetayViewModel
    private lateinit var dataBinding : FragmentDetayBinding
    private var haberId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detay,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            haberId = DetayFragmentArgs.fromBundle(it).id
        }

        viewModel = ViewModelProvider(this).get(DetayViewModel::class.java)
        viewModel.getDataFromRoom(haberId)

        observeLiveData()


    }

    private fun observeLiveData(){

        viewModel.haberLiveData.observe(viewLifecycleOwner, Observer { haber ->

            haber?.let {
                dataBinding.selectedHaber = haber
            }
        })


    }

}