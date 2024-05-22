package com.example.gamersgym.Fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gamersgym.DbHelper
import com.example.gamersgym.Workouts.FullBodyWorkoutActivity
import com.example.gamersgym.R
import com.example.gamersgym.Workouts.FatburningActivity

class Training : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_training, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // do your variables initialisations here except Views!!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullbodyButton = getTextViewByTag(R.id.fullbody_button)
        val fatburningButton = getTextViewByTag(R.id.fatburning_button)

        val fullbodyTextTime = getTextViewByTag(R.id.fullbody_text_time)
        val fatburningTextTime = getTextViewByTag(R.id.fatburning_text_time)

        fullbodyButton?.setOnClickListener {
            val intent = Intent(context, FullBodyWorkoutActivity::class.java)
            startActivity(intent)
        }

        fatburningButton?.setOnClickListener {
            val intent = Intent(context, FatburningActivity::class.java)
            startActivity(intent)
        }

        fullbodyTextTime?.text= Html.fromHtml(
             "<font color=${Color.BLACK}>Продолжительность<br></font>" +
                    "<font color=${Color.BLACK}>тренировки </font>" +
                    "<font color=#338AF3>10 минут</font>"
        )

        fatburningTextTime?.text= Html.fromHtml(
             "<font color=${Color.BLACK}>Продолжительность<br></font>" +
                    "<font color=${Color.BLACK}>тренировки </font>" +
                    "<font color=#338AF3>10 минут</font>"
        )
    }

    fun getTextViewByTag(tag: Int): TextView?{
        val resourceId = resources.getIdentifier(tag.toString(), "id", context?.packageName)
        return view?.findViewById(resourceId)
    }
}