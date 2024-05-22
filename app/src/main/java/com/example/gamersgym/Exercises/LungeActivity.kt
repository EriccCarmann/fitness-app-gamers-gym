package com.example.gamersgym.Exercises

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gamersgym.R
import com.example.gamersgym.TabsActivity

class LungeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lunge)

        val calBurn  = findViewById<TextView>(R.id.cal_burn)
        val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity)
        val highIntensivity  = findViewById<TextView>(R.id.high_intensivity)
        val backButton  = findViewById<ImageView>(R.id.back_to_tabs)

        backButton.setOnClickListener{
            val intent = Intent(this, TabsActivity::class.java)
            intent.putExtra("tab", "1")
            startActivity(intent)
        }

        calBurn.text= Html.fromHtml(
            "<font color=${Color.BLACK}>За </font>" +
                    "<font color=#338AF3>12 повторений </font>"+
                    "<font color=${Color.BLACK}>вы потратите<br></font>" +
                    "<font color=${Color.BLACK}>примерно (ккал):</font>"
        )

        lowIntensivity.text= Html.fromHtml(
            "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 1.7 до 2.8</font>"
        )

        highIntensivity.text= Html.fromHtml(
            "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 2.5 до 5</font>"
        )
    }
}