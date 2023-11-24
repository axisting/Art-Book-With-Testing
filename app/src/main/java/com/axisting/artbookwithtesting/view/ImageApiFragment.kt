package com.axisting.artbookwithtesting.view

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.axisting.artbookwithtesting.R
import com.axisting.artbookwithtesting.adapter.ImageRecyclerAdapter
import com.axisting.artbookwithtesting.databinding.FragmentImageApiBinding
import com.axisting.artbookwithtesting.util.Resource
import com.axisting.artbookwithtesting.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    val imageRecyclerAdapter : ImageRecyclerAdapter

) : Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel : ArtViewModel

    private var fragmentBinding : FragmentImageApiBinding ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        var job :Job ?= null
        binding.searchEditText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())

                    }
                }
            }
        }

        imageRecyclerAdapter.setOnItemClickListener {
            viewModel.setSelectedImage(it)
            findNavController().popBackStack()
        }
    }

    fun subscribeToObservers(){
        viewModel.imageList.observe(viewLifecycleOwner , Observer {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    fragmentBinding?.progressBar?.visibility = View.GONE
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL

                    }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    fragmentBinding?.progressBar?.visibility = View.GONE

                }
                Resource.Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    fragmentBinding?.progressBar?.visibility = View.GONE
                    Toast.makeText(requireContext() , it.message ?: "Error" , Toast.LENGTH_SHORT).show()

                }

            }
        })

    }

}