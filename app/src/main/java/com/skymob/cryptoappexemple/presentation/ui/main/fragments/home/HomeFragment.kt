package com.skymob.cryptoappexemple.presentation.ui.main.fragments.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.skymob.cryptoappexemple.databinding.FragmentHomeBinding
import com.skymob.cryptoappexemple.presentation.ui.adapter.CurrenciesAdapter
import com.skymob.cryptoappexemple.presentation.ui.adapter.LosersAdapter
import com.skymob.cryptoappexemple.presentation.ui.adapter.VolumeAdapter
import com.skymob.cryptoappexemple.presentation.ui.adapter.FavoriteAdapter
import com.skymob.cryptoappexemple.presentation.ui.adapter.TopCurrenciesAdapter
import com.skymob.cryptoappexemple.presentation.ui.details.DetailsActivity
import com.skymob.cryptoappexemple.presentation.ui.main.fragments.base.BaseFragment
import com.skymob.cryptoappexemple.presentation.ui.state.UiState
import com.skymob.cryptoappexemple.presentation.util.LoadingDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModel()

    private lateinit var currenciesAadapter: CurrenciesAdapter
    private lateinit var adapterLosers: LosersAdapter
    private lateinit var adapterVolumeCurrencies: VolumeAdapter
    private lateinit var adapterTopCurrencies: TopCurrenciesAdapter

    private val alertLoading by lazy {
        LoadingDialogFragment()
    }


    override fun setupViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        currenciesAadapter = CurrenciesAdapter{
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            intent.putExtra("Crypto_ID", it.id.toString())
            startActivity(intent)
        }
        binding.rvCurrencies.adapter = currenciesAadapter
        binding.rvCurrencies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapterLosers = LosersAdapter{
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            intent.putExtra("Crypto_ID", it.id.toString())
            startActivity(intent)
        }
        binding.rvTopLosers.adapter = adapterLosers
        binding.rvTopLosers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapterVolumeCurrencies = VolumeAdapter{
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            intent.putExtra("Crypto_ID", it.id.toString())
            startActivity(intent)
        }
        binding.rvVolumeCurrencies.adapter = adapterVolumeCurrencies
        binding.rvVolumeCurrencies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapterTopCurrencies = TopCurrenciesAdapter{
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            intent.putExtra("Crypto_ID", it.id.toString())
            startActivity(intent)
        }
        binding.rvTopCurrencies.adapter = adapterTopCurrencies
        binding.rvTopCurrencies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun observeViewModel() {
        viewModel.cryptoLists.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    alertLoading.show(parentFragmentManager, "loading")
                }

                UiState.Empty -> Unit
                is UiState.Error -> {
                    alertLoading.dismiss()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    alertLoading.dismiss()
                    currenciesAadapter.addItems(state.data.gainers)
                    adapterLosers.addItems(state.data.losers)
                    adapterVolumeCurrencies.addItems(state.data.volumeCurrencies)
                    adapterTopCurrencies.submitList(state.data.topCurrencies)
                    binding.main.visibility = View.VISIBLE
                }
            }

        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}