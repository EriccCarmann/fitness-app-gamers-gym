package com.example.gamersgym

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.gamersgym.Fragments.Exercises
import com.example.gamersgym.Fragments.Achievements
import com.example.gamersgym.Fragments.Profile
import com.example.gamersgym.Fragments.Tasks
import com.example.gamersgym.Fragments.Training

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Training()
            }
            1 -> Exercises()
            2 -> Profile()
            3 -> Achievements()
            else -> {
                return Tasks()
            }
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Тренировка"
            1 -> "Упражнения"
            2 -> "Профиль"
            3 -> "Достижения"
            else -> {
                return "Задания"
            }
        }
    }
}