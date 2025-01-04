package com.skymob.cryptoappexemple.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skymob.cryptoappexemple.R

class LoadingDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate o layout personalizado
        return inflater.inflate(R.layout.layout_carregamento, container, false)
    }

    override fun onStart() {
        super.onStart()
        // Torna o fundo transparente e desativa o clique fora
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        isCancelable = false
    }
}