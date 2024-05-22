package com.example.gamersgym

import android.app.AlertDialog
import android.content.Context

class CustomDialogueBoxes {
    fun showDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("ПОНЯТНО") { dialog, which ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    fun showYesNoDialog(context: Context, title: String, message: String, onYesClick: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("Подвердить") { dialog, which ->
            onYesClick()
            dialog.dismiss()
        }

        builder.setNegativeButton("Отменить") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}