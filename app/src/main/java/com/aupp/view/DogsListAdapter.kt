package com.aupp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aupp.R
import com.aupp.model.DogBreed
import kotlinx.android.synthetic.main.item_list.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreed>) : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {

    fun updateDogList(newDogsList: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = dogsList[position]
        with(holder.view) {
            dogBreed.text = dog.dogBreed
            dogLifespan.text = dog.lifeSpan

            setOnClickListener {
                Navigation.findNavController(it).navigate(ListFragmentDirections.actionDetailFragment())
            }
        }
    }

    class DogViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}