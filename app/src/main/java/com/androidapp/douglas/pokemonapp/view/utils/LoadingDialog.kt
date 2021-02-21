package com.example.pokemonapp.view.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.LoadingLayoutBinding


/**
 * Gets the dialog.
 */
fun getDialog(context: Context?): Dialog {
    val dialogBinding =
        LoadingLayoutBinding.inflate(LayoutInflater.from(context), null, false)


    return Dialog(context as Activity, R.style.LoadingTheme).apply {
        setContentView(dialogBinding.root)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}