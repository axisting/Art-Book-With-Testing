package com.axisting.artbookwithtesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.axisting.artbookwithtesting.adapter.ArtRecyclerAdapter
import com.axisting.artbookwithtesting.adapter.ImageRecyclerAdapter
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
     val artRecyclerAdapter: ArtRecyclerAdapter,
     val glide : RequestManager,
     val imageRecyclerAdapter: ImageRecyclerAdapter

    ) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            ArtsFragment::class.java.name -> ArtsFragment(artRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }


}