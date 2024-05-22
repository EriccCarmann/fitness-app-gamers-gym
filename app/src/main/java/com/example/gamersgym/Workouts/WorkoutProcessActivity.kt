package com.example.gamersgym.Workouts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gamersgym.CustomDialogueBoxes
import com.example.gamersgym.DbHelper
import com.example.gamersgym.R
import com.example.gamersgym.TabsActivity
import java.time.LocalDate
import java.util.Timer
import java.util.TimerTask

class WorkoutProcessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_current_exercise)

        val db = DbHelper(this, null)

        //<editor-fold desc="CURRENT EXERCISES VARIABLES">
        val current_image = findViewById<ImageView>(R.id.current_image) //картинка текущего упражнения
        val currentActivity = findViewById<TextView>(R.id.current_activity) //название текущего упражнения
        val rest = findViewById<TextView>(R.id.rest) //надпись "Отдых"
        val countDownOrReps = findViewById<TextView>(R.id.countdown) //нужное время/повторения
        val nextExercise = findViewById<Button>(R.id.next_exercise)
        val backButton = findViewById<ImageButton>(R.id.back)

        val exercisesScreen = findViewById<LinearLayout>(R.id.exercises)

        val totalTime = findViewById<TextView>(R.id.total_time)
        val totalCalories = findViewById<TextView>(R.id.total_cal)
        val workoutName = findViewById<TextView>(R.id.workout_name)

        val day = intent.getStringExtra("day")
        val type = intent.getStringExtra("type")

        var canProceed = false
        var index = 0
        var burnedCalories = 0.0

        val exerciseNames: List<String> = db.getAllExerciseTags()
        val exerciseImages = mapOf(
            "jump" to R.drawable.jump_in_place,
            "push_support" to R.drawable.push_up_support,
            "push_knee_support" to R.drawable.push_ups_knee_support,
            "push" to R.drawable.push_ups_floor,
            "push_wide" to R.drawable.push_ups_floor_wide_support,
            "sit_up" to R.drawable.sit_ups,
            "lunge" to R.drawable.lunge,
            "alpinist" to R.drawable.alpinist,
            "bulgarian" to R.drawable.bulgarian_sit_ups,
            "cobra" to R.drawable.cobra_strech,
            "chest" to R.drawable.chest_strech_door,
            "crunch" to R.drawable.crunch,
            "swing" to R.drawable.swing,
            "half_laid_crunch" to R.drawable.half_laid_crunch,
            "reverse_crunch" to R.drawable.reverse_crunch,
            "plank" to R.drawable.plank,
            "hips_crunch_half_laid_left" to R.drawable.hips_crunch_half_laid,
            "hips_crunch_half_laid_right" to R.drawable.hips_crunch_half_laid,
            "arms_spinnig" to R.drawable.arms_spinnig,
            "elbow_together" to R.drawable.elbow_together,
            "arms_up" to R.drawable.arms_up,
            "catterpillar" to R.drawable.catterpillar,
            "scissors" to R.drawable.scissors,
            "cat_cow" to R.drawable.cat_cow,
            "extention" to R.drawable.extention,
            "hip_bridge" to R.drawable.hip_bridge,
            "side_plank_right" to R.drawable.side_plank,
            "side_plank_left" to R.drawable.side_plank,
            "infant_pose" to R.drawable.infant_pose
        )
        //</editor-fold>

        //<editor-fold desc="WORKOUT END VARIABLES">
        val endScreen = findViewById<LinearLayout>(R.id.end_screen)
        val finishWorkoutButton = findViewById<Button>(R.id.finish_workout)
        //</editor-fold>

        //<editor-fold desc="GENERAL TIMER VARIABLES">
        val timer = findViewById<TextView>(R.id.timer)
        val stoptimer = findViewById<ImageView>(R.id.stop_timer)
        var count = 1
        var isGoing = true
        val T = Timer()
        //</editor-fold>

        //<editor-fold desc="GENERAL TIMER">
        T.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if(isGoing){
                    runOnUiThread {
                        when(count){
                            in 0..9 -> timer.text = "00:0$count"
                            in 10..59 -> timer.text = "00:$count"
                            in 60..599 -> {
                                val min = count / 60
                                val sec = count % 60

                                if(sec < 10){
                                    timer.text = "0$min:0$sec"
                                }
                                else timer.text = "0$min:$sec"
                            }
                            in 599..3599 -> {
                                val min = count / 60
                                val sec = count % 60
                                if(sec < 10){
                                    timer.text = "$min:0$sec"
                                }
                                else timer.text = "$min:$sec"
                            }
                        }
                        count++
                    }
                }
            }
        }, 1000, 1000)
        //</editor-fold>

        //<editor-fold desc="REST AND JUMP TIMER">
        var tillFinishRest: Long = 11000
        var tillFinishAction: Long = 31000

        var restEnded = false
        var isExercise = false

        fun TimerAction(time: Long){
            canProceed = false
            rest.visibility = (View.INVISIBLE)
            object : CountDownTimer(time, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    if(millisUntilFinished / 1000 < 10){
                        countDownOrReps.setText("00:0" + millisUntilFinished / 1000)
                    }
                    else if(millisUntilFinished / 1000 > 10){
                        countDownOrReps.setText("00:" + millisUntilFinished / 1000)
                    }
                    if(!isGoing && millisUntilFinished / 1000 > 10){
                        countDownOrReps.setText("00:" + (millisUntilFinished + 1000) / 1000)
                        tillFinishAction = millisUntilFinished
                        this.cancel()
                    }
                    else if(!isGoing && millisUntilFinished / 1000 < 10){
                        countDownOrReps.setText("00:0" + (millisUntilFinished + 1000) / 1000)
                        tillFinishAction = millisUntilFinished
                        this.cancel()
                    }
                }
                override fun onFinish() {
                    isExercise = true
                    restEnded = false
                    canProceed = true
                }
            }.start()
        }

        fun TimerRest(time: Long){
            if(tillFinishRest.toInt() == 11000){
                countDownOrReps.setText("00:10")
            }

            object : CountDownTimer(time, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    if(millisUntilFinished / 1000 < 10){
                        countDownOrReps.setText("00:0" + millisUntilFinished / 1000)
                    }
                    else if(millisUntilFinished / 1000 > 10){
                        countDownOrReps.setText("00:" + millisUntilFinished / 1000)
                    }
                    if(!isGoing){
                        countDownOrReps.setText("00:0" + (millisUntilFinished + 1000) / 1000)
                        tillFinishRest = millisUntilFinished
                        this.cancel()
                    }
                }
                override fun onFinish() {
                    restEnded = true
                    tillFinishRest = 11000
                    TimerAction(tillFinishAction)
                }
            }.start()
        }

        TimerRest(tillFinishRest)
        //</editor-fold>

        //<editor-fold desc="CURRENT EXERCISES METHODS">
        fun ResetValues(){
            canProceed = false
            index = 0
            burnedCalories = 0.0
        }

        fun ActionExercise(repsOrTime: Map<String, Int>, name: String, time: Long){
            rest.visibility = (View.INVISIBLE)
            canProceed = true
            if (repsOrTime[name]!! > 1000) { //ЕСЛИ ЭТО УПРАЖНЕНИЕ НА ВРЕМЯ, ТО ТАЙМЕР
                canProceed = false
                object : CountDownTimer(time, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        if (millisUntilFinished / 1000 < 10 && isGoing) {
                            countDownOrReps.setText("00:0" + millisUntilFinished / 1000)
                        }
                        else if(millisUntilFinished / 1000 > 10 && isGoing){
                            countDownOrReps.setText("00:" + millisUntilFinished / 1000)
                        }
                        if(!isGoing && millisUntilFinished / 1000 > 10){
                            countDownOrReps.setText("00:" + (millisUntilFinished + 1000) / 1000)
                            tillFinishAction = millisUntilFinished
                            this.cancel()
                        }
                        else if(!isGoing && millisUntilFinished / 1000 < 10){
                            countDownOrReps.setText("00:0" + (millisUntilFinished + 1000) / 1000)
                            tillFinishAction = millisUntilFinished
                            this.cancel()
                        }
                    }
                    override fun onFinish() {
                        restEnded = false
                        canProceed = true
                    }
                }.start()
            } else countDownOrReps.text = "x" + repsOrTime[name].toString() //ЕСЛИ ЭТО УПРАЖНЕНИЕ НА ПОВТОРЕНИЯ, ТО НАДПИСЬ

        }

        fun RestExercise(repsOrTime: Map<String, Int>, name: String, time: Long){
            if(tillFinishRest.toInt() == 11000){
                countDownOrReps.setText("00:10")
            }

            restEnded = false

            //<editor-fold desc="ОТДЫХ + УПРАЖНЕНИЕ">
            object : CountDownTimer(time, 1000) {
                //<editor-fold desc="ОТДЫХ">
                override fun onTick(millisUntilFinished: Long) {
                    if (millisUntilFinished / 1000 < 10 && isGoing) {
                        countDownOrReps.setText("00:0" + millisUntilFinished / 1000)
                    }
                    else if(millisUntilFinished / 1000 > 10 && isGoing){
                        countDownOrReps.setText("00:" + millisUntilFinished / 1000)
                    }
                    else if(!isGoing){
                        countDownOrReps.setText("00:0" + (millisUntilFinished + 1000) / 1000)
                        tillFinishRest = millisUntilFinished
                        this.cancel()
                    }
                }
                //</editor-fold>

                //<editor-fold desc="УПРАЖНЕНИЕ">
                override fun onFinish() {
                    restEnded = true
                    tillFinishRest = 11000
                    tillFinishAction = repsOrTime[name]!!.toLong()
                    ActionExercise(repsOrTime, name, tillFinishAction)
                }
                //</editor-fold>
            }.start()
            //</editor-fold>
        }

        fun SwitchExercises(exerciseOrder: List<Int>, dayName: String, repsOrTime: Map<String, Int>){
            if(canProceed && index != exerciseOrder.size) {
                canProceed = false

                rest.visibility = (View.VISIBLE) // НАДПИСЬ "ОТДЫХ"

                val exerciseIndex = exerciseOrder[index] //ПОЛУЧЕНИЯ ИНДЕКСОВ УПРАЖНЕНИЙ В НУЖНОМ ПОРЯДКЕ
                val name = exerciseNames[exerciseIndex] //ПОЛУЧЕНИЕ ИМЕНИ УПРАЖНЕНИЯ ПО ИНДЕКСУ

                exerciseImages[name]?.let { current_image.setImageResource(it) } //ПОЛУЧЕИЕ ИЗОБРАЖЕНИЯ ПО ИМЕНИ
                currentActivity.text = db.getExerciseNameByTag(name)//ПОЛУЧЕИЕ ТЕКСТА ПО ИМЕНИ

                burnedCalories += db.getExerciseCalByTag(name)!!.toFloat()//ПОЛУЧЕНИЕ КАЛЛОРИЙ ПО ИМЕНИ

                index++ //СЛЕДУЮЩИЙ ИНДЕКС СПИСКА УПРАЖНЕНИЙ

                stoptimer.setOnClickListener {
                    if(isExercise == true){
                        isGoing = !isGoing

                        if(isGoing){
                            stoptimer.setImageResource(R.drawable.pause)
                            if(!restEnded){
                                RestExercise(repsOrTime, name, tillFinishRest)
                            }
                            else{
                                ActionExercise(repsOrTime, name, tillFinishAction)
                            }
                        }
                        else {
                            stoptimer.setImageResource(R.drawable.play)

                        }
                    }
                }

                RestExercise(repsOrTime, name, tillFinishRest)

            }
            //<editor-fold desc="КОНЕЦ ТРЕНИРОВКИ">
            else if (index == exerciseOrder.size){
                var pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                var editor = pref.edit()
                if(type == "fullbody") editor.putString(dayName, "true")
                editor.apply();
                exercisesScreen.visibility = (View.GONE)

                when(type){
                    "fullbody" -> {
                        workoutName.text = "Тренировка на всё тело"
                        workoutName.setBackgroundResource(R.drawable.fullbody_background)
                    }
                    "fatburning" -> {
                        workoutName.text = "Жиросжигающая тренировка"
                        workoutName.setBackgroundResource(R.drawable.fatburning_workout)
                    }
                }

                endScreen.visibility = (View.VISIBLE)

                val login = pref.getString("USER_LOGIN", "")

                if(pref.getString("TODAY_WORKOUT", null) != LocalDate.now().toString() ||
                    pref.getString("TODAY_WORKOUT", "") != LocalDate.now().toString()){
                    editor.putString("TODAY_WORKOUT", LocalDate.now().toString())
                    editor.apply();
                    db.IncreaseOrFinishAchievement("Halt", login!!, 1)
                    db.ChangeOrFinishTask(login, "DaysWorkingOut", 1)
                }

                if (login != null) {
                    db.InsertOrRewriteCalories(burnedCalories.toFloat(), login) //КАЛЛОРИИ ЗА ТРЕНИРОВКУ
                    db.ChangeOrFinishTask(login, "DoWorkouts", 1)
                    db.ChangeOrFinishTask(login, "DoExer", exerciseOrder.size)
                    db.IncreaseOrFinishAchievement("MoreWorkouts", login, 1)
                    db.IncreaseOrFinishAchievement("Wealth", login, 1)
                }

                totalCalories.text = String.format("%.2f", burnedCalories)
                totalTime.text = timer.text
            }
            //</editor-fold>
        }
        //</editor-fold>

        //<editor-fold desc="WORKOUTS">
        when(type){
            "fullbody" -> {
                when(day){
                    "1" -> {
                        ResetValues()

                        val exerciseOrder: List<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 8, 9 ,10)

                        val dayRepsOrTime = mapOf(
                            "push_support" to 12,
                            "push_knee_support" to 10,
                            "push" to 10,
                            "push_wide" to 10,
                            "sit_up" to 16,
                            "lunge" to 14,
                            "alpinist" to 12,
                            "bulgarian" to 12,
                            "cobra" to 21000,
                            "chest" to 21000
                        )

                        nextExercise.setOnClickListener {
                            SwitchExercises(exerciseOrder, "DAY_TWO_ACCESS", dayRepsOrTime)
                        }
                    }
                    "2" -> {
                        ResetValues()

                        val exerciseOrder: List<Int> = mutableListOf(11, 7, 13, 14, 15, 11, 7, 13, 14, 15, 9, 16, 17)

                        val dayRepsOrTime = mapOf(
                            "crunch" to 12,
                            "alpinist" to 14,
                            "half_laid_crunch" to 16,
                            "reverse_crunch" to 14,
                            "plank" to 31000,
                            "cobra" to 31000,
                            "hips_crunch_half_laid_left" to 31000,
                            "hips_crunch_half_laid_right" to 31000
                        )

                        nextExercise.setOnClickListener {
                            SwitchExercises(exerciseOrder, "DAY_THREE_ACCESS", dayRepsOrTime)
                        }
                    }
                    "3" -> {
                        ResetValues()

                        val exerciseOrder: List<Int> = mutableListOf(18, 19, 20, 1, 21, 16, 17, 22, 19, 20, 1, 23, 15)

                        val dayRepsOrTime = mapOf(
                            "arms_spinnig" to 20,
                            "elbow_together" to 18,
                            "arms_up" to 20,
                            "push_support" to 14,
                            "catterpillar" to 10,
                            "hips_crunch_half_laid_left" to 31000,
                            "hips_crunch_half_laid_right" to 31000,
                            "scissors" to 30,
                            "cat_cow" to 31000,
                            "plank" to 31000
                        )

                        nextExercise.setOnClickListener {
                            SwitchExercises(exerciseOrder, "DAY_FOUR_ACCESS", dayRepsOrTime)
                        }
                    }
                    "4" -> {
                        ResetValues()

                        val exerciseOrder: List<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 8, 9, 24, 10)

                        val dayRepsOrTime = mapOf(
                            "push_support" to 14,
                            "push_knee_support" to 12,
                            "push" to 12,
                            "push_wide" to 12,
                            "sit_up" to 18,
                            "lunge" to 16,
                            "alpinist" to 18,
                            "bulgarian" to 14,
                            "cobra" to 31000,
                            "extention" to 14,
                            "chest" to 31000
                        )

                        nextExercise.setOnClickListener {
                            SwitchExercises(exerciseOrder, "DAY_FIVE_ACCESS", dayRepsOrTime)
                        }
                    }
                    "5" -> {
                        ResetValues()

                        val exerciseOrder: List<Int> = mutableListOf(12, 14, 25, 26, 27, 11, 12, 14, 25, 26, 27, 11, 15, 3, 26, 27)

                        val dayRepsOrTime = mapOf(
                            "swing" to 16,
                            "reverse_crunch" to 16,
                            "hip_bridge" to 16,
                            "side_plank_right" to 31000,
                            "side_plank_left" to 31000,
                            "crunch" to 16,
                            "plank" to 31000,
                            "push" to 18,
                        )

                        nextExercise.setOnClickListener {
                            SwitchExercises(exerciseOrder, "DAY_SIX_ACCESS", dayRepsOrTime)
                        }
                    }
                    "6" -> {
                        ResetValues()

                        val exerciseOrder: List<Int> = mutableListOf(22, 19, 1, 3, 24, 15, 18, 21, 24, 3, 11, 15, 10, 28)

                        val dayRepsOrTime = mapOf(
                            "scissors" to 30,
                            "elbow_together" to 30,
                            "push_support" to 16,
                            "push" to 16,
                            "extention" to 14,
                            "plank" to 41000,
                            "arms_spinnig" to 16,
                            "catterpillar" to 12,
                            "crunch" to 14,
                            "chest" to 31000,
                            "infant_pose" to 31000
                        )

                        nextExercise.setOnClickListener {
                            SwitchExercises(exerciseOrder, "DAY_SEVEN_ACCESS", dayRepsOrTime)
                        }
                    }
                    "7" -> {
                        ResetValues()

                        val exerciseOrder: List<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 8, 9, 10)

                        val dayRepsOrTime = mapOf(
                            "push_support" to 14,
                            "push_knee_support" to 12,
                            "push" to 12,
                            "push_wide" to 12,
                            "sit_up" to 18,
                            "lunge" to 16,
                            "alpinist" to 18,
                            "bulgarian" to 14,
                            "cobra" to 31000,
                            "chest" to 31000
                        )

                        nextExercise.setOnClickListener {
                            SwitchExercises(exerciseOrder, "FULLBODY_DONE", dayRepsOrTime)
                        }
                    }
                }
            }
            "fatburning" ->{
                ResetValues()

                val exerciseOrder: List<Int> = mutableListOf(7, 3, 5, 11, 24, 15, 0, 7, 3, 5, 11, 24, 15, 9, 16, 17)

                val dayRepsOrTime = mapOf(
                    "alpinist" to 24,
                    "push" to 15,
                    "sit_up" to 28,
                    "crunch" to 18,
                    "extention" to 12,
                    "plank" to 31000,
                    "jump" to 46000,
                    "cobra" to 31000,
                    "hips_crunch_half_laid_left" to 31000,
                    "hips_crunch_half_laid_right" to 31000
                )

                nextExercise.setOnClickListener {
                    SwitchExercises(exerciseOrder, "", dayRepsOrTime)
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="BUTTONS">
        stoptimer.setOnClickListener {
            if(isExercise == false){
                isGoing = !isGoing

                if(isGoing){
                    stoptimer.setImageResource(R.drawable.pause)
                    if(!restEnded){
                        TimerRest(tillFinishRest)
                    }
                    else{
                        TimerAction(tillFinishAction)
                    }
                }
                else {
                    stoptimer.setImageResource(R.drawable.play)
                }
            }
        }

        finishWorkoutButton.setOnClickListener {
            when(type){
                "fullbody" -> {
                    val intent = Intent(this, FullBodyWorkoutActivity::class.java)
                    startActivity(intent)
                }
                "fatburning" -> {
                    val intent = Intent(this, TabsActivity::class.java)
                    intent.putExtra("tab", "0")
                    startActivity(intent)
                }
            }
        }

        backButton.setOnClickListener{
            CustomDialogueBoxes().showYesNoDialog(this, "Вы уверены, что хотите вернуться?",
                "Подтверждая, вы потеряете ваш прогресс."
            ) {
                when(type){
                    "fullbody" -> {
                        val intent = Intent(this, FullBodyWorkoutActivity::class.java)
                        startActivity(intent)
                    }
                    "fatburning" ->{
                        val intent = Intent(this, FatburningActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        //</editor-fold>
    }
}