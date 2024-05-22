package com.example.gamersgym.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.gamersgym.DbHelper
import com.example.gamersgym.R

class Achievements : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_achievements, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // do your variables initialisations here except Views!!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = activity?.let { DbHelper(it, null) }

        val preferences = this.activity?.getSharedPreferences(
            "MyPref",
            Context.MODE_PRIVATE
        )

        val login_user = preferences?.getString("USER_LOGIN", "")

        //<editor-fold desc="ACHIEVEMENT LEVEL">
        val moreWorkoutsLevel = getTextViewByTag(R.id.moreworkouts_level)
        val fatBurnerLevel = getTextViewByTag(R.id.fatburner_level)
        val startedFinishedLevel = getTextViewByTag(R.id.startedandfinished_level)
        val haltLevel = getTextViewByTag(R.id.halt_level)
        val stepByStepLevel = getTextViewByTag(R.id.step_by_step_level)
        val wealthLevel = getTextViewByTag(R.id.wealth_level)
        //</editor-fold>

        //<editor-fold desc="CURRENT ACHIEVEMENT POINTS">
        val currentWorkoutsCount = getTextViewByTag(R.id.current_workouts_count)
        val currentСalorieСount = getTextViewByTag(R.id.current_calorie_count)
        val currentTasksCount = getTextViewByTag(R.id.current_tasks_count)
        val currentDaysCount = getTextViewByTag(R.id.current_days_count)
        val stepByStepLevelCount = getTextViewByTag(R.id.step_by_step_level_count)
        val wealthLevelCount = getTextViewByTag(R.id.wealth_level_count)
        //</editor-fold>

        //<editor-fold desc="PROGRESS BAR">
        val progressBarMoreWorkouts = view.findViewById<View>(R.id.progress_bar_more_workouts) as ProgressBar
        val progressBarFatburner = view.findViewById<View>(R.id.progress_bar_fatburner) as ProgressBar
        val progressBarStartnfinish = view.findViewById<View>(R.id.progress_bar_startnfinish) as ProgressBar
        val progressBarHalt = view.findViewById<View>(R.id.progress_bar_halt) as ProgressBar
        val progressBarStepByStep = view.findViewById<View>(R.id.progress_bar_step_by_step) as ProgressBar
        val progressBarWealth = view.findViewById<View>(R.id.progress_bar_wealth) as ProgressBar
        //</editor-fold>

        //<editor-fold desc="ACHIEVEMENT CONDITION">
        val moreWorkoutsCondition = getTextViewByTag(R.id.more_workouts_condition)
        val fatBurnerCondition = getTextViewByTag(R.id.fatburner_condition)
        val startedFinishedCondition = getTextViewByTag(R.id.startnfinish_condition)
        val haltCondition = getTextViewByTag(R.id.halt_condition)
        val stepByStepCondition = getTextViewByTag(R.id.step_by_step_condition)
        val wealthCondition = getTextViewByTag(R.id.wealth_condition)
        //</editor-fold>

        //<editor-fold desc="PROGRESS BAR CONDITION">
        val moreWorkoutsBarValue = getTextViewByTag(R.id.needed_workouts_condition_bar_value)
        val haltConditionBarValue = getTextViewByTag(R.id.halt_condition_bar_value)
        val fatBurnerConditionBarValue = getTextViewByTag(R.id.needed_calories_condition_bar_value)
        val startedFinishedConditionBarValue = getTextViewByTag(R.id.needed_tasks_condition_bar_value)
        val stepByStepConditionBarValue = getTextViewByTag(R.id.step_by_step_condition_bar_value)
        val wealthConditionBarValue = getTextViewByTag(R.id.wealth_condition_bar_value)
        //</editor-fold>

        //<editor-fold desc="REWARDS">
        val moreWorkoutsReward = getTextViewByTag(R.id.more_workouts_reward)
        val fatBurnerReward = getTextViewByTag(R.id.fat_burner_reward)
        val startAndFinishReward = getTextViewByTag(R.id.startnfinish_reward)
        val haltReward = getTextViewByTag(R.id.halt_reward_display)
        val stepByStepReward = getTextViewByTag(R.id.step_by_step_reward_display)
        val wealthReward = getTextViewByTag(R.id.wealth_reward_display)
        //</editor-fold>

        if (db != null) {
            //<editor-fold desc="CURRENT ACHIEVEMENT LEVEL">
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

            //<editor-fold desc="CURRENT ACHIEVEMENT POINTS">
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

            //<editor-fold desc="ACHIEVEMENTS CONDITIONS">
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

            //<editor-fold desc="REWARDS">
            moreWorkoutsReward!!.text = login_user?.let { db.getAchievementReward("MoreWorkouts", it).toString() }
            fatBurnerReward!!.text = login_user?.let { db.getAchievementReward("FatBurner", it).toString() }
            startAndFinishReward!!.text = login_user?.let { db.getAchievementReward("StartedAndFinished", it).toString() }
            haltReward!!.text = login_user?.let { db.getAchievementReward("Halt", it).toString() }
            stepByStepReward!!.text = login_user?.let { db.getAchievementReward("StepByStep", it).toString() }
            wealthReward!!.text = login_user?.let { db.getAchievementReward("Wealth", it).toString() }
            //</editor-fold>
        }

        //<editor-fold desc="PROGRESS BAR">
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

        //<editor-fold desc="EXPAND ACHIEVEMENTS">
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
    }

    fun expandAchievements(moreWorkouts: LinearLayout){
        val layoutParams = moreWorkouts.layoutParams as ViewGroup.LayoutParams

        if(layoutParams.height == 250){
            layoutParams.height = 700
            moreWorkouts.layoutParams = layoutParams
        }
        else{
            layoutParams.height = 250
            moreWorkouts.layoutParams = layoutParams
        }
    }

    fun getTextViewByTag(tag: Int): TextView?{
        val resourceId = resources.getIdentifier(tag.toString(), "id", context?.packageName)
        return view?.findViewById(resourceId)
    }
}