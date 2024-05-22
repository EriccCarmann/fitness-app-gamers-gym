package com.example.gamersgym.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import com.example.gamersgym.Exercises.ExercisesDescription
import com.example.gamersgym.R

class Exercises : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // do your variables initialisations here except Views!!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exercisesList: MutableList<TextView> = mutableListOf()

        //<editor-fold desc="ПОЛУЧЕНИЕ">
        val search = view.findViewById<EditText>(R.id.search)

        val alpinist = view.findViewById<TextView>(R.id.alpinist)
        exercisesList.add(alpinist)
        val sidePlank = view.findViewById<TextView>(R.id.side_plank)
        exercisesList.add(sidePlank)
        val halfLaidCrunch = view.findViewById<TextView>(R.id.half_laid_crunch)
        exercisesList.add(halfLaidCrunch)
        val bulgarianSitUps = view.findViewById<TextView>(R.id.bulgarian_sit_ups)
        exercisesList.add(bulgarianSitUps)
        val armsSpinning = view.findViewById<TextView>(R.id.arms_spinning)
        exercisesList.add(armsSpinning)
        val lunge = view.findViewById<TextView>(R.id.lunge)
        exercisesList.add(lunge)
        val extentions = view.findViewById<TextView>(R.id.extentions_exer)
        exercisesList.add(extentions)
        val chestDoor = view.findViewById<TextView>(R.id.chest_door)
        exercisesList.add(chestDoor)
        val catterpillar = view.findViewById<TextView>(R.id.catterpillar)
        exercisesList.add(catterpillar)
        val swing = view.findViewById<TextView>(R.id.swing)
        exercisesList.add(swing)
        val scissors = view.findViewById<TextView>(R.id.scissors)
        exercisesList.add(scissors)
        val reverseCrunch = view.findViewById<TextView>(R.id.reverse_crunch)
        exercisesList.add(reverseCrunch)
        val pushUpsFloor = view.findViewById<TextView>(R.id.push_ups_floor)
        exercisesList.add(pushUpsFloor)
        val pushUpsSupport = view.findViewById<TextView>(R.id.push_ups_support)
        exercisesList.add(pushUpsSupport)
        val pushPpsKneeSupport = view.findViewById<TextView>(R.id.push_ups_knee_support)
        exercisesList.add(pushPpsKneeSupport)
        val widePushUps = view.findViewById<TextView>(R.id.wide_push_ups)
        exercisesList.add(widePushUps)
        val plank = view.findViewById<TextView>(R.id.plank)
        exercisesList.add(plank)
        val arsmUp = view.findViewById<TextView>(R.id.arsm_up)
        exercisesList.add(arsmUp)
        val catCow = view.findViewById<TextView>(R.id.cat_cow)
        exercisesList.add(catCow)
        val infantPose = view.findViewById<TextView>(R.id.infant_pose)
        exercisesList.add(infantPose)
        val sitUps = view.findViewById<TextView>(R.id.sit_ups)
        exercisesList.add(sitUps)
        val jumpInPlace = view.findViewById<TextView>(R.id.jump_in_place)
        exercisesList.add(jumpInPlace)
        val cobraStretch = view.findViewById<TextView>(R.id.cobra_stretch)
        exercisesList.add(cobraStretch)
        val elbowsTogether = view.findViewById<TextView>(R.id.elbows_together)
        exercisesList.add(elbowsTogether)
        val crunch = view.findViewById<TextView>(R.id.crunch_exer)
        exercisesList.add(crunch)
        val hipBridge = view.findViewById<TextView>(R.id.hip_bridge)
        exercisesList.add(hipBridge)
        val hipsCrunchHalfLaid = view.findViewById<TextView>(R.id.hips_crunch_half_laid)
        exercisesList.add(hipsCrunchHalfLaid)
        //</editor-fold>

        search.doAfterTextChanged {
            for(i in 0..exercisesList.size-1){
                val newSearch = exercisesList[i].text.contains(search.text, ignoreCase = true)

                if(newSearch){
                    exercisesList[i].visibility = (View.VISIBLE)
                }
                else{
                    exercisesList[i].visibility = (View.GONE)
                }

                val isSearchEmpty = search.getText().toString()

                if (isSearchEmpty.matches("".toRegex())) {
                    exercisesList[i].visibility = (View.VISIBLE)
                }
            }
        }

        fun ShowExercise(name: String){
            val intent = Intent(context, ExercisesDescription::class.java)
            intent.putExtra("ExerName", name)
            startActivity(intent)
        }

        //<editor-fold desc="ПЕРЕНОС">
        alpinist.setOnClickListener {
            ShowExercise("Alpinist")
        }

        sidePlank.setOnClickListener {
            ShowExercise("SidePlank")
        }

        halfLaidCrunch.setOnClickListener {
            ShowExercise("HalfLaidCrunch")
        }

        bulgarianSitUps.setOnClickListener {
            ShowExercise("BulgarianSitUps")
        }

        armsSpinning.setOnClickListener {
            ShowExercise("ArmsSpinnig")
        }

        lunge.setOnClickListener {
            ShowExercise("Lunge")
        }

        extentions.setOnClickListener {
            ShowExercise("Extention")
        }

        chestDoor.setOnClickListener {
            ShowExercise("ChestStretchDoor")
        }

        catterpillar.setOnClickListener {
            ShowExercise("Catterpillar")
        }

        swing.setOnClickListener {
            ShowExercise("Swing")
        }

        scissors.setOnClickListener {
            ShowExercise("Scissors")
        }

        reverseCrunch.setOnClickListener {
            ShowExercise("ReverseCrunch")
        }

        pushUpsFloor.setOnClickListener {
            ShowExercise("FloorPushUps")
        }

        pushUpsSupport.setOnClickListener {
            ShowExercise("PushUpSupport")
        }

        pushPpsKneeSupport.setOnClickListener {
            ShowExercise("PushUpsKneeSupport")
        }

        widePushUps.setOnClickListener {
            ShowExercise("PushUpsFloorWideSupport")
        }

        plank.setOnClickListener {
            ShowExercise("Plank")
        }

        arsmUp.setOnClickListener {
            ShowExercise("ArmsUp")
        }

        catCow.setOnClickListener {
            ShowExercise("CatCow")
        }

        infantPose.setOnClickListener {
            ShowExercise("InfantPose")
        }

        sitUps.setOnClickListener {
            ShowExercise("SitUps")
        }

        jumpInPlace.setOnClickListener {
            ShowExercise("JumpInPlace")
        }

        cobraStretch.setOnClickListener {
            ShowExercise("StretchCobra")
        }

        elbowsTogether.setOnClickListener {
            ShowExercise("ElbowsTogether")
        }

        crunch.setOnClickListener {
            ShowExercise("Crunch")
        }

        hipBridge.setOnClickListener {
            ShowExercise("HipBridge")
        }

        hipsCrunchHalfLaid.setOnClickListener {
            ShowExercise("HipsCrunchHalfLaid")
        }
        //</editor-fold>
    }
}