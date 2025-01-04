

package com.skymob.cryptoappexemple.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skymob.cryptoappexemple.databinding.ItemFavoritesBinding
import com.skymob.cryptoappexemple.domain.local.model.CryptoFavorite

class FavoriteAdapter(
    private val onItemClick: (CryptoFavorite) -> Unit,
    private val onDeleteClick: (CryptoFavorite) -> Unit
) : ListAdapter<CryptoFavorite, FavoriteAdapter.CryptoViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = ItemFavoritesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoViewHolder(binding, onItemClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CryptoViewHolder(
        private val binding: ItemFavoritesBinding,
        private val onItemClick: (CryptoFavorite) -> Unit,
        private val onDeleteClick: (CryptoFavorite) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CryptoFavorite) {
            with(binding) {
                // Carrega a imagem usando Glide
                Glide.with(imgCryptoFavorite.context)
                    .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${item.id}.png")
                    .into(imgCryptoFavorite)

                // Define os textos
                txtNameFavorite.text = item.name
                txtDescriptionFavorite.text = item.symbol

                // Define ação ao clicar no botão de delete
                btnDelete.setOnClickListener {
                    onDeleteClick(item)
                }

                // Define ação ao clicar no item
                root.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    // Callback do DiffUtil para comparar os itens
    class FavoriteDiffCallback : DiffUtil.ItemCallback<CryptoFavorite>() {
        override fun areItemsTheSame(oldItem: CryptoFavorite, newItem: CryptoFavorite): Boolean {
            // Compara se os IDs são iguais
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoFavorite, newItem: CryptoFavorite): Boolean {
            // Compara se o conteúdo dos itens é igual
            return oldItem == newItem
        }
    }
}