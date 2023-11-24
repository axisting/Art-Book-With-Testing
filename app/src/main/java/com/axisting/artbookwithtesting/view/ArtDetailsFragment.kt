package com.axisting.artbookwithtesting.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.axisting.artbookwithtesting.R
import com.axisting.artbookwithtesting.databinding.FragmentArtDetailsBinding
import com.axisting.artbookwithtesting.util.Resource
import com.axisting.artbookwithtesting.viewmodel.ArtViewModel
import com.bumptech.glide.RequestManager
import javax.inject.Inject


public class ArtDetailsFragment
    @Inject
    constructor( val glide : RequestManager): Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding : FragmentArtDetailsBinding? = null

    lateinit var viewModel : ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)



        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObserves()
        binding.artImageView.setOnClickListener(){
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener(){
            viewModel.makeArt(binding.artNameEditText.text.toString() , binding.artistNameEditText.text.toString() , binding.artYearEditText.text.toString())

        }


    }


    private fun subscribeToObserves(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner , Observer {url->
            fragmentBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner , Observer {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(requireContext() , "Success" , Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMessage()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext() , it.message ?: "Error" , Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {

                }
            }
        })
    }
    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }

}