package com.example.gamersgym.Fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gamersgym.CustomDialogueBoxes
import com.example.gamersgym.DbHelper
import com.example.gamersgym.PremiumDescriptionActivity
import com.example.gamersgym.R
import java.time.LocalDate
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.math.log


class Tasks : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = activity?.let { DbHelper(it, null) }//ПОЛУЧЕНИЕ БД
        val preferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE) //ПЕРЕМЕННАЯ ДЛЯ ПОЛУЧЕНИЯ ДАННЫХ ИЗ PREFERENCES

        //<editor-fold desc="ПЕРЕМЕННЫЕ">
        val userLevel = getTextViewByTag(R.id.user_level_display)
        val neededPoints = getTextViewByTag(R.id.needed_points_user_level)

        val goToPremiumButton = getTextViewByTag(R.id.go_to_premium_button)
        val activateBoosterButton = getTextViewByTag(R.id.activate_booster_button)
        val login = preferences?.getString("USER_LOGIN", "")

        val isExpBoostActive = getTextViewByTag(R.id.is_exp_boost_active)
        val isPremiumActive = getTextViewByTag(R.id.subscription_status)

        var boosterTasksLeft = getTextViewByTag(R.id.booster_tasks_left)
        val levelDisplay = getTextViewByTag(R.id.user_level_display)
        val currentPointsDisplay = getTextViewByTag(R.id.current_points_user_level)
        var currentBoosterPoints = getTextViewByTag(R.id.current_booster_points)
        var hintPremiumTask = getTextViewByTag(R.id.hint_premium_task)

        var daysCountdown = getTextViewByTag(R.id.days_countdown)
        //</editor-fold>

        val datePref = this.activity?.getSharedPreferences("DatePref" + login, Context.MODE_PRIVATE)
        val finisgDate = datePref?.getString("DATE_FINISH", "")
        var dateEditor = datePref!!.edit()

        when(finisgDate){
            LocalDate.now().toString() -> {
                val newFinishDate = LocalDate.now().plusDays(7)
                dateEditor.putString("DATE_FINISH", newFinishDate.toString())
                dateEditor.apply()

                db!!.ResetTask("BurnCalories", login!!)
                db.ResetTask("DoExer", login!!)
                db.ResetTask("DoWorkouts", login!!)
                db.ResetTask("DaysWorkingOut", login!!)

                daysCountdown!!.text = "7"
            }
            LocalDate.now().plusDays(1).toString() -> {
                daysCountdown!!.text = "1"
            }
            LocalDate.now().plusDays(2).toString() -> {
                daysCountdown!!.text = "2"
            }
            LocalDate.now().plusDays(3).toString() -> {
                daysCountdown!!.text = "3"
            }
            LocalDate.now().plusDays(4).toString() -> {
                daysCountdown!!.text = "4"
            }
            LocalDate.now().plusDays(5).toString() -> {
                daysCountdown!!.text = "5"
            }
            LocalDate.now().plusDays(6).toString() -> {
                daysCountdown!!.text = "6"
            }
            LocalDate.now().plusDays(7).toString() -> {
                daysCountdown!!.text = "7"
            }
        }

        //<editor-fold desc="ПРОВЕРКА ВЫПОЛНЕННОСТИ ЗАДАНИЯ">
        fun CheckIfTaskIsDone(isTaskDone: Int, task: LinearLayout){
            if(isTaskDone == 1){
                task.visibility = (View.GONE)
            }
            else{
                task.visibility = (View.VISIBLE)
            }
        }

        val isBurnCaloriesDone = db?.IsTaskDone("BurnCalories", login!!)
        val isDoWorkoutDone = db?.IsTaskDone("DoWorkouts", login!!)
        val isDoExerDone = db?.IsTaskDone("DoExer", login!!)
        val isDaysWorkingOutDone = db?.IsTaskDone("DaysWorkingOut", login!!)

        val burnCalTask = view.findViewById<View>(R.id.burn_cal_task) as LinearLayout
        val doWorkoutsTask = view.findViewById<View>(R.id.do_workouts_task) as LinearLayout
        val doExerTask = view.findViewById<View>(R.id.do_exer_task) as LinearLayout
        val daysWorkingOutTask = view.findViewById<View>(R.id.days_working_out_task) as LinearLayout

        CheckIfTaskIsDone(isBurnCaloriesDone!!, burnCalTask)
        CheckIfTaskIsDone(isDoWorkoutDone!!, doWorkoutsTask)
        CheckIfTaskIsDone(isDoExerDone!!, doExerTask)
        CheckIfTaskIsDone(isDaysWorkingOutDone!!, daysWorkingOutTask)
        //</editor-fold>

        //<editor-fold desc="НУЖНО ОЧКОВ">
        val caloriesToBurnNeededPoints = getTextViewByTag(R.id.burn_cal_task_needed_points)
        val caloriesToBurn = getTextViewByTag(R.id.calories_to_burn)
        val exerToDoNeededPoints = getTextViewByTag(R.id.do_exer_needed_points)
        val exerToDo = getTextViewByTag(R.id.exer_to_do)
        val doWorkoutsNeededPoints = getTextViewByTag(R.id.do_workouts_needed_points)
        val workoutsToDo = getTextViewByTag(R.id.workouts_to_do)
        val daysWorkingOutNeededPoints = getTextViewByTag(R.id.days_working_out_needed_points)
        val daysWorkingOut = getTextViewByTag(R.id.days_working_out)
        //</editor-fold>

        //<editor-fold desc="ЕСТЬ ОЧКОВ">
        val caloriesToBurnCurrentPoints = getTextViewByTag(R.id.burn_cal_task_current_points)
        val doExerCurrentPoints = getTextViewByTag(R.id.do_exer_current_points)
        val doWorkoutsCurrentPoints= getTextViewByTag(R.id.do_workouts_current_points)
        val daysWorkingOutCurrentPoints = getTextViewByTag(R.id.days_working_out_current_points)
        //</editor-fold>

        //<editor-fold desc="НАГРАДЫ">
        val burnCalReward = getTextViewByTag(R.id.burn_cal_reward)
        val doExerReward = getTextViewByTag(R.id.do_exer_reward)
        val doWorkoutsReward = getTextViewByTag(R.id.do_workouts_reward)
        val daysWorkingOutReward = getTextViewByTag(R.id.days_working_out_reward)
        //</editor-fold>

        levelDisplay!!.text = login?.let { db?.getUserLevel(it)}.toString()
        currentPointsDisplay!!.text = login?.let { db?.getCurrentPoints(it)}.toString()
        currentBoosterPoints!!.text = login?.let { db?.getCurrentBoosterPoints(it)}.toString()
        boosterTasksLeft!!.text = login?.let { db?.GetBoosterTaskLeft(it) }.toString()

        fun BoosterIndicator(){
            if(login?.let { db?.getBoosterStatus(it) } == 1){
                isExpBoostActive?.setTextColor(Color.parseColor("#338AF3"))
            }
            else isExpBoostActive?.setTextColor(Color.parseColor("#50338AF3"))
        }

        if(login?.let { db.GetPremiumStatus(it) } == 1){
            isPremiumActive!!.text = "Подписка активна"
            doWorkoutsTask.alpha = 1f
            hintPremiumTask!!.visibility = (View.GONE)
        }
        else {
            isPremiumActive!!.text = "Подписка не активна"
            doWorkoutsTask.alpha = 0.5f
            hintPremiumTask!!.visibility = (View.VISIBLE)
        }

        BoosterIndicator()

        if (db != null) {
            userLevel!!.text = login?.let { db.getUserLevel(it).toString() }
            neededPoints!!.text = login?.let { db.getUserPointToLevelUpByLogin(it).toString() }

            if(isBurnCaloriesDone == 0){
                caloriesToBurnNeededPoints!!.text = login?.let { db.GetNeededPointsTask("BurnCalories", it).toString() }
                caloriesToBurnCurrentPoints!!.text = login?.let { db.GetCurrentPointsTask("BurnCalories", it).toString() }
                burnCalReward!!.text = login?.let { db.GetTaskReward("BurnCalories", it).toString() }
                caloriesToBurn!!.text = caloriesToBurnNeededPoints.text
            }

            if(isDoExerDone == 0){
                exerToDoNeededPoints!!.text = login?.let { db.GetNeededPointsTask("DoExer", it).toString() }
                doExerCurrentPoints!!.text = login?.let { db.GetCurrentPointsTask("DoExer", it).toString() }
                doExerReward!!.text = login?.let { db.GetTaskReward("DoExer", it).toString() }
                exerToDo!!.text = exerToDoNeededPoints.text
            }

            if(isDoWorkoutDone == 0){
                doWorkoutsNeededPoints!!.text = login?.let { db.GetNeededPointsTask("DoWorkouts", it).toString() }
                doWorkoutsCurrentPoints!!.text = login?.let { db.GetCurrentPointsTask("DoWorkouts", it).toString() }
                doWorkoutsReward!!.text = login?.let { db.GetTaskReward("DoWorkouts", it).toString() }
                workoutsToDo!!.text = doWorkoutsNeededPoints.text
            }

            if(isDaysWorkingOutDone == 0){
                daysWorkingOutNeededPoints!!.text = login?.let { db.GetNeededPointsTask("DaysWorkingOut", it).toString() }
                daysWorkingOutCurrentPoints!!.text = login?.let { db.GetCurrentPointsTask("DaysWorkingOut", it).toString() }
                daysWorkingOutReward!!.text = login?.let { db.GetTaskReward("DaysWorkingOut", it).toString() }
                daysWorkingOut!!.text = daysWorkingOutNeededPoints.text
            }
        }

        //<editor-fold desc="ПРОГРЕСС БАР">
        fun CountPercenrage(current: Int, needed: Int): Int{
            return (current * 100).div(needed)
        }

        val userLevelProgressBar = view.findViewById<View>(R.id.progress_bar) as ProgressBar
        userLevelProgressBar.progress = CountPercenrage(Integer.parseInt(currentPointsDisplay!!.text.toString()),
            Integer.parseInt(neededPoints!!.text.toString())
        )

        val boosterProgress = view.findViewById<View>(R.id.booster_progress) as ProgressBar
        val currentPointsBooster = Integer.parseInt(currentBoosterPoints!!.text.toString())
        boosterProgress.progress = CountPercenrage(currentPointsBooster, 100)


        if(isDoWorkoutDone == 0){
            val doWorkoutsTaskProgress = view.findViewById<View>(R.id.do_workouts_task_progress) as ProgressBar
            doWorkoutsTaskProgress.progress = CountPercenrage(Integer.parseInt(doWorkoutsCurrentPoints!!.text.toString()),
                Integer.parseInt(doWorkoutsNeededPoints!!.text.toString())
            )
        }
        if(isBurnCaloriesDone == 0){
            val burnCalTaskProgress = view.findViewById<View>(R.id.burn_cal_task_progress) as ProgressBar
            burnCalTaskProgress.progress = CountPercenrage(Integer.parseInt(caloriesToBurnCurrentPoints!!.text.toString()),
                Integer.parseInt(caloriesToBurnNeededPoints!!.text.toString())
            )
        }
        if(isDoExerDone == 0){
            val doExerTaskProgress = view.findViewById<View>(R.id.do_exer_task_progress) as ProgressBar
            doExerTaskProgress.progress = CountPercenrage(Integer.parseInt(doExerCurrentPoints!!.text.toString()),
                Integer.parseInt(exerToDoNeededPoints!!.text.toString())
            )
        }
        if(isDaysWorkingOutDone == 0){
            val daysWorkingOutTaskProgress = view.findViewById<View>(R.id.days_working_out_task_progress) as ProgressBar
            daysWorkingOutTaskProgress.progress = CountPercenrage(Integer.parseInt(daysWorkingOutCurrentPoints!!.text.toString()),
                Integer.parseInt(daysWorkingOutNeededPoints!!.text.toString())
            )
        }
        //</editor-fold>

        goToPremiumButton?.setOnClickListener {
            val intent = Intent(context, PremiumDescriptionActivity::class.java)
            startActivity(intent)
        }

        activateBoosterButton?.setOnClickListener {
            if (currentPointsBooster >= 100 && db.getBoosterStatus(login!!) == 0){
                context?.let { it ->
                    CustomDialogueBoxes().showYesNoDialog(
                        it, "Вы уверены, что хотите потратить очки на активацию?",
                        "Набранные очки сбросятся и бустер будет активирован."){
                        db.SetBoosterStatus(login!!, 1)
                        db.UpdateBoosterTaskLeft(login, 5)
                        BoosterIndicator()
                        boosterTasksLeft!!.text = login?.let { db?.GetBoosterTaskLeft(it) }.toString()
                        currentBoosterPoints!!.text = login?.let { db?.getCurrentBoosterPoints(it)}.toString()
                        val boosterProgress = view.findViewById<View>(R.id.booster_progress) as ProgressBar
                        val currentPointsBooster = Integer.parseInt(currentBoosterPoints!!.text.toString())
                        boosterProgress.progress = CountPercenrage(currentPointsBooster, 100)
                    }
                }
            }
            else{
                context?.let { it -> CustomDialogueBoxes().showDialog(it, "Недостаточно очков",
                    "Для активации бустера наберите достаточное количество очков.") }
            }
        }
    }

    fun getTextViewByTag(tag: Int): TextView?{
        val resourceId = resources.getIdentifier(tag.toString(), "id", context?.packageName)
        return view?.findViewById(resourceId)
    }
}