package com.example.gamersgym

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.time.LocalDate
import java.util.Calendar
import java.util.GregorianCalendar

class BiometricsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_biometrics)

        val userWeight: EditText = findViewById(R.id.user_weight)
        val userHeight: EditText = findViewById(R.id.user_height)

        val weightHint: TextView = findViewById(R.id.weight_hint)
        val heightHint: TextView = findViewById(R.id.height_hint)

        val radioGroup: RadioGroup = findViewById(R.id.radio_group)
        val button: Button = findViewById(R.id.button_biomerics_add)

        //<editor-fold desc="CHANGE TEXT AND HINTS IF INPUT IS INCORRECT">
        userWeight.addTextChangedListener{
            userWeight.setTextColor(Color.WHITE)
            weightHint.setText("Введите значение от 35 до 150")
            weightHint.setTextColor(Color.WHITE)
        }

        userHeight.addTextChangedListener{
            userHeight.setTextColor(Color.WHITE)
            heightHint.setText("Введите значение от 130 до 210")
            heightHint.setTextColor(Color.WHITE)
        }
        //</editor-fold>

        button.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val height = userHeight.text.toString()
            val weight = userWeight.text.toString()
            var gender = ""
            var isHeightValid = false
            var isWeightValid = false

            val db = DbHelper(this, null)


            if(selectedId != -1){
                if(selectedId == R.id.male_option) {
                    gender = "Male"
                }
                else{
                    gender = "Female"
                }
            }

            if(height!= "" && weight!= ""  && selectedId != -1){

                if(weight.toFloat() in 35f..150f){
                    isHeightValid = true
                }
                else{
                    weightHint.setTextColor(Color.RED)
                    weightHint.setText("Введено некорректное значение")
                    userWeight.setTextColor(Color.RED)
                }

                if(height.toFloat() in 130f..210f){
                    isWeightValid = true
                }
                else{
                    heightHint.setTextColor(Color.RED)
                    heightHint.setText("Введено некорректное значение")
                    userHeight.setTextColor(Color.RED)
                }

                if(isHeightValid && isWeightValid){
                    val bio = Biometrics(
                        height.toFloat(),
                        weight.toFloat(),
                        weight.toFloat(),
                        weight.toFloat(),
                        gender
                    )

                    val login = intent.getStringExtra("userLogin").toString()

                    db.addBiometrics(bio, login)

                    //<editor-fold desc="SAVE TO PREFS">
                    var pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                    var editor = pref.edit()
                    editor.putString("USER_LOGIN", login);
                    editor.putBoolean("LOGGED_IN", true);
                    editor.apply();

                    var datePref = getSharedPreferences("DatePref" + login, Context.MODE_PRIVATE)
                    var dateEditor = datePref.edit()

                    val finishDate = LocalDate.now().plusDays(7)

                    dateEditor.putString("DATE_FINISH", finishDate.toString())
                    dateEditor.apply();
                    //</editor-fold>

                    //<editor-fold desc="ADDING ACHIEVEMENTS">
                    db.addAchievement(
                        Achievement("MoreWorkouts", 0, 8, 10, 2),
                        login
                    )
                    db.addAchievement(
                        Achievement("FatBurner", 0, 20, 6, 1000),
                        login
                    )
                    db.addAchievement(
                        Achievement("StartedAndFinished", 0, 34, 8, 2),
                        login
                    )
                    db.addAchievement(
                        Achievement("Halt", 0, 30, 4, 3),
                        login
                    )
                    db.addAchievement(
                        Achievement("StepByStep", 0, 100, 5, 1),
                        login
                    )
                    db.addAchievement(
                        Achievement("Wealth", 0, 10, 10, 3),
                        login
                    )
                    //</editor-fold>

                    //<editor-fold desc="ADDING TASKS">
                    db.addTask(
                        Task("BurnCalories", 1000, 5),
                        login
                    )
                    db.addTask(
                        Task("DoExer", 25, 5),
                        login
                    )
                    db.addTask(
                        Task("DoWorkouts", 3, 8),
                        login
                    )
                    db.addTask(
                        Task("DaysWorkingOut", 3, 25),
                        login
                    )
                    //</editor-fold>

                    //<editor-fold desc="ADDING EXERCISES">
                    db.addExercise(Exercise(
                        "jump", "Прыжки", 5.5))
                    db.addExercise(Exercise(
                        "push_support", "Отжимания с опорой впереди", 5.0))
                    db.addExercise(Exercise(
                        "push_knee_support", "Отжимание с упором на колени", 5.0))
                    db.addExercise(Exercise(
                        "push", "Отжимания от пола", 6.0))
                    db.addExercise(Exercise(
                        "push_wide", "Отжимание с широким упором", 5.0))
                    db.addExercise(Exercise(
                        "sit_up", "Приседания", 1.3))
                    db.addExercise(Exercise(
                        "lunge", "Выпады", 3.4))
                    db.addExercise(Exercise(
                        "alpinist", "Альпинист", 3.4))
                    db.addExercise(Exercise(
                        "bulgarian", "Болгарские приседания", 5.5))
                    db.addExercise(Exercise(
                        "cobra", "Растяжка “Кобра”", 0.0))
                    db.addExercise(Exercise(
                        "chest", "Растяжка грудных мышц в дверном проеме", 0.0))
                    db.addExercise(Exercise(
                        "crunch", "Скручивания “Кранчи”" , 3.5))
                    db.addExercise(Exercise(
                        "swing", "Качели", 6.9))
                    db.addExercise(Exercise(
                        "half_laid_crunch", "Боковые скручивание полулежа", 4.2))
                    db.addExercise(Exercise(
                        "reverse_crunch", "Обратные скручивания с поднятыми ногами", 4.5))
                    db.addExercise(Exercise(
                        "plank", "Планка на локтях", 4.0))
                    db.addExercise(Exercise(
                        "hips_crunch_half_laid_left", "Скручивания в пояснице лежа на полу (влево)", 0.0))
                    db.addExercise(Exercise(
                        "hips_crunch_half_laid_right", "Скручивания в пояснице лежа на полу (вправо)", 0.0))
                    db.addExercise(Exercise(
                        "arms_spinnig", "Вращение руками в плечевом суставе", 2.0))
                    db.addExercise(Exercise(
                        "elbow_together", "Сведение локтей стоя", 0.0))
                    db.addExercise(Exercise(
                        "arms_up", "Подъем рук в стороны", 2.0))
                    db.addExercise(Exercise(
                        "catterpillar", "Гусеница", 4.3))
                    db.addExercise(Exercise(
                        "scissors", "Ножницы", 0.0))
                    db.addExercise(Exercise(
                        "cat_cow", "Поза кошки - поза коровы", 0.0))
                    db.addExercise(Exercise(
                        "extention", "Гиперэкстензии", 0.0))
                    db.addExercise(Exercise(
                        "hip_bridge", "Ягодичный мостик", 0.0))
                    db.addExercise(Exercise(
                        "side_plank_right", "Боковая планка (вправо)", 2.9))
                    db.addExercise(Exercise(
                        "side_plank_left", "Боковая планка (влево)", 2.9))
                    db.addExercise(Exercise(
                        "infant_pose", "Поза ребенка", 0.0))
                    //</editor-fold>

                    val intent = Intent(this, TabsActivity::class.java)
                    startActivity(intent)
                }
            }
            else{
                if(height== "" || weight == ""){
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                }
                if(selectedId == -1){
                    Toast.makeText(this, "Опция не выбрана", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}