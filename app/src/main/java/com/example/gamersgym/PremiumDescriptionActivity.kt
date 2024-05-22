package com.example.gamersgym

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PremiumDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_premium_description)

        val db = DbHelper(this, null)
        val preferences = this.getSharedPreferences(
            "MyPref",
            MODE_PRIVATE
        )
        val benefitsDescription = findViewById<TextView>(R.id.benefits_description)
        val access = findViewById<TextView>(R.id.access)
        val coupon = findViewById<TextView>(R.id.coupon)
        val accumulation = findViewById<TextView>(R.id.accumulation)
        val boosterDesc = findViewById<TextView>(R.id.booster_desc)
        val backButton  = findViewById<ImageView>(R.id.back_to_tabs)

        val activatePremiumButton: Button  = findViewById(R.id.activate_premium_button)

        val login = preferences.getString("USER_LOGIN", "")

        activatePremiumButton.setOnClickListener {
            val isPremiumActive = db.GetPremiumStatus(login!!)
            if(isPremiumActive == 0){
                CustomDialogueBoxes().showYesNoDialog(this,"Вы уверены, что хотите купить премиум?",
                    "Нажмая на кнопку \"Подтвердить\" вы соглашаетесь на покупку премиума.", {
                        db.SetPremiumStatus(login!!, 1)
                    })
            }else{
                CustomDialogueBoxes().showDialog(this, "Невозможно совершить действие",
                    "Премиум активен!")
            }

        }

        backButton.setOnClickListener{
            val intent = Intent(this, TabsActivity::class.java)
            intent.putExtra("tab", "4")
            startActivity(intent)
        }

        benefitsDescription.text= Html.fromHtml(
                "<font color=${Color.BLACK}>При подписке </font>" +
                "<font color=#338AF3> PREMIUM </font>" +
                "<font color=${Color.BLACK}>на </font>" +
                "<font color=#338AF3> месяц<br></font>" +
                "<font color=${Color.BLACK}>пользователь получит следующие<br></font>" +
                "<font color=${Color.BLACK}>преимущества:</font>"
        )

        access.text= Html.fromHtml(
            "<font color=${Color.BLACK}>Получение доступа к<br></font>" +
                    "<font color=#338AF3>эксклюзивным заданиям</font>"
        )

        coupon.text= Html.fromHtml(
                    "<font color=#338AF3>Увеличенный шанс </font>" +
                    "<font color=${Color.BLACK}>на получение<br></font>" +
                    "<font color=${Color.BLACK}>подарочного купона в магазинах-партнёрах</font>"
        )

        accumulation.text= Html.fromHtml(
            "<font color=${Color.BLACK}>Ускоренное в </font>" +
                    "<font color=#338AF3>x1,5 </font>" +
                    "<font color=${Color.BLACK}>раза накопление<br></font>" +
                    "<font color=${Color.BLACK}>бонусных баллов на </font>" +
                    "<font color=#338AF3>усилитель опыта</font>"
        )

        boosterDesc.text= Html.fromHtml(
                    "<font color=#338AF3>Увеличение количества </font>" +
                    "<font color=${Color.BLACK}>тренировок с<br></font>" +
                    "<font color=${Color.BLACK}>бустером опыта</font>"
        )
    }
}