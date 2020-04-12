package com.aupp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aupp.R
import com.aupp.databinding.ItemListBinding
import com.aupp.model.DogBreed

class DogsListAdapter(
    val dogsList: ArrayList<DogBreed>
) : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(),
    DogClickListener {
    var view: View? = null

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
        with(holder.view) {
            dog = dogsList[position]
            clickListener = this@DogsListAdapter
        }
    }

    class DogViewHolder(var view: ItemListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onDogClicked(dog: DogBreed, view: View) {
        val action = ListFragmentDirections.actionDetailFragment()
        action.uuid = dog.uuid
        Navigation.findNavController(view).navigate(action)
    }
}