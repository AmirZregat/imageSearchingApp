package com.project.unsplash.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.unsplash.R
import com.project.unsplash.databinding.FragmentImagesBinding
import com.project.unsplash.databinding.FragmentSearchBinding
import androidx.navigation.fragment.findNavController
import com.project.unsplash.util.toast


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!! //safe call , if _binding is null it will give exception
    private lateinit var word: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view)
        binding.apply {
            buttonSearch.setOnClickListener {
                word = txtvSearchImage.text.toString()
                if (word.isNotEmpty()) {
                    val action =
                        SearchFragmentDirections.actionSearchFragmentToImagesFragment2(word) //auto generated by safe args by navigation componant
                    findNavController().navigate(action)
                } else
                    toast("Please enter a word!")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBackButton()
    }

    //hide back button from the tool bar
    private fun hideBackButton() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }


    }
}
