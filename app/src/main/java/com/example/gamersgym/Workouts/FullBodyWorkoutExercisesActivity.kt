package com.example.gamersgym.Workouts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gamersgym.R

class FullBodyWorkoutExercisesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_body_workout_exercises)

        val backToWorkout = findViewById<ImageButton>(R.id.back_to_workout)

        val day1 = findViewById<LinearLayout>(R.id.day1)
        val day2 = findViewById<LinearLayout>(R.id.day2)
        val day3 = findViewById<LinearLayout>(R.id.day3)
        val day4 = findViewById<LinearLayout>(R.id.day4)
        val day5 = findViewById<LinearLayout>(R.id.day5)
        val day6 = findViewById<LinearLayout>(R.id.day6)
        val day7 = findViewById<LinearLayout>(R.id.day7)
        val fatburning = findViewById<LinearLayout>(R.id.fatburning)

        val start_workout = findViewById<Button>(R.id.start_workout)

        val days: List<LinearLayout> = mutableListOf(day1, day2, day3, day4, day5, day6, day7)

        for (i in 0..6){
            days[i].visibility = (View.GONE)
        }

        fatburning.visibility = (View.GONE)

        val day = intent.getStringExtra("day")
        val type = intent.getStringExtra("type")

        when(type){
            "fullbody" -> {
                when (day) {
                    "1" -> {
                        day1.visibility = (View.VISIBLE)
                    }
                    "2" -> {
                        day2.visibility = (View.VISIBLE)
                    }
                    "3" -> {
                        day3.visibility = (View.VISIBLE)
                    }
                    "4" -> {
                        day4.visibility = (View.VISIBLE)
                    }
                    "5" -> {
                        day5.visibility = (View.VISIBLE)
                    }
                    "6" -> {
                        day6.visibility = (View.VISIBLE)
                    }
                    "7" -> {
                        day7.visibility = (View.VISIBLE)
                    }
                }
            }
            "fatburning" -> fatburning.visibility = (View.VISIBLE)
        }

        backToWorkout.setOnClickListener{
            when(type){
                "fullbody" -> {
                    val intent = Intent(this, FullBodyWorkoutActivity::class.java)
                    startActivity(intent)
                }
                "fatburning" -> {
                    val intent = Intent(this, FatburningActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        start_workout.setOnClickListener{
            when(type){
                "fullbody" -> {
                    val intent = Intent(this, WorkoutProcessActivity::class.java)
                    intent.putExtra("day", day.toString())
                    intent.putExtra("type", "fullbody")
                    startActivity(intent)
                }
                "fatburning" -> {
                    val intent = Intent(this, WorkoutProcessActivity::class.java)
                    intent.putExtra("type", "fatburning")
                    startActivity(intent)
                }
            }
        }
    }
}