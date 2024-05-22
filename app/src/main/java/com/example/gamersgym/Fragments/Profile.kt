package com.example.gamersgym.Fragments

import android.R.attr.data
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.gamersgym.ChangePasswordActivity
import com.example.gamersgym.CustomDialogueBoxes
import com.example.gamersgym.DbHelper
import com.example.gamersgym.MainActivity
import com.example.gamersgym.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.DecimalFormat


class Profile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // do your variables initialisations here except Views!!!
    }

    lateinit var userAvatar: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = activity?.let { DbHelper(it, null) } //ПОЛУЧЕНИЕ БД
        val preferences = this.activity?.getSharedPreferences(
            "MyPref",
            MODE_PRIVATE
        )
        //ПЕРЕМЕННАЯ ДЛЯ ПОЛУЧЕНИЯ ДАННЫХ ИЗ PREFERENCES
        var editor = preferences?.edit()

        //<editor-fold desc="ПЕРЕМЕННЫЕ">
        val login = getTextViewByTag(R.id.user_login_display)
        val maxWeight = getTextViewByTag(R.id.max_weight)
        val minWeight = getTextViewByTag(R.id.min_weight)
        val heightDisplay = getTextViewByTag(R.id.user_height_profile_display)
        val weightDisplay = getTextViewByTag(R.id.user_weight_profile_display)
        val exit = getTextViewByTag(R.id.exit_acc)
        val changePass = getTextViewByTag(R.id.change_pass)
        val startDate = getTextViewByTag(R.id.start_date_display)
        val bmiDisplay = getTextViewByTag(R.id.bmi_display)
        val bmiTextExpl = getTextViewByTag(R.id.bmi_text_expl)

        val userHeightDisplay = view.findViewById<View>(R.id.user_height_profile_display) as EditText
        val userWeightDisplay = view.findViewById<View>(R.id.user_weight_profile_display) as EditText

        val arrow = view.findViewById<View>(R.id.arrow_bmi) as ImageView

        val spinner = view.findViewById<View>(R.id.spinner) as Spinner

        userAvatar = view.findViewById<View>(R.id.user_avatar) as ImageView

        login?.text = preferences?.getString("USER_LOGIN", "")
        val login_user = preferences?.getString("USER_LOGIN", "")
        //</editor-fold>

        //<editor-fold desc="ИЗМЕНЕНИЕ РОСТА И ВЕСА">
        val changeHeightButton = view.findViewById<View>(R.id.change_height_button) as Button
        val changeWeightButton = view.findViewById<View>(R.id.change_weight_button) as Button

        val currentMinWeight = login_user?.let { db?.getMinWeightByLogin(it) }
        val currentMaxWeight = login_user?.let { db?.getMaxWeightByLogin(it) }

        maxWeight?.text = currentMaxWeight
        minWeight?.text = currentMinWeight

        heightDisplay?.text = login_user?.let { db?.getHeightByLogin(it) }
        weightDisplay?.text = login_user?.let { db?.getWeightByLogin(it) }

        if(db?.GetURL(login_user!!) != null && db?.GetURL(login_user!!) != ""){
            val uri = db?.GetURL(login_user!!)?.toUri()
            userAvatar.setImageURI(uri)
        }

        userAvatar.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
        //</editor-fold>

        //<editor-fold desc="УРОВЕНЬ ДОСТИЖЕНИЯ">
        val moreWorkoutsLevel = getTextViewByTag(R.id.moreworkouts_level)
        val fatBurnerLevel = getTextViewByTag(R.id.fatburner_level)
        val startedFinishedLevel = getTextViewByTag(R.id.startedandfinished_level)
        val haltLevel = getTextViewByTag(R.id.halt_level)
        val stepByStepLevel = getTextViewByTag(R.id.step_by_step_level)
        val wealthLevel = getTextViewByTag(R.id.wealth_level)
        //</editor-fold>

        //<editor-fold desc="НАГРАДЫ">
        val moreWorkoutsReward = getTextViewByTag(R.id.more_workouts_reward)
        val fatBurnerReward = getTextViewByTag(R.id.fat_burner_reward)
        val startAndFinishReward = getTextViewByTag(R.id.startnfinish_reward)
        val haltReward = getTextViewByTag(R.id.halt_reward_display)
        val stepByStepReward = getTextViewByTag(R.id.step_by_step_reward_display)
        val wealthReward = getTextViewByTag(R.id.wealth_reward_display)
        //</editor-fold>

        //<editor-fold desc="УСЛОВИЕ ДОСТИЖЕНИЯ">
        val moreWorkoutsCondition = getTextViewByTag(R.id.more_workouts_condition)
        val fatBurnerCondition = getTextViewByTag(R.id.fatburner_condition)
        val startedFinishedCondition = getTextViewByTag(R.id.startnfinish_condition)
        val haltCondition = getTextViewByTag(R.id.halt_condition)
        val stepByStepCondition = getTextViewByTag(R.id.step_by_step_condition)
        val wealthCondition = getTextViewByTag(R.id.wealth_condition)
        //</editor-fold>

        //<editor-fold desc="ПРОГРЕСС БАР УСЛОВИЕ ДОСТИЖЕНИЯ">
        val moreWorkoutsBarValue = getTextViewByTag(R.id.needed_workouts_condition_bar_value)
        val haltConditionBarValue = getTextViewByTag(R.id.halt_condition_bar_value)
        val fatBurnerConditionBarValue = getTextViewByTag(R.id.needed_calories_condition_bar_value)
        val startedFinishedConditionBarValue = getTextViewByTag(R.id.needed_tasks_condition_bar_value)
        val stepByStepConditionBarValue = getTextViewByTag(R.id.step_by_step_condition_bar_value)
        val wealthConditionBarValue = getTextViewByTag(R.id.wealth_condition_bar_value)
        //</editor-fold>

        //<editor-fold desc="ПРОГРЕСС БАР">
        val progressBarMoreWorkouts = view.findViewById<View>(R.id.progress_bar_more_workouts) as ProgressBar
        val progressBarFatburner = view.findViewById<View>(R.id.progress_bar_fatburner) as ProgressBar
        val progressBarStartnfinish = view.findViewById<View>(R.id.progress_bar_startnfinish) as ProgressBar
        val progressBarHalt = view.findViewById<View>(R.id.progress_bar_halt) as ProgressBar
        val progressBarStepByStep = view.findViewById<View>(R.id.progress_bar_step_by_step) as ProgressBar
        val progressBarWealth = view.findViewById<View>(R.id.progress_bar_wealth) as ProgressBar
        //</editor-fold>

        //<editor-fold desc="ТЕКУЩИЕ ОЧКИ ДОСТИЖЕНИЯ">
        val currentWorkoutsCount = getTextViewByTag(R.id.current_workouts_count)
        val currentСalorieСount = getTextViewByTag(R.id.current_calorie_count)
        val currentTasksCount = getTextViewByTag(R.id.current_tasks_count)
        val currentDaysCount = getTextViewByTag(R.id.current_days_count)
        val stepByStepLevelCount = getTextViewByTag(R.id.step_by_step_level_count)
        val wealthLevelCount = getTextViewByTag(R.id.wealth_level_count)
        //</editor-fold>


        if (db != null) {
            //<editor-fold desc="ТЕКУЩИЙ УРОВЕНЬ ДОСТИЖЕНИЯ">
            moreWorkoutsLevel!!.text =
                login_user?.let { db.getCurrentAchievementLevel("MoreWorkouts", it).toString() }

            fatBurnerLevel!!.text =
                login_user?.let { db.getCurrentAchievementLevel("FatBurner", it).toString() }

            startedFinishedLevel!!.text =
                login_user?.let { db.getCurrentAchievementLevel("StartedAndFinished", it).toString() }

            haltLevel!!.text =
                login_user?.let { db.getCurrentAchievementLevel("Halt", it).toString() }

            stepByStepLevel!!.text =
                login_user?.let { db.getCurrentAchievementLevel("StepByStep", it).toString() }

            wealthLevel!!.text =
                login_user?.let { db.getCurrentAchievementLevel("Wealth", it).toString() }
            //</editor-fold>

            //<editor-fold desc="ТЕКУЩИЕ ОЧКИ">
            currentWorkoutsCount!!.text =
                login_user?.let { db.getCurrentPointsAchievement("MoreWorkouts", it).toString() }
            currentСalorieСount!!.text =
                login_user?.let { db.getCurrentPointsAchievement("FatBurner", it).toString() }
            currentTasksCount!!.text =
                login_user?.let { db.getCurrentPointsAchievement("StartedAndFinished", it).toString() }
            currentDaysCount!!.text =
                login_user?.let { db.getCurrentPointsAchievement("Halt", it).toString() }
            stepByStepLevelCount!!.text =
                login_user?.let { db.getCurrentPointsAchievement("StepByStep", it).toString() }
            wealthLevelCount!!.text =
                login_user?.let { db.getCurrentPointsAchievement("Wealth", it).toString() }
            //</editor-fold>

            //<editor-fold desc="УСЛОВИЯ ПОЛУЧЕНИЯ ДОСТИЖЕНИЯ">
            moreWorkoutsCondition!!.text = login_user?.let { db.getAchievementCondition("MoreWorkouts", it).toString() }
            moreWorkoutsBarValue!!.text = moreWorkoutsCondition.text

            fatBurnerCondition!!.text = login_user?.let { db.getAchievementCondition("FatBurner", it).toString() }
            fatBurnerConditionBarValue!!.text = fatBurnerCondition.text

            startedFinishedCondition!!.text = login_user?.let { db.getAchievementCondition("StartedAndFinished", it).toString() }
            startedFinishedConditionBarValue!!.text = startedFinishedCondition.text

            haltCondition!!.text = login_user?.let { db.getAchievementCondition("Halt", it).toString() }
            haltConditionBarValue!!.text = haltCondition.text

            stepByStepCondition!!.text = login_user?.let { db.getAchievementCondition("StepByStep", it).toString() }
            stepByStepConditionBarValue!!.text = stepByStepCondition.text

            wealthCondition!!.text = login_user?.let { db.getAchievementCondition("Wealth", it).toString() }
            wealthConditionBarValue!!.text = wealthCondition.text
            //</editor-fold>

            //<editor-fold desc="НАГРАДЫ">
            moreWorkoutsReward!!.text = login_user?.let { db.getAchievementReward("MoreWorkouts", it).toString() }
            fatBurnerReward!!.text = login_user?.let { db.getAchievementReward("FatBurner", it).toString() }
            startAndFinishReward!!.text = login_user?.let { db.getAchievementReward("StartedAndFinished", it).toString() }
            haltReward!!.text = login_user?.let { db.getAchievementReward("Halt", it).toString() }
            stepByStepReward!!.text = login_user?.let { db.getAchievementReward("StepByStep", it).toString() }
            wealthReward!!.text = login_user?.let { db.getAchievementReward("Wealth", it).toString() }
            //</editor-fold>
        }

        //<editor-fold desc="ПРОГРЕСС БАР">
        fun CountPercenrage(current: Int, needed: Int): Int{
            return (current * 100).div(needed)
        }
        progressBarMoreWorkouts.progress = CountPercenrage(Integer.parseInt(currentWorkoutsCount!!.text.toString()),
            Integer.parseInt(moreWorkoutsCondition!!.text.toString())
        )
        progressBarFatburner.progress = CountPercenrage(Integer.parseInt(currentСalorieСount!!.text.toString()),
            Integer.parseInt(fatBurnerCondition!!.text.toString())
        )
        progressBarStartnfinish.progress = CountPercenrage(Integer.parseInt(currentTasksCount!!.text.toString()),
            Integer.parseInt(startedFinishedCondition!!.text.toString())
        )
        progressBarHalt.progress = CountPercenrage(Integer.parseInt(currentDaysCount!!.text.toString()),
            Integer.parseInt(haltCondition!!.text.toString())
        )
        progressBarStepByStep.progress = CountPercenrage(Integer.parseInt(stepByStepLevelCount!!.text.toString()),
            Integer.parseInt(stepByStepCondition!!.text.toString())
        )
        progressBarWealth.progress = CountPercenrage(Integer.parseInt(wealthLevelCount!!.text.toString()),
            Integer.parseInt(wealthCondition!!.text.toString())
        )
        //</editor-fold>

        //<editor-fold desc="РАЗВЕРНУТЬ ДОСТИЖЕНИЯ">
        val moreWorkouts = view.findViewById<View>(R.id.more_workouts) as LinearLayout
        val fatBurner = view.findViewById<View>(R.id.fat_burner) as LinearLayout
        val startedAndFinished = view.findViewById<View>(R.id.started_and_finished) as LinearLayout
        val haltView = view.findViewById<View>(R.id.halt) as LinearLayout
        val stepByStep = view.findViewById<View>(R.id.step_by_step) as LinearLayout
        val wealth = view.findViewById<View>(R.id.wealth) as LinearLayout

        moreWorkouts.setOnClickListener{
            expandAchievements(moreWorkouts)
        }

        fatBurner.setOnClickListener{
            expandAchievements(fatBurner)
        }

        startedAndFinished.setOnClickListener{
            expandAchievements(startedAndFinished)
        }

        haltView.setOnClickListener{
            expandAchievements(haltView)
        }

        stepByStep.setOnClickListener{
            expandAchievements(stepByStep)
        }
        wealth.setOnClickListener{
            expandAchievements(wealth)
        }
        //</editor-fold>

        //<editor-fold desc="ВЫХОД ИЗ АККАУНТА И ИЗМЕНЕНИЕ ПАРОЛЯ">
        exit!!.setOnClickListener {
            val editor = preferences?.edit()
            if (editor != null) {
                editor.putBoolean("LOGGED_IN", false)
                editor.apply()
            };

            val intent = Intent(context, MainActivity::class.java) // Replace with your target activity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK // Clear previous activities
            startActivity(intent)
        }

        changePass!!.setOnClickListener {
            val intent = Intent(context, ChangePasswordActivity::class.java)
            intent.putExtra("login", login_user)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        //</editor-fold>

        //<editor-fold desc="СЕТТЕР ДАТЫ НАЧАЛА И КАЛЛОРИЙ">
        if (db != null) {
            startDate!!.text = login_user?.let { db.getRegDate(it) }
        }
        val cals = login_user?.let { db?.getCalorie(it) }
        //</editor-fold">

        //<editor-fold desc="ИМС">
        fun CalculateBMI(){
            val bmiOffsets: List<Int> = mutableListOf(0, 10, 44, 115, 177, 237, 300, 325)
            val weightFormulaElem = login_user?.let { db?.getWeightByLogin(it)}?.toFloat()

            val heightFormulaElem = login_user?.let { db?.getHeightByLogin(it)?.toFloat()?.div(100) }

            val bmi = weightFormulaElem?.div((heightFormulaElem?.times(heightFormulaElem)!!))
            var offset = 0

            val df = DecimalFormat("#.#")
            val p: Double = df.format(bmi).toDouble()
            bmiDisplay?.text = p.toString()

            when (bmi!!) {
                in 0f..15f ->{
                    offset = bmiOffsets[0]
                    bmiTextExpl!!.text = "Серьезный недовес"
                }
                in 15f..16f -> {
                    offset = bmiOffsets[1]
                    bmiTextExpl!!.text = "Значительный дефицит массы тела"
                }
                in 16f..18.5f -> {
                    offset = bmiOffsets[2]
                    bmiTextExpl!!.text = "Дефицит массы тела"
                }
                in 18.5f..25f -> {
                    offset = bmiOffsets[3]
                    bmiTextExpl!!.text = "Нормальная масса тела"
                }
                in 25f..30f -> {
                    offset = bmiOffsets[4]
                    bmiTextExpl!!.text = "Лишний вес"
                }
                in 30f..35f -> {
                    offset = bmiOffsets[5]
                    bmiTextExpl!!.text = "Ожирение первой степени"
                }
                in 35f..40f -> {
                    offset = bmiOffsets[6]
                    bmiTextExpl!!.text = "Ожирение второй степени"
                }
                in 40f..100f -> {
                    offset = bmiOffsets[7]
                    bmiTextExpl!!.text = "Экстремальное ожирение"
                }
                else -> {
                    return
                }
            }

            val layoutParams = arrow.layoutParams as ViewGroup.MarginLayoutParams // Assuming your layout uses margins

            layoutParams.leftMargin = (offset * 2.5).toInt()
            layoutParams.topMargin = 0
            layoutParams.rightMargin = 0
            layoutParams.bottomMargin = 0

            arrow.layoutParams = layoutParams
        }

        CalculateBMI()

        changeHeightButton.setOnClickListener{
            val newHeight = userHeightDisplay.text.toString()

            if(newHeight.toFloat() in 130f..210f && login_user != null){
                db?.updateHeight(newHeight.toFloat(), login_user)

                heightDisplay?.text = newHeight

                CalculateBMI()
            }
            else{
                context?.let { it ->
                    CustomDialogueBoxes().showDialog(
                        it, "Введены некорректные данные",
                        "Попробуйте ввести значения от 130 до 210")
                }
            }
        }

        changeWeightButton.setOnClickListener{
            val newWeight = userWeightDisplay.text.toString()

            if(newWeight.toFloat() in 35f..150f && login_user != null){
                db?.updateWeight(newWeight.toFloat(), login_user)

                if(newWeight.toFloat() > currentMaxWeight!!.toFloat()){
                    db?.updateMaxWeight(newWeight.toFloat(), login_user)
                }
                else if(newWeight.toFloat() < currentMinWeight!!.toFloat()){
                    db?.updateMinWeight(newWeight.toFloat(), login_user)
                }
                maxWeight?.text = login_user.let { db?.getMinWeightByLogin(it) }
                minWeight?.text = login_user.let { db?.getMaxWeightByLogin(it) }

                weightDisplay?.text = newWeight

                CalculateBMI()
            }
            else {
                context?.let { it ->
                    CustomDialogueBoxes().showDialog(
                        it, "Введены некорректные данные",
                        "Попробуйте ввести значения от 35 до 150")
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="ГРАФИК">
        fun AddDays(maxDays: Int): List<String> {
            val daysList: MutableList<String> = mutableListOf()

            for(i in 1..maxDays){
                daysList.add(i.toString())
            }
            return daysList
        }

        fun AddCalories(items: Int): List<Float> {
            val itemsList: MutableList<Float> = mutableListOf()

            for(i in items downTo 0){
                itemsList.add(cals!![i])
            }
            return itemsList
        }

        fun CreateChart(maxDays: Int){
            val xValues: List<String> = AddDays(maxDays)
            val barEntries: List<Float> = AddCalories(xValues.size - 1)

            val barChart = view.findViewById<View>(R.id.chart) as BarChart
            barChart.axisRight.setDrawLabels(false)

            val entries = ArrayList<BarEntry>()

            for(i in  0..maxDays - 1){
                entries.add(BarEntry(i.toFloat(), barEntries[i]))
            }

            val yAxis = barChart.axisLeft
            yAxis.setAxisMaximum(barEntries.max())
            yAxis.axisLineWidth = 0.1f
            yAxis.axisLineColor = Color.BLACK
            yAxis.setLabelCount(10)

            val dataSet = BarDataSet(entries, "Дни")

            val barData = BarData(dataSet)
            barChart.setData(barData)

            barChart.description.isEnabled = false
            barChart.invalidate()

            barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.xAxis.setGranularity(1f)
            barChart.xAxis.isGranularityEnabled = true
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent.getItemAtPosition(position) as String

                    if(item == "Неделя"){
                        CreateChart(7)

                    }else if(item == "Месяц"){
                        CreateChart(30)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        spinner.onItemSelectedListener = itemSelectedListener
        //</editor-fold>
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!
            val db = activity?.let { DbHelper(it, null) } //ПОЛУЧЕНИЕ БД
            val preferences = this.activity?.getSharedPreferences(
                "MyPref",
                MODE_PRIVATE
            )
            val login_user = preferences?.getString("USER_LOGIN", "")
            db?.UpdateURL(uri.toString(), login_user!!)
            userAvatar.setImageURI(uri)
        }
    }

    fun expandAchievements(moreWorkouts: LinearLayout){
        val layoutParams = moreWorkouts.layoutParams as ViewGroup.LayoutParams

        if(layoutParams.height == 260){
            layoutParams.height = 700
            moreWorkouts.layoutParams = layoutParams
        }
        else{
            layoutParams.height = 260
            moreWorkouts.layoutParams = layoutParams
        }
    }

    @SuppressLint("DiscouragedApi")
    fun getTextViewByTag(tag: Int): TextView?{
        val resourceId = resources.getIdentifier(tag.toString(), "id", context?.packageName)
        return view?.findViewById(resourceId)
    }
}