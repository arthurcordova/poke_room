package com.proway.pokemonapp.view.dialogs

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.proway.pokemonapp.R
import com.proway.pokemonapp.view_model.FiltersViewModel

class FiltersFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = FiltersFragment()
    }

    private lateinit var viewModel: FiltersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filters_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FiltersViewModel::class.java)
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

}