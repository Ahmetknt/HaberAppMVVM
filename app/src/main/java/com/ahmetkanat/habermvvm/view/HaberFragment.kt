package com.ahmetkanat.habermvvm.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmetkanat.habermvvm.R
import com.ahmetkanat.habermvvm.adapter.HaberAdapter
import com.ahmetkanat.habermvvm.databinding.FragmentHaberBinding
import com.ahmetkanat.habermvvm.model.Haber
import com.ahmetkanat.habermvvm.model.Response
import com.ahmetkanat.habermvvm.network.APIUtils
import com.ahmetkanat.habermvvm.roomdb.HaberDatabase
import com.ahmetkanat.habermvvm.viewmodel.HaberViewModel
import retrofit2.Call
import retrofit2.Callback

class HaberFragment : Fragment() {

    private lateinit var viewModel : HaberViewModel
    private lateinit var binding : FragmentHaberBinding
    private val adapter = HaberAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHaberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HaberViewModel::class.java)
        viewModel.refreshData()

        binding.rvHaber.layoutManager = LinearLayoutManager(context)
        binding.rvHaber.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.rvHaber.visibility = View.GONE
            binding.errorText.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()


        binding.ecoButton.setOnClickListener {
            viewModel.getDataFromAPI("economy","tr")
        }
        binding.sporButton.setOnClickListener {
            viewModel.getDataFromAPI("sport","tr")
        }
        binding.teknoButton.setOnClickListener {
            viewModel.getDataFromAPI("technology","tr")
        }
        viewModel.getDataFromAPI("general","tr")

    }

    private fun observeLiveData(){

        viewModel.haberler.observe(viewLifecycleOwner, Observer { haber ->
            haber?.let {
                binding.rvHaber.visibility = View.VISIBLE
                adapter.updateHaberList(haber)
            }
        })
        viewModel.haberError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it){
                    binding.errorText.visibility = View.VISIBLE
                }else{
                    binding.errorText.visibility = View.INVISIBLE
                }
            }
        })

        viewModel.haberLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvHaber.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

    }



}