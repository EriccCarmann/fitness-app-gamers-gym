package com.example.gamersgym

class RegularExpressions {

    fun isValidString(input: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9-_]{3,}$")
        return regex.matches(input)
    }

    fun isValidStringEmail(input: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }
}