package com.example.pokemonapp.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.example.pokemonapp.R

class LoadingDialog {
    fun getDialog(context: Context?): Dialog {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.loading_layout, null)


        return Dialog(context, R.style.LoadingTheme).apply {
            setContentView(view)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }
}