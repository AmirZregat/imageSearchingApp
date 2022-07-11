package com.project.unsplash.ui.images

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.project.unsplash.R
import com.project.unsplash.databinding.FragmentImagesBinding
import com.project.unsplash.models.UnsplashImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImagesFragment : Fragment(), UnsplashImagesAdapter.OnItemClickListener {

    private val viewModel: ImagesViewModel by viewModels()
    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!! //safe call , if _binding is null it will give exception
    private val args by navArgs<ImagesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()
        setHasOptionsMenu(true)

        _binding = FragmentImagesBinding.bind(view)
        val adapter = UnsplashImagesAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashImageLoadStateAdapter { adapter.retry() },
                footer = UnsplashImageLoadStateAdapter { adapter.retry() },
            )
            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.images.observe(viewLifecycleOwner) {
            //this will be triggered whenever the values of images live data changes
            //submitData is a function from paging lib
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        //to get the word from search fragment and search for it
        args.word?.let { viewModel.searchImages(it) }

        //to handle loading state and displaying in ui
        adapter.addLoadStateListener { loadStates ->
            binding.apply {
                progressBar.isVisible = loadStates.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadStates.source.refresh is LoadState.NotLoading
                btnRetry.isVisible =
                    loadStates.source.refresh is LoadState.Error //like no internet connection
                txtvError.isVisible = loadStates.source.refresh is LoadState.Error

                //empty view
                if (loadStates.source.refresh is LoadState.NotLoading && loadStates.append.endOfPaginationReached && adapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    txtvNoResult.isVisible = true
                } else {
                    txtvNoResult.isVisible = false
                }

            }
        }

    }

    //handle the event when user click on the username of the photographer
    override fun onItemClick(image: UnsplashImage) {
        val uri = Uri.parse(image.user.attributionUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context?.startActivity(intent)
    }


    //for the search in the tool bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_images, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    //jump back to the top whenever we execute new search
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchImages(p0)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })


    }

    //when the back button pressed from the tool bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                findNavController().navigate(R.id.action_imagesFragment2_to_searchFragment)
        }
        return true
    }

    //displaying back button in the tool bar
    private fun setupBackButton() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}