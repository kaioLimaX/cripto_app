package com.skymob.cryptoappexemple.presentation.ui.main.fragments.favorite

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.skymob.cryptoappexemple.databinding.FragmentFavoriteBinding
import com.skymob.cryptoappexemple.presentation.ui.adapter.CurrenciesAdapter
import com.skymob.cryptoappexemple.presentation.ui.adapter.FavoriteAdapter
import com.skymob.cryptoappexemple.presentation.ui.details.DetailsActivity
import com.skymob.cryptoappexemple.presentation.ui.main.fragments.base.BaseFragment
import com.skymob.cryptoappexemple.presentation.ui.state.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    override val viewModel: FavoriteViewModel by viewModel()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun setupViews() {
        initRecyclerView()

    }

    private fun initRecyclerView() {
        favoriteAdapter = FavoriteAdapter(
            onItemClick = {
                val intent = Intent(requireContext(), DetailsActivity::class.java)
                intent.putExtra("Crypto_ID", it.id.toString())
                startActivity(intent)
            },
            onDeleteClick = {
                viewModel.deleteFavorite(it)
            }
        )

        binding.rvFavorites.adapter = favoriteAdapter
        binding.rvFavorites.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    override fun observeViewModel() {
        viewModel.cryptoListsFavorites.observe(viewLifecycleOwner) { state ->
            when (state) {
                UiState.Empty -> {
                    showProgress(false)

                }

                is UiState.Error -> {
                    showProgress(false)
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }

                UiState.Loading -> {
                    showProgress(true)

                }

                is UiState.Success -> {
                    showProgress(false)
                    favoriteAdapter.submitList(state.data)
                    Log.i("info_favorites", "${state.data} ")
                }
            }
        }

    }


    private fun showProgress(b: Boolean) {
        binding.progressFavorite.visibility = if (b) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadList()

    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
}