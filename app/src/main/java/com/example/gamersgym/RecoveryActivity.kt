package com.example.gamersgym

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class RecoveryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recovery)

        val userPassword: EditText = findViewById(R.id.user_password_rec)
        val userRePassword: EditText = findViewById(R.id.user_password_re_rec)
        val userEmail: EditText = findViewById(R.id.user_email_rec)
        val button: Button = findViewById(R.id.button_re)
        val linkToAuth: ImageButton = findViewById(R.id.link_to_auth)

        val emailHint: TextView = findViewById(R.id.email_hint_recovery)
        val passHint: TextView = findViewById(R.id.pass_hint_recovery)
        val passReHint: TextView = findViewById(R.id.pass_re_hint_recovery)

        linkToAuth.setOnClickListener{
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

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

        button.setOnClickListener {
            val email = userEmail.text.toString()
            val pass = userPassword.text.toString()
            val repass = userRePassword.text.toString()

            val db = DbHelper(this, null)
            val user = db.getUserByEmail(email)

            val passIsValid = RegularExpressions().isValidString(pass)
            val emailIsValid = RegularExpressions().isValidStringEmail(email)
            val emailExists = db.userExistsByEmail(email)

            if (user != null && pass == repass
                && passIsValid && emailIsValid
                && emailExists) {

                db.UpdatePassword(pass, email)

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
                if (!emailExists){
                    userEmail.setTextColor(Color.RED)
                    emailHint.setTextColor(Color.RED)
                }
            }

        }
    }
}