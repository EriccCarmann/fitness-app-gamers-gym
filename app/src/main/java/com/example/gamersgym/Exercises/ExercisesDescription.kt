package com.example.gamersgym.Exercises

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gamersgym.R
import com.example.gamersgym.TabsActivity

class ExercisesDescription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exercises_description)

        //<editor-fold desc="ПЕРЕМЕННЫЕ">
        val backButton  = findViewById<ImageView>(R.id.back_to_tabs)
        val alpinistLayout = findViewById<LinearLayout>(R.id.alpinist_layout)
        val sidePlankLayout = findViewById<LinearLayout>(R.id.side_plank)
        val halfLaidCrunchLayout = findViewById<LinearLayout>(R.id.half_laid_crunch_layout)
        val catCowLayout = findViewById<LinearLayout>(R.id.cat_cow_layout)
        val crunchLayout = findViewById<LinearLayout>(R.id.crunch_layout)
        val elbowLogetherLayout = findViewById<LinearLayout>(R.id.elbow_together_layout)
        val extentionLayout = findViewById<LinearLayout>(R.id.extention_layout)
        val swingLayout = findViewById<LinearLayout>(R.id.swing_layout)
        val scissorsLayout = findViewById<LinearLayout>(R.id.scissors_layout)
        val reverseCrunchLayout = findViewById<LinearLayout>(R.id.reverse_crunch_layout)
        val floorPushUpsLayout = findViewById<LinearLayout>(R.id.floor_push_ups_layout)
        val hipBridgeLayout = findViewById<LinearLayout>(R.id.hip_bridge_layout)
        val hipCrunchLayout = findViewById<LinearLayout>(R.id.hip_crunch_layout)
        val pushUpsSupportLayout = findViewById<LinearLayout>(R.id.push_ups_support_layout)
        val pushUpKneeSupportLayout = findViewById<LinearLayout>(R.id.push_up_knee_support_layout)
        val pushUpWideSupportLayout = findViewById<LinearLayout>(R.id.push_up_wide_support_layout)
        val plankLayout = findViewById<LinearLayout>(R.id.plank_layout)
        val jumpLayout = findViewById<LinearLayout>(R.id.jump_layout)
        val infantPoseLayout = findViewById<LinearLayout>(R.id.infant_pose_layout)
        val cobraLayout = findViewById<LinearLayout>(R.id.cobra_layout)
        //</editor-fold>

        //<editor-fold desc="МЕТОДЫ">
        fun Alpinist(){
            alpinistLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn)

            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>12 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 1.7 до 2.8</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 1.9 до 5</font>"
            )
        }

        fun InfantPose(){
            infantPoseLayout.visibility = (View.VISIBLE)
        }

        fun StretchCobra(){
            cobraLayout.visibility = (View.VISIBLE)
        }

        fun SitUps(){
            val intent = Intent(this, SitUpsActivity::class.java)
            startActivity(intent)
        }

        fun JumpInPlace(){
            jumpLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_jump)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_jump)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_jump)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>30 секунд </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 3 до 5</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 4 до 8</font>"
            )
        }

        fun Plank(){
            plankLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_plank)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>1 минуту </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал) от 2 до 5</font>"
            )
        }

        fun PushUpsFloorWideSupport(){
            pushUpWideSupportLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_push_up_wide_support)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_push_up_wide_support)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_push_up_wide_support)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>12 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 2 до 5</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 3 до 8</font>"
            )
        }

        fun PushUpsKneeSupport(){
            pushUpKneeSupportLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_push_up_knee_support)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_push_up_knee_support)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_push_up_knee_support)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>12 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 2 до 5</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 3 до 8</font>"
            )
        }

        fun PushUpSupport(){
            pushUpsSupportLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_push_up_support)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_push_up_support)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_push_up_support)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>12 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 2 до 5</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 3 до 8</font>"
            )
        }

        fun HipsCrunchHalfLaid(){
            hipCrunchLayout.visibility = (View.VISIBLE)
        }

        fun Scissors(){
            scissorsLayout.visibility = (View.VISIBLE)
        }

        fun HipBridge(){
            hipBridgeLayout.visibility = (View.VISIBLE)
        }

        fun SidePlank(){
            sidePlankLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_side)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>30 секунд </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал) от 2.5 до 3.3</font>"
            )
        }

        fun HalfLaidCrunch(){
            halfLaidCrunchLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_half_crunch)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_half_crunch)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_half_crunch)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>12 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 1.6 до 3.2</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 1.9 до 6.7</font>"
            )
        }

        fun BulgarianSitUps(){
            val intent = Intent(this, BulgarianSitUpsActivity::class.java)
            startActivity(intent)
        }

        fun ArmsSpinnig(){
            val intent = Intent(this, ArmsSpinnigActivity::class.java)
            startActivity(intent)
        }

        fun ArmsUp(){
            val intent = Intent(this, ArmsUpActivity::class.java)
            startActivity(intent)
        }

        fun CatCow(){
            catCowLayout.visibility = (View.VISIBLE)
        }

        fun Catterpillar(){
            val intent = Intent(this, CatterpillarActivity::class.java)
            startActivity(intent)
        }

        fun ChestStretchDoor(){
            val intent = Intent(this, ChestStretchDoorActivity::class.java)
            startActivity(intent)
        }

        fun Crunch(){
            crunchLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_crunch)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_crunch)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_crunch)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>12 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 1.8 до 3.9</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 2.3 до 5.1</font>"
            )
        }

        fun ElbowsTogether(){
            elbowLogetherLayout.visibility = (View.VISIBLE)
        }

        fun Extention(){
            extentionLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_extention)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_extention)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_extention)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>12 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 1.5 до 4.6</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 1.8 до 5.2</font>"
            )
        }

        fun Lunge(){
            val intent = Intent(this, LungeActivity::class.java)
            startActivity(intent)
        }

        fun Swing(){
            swingLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_swing)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_swing)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_swing)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>12 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 2.1 до 3.7</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 2.4 до 4.8</font>"
            )
        }

        fun ReverseCrunch(){
            reverseCrunchLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_crunch)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_crunch)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_crunch)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>14 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 1.9 до 3.9</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 2.1 до 7.1</font>"
            )
        }

        fun FloorPushUps(){
            floorPushUpsLayout.visibility = (View.VISIBLE)

            val calBurn  = findViewById<TextView>(R.id.cal_burn_floor_push_ups)
            val lowIntensivity  = findViewById<TextView>(R.id.low_intensivity_floor_push_ups)
            val highIntensivity  = findViewById<TextView>(R.id.high_intensivity_floor_push_ups)

            calBurn.text= Html.fromHtml(
                "<font color=${Color.BLACK}>За </font>" +
                        "<font color=#338AF3>12 повторений </font>"+
                        "<font color=${Color.BLACK}>вы потратите<br></font>" +
                        "<font color=${Color.BLACK}>примерно (ккал):</font>"
            )

            lowIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>низкой интенсивности</u> — от 2 до 5</font>"
            )

            highIntensivity.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При <u>высокой интенсивности</u> — от 3 до 8</font>"
            )
        }
        //</editor-fold>

        when(intent.getStringExtra("ExerName")){
            "Alpinist" -> Alpinist()
            "SidePlank" -> SidePlank()
            "HalfLaidCrunch" -> HalfLaidCrunch()
            "BulgarianSitUps" -> BulgarianSitUps()
            "ArmsSpinnig" -> ArmsSpinnig()
            "ArmsUp" -> ArmsUp()
            "CatCow" -> CatCow()
            "Catterpillar" -> Catterpillar()
            "Lunge" -> Lunge()
            "Extention" -> Extention()
            "ChestStretchDoor" -> ChestStretchDoor()
            "Swing" -> Swing()
            "Scissors" -> Scissors()
            "ReverseCrunch" -> ReverseCrunch()
            "FloorPushUps" -> FloorPushUps()
            "PushUpSupport" -> PushUpSupport()
            "PushUpsKneeSupport" -> PushUpsKneeSupport()
            "PushUpsFloorWideSupport" -> PushUpsFloorWideSupport()
            "Plank" -> Plank()
            "InfantPose" -> InfantPose()
            "SitUps" -> SitUps()
            "JumpInPlace" -> JumpInPlace()
            "StretchCobra" -> StretchCobra()
            "ElbowsTogether" -> ElbowsTogether()
            "Crunch" -> Crunch()
            "HipBridge" -> HipBridge()
            "HipsCrunchHalfLaid" -> HipsCrunchHalfLaid()
        }

        backButton.setOnClickListener{
            val intent = Intent(this, TabsActivity::class.java)
            intent.putExtra("tab", "1")
            startActivity(intent)
        }
    }
}