package com.skymob.cryptoappexemple.presentation.ui.details

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.skymob.cryptoappexemple.R
import com.skymob.cryptoappexemple.databinding.ActivityDetailsBinding
import com.skymob.cryptoappexemple.domain.remote.model.Crypto
import com.skymob.cryptoappexemple.presentation.ui.state.UiState
import com.skymob.cryptoappexemple.presentation.util.formatAsShortScale
import com.skymob.cryptoappexemple.presentation.util.roundToTwoDecimalPlaces
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailsViewModel by viewModel()

    private var crypto : Crypto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cryptoId = intent.getStringExtra("Crypto_ID")
        if (cryptoId != null) {
            viewModel.getInfo(cryptoId)
        }



        initView()
        initObserver()

        setContentView(binding.root)

    }

    private fun initObserver() {
        viewModel.isFavorite.observe(this) { isFavorite ->
            if (isFavorite) {
                binding.fabFavorite.imageTintList = ContextCompat.getColorStateList(this, R.color.red)
            }
        }
        viewModel.cryptoInfo.observe(this) { state ->
            when (state) {
                UiState.Empty -> Unit
                is UiState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }

                UiState.Loading -> Unit
                is UiState.Success -> {
                    loadDataInto(state.data)
                    viewModel.checkIfFavorite(state.data.id.toString())
                    crypto = state.data

                }
            }

        }
        viewModel.addFavoriteStatus.observe(this){state->
            when(state){
                UiState.Empty -> Unit
                is UiState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                UiState.Loading -> Unit
                is UiState.Success -> {
                    Toast.makeText(this, "Crypto added to favorites", Toast.LENGTH_SHORT).show()
                    binding.fabFavorite.imageTintList = ContextCompat.getColorStateList(this, R.color.red)
                }
            }


        }
    }

    private fun loadDataInto(data: Crypto) {
        with(binding) {
            Glide.with(this@DetailsActivity)
                .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${data.id}.png")
                .error(R.drawable.error_image)
                .into(imgCryptoDetails)

            txtNameDetails.text = "${data.name} - ${data.symbol}"
            txtPriceDetails.text = "${data.price.roundToTwoDecimalPlaces()} $"
            txtMarketCap.text = "MarketCap: $${data.marketCap.formatAsShortScale()}"
            txtVolume.text = "volume at 24h: $${data.volume24h.formatAsShortScale()}"
            val percent = data.percentChange24h.roundToTwoDecimalPlaces()
            var textColor: Int
            val textPercent: String

            if (percent >= 0) {
                textColor = ContextCompat.getColor(this@DetailsActivity, R.color.green)
                textPercent = "+$percent%"
            } else {
                textColor = ContextCompat.getColor(this@DetailsActivity, R.color.red)
                textPercent = "$percent%"
            }

            txtPercentDetails.setTextColor(textColor)
            txtPercentDetails.text = textPercent
        }
    }

    private fun initView() {
        setToolbar()
        initOnclick()

    }

    private fun initOnclick() {
        with(binding){
            fabFavorite.setOnClickListener {
                if (crypto != null) {
                    viewModel.addFavorite(crypto!!)
                } else {
                    Toast.makeText(this@DetailsActivity, "Crypto is null", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Details"


        binding.toolbarDetails.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}