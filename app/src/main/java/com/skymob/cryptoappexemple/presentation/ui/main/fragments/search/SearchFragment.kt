package com.skymob.cryptoappexemple.presentation.ui.main.fragments.search


import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.skymob.cryptoappexemple.databinding.FragmentSearchBinding
import com.skymob.cryptoappexemple.presentation.ui.adapter.TopCurrenciesAdapter
import com.skymob.cryptoappexemple.presentation.ui.details.DetailsActivity
import com.skymob.cryptoappexemple.presentation.ui.main.fragments.base.BaseFragment
import com.skymob.cryptoappexemple.presentation.ui.state.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    override val viewModel: SearchViewModel by viewModel()
    private lateinit var adapterSearch: TopCurrenciesAdapter


    override fun setupViews() {
        initOnClick()
        initRecyclerView()

    }

    override fun observeViewModel() {
        viewModel.searchResults.observe(viewLifecycleOwner) { state ->
            when (state) {
                UiState.Empty -> {
                    showProgressBar(false)
                    binding.txtEmpty.visibility = View.VISIBLE
                }
                is UiState.Error -> {
                    showProgressBar(false)
                }
                UiState.Loading -> {
                    showProgressBar(true)
                }
                is UiState.Success -> {
                    showProgressBar(false)
                    adapterSearch.submitList(state.data)
                }
            }
        }

    }

    private fun showProgressBar(b: Boolean) {
        binding.progressSearch.visibility = if (b) View.VISIBLE else View.GONE
    }

    private fun initRecyclerView() {
        adapterSearch = TopCurrenciesAdapter{
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            intent.putExtra("Crypto_ID", it.id.toString())
            startActivity(intent)
        }
        binding.rvSearch.adapter = adapterSearch
        binding.rvSearch.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun initOnClick() {

        with(binding) {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Lógica para submissão da busca (opcional)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!newText.isNullOrEmpty()) {
                        viewModel.updateSearchQuery(newText)
                        binding.txtEmpty.visibility = View.GONE
                    }else{
                        adapterSearch.submitList(emptyList())
                    }

                    return true
                }
            }) // Fechando o método setOnQueryTextListener
        }
    }

    override fun onResume() {
        super.onResume()
        clearSearchView()
        hideKeyboard()

    }

    private fun clearSearchView() {
        binding.search.setQuery("", false) // Limpa o texto
        binding.search.clearFocus()       // Remove o foco do SearchView
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = requireActivity().currentFocus
        if (currentFocusView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
}