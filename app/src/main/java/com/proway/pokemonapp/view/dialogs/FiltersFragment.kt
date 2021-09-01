package com.proway.pokemonapp.view.dialogs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.proway.pokemonapp.R
import com.proway.pokemonapp.databinding.FiltersFragmentBinding
import com.proway.pokemonapp.view_model.FiltersViewModel

class FiltersFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = FiltersFragment()
    }

    private lateinit var viewModel: FiltersViewModel
    private lateinit var binding: FiltersFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filters_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FiltersFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(FiltersViewModel::class.java)
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

}