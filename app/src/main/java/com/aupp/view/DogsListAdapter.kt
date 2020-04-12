package com.aupp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aupp.R
import com.aupp.databinding.ItemListBinding
import com.aupp.model.DogBreed

class DogsListAdapter(val dogsList: ArrayList<DogBreed>) : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {

    fun updateDogList(newDogsList: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemListBinding>(inflater, R.layout.item_list, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.dog = dogsList[position]
        // val dog = dogsList[position]
        // with(holder.view) {
        //     dogBreed.text = dog.dogBreed
        //     dogLifespan.text = dog.lifeSpan
        //     imageView.loadImage(dog.imageUrl)
        //
        //     setOnClickListener {
        //         val action = ListFragmentDirections.actionDetailFragment()
        //         action.uuid = dog.uuid
        //         Navigation.findNavController(it).navigate(action)
        //     }
        // }
    }

    class DogViewHolder(var view: ItemListBinding) : RecyclerView.ViewHolder(view.root)
}