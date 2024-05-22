package com.example.gamersgym.Workouts

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gamersgym.R
import com.example.gamersgym.TabsActivity
import com.example.gamersgym.CustomDialogueBoxes

class FullBodyWorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_body_workout)

        //<editor-fold desc="ПЕРЕМЕННЫЕ">
        val secondText = findViewById<TextView>(R.id.text2)
        val thirdText = findViewById<TextView>(R.id.text3)

        val backButton  = findViewById<ImageView>(R.id.back_to_tabs)

        val startWorkout = findViewById<Button>(R.id.start_workout)
        val resetProgress = findViewById<Button>(R.id.reset_progress)

        val sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE)?: return

        var dayIsChosen = false
        //</editor-fold>

        fun ChooseDay(day: TextView, days: List<TextView>, index: Int): Int{

            val params = day.layoutParams

            //ПРОВЕРКА РАЗМЕРА НЫНЕШНЕЙ КНОПКИ
            if(params.height == 80){
                params.height = 110
                params.width = 110
                day.layoutParams = params

                day.textSize = 25f

                dayIsChosen = true
            }
            else{
                params.height = 80
                params.width = 80
                day.layoutParams = params

                day.textSize = 15f

                dayIsChosen = false

                return 0
            }
            //ПРОВЕРКА РАЗМЕРА НЫНЕШНЕЙ КНОПКИ

            //УМЕНЬШЕНИЕ ОСТАЛЬНЫХ КНОПОК
            for(i in 0..6){
                if(days[i] != day){
                    val params = days[i].layoutParams
                    params.height = 80
                    params.width = 80
                    days[i].layoutParams = params

                    days[i].textSize = 15f
                }
            }
            //УМЕНЬШЕНИЕ ОСТАЛЬНЫХ КНОПОК

            return index
        }

        //<editor-fold desc="ДНИ ПОЛУЧЕНИЕ">
        val day1 = findViewById<TextView>(R.id.day1)
        val day2 = findViewById<TextView>(R.id.day2)
        val day3 = findViewById<TextView>(R.id.day3)
        val day4 = findViewById<TextView>(R.id.day4)
        val day5 = findViewById<TextView>(R.id.day5)
        val day6 = findViewById<TextView>(R.id.day6)
        val day7 = findViewById<TextView>(R.id.day7)

        val days: List<TextView> = mutableListOf(day1, day2, day3, day4, day5, day6, day7)
        //</editor-fold>

        //<editor-fold desc="ДОСТУПНЫ ЛИ СЛЕДУЮЩИЕ ДНИ">
        val daysAccess: List<String> = mutableListOf("DAY_ONE_ACCESS", "DAY_TWO_ACCESS", "DAY_THREE_ACCESS", "DAY_FOUR_ACCESS",
            "DAY_FIVE_ACCESS", "DAY_SIX_ACCESS", "DAY_SEVEN_ACCESS")

        var selectedDay = 0

        days[0].setOnClickListener {
            selectedDay = ChooseDay(days[0], days, 0)
        }

        for(i in 1..6){
            if(sharedPreferences.contains(daysAccess[i]) &&
                sharedPreferences.getString(daysAccess[i], "false") == "true"){
                days[i].background = resources.getDrawable(R.drawable.circle_background_active)

                days[i].setOnClickListener {
                    selectedDay = ChooseDay(days[i], days,  i)
                }
            }else{
                days[i].setOnClickListener {
                    CustomDialogueBoxes().showDialog(this, "Тренировка недоступна",
                        "Для получения доступа к ней, пройдите предыдущие тренировки")
                }
            }
        }
        //</editor-fold>

        startWorkout.setOnClickListener {
            if(dayIsChosen){
                val intent = Intent(this, FullBodyWorkoutExercisesActivity::class.java)
                intent.putExtra("day", (selectedDay + 1).toString())
                intent.putExtra("type", "fullbody")
                startActivity(intent)
            }
            else {
                CustomDialogueBoxes().showDialog(this, "Вы не выбрали день тренировки",
                    "Выберите интересующий вас день для тренировки")
            }
        }

        resetProgress.setOnClickListener {
            CustomDialogueBoxes().showYesNoDialog(this, "Вы уверены, что хотите сбросить прогресс?",
                "Подтверждая, вы сбросите и начнёте сначала ваш прогрес."
            ) {
                var pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                var editor = pref.edit()
                editor.putString("DAY_TWO_ACCESS", "false")
                editor.apply()

                for (i in 1..6) {
                    days[i].background =
                        resources.getDrawable(R.drawable.circle_background_inactive)
                    days[i].setOnClickListener {
                        CustomDialogueBoxes().showDialog(
                            this, "Тренировка недоступна",
                            "Для получения доступа к ней, пройдите предыдущие тренировки"
                        )
                    }
                }
            }
        }

        backButton.setOnClickListener{
            val intent = Intent(this, TabsActivity::class.java)
            intent.putExtra("tab", "0")
            startActivity(intent)
        }

        //<editor-fold desc="HTML">
        secondText.text= Html.fromHtml(
                    "<font color=#338AF3>Крайне рекомендуется </font>" +
                    "<font color=${Color.BLACK}>тем, кто долгое<br></font>" +
                    "<font color=${Color.BLACK}>время не занимался спортом</font>"
        )

        thirdText.text= Html.fromHtml(
                    "<font color=${Color.BLACK}>Средняя продолжительность<br></font>" +
                    "<font color=${Color.BLACK}>тренировки </font>" +
                    "<font color=#338AF3>10 минут</font>"
        )
        //</editor-fold>

    }
}
