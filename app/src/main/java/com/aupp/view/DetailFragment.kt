package com.aupp.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.aupp.R
import com.aupp.databinding.FragmentDetailBinding
import com.aupp.model.DogPalette
import com.aupp.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class DetailFragment : Fragment() {

    lateinit var viewModel: DetailViewModel

    private var uid = 0

    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        arguments?.let {
            uid = DetailFragmentArgs.fromBundle(it).uuid
        }
        viewModel.getDog(uid)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dog.observe(viewLifecycleOwner, Observer { dog ->
            dataBinding.dog = dog
            dog.imageUrl?.let {
                setupBackgroundColor(it)
            }
        })
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this).asBitmap().load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate { palette ->
                        val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                        val myPalette = DogPalette(intColor)
                        dataBinding.palette = myPalette
                    }
                }
            })
    }
}
