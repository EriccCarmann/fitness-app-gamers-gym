package com.example.gamersgym.Workouts

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gamersgym.R
import com.example.gamersgym.TabsActivity

class FatburningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fatburning)

        //<editor-fold desc="VARIABLES">
        val firstText = findViewById<TextView>(R.id.text1)
        val secondText = findViewById<TextView>(R.id.text2)
        val thirdText = findViewById<TextView>(R.id.text3)

        val backButton  = findViewById<ImageView>(R.id.back_to_tabs)

        val startWorkout = findViewById<Button>(R.id.start_workout)
        //</editor-fold>

        //<editor-fold desc="BUTTONS">
        startWorkout.setOnClickListener {
            val intent = Intent(this, FullBodyWorkoutExercisesActivity::class.java)
            intent.putExtra("type", "fatburning")
            startActivity(intent)
        }

        backButton.setOnClickListener{
            val intent = Intent(this, TabsActivity::class.java)
            intent.putExtra("tab", "0")
            startActivity(intent)
        }
        //</editor-fold>

        //<editor-fold desc="HTML">
        firstText.text= Html.fromHtml(
            "<font color=${Color.BLACK}>Благодаря этой тренировке, вы<br></font>" +
                    "<font color=${Color.BLACK}>сможете </font>" +
                    "<font color=#338AF3>сжечь большое количество<br></font>" +
                    "<font color=#338AF3>жира, развить свою выносливость </font>" +
                    "<font color=${Color.BLACK}>и<br></font>" +
                    "<font color=#338AF3>поднять общий тонус организма</font>" +
                    "<font color=${Color.BLACK}>!</font>"
        )

        secondText.text= Html.fromHtml(
            "<font color=#338AF3>Крайне рекомендуется </font>" +
                    "<font color=${Color.BLACK}>для людей,<br></font>" +
                    "<font color=${Color.BLACK}>прошедших недельный план<br></font>" +
                    "<font color=${Color.BLACK}>тренировок, так как данная тренировка<br></font>" +
                    "<font color=${Color.BLACK}>подрузамевает </font>" +
                    "<font color=#338AF3>высокий тем</font>"
        )

        thirdText.text= Html.fromHtml(
            "<font color=${Color.BLACK}>Средняя продолжительность<br></font>" +
                    "<font color=${Color.BLACK}>тренировки </font>" +
                    "<font color=#338AF3>10 минут</font>"
        )
        //</editor-fold>
    }
}