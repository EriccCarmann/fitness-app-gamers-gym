package com.example.gamersgym

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.addTextChangedListener
import com.example.gamersgym.databinding.ActivityMainBinding
import java.time.LocalDate


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return

        val hasData = sharedPreferences.contains("USER_LOGIN")
        val isLoggedInExists = sharedPreferences.contains("LOGGED_IN")

        if(isLoggedInExists && hasData){
            val isLoggedIn = sharedPreferences.getBoolean("LOGGED_IN", false)

            if (isLoggedIn) {
                val intent = Intent(this, TabsActivity::class.java)
                startActivity(intent)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPassword: EditText = findViewById(R.id.user_password)
        val userRePassword: EditText = findViewById(R.id.user_re_password)
        val userEmail: EditText = findViewById(R.id.user_email)
        val button: Button = findViewById(R.id.button_reg)
        val linkToAuth: TextView = findViewById(R.id.link_to_auth)

        val loginHint: TextView = findViewById(R.id.login_hint_reg)
        val passHint: TextView = findViewById(R.id.pass_hint_reg)
        val passReHint: TextView = findViewById(R.id.pass_re_hint_reg)
        val emailHint: TextView = findViewById(R.id.email_hint_reg)

        linkToAuth.setOnClickListener{
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        //<editor-fold desc="CHANGE TEXT AND HINTS IF INPUT IS INCORRECT">
        userPassword.addTextChangedListener{
            userPassword.setTextColor(Color.BLACK)
            passHint.setText(R.string.log_pass)
            passHint.setTextColor(Color.WHITE)
        }

        userRePassword.addTextChangedListener{
            userRePassword.setTextColor(Color.BLACK)
            passReHint.setText(R.string.log_pass)
            passReHint.setTextColor(Color.WHITE)
        }

        userEmail.addTextChangedListener {
            userEmail.setTextColor(Color.BLACK)
            emailHint.setTextColor(Color.WHITE)
        }

        userLogin.addTextChangedListener {
            userLogin.setTextColor(Color.BLACK)
            loginHint.setTextColor(Color.WHITE)
        }
        //</editor-fold>

        button.setOnClickListener {
            val login = userLogin.text.toString()
            val pass = userPassword.text.toString()
            val repass = userRePassword.text.toString()
            val email = userEmail.text.toString()
            val db = DbHelper(this, null)

            val passIsValid = RegularExpressions().isValidString(pass)
            val rePassIsValid = RegularExpressions().isValidString(repass)
            val loginIsValid = RegularExpressions().isValidString(login)
            val emailIsValid = RegularExpressions().isValidStringEmail(email)

            //<editor-fold desc="ПРОВЕРКА">
            if (passIsValid && emailIsValid && loginIsValid && rePassIsValid &&
                login != "" && pass != "" && email != ""){
                val user = User(login, pass, email, LocalDate.now().toString(), 0, 15)

                db.addUser(user)

                Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()

                val intent = Intent(this, BiometricsActivity::class.java)
                intent.putExtra("userLogin", login)
                startActivity(intent)
            }
            else{
                if(login == "" || pass == "" || email == ""){
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                }

                if(pass != repass){ //ПАРОЛИ НЕ СОВПАДАЮТ
                    userPassword.setTextColor(Color.RED)
                    passHint.setText(R.string.pass_not_same)
                    passHint.setTextColor(Color.RED)

                    userRePassword.setTextColor(Color.RED)
                    passReHint.setText(R.string.pass_not_same)
                    passReHint.setTextColor(Color.RED)
                }
                else { //ПАРОЛЬ НЕ ПОДХОДИТ
                    if (!passIsValid)
                    {
                        userPassword.setTextColor(Color.RED)
                        passHint.setTextColor(Color.RED)
                    }
                    if (!rePassIsValid)
                    {
                        userRePassword.setTextColor(Color.RED)
                        passReHint.setTextColor(Color.RED)
                    }
                }

                if (!emailIsValid){ //ПОЧТА НЕ ПОДХОДИТ
                    userEmail.setTextColor(Color.RED)
                    emailHint.setTextColor(Color.RED)
                }

                if(!loginIsValid){ //ЛОГИН НЕ ПОДХОДИТ
                    userLogin.setTextColor(Color.RED)
                    loginHint.setTextColor(Color.RED)
                }
            }
            //</editor-fold>
        }
    }
}