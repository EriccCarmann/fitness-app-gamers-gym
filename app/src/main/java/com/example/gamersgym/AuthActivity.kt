package com.example.gamersgym

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)

        val userLogin: EditText = findViewById(R.id.user_login_auth)
        val userPassword: EditText = findViewById(R.id.user_password_auth)
        val button: Button = findViewById(R.id.button_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)
        val linkToReсovery: TextView = findViewById(R.id.link_to_recovery)

        val loginHint: TextView = findViewById(R.id.login_hint_auth)
        val passHint: TextView = findViewById(R.id.pass_hint_auth)

        userLogin.addTextChangedListener {
            userLogin.setTextColor(Color.BLACK)
            loginHint.setTextColor(Color.WHITE)
        }

        userPassword.addTextChangedListener {
            userPassword.setTextColor(Color.BLACK)
            passHint.setTextColor(Color.WHITE)
        }

        linkToReg.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        linkToReсovery.setOnClickListener{
            val intent = Intent(this, RecoveryActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val db = DbHelper(this, null)

            val login = userLogin.text.toString().trim()
            val pass = userPassword.text.toString().trim()

            val isAuth = db.getUser(login, pass)
            val passIsValid = db.getPasswordByLogin(login, pass)
            val userExists = db.getUserByLogin(login)

            if (login != "" && pass != "" && isAuth){
                var pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                var editor = pref.edit()
                editor.putString("USER_LOGIN", login);
                editor.putString("USER_HEIGHT", db.getHeightByLogin(login));
                editor.putString("USER_WEIGHT", db.getWeightByLogin(login));
                editor.putBoolean("LOGGED_IN", true);
                editor.apply();
                val intent = Intent(this, TabsActivity::class.java)
                startActivity(intent)
            }
            else{
                if(login == "" || pass == ""){
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                }
                if(!userExists){
                    userLogin.setTextColor(Color.RED)
                    loginHint.setTextColor(Color.RED)
                }
                if (!passIsValid){
                    userPassword.setTextColor(Color.RED)
                    passHint.setTextColor(Color.RED)
                }
            }
        }
    }
}