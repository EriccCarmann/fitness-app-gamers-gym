package com.example.gamersgym

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_password)

        val db = DbHelper(this, null)

        val userPassword: EditText = findViewById(R.id.user_password_change)
        val userRePassword: EditText = findViewById(R.id.user_password_re_change)
        val button: Button = findViewById(R.id.button_change)
        val linkToAuth: ImageButton = findViewById(R.id.link_to_profile)

        val passHint: TextView = findViewById(R.id.pass_hint_recovery)
        val passReHint: TextView = findViewById(R.id.pass_re_hint_recovery)

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

        linkToAuth.setOnClickListener{
            val intent = Intent(this, TabsActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val pass = userPassword.text.toString()
            val repass = userRePassword.text.toString()
            val login = intent.getStringExtra("login")

            Log.d("i", login.toString())

            val passIsValid = RegularExpressions().isValidString(pass)

            if (pass == repass && passIsValid) {

                db.updatePasswordByLogin(pass, login.toString())

                Toast.makeText(this, "Пароль обновлен", Toast.LENGTH_SHORT).show()
            }
            else{
                if (pass != repass){
                    userPassword.setTextColor(Color.RED)
                    passHint.setText(R.string.pass_not_same)
                    passHint.setTextColor(Color.RED)

                    userRePassword.setTextColor(Color.RED)
                    passReHint.setText(R.string.pass_not_same)
                    passReHint.setTextColor(Color.RED)
                }
            }

        }
    }
}