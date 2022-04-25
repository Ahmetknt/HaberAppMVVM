package com.ahmetkanat.habermvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmetkanat.habermvvm.databinding.CardTasarimBinding
import com.ahmetkanat.habermvvm.model.Haber
import com.ahmetkanat.habermvvm.view.HaberFragment
import com.ahmetkanat.habermvvm.view.HaberFragmentDirections
import com.squareup.picasso.Picasso

class HaberAdapter(private var haberList : ArrayList<Haber>) : RecyclerView.Adapter<HaberAdapter.HaberHolder>() {

    class HaberHolder(var binding : CardTasarimBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HaberHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardTasarimBinding.inflate(layoutInflater,parent,false)
        return HaberHolder(binding)
    }

    override fun onBindViewHolder(holder: HaberHolder, position: Int) {
        val haber = haberList[position]

        holder.binding.apply {
            name.text = haber.name
            source.text = haber.source
            Picasso.get().load(haber.image).into(imageView)
        }
        holder.binding.cardView.setOnClickListener {
            val action = HaberFragmentDirections.actionHaberFragmentToDetayFragment(haber.uuid)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return haberList.size
    }

    fun updateHaberList(newHaberList : List<Haber>){
        haberList.clear()
        haberList.addAll(newHaberList)
        notifyDataSetChanged()
    }
}