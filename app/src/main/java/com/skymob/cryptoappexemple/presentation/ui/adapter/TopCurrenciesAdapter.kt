package com.skymob.cryptoappexemple.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skymob.cryptoappexemple.R
import com.skymob.cryptoappexemple.databinding.ItemTopCurrenciesBinding
import com.skymob.cryptoappexemple.domain.remote.model.Crypto
import kotlin.math.roundToInt

class TopCurrenciesAdapter(
    private val onItemClick: (Crypto) -> Unit
) : ListAdapter<Crypto, TopCurrenciesAdapter.CryptoViewHolder>(CryptoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = ItemTopCurrenciesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class CryptoViewHolder(
        private val binding: ItemTopCurrenciesBinding,
        private val onItemClick: (Crypto) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(item: Crypto) {
            with(binding) {
                Glide.with(imageView.context)
                    .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${item.id}.png")
                    /* .placeholder(R.drawable.placeholder) // Placeholder opcional
                     .error(R.drawable.error_image)       // Imagem de erro opcional*/
                    .into(imageView)

                txtName.text = item.name
                txtdescription.text = item.symbol

                // Formata o valor do preço
                val value = (((item.price * 100).roundToInt()) / 100.0)
                itemValue.text = "$value $"

                // Formata o valor da variação percentual
                val percent = (((item.percentChange24h * 100).roundToInt()) / 100.0)
                val textColor = if (percent >= 0) {
                    ContextCompat.getColor(itemView.context, R.color.green)
                } else {
                    ContextCompat.getColor(itemView.context, R.color.red)
                }
                itemPercent.setTextColor(textColor)
                itemPercent.text = "$percent%"

                root.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }
    class CryptoDiffCallback : DiffUtil.ItemCallback<Crypto>() {
        override fun areItemsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
            // Compara se são o mesmo item pelo ID
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
            // Compara se o conteúdo do item é o mesmo
            return oldItem == newItem
        }
    }
}