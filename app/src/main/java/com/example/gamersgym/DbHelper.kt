package com.example.gamersgym

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.LocalDate

class DbHelper(val contex: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(contex, "DbGamersGym", factory, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("""CREATE TABLE users (
            id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT NULL, 
            login STRING, 
            URL STRING, 
            password TEXT,
            email TEXT,
            trainings INTEGER,
            level INTEGER,
            current_points INTEGER,
            points_to_levelup INTEGER,
            start_date STRING,
            is_booster_active INTEGER,
            is_premium_active INTEGER,
            booster_points INTEGER,
            booster_task_left INTEGER,
            days INTEGER
            )
        """)
        db.execSQL("""
            CREATE TABLE biometrics (
                bio_id INTEGER PRIMARY KEY AUTOINCREMENT,
                height FLOAT,
                weight FLOAT,
                max_weight FLOAT,
                min_weight FLOAT,
                gender STRING,
                user_id INTEGER,
               CONSTRAINT user_fk_biom FOREIGN KEY (user_id) REFERENCES users(id)
            )
        """)
        db.execSQL("""
            CREATE TABLE achievements (
                achievement_id INTEGER PRIMARY KEY AUTOINCREMENT,
                achievement_name STRING,
                current_level INTEGER,
                max_level INTEGER,
                reward INTEGER,
                current_points INTEGER,
                condition INTEGER,
                user_id INTEGER,
                done INTEGER,
                CONSTRAINT user_fk_achievement FOREIGN KEY (user_id) REFERENCES users(id)
            )
        """)
        db.execSQL("""
            CREATE TABLE tasks (
                task_id INTEGER PRIMARY KEY AUTOINCREMENT,
                task_name STRING,
                needed_points INTEGER,
                current_points INTEGER,
                reward INTEGER,
                user_id INTEGER,
                done INTEGER,
                CONSTRAINT user_fk_tasks FOREIGN KEY (user_id) REFERENCES users(id)
            )
        """)
        db.execSQL("""
            CREATE TABLE calorie_log (
                cal_id INTEGER PRIMARY KEY AUTOINCREMENT,
                date_day DATE,
                cal_burned DOUBLE,
                user_id INTEGER,
                CONSTRAINT user_fk_tasks FOREIGN KEY (user_id) REFERENCES users(id)
            )
        """)
        db.execSQL("""
            CREATE TABLE exercises (
                exer_id INTEGER PRIMARY KEY AUTOINCREMENT,
                tag_name STRING,
                name STRING,
                calories DOUBLE
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS biometrics")
        db.execSQL("DROP TABLE IF EXISTS achievements")
        db.execSQL("DROP TABLE IF EXISTS calorie_log")
        db.execSQL("DROP TABLE IF EXISTS tasks")
        db.execSQL("DROP TABLE IF EXISTS exercises")
        onCreate(db)
    }

    //<editor-fold desc="ЮЗЕР">
    fun addUser(user: User){
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put("login", user.login)
            put("URL", "")
            put("password", user.pass)
            put("email", user.email)
            put("trainings", user.trainings)
            put("level", 0)
            put("current_points", 0)
            put("points_to_levelup", user.points_to_levelup)
            put("is_booster_active", 0)
            put("is_premium_active", 0)
            put("booster_points", 0)
            put("booster_task_left", 0)
            put("start_date", user.date)
            put("days", 0)
        }

        db?.insert("users", null, values)

        db.close()
    }

    fun UpdateURL(URL: String, log: String){
        val db = writableDatabase
        val upd = db.compileStatement("UPDATE users SET URL = '$URL' WHERE login = '$log'")
        upd.executeUpdateDelete()
        db.close()
    }

    fun GetURL(login: String): String?{
        val db = readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT URL FROM users WHERE id = '$userId'", null)

        if (cursor.moveToFirst()) {
            val height = cursor.getString(0)
            return height
        }
        else{
            return null
        }
    }

    fun getRegDate(login: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT start_date FROM users WHERE login = '$login'", null)

        if (cursor.moveToFirst()) {
            val startDate = cursor.getString(0)
            return startDate
        }
        else{
            return null
        }
    }

    fun GetBoosterTaskLeft(login: String): Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT booster_task_left FROM users WHERE id = '$userId'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("booster_task_left")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun getBoosterStatus(login: String): Int?{
        val db = this.readableDatabase
        val userId = getUserIdByLogin(login)
        val cursor = db.rawQuery("SELECT is_booster_active FROM users WHERE id = '$userId'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("is_booster_active")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun getPasswordByLogin(login: String, pass: String): Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT password FROM users WHERE login = '$login' AND password = '$pass'", null)
        return cursor.moveToFirst()
    }

    fun UpdatePassword(pass: String, email: String){
        val db = writableDatabase
        val upd = db.compileStatement("UPDATE users SET password = '$pass' WHERE email = '$email'")
        upd.executeUpdateDelete()
        db.close()
    }

    fun updatePasswordByLogin(pass: String, log: String){
        val db = writableDatabase
        val upd = db.compileStatement("UPDATE users SET password = '$pass' WHERE login = '$log'")
        upd.executeUpdateDelete()
        db.close()
    }

    fun userExistsByEmail(email: String): Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE email = '$email'", null)
        return cursor.moveToFirst()
    }

    fun getUserByLogin(login: String): Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT login FROM users WHERE login = '$login'", null)
        return cursor.moveToFirst()
    }

    fun getUserIdByLogin(log: String): Int? {
        val db = this.readableDatabase

        val cursor  = db.rawQuery("SELECT id FROM users WHERE login = '$log'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("id")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun updateUserLevel(newLevel: Int, login: String){
        val db = writableDatabase
        val upd = db.compileStatement("UPDATE users SET level = '$newLevel' WHERE login = '$login'")
        upd.executeUpdateDelete()
        db.close()
    }

    fun getUserLevel(login: String): Int?{
        val db = writableDatabase

        val cursor = db.rawQuery("SELECT level FROM users WHERE login = '$login'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("level")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun updateUserCurrentPoints(newPoints: Int, login: String){
        val db = writableDatabase
        val upd = db.compileStatement("UPDATE users SET current_points = '$newPoints' WHERE login = '$login'")
        upd.executeUpdateDelete()
        db.close()
    }

    fun updateUserPointsToLevelUp(newPoints: Int, login: String){
        val db = writableDatabase
        val upd = db.compileStatement("UPDATE users SET points_to_levelup = '$newPoints' WHERE login = '$login'")
        upd.executeUpdateDelete()
        db.close()
    }

    fun getCurrentPoints(login: String): Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT current_points FROM users WHERE id = '$userId'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("current_points")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun getUserPointToLevelUpByLogin(log: String): Int? {
        val db = this.readableDatabase

        val cursor  = db.rawQuery("SELECT points_to_levelup FROM users WHERE login = '$log'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("points_to_levelup")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun getUser(login: String, pass: String): Boolean{
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND password = '$pass'", null)
        return result.moveToFirst()
    }

    @SuppressLint("Range")
    fun getUserByEmail(email: String): Any?{
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT email FROM users WHERE email = '$email'", null)
        return email
    }
    //</editor-fold>

    //<editor-fold desc="АЧИВКИ">
    fun addAchievement(achievement: Achievement, login: String){
        val db = this.writableDatabase

        val userId = getUserIdByLogin(login)

        val values = ContentValues().apply {
            put("achievement_name", achievement.name)
            put("current_level", achievement.current_level)
            put("max_level", achievement.max_level)
            put("reward", achievement.reward)
            put("condition", achievement.condition)
            put("current_points", 0)
            put("done", 0)
            put("user_id", userId)
        }

        db?.insert("achievements", null, values)

        db.close()
    }

    fun getAchievementCondition(achievement_name: String, login: String): Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT condition FROM achievements WHERE user_id = '$userId' AND achievement_name = '$achievement_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("condition")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun getAchievementMaxLevel(achievement_name: String, login: String): Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT max_level FROM achievements WHERE user_id = '$userId' AND achievement_name = '$achievement_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("max_level")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun getAchievementReward(achievement_name: String, login: String): Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT reward FROM achievements WHERE user_id = '$userId' AND achievement_name = '$achievement_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("reward")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun getCurrentAchievementLevel(achievement_name: String, login: String): Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT current_level FROM achievements WHERE user_id = '$userId' AND achievement_name = '$achievement_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("current_level")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun isAchievementDone(achievement_name: String, login: String): Int?{
        val db = this.readableDatabase
        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT done FROM achievements WHERE user_id = '$userId' AND achievement_name = '$achievement_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("done")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun getCurrentPointsAchievement(achievement_name: String, login: String):  Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT current_points FROM achievements WHERE user_id = '$userId' AND achievement_name = '$achievement_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("current_points")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }
    //</editor-fold>

    //<editor-fold desc="ЗАДАНИЯ">
    fun addTask(tasks: Task, login: String){
        val db = this.writableDatabase

        val userId = getUserIdByLogin(login)

        val values = ContentValues().apply {
            put("task_name", tasks.task_name)
            put("needed_points", tasks.condition)
            put("current_points", 0)
            put("reward", tasks.reward)
            put("user_id", userId)
            put("done", "0")
        }

        db?.insert("tasks", null, values)

        db.close()
    }

    fun SetTaskDone(task_name: String, login: String){
        val db = this.writableDatabase
        val userId = getUserIdByLogin(login)

        val upd = db.compileStatement("UPDATE tasks SET done = 1  WHERE user_id = '$userId' AND task_name = '$task_name'")

        upd.executeUpdateDelete()
    }

    fun ResetTask(task_name: String, login: String){
        val db = this.writableDatabase
        val userId = getUserIdByLogin(login)

        val upd = db.compileStatement("UPDATE tasks SET done = 0, current_points = 0 WHERE user_id = '$userId' AND task_name = '$task_name'")

        upd.executeUpdateDelete()
    }

    fun IsTaskDone(task_name: String, login: String): Int?{
        val db = this.readableDatabase
        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT done FROM tasks WHERE user_id = '$userId' AND task_name = '$task_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("done")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun updateUserLevelInfo(login: String, points: Int){
        val currentPointsUserLevel = getCurrentPoints(login)
        val pointToLevelUp = getUserPointToLevelUpByLogin(login)

        val newPoints = currentPointsUserLevel?.plus(points)

        if(newPoints!! >= pointToLevelUp!!){
            val pointsToKeep = newPoints - pointToLevelUp
            val userLevel = getUserLevel(login)?.plus(1)

            IncreaseOrFinishAchievement("StepByStep", login, userLevel!!)

            updateUserLevel(userLevel!!, login)
            updateUserPointsToLevelUp(pointToLevelUp + 15, login)
            updateUserCurrentPoints(pointsToKeep, login)
        }
        else{
            val addPoints = points + currentPointsUserLevel
            updateUserCurrentPoints(addPoints, login)
        }
    }

    fun GetNeededPointsTask(task_name: String, login: String): Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT needed_points FROM tasks WHERE user_id = '$userId' AND task_name = '$task_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("needed_points")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun GetCurrentPointsTask(task_name: String, login: String):  Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT current_points FROM tasks WHERE user_id = '$userId' AND task_name = '$task_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("current_points")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun GetTaskReward(task_name: String, login: String):  Int?{
        val db = this.readableDatabase

        val userId = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT reward FROM tasks WHERE user_id = '$userId' AND task_name = '$task_name'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("reward")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    //</editor-fold>

    //<editor-fold desc="УПРАЖНЕНИЯ">
    fun addExercise(exercise: Exercise){
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put("tag_name", exercise.tag_name)
            put("name", exercise.name)
            put("calories", exercise.calories)
        }

        db?.insert("exercises", null, values)

        db.close()
    }

    @SuppressLint("Range")
    fun getAllExerciseTags(): List<String> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT tag_name FROM exercises", null)

        val tags = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val calBurnedString = cursor.getString(cursor.getColumnIndex("tag_name"))
            tags.add(calBurnedString)
        }
        return tags
    }

    fun getExerciseNameByTag(tag: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT name FROM exercises WHERE tag_name = '$tag'", null)

        if (cursor.moveToFirst()) {
            val name = cursor.getString(0)
            return name
        }
        else{
            return null
        }

        cursor.close()
    }

    fun getExerciseCalByTag(tag: String): Double? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT calories FROM exercises WHERE tag_name = '$tag'", null)

        if (cursor.moveToFirst()) {
            val calories = cursor.getDouble(0)
            return calories
        }
        else{
            return null
        }

        cursor.close()
    }

    //</editor-fold>

    //<editor-fold desc="БИОМЕТРИКА">
    fun addBiometrics(biometrics: Biometrics, login: String) {
        val db = this.writableDatabase

        val userId = getUserIdByLogin(login)

        val values = ContentValues().apply {
            put("height", biometrics.height)
            put("weight", biometrics.weight)
            put("max_weight", biometrics.max_weight)
            put("min_weight", biometrics.min_weight)
            put("gender", biometrics.gender)
            put("user_id", userId)
        }

        db?.insert("biometrics", null, values)
        db.close()
    }

    fun getWeightByLogin(login: String): String?{
        val db = readableDatabase

        val log = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT weight  FROM biometrics WHERE user_id = '$log'", null)

        if (cursor.moveToFirst()) {
            val weight = cursor.getString(0)
            return weight
        }
        else{
            return null
        }
    }

    fun getMaxWeightByLogin(login: String): String?{
        val db = readableDatabase

        val log = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT max_weight  FROM biometrics WHERE user_id = '$log'", null)

        if (cursor.moveToFirst()) {
            val weight = cursor.getString(0)
            return weight
        }
        else{
            return null
        }
    }

    fun getMinWeightByLogin(login: String): String?{
        val db = readableDatabase

        val log = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT min_weight  FROM biometrics WHERE user_id = '$log'", null)

        if (cursor.moveToFirst()) {
            val weight = cursor.getString(0)
            return weight
        }
        else{
            return null
        }
    }

    fun updateWeight(weight: Float, login: String){
        val db = writableDatabase
        val userId = getUserIdByLogin(login)

        val upd = db.compileStatement("UPDATE biometrics SET weight = '$weight' WHERE user_id = '$userId'")

        upd.executeUpdateDelete()
        db.close()
    }

    fun updateMaxWeight(weight: Float, login: String){
        val db = writableDatabase
        val userId = getUserIdByLogin(login)

        val upd = db.compileStatement("UPDATE biometrics SET max_weight = '$weight' WHERE user_id = '$userId'")

        upd.executeUpdateDelete()
        db.close()
    }

    fun updateMinWeight(weight: Float, login: String){
        val db = writableDatabase
        val userId = getUserIdByLogin(login)

        val upd = db.compileStatement("UPDATE biometrics SET min_weight = '$weight' WHERE user_id = '$userId'")

        upd.executeUpdateDelete()
        db.close()
    }

    fun updateHeight(height: Float, login: String){
        val db = writableDatabase
        val userId = getUserIdByLogin(login)

        val upd = db.compileStatement("UPDATE biometrics SET height = '$height' WHERE user_id = '$userId'")

        upd.executeUpdateDelete()
        db.close()
    }

    fun getHeightByLogin(login: String): String?{
        val db = readableDatabase

        val log = getUserIdByLogin(login)

        val cursor = db.rawQuery("SELECT height  FROM biometrics WHERE user_id = '$log'", null)

        if (cursor.moveToFirst()) {
            val height = cursor.getString(0)
            return height
        }
        else{
            return null
        }
    }
    //</editor-fold>

    //<editor-fold desc="КАЛЛОРИИ">
    fun addCalorie(cal: Float, login: String){
        val db = this.writableDatabase

        val userId = getUserIdByLogin(login)

        val values = ContentValues().apply {
            put("date_day", LocalDate.now().toString())
            put("cal_burned", cal)
            put("user_id", userId)
        }

        db?.insert("calorie_log", null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getCalorie(login: String): List<Float>{
        val db = this.readableDatabase
        val calBurnedList = mutableListOf<Float>()
        val userId = getUserIdByLogin(login)

        val cursor  = db.rawQuery("SELECT cal_burned FROM calorie_log WHERE user_id = '$userId' order by cal_id desc limit 30", null)

        if (cursor.moveToFirst()) {
            do {
                val calBurned = cursor.getFloat(cursor.getColumnIndex("cal_burned"))
                calBurnedList.add(calBurned)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return calBurnedList
    }

    @SuppressLint("Range")
    fun InsertOrRewriteCalories(cal: Float, login: String){
        val db = this.writableDatabase
        val userId = getUserIdByLogin(login)
        var currentDate = ""
        var calBurned = 0f

        val cursor = db.rawQuery("SELECT date_day FROM calorie_log WHERE user_id = '$userId'", null)
        if (cursor.moveToLast()) {
            currentDate = cursor.getString(cursor.getColumnIndex("date_day"))
        }

        if(currentDate == LocalDate.now().toString()){

            val cursor = db.rawQuery("SELECT cal_burned from calorie_log WHERE date_day = '$currentDate'", null)

            if (cursor.moveToFirst()) {
                calBurned = cursor.getFloat(cursor.getColumnIndex("cal_burned"))
            }

            val totalCalBurned = calBurned + cal

            db.compileStatement("UPDATE calorie_log SET cal_burned = ? WHERE date_day = '$currentDate'").apply {
                bindDouble(1, totalCalBurned.toDouble())
                execute()
            }
        }
        else{
            val values = ContentValues().apply {
                put("date_day", LocalDate.now().toString())
                put("cal_burned", cal)
                put("user_id", userId)
            }
            db?.insert("calorie_log", null, values)
        }

        IncreaseOrFinishAchievement("FatBurner", login, cal.toInt())
        ChangeOrFinishTask(login,"BurnCalories", cal.toInt())

        cursor.close()
        db.close()
    }
    //</editor-fold>

    //<editor-fold desc="БУСТЕР">
    fun SetBoosterStatus(login: String, newStatus: Int){
        val db = writableDatabase
        val upd = db.compileStatement("UPDATE users SET is_booster_active = '$newStatus' WHERE login = '$login'")
        upd.executeUpdateDelete()

        if(newStatus == 1){
            val upd = db.compileStatement("UPDATE users SET booster_points = 0 WHERE login = '$login'")
            upd.executeUpdateDelete()
        }
        db.close()
    }

    fun updateCurrentBoosterPoints(add_booster_points: Int, login: String){
        val db = writableDatabase
        val currentPoints = getCurrentBoosterPoints(login)

        if(currentPoints!! < 100){
            var newPoints = currentPoints + add_booster_points

            if(newPoints > 100){
                newPoints = 100
            }

            val upd = db.compileStatement("UPDATE users SET booster_points = '$newPoints' WHERE login = '$login'")
            upd.executeUpdateDelete()
            db.close()
        }
    }

    fun getCurrentBoosterPoints(login: String): Int?{
        val db = writableDatabase

        val cursor = db.rawQuery("SELECT booster_points FROM users WHERE login = '$login'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("booster_points")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }

    fun UpdateBoosterTaskLeft(login: String, addCount: Int){
        val db = writableDatabase

        val userId = getUserIdByLogin(login)

        val newTaskCount = GetBoosterTaskLeft(login)?.plus(addCount)

        val upd = db.compileStatement("UPDATE users SET booster_task_left = '$newTaskCount' WHERE id = '$userId'")
        upd.executeUpdateDelete()

        if(GetBoosterTaskLeft(login!!) == 0 && getBoosterStatus(login!!) == 1){
            SetBoosterStatus(login!!, 0)
            SetPremiumStatus(login!!, 0)
        }

        db.close()
    }
    //</editor-fold>

    //<editor-fold desc="ПРЕМИУМ">
    fun SetPremiumStatus(login: String, newStatus: Int){
        val db = writableDatabase
        val upd = db.compileStatement("UPDATE users SET is_premium_active = '$newStatus' WHERE login = '$login'")
        upd.executeUpdateDelete()

        if(newStatus == 1){
            UpdateBoosterTaskLeft(login, 5)
        }
        db.close()
    }

    fun GetPremiumStatus(login: String): Int?{
        val db = writableDatabase

        val cursor = db.rawQuery("SELECT is_premium_active FROM users WHERE login = '$login'", null)

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("is_premium_active")
            return cursor.getInt(columnIndex)
        }
        else {
            return null
        }
    }
    //</editor-fold>

    fun ChangeOrFinishTask(login: String, taskName: String, addPoints: Int){
        val db = writableDatabase
        val userId = getUserIdByLogin(login)

        val isDoWorkoutDone = IsTaskDone(taskName, login)

        val isPremiumActive = GetPremiumStatus(login)

        if(taskName == "DoWorkouts" && isPremiumActive == 0){
            db.close()
            return
        }
        else{
            if(isDoWorkoutDone == 0){
                val isBoosterActive = getBoosterStatus(login)
                val isPremiumActive = GetPremiumStatus(login)

                var boosterMultiplier: Float
                var premiumMultiplier: Float

                if(isBoosterActive == 1){
                    boosterMultiplier = 1.5f
                }
                else boosterMultiplier = 1f

                if(isPremiumActive == 1){
                    premiumMultiplier = 1.5f
                }
                else premiumMultiplier = 1f

                var newPoints = GetCurrentPointsTask(taskName, login)?.plus(addPoints)!!

                val updTask = db.compileStatement("UPDATE tasks SET current_points = '$newPoints' WHERE user_id = '$userId' AND task_name = '$taskName'")
                updTask.executeUpdateDelete()

                val currentPoints = GetCurrentPointsTask(taskName, login)
                val neededPoints = GetNeededPointsTask(taskName, login)

                val reward = GetTaskReward(taskName, login)

                if(currentPoints!! >= neededPoints!!){
                    val updTask = db.compileStatement("UPDATE tasks SET current_points = '$neededPoints' WHERE user_id = '$userId' AND task_name = '$taskName'")
                    updTask.executeUpdateDelete()
                    SetTaskDone(taskName, login)

                    updateUserLevelInfo(login, reward!!.times(boosterMultiplier).toInt())

                    updateCurrentBoosterPoints(reward!!.times(premiumMultiplier).toInt(), login)

                    if(getBoosterStatus(login) == 1 || GetPremiumStatus(login) == 1){
                        UpdateBoosterTaskLeft(login, -1)
                    }

                    IncreaseOrFinishAchievement("StartedAndFinished", login, 1)
                }
            }
        }
        db.close()
    }

    fun IncreaseOrFinishAchievement(achName: String, login: String, addPoints: Int){
        val db = writableDatabase
        val userId = getUserIdByLogin(login)

        val isDone = isAchievementDone(achName, login)

        val isPremiumActive = GetPremiumStatus(login)

        if(achName == "Wealth" && isPremiumActive == 0){
            db.close()
            return
        }else{
            if(isDone == 0){
                var newPoints = getCurrentPointsAchievement(achName, login)?.plus(addPoints)!!

                val updAch = db.compileStatement("UPDATE achievements SET current_points = '$newPoints' WHERE " +
                        "user_id = '$userId' AND achievement_name = '$achName'")
                updAch.executeUpdateDelete()

                val currentPoints = getCurrentPointsAchievement(achName, login)
                val neededPoints = getAchievementCondition(achName, login)
                val currentReward = getAchievementReward(achName, login)

                if(currentPoints!! >= neededPoints!!){
                    val newPoints = currentPoints - neededPoints
                    var newCondition = 0
                    var newReward = 0

                    when(achName){
                        "MoreWorkouts" -> {
                            newCondition = neededPoints * 2
                            newReward = currentReward!!
                        }
                        "FatBurner" -> {
                            newCondition = neededPoints + 1000
                            newReward = currentReward!! + 6
                        }
                        "StartedAndFinished" -> {
                            newCondition = neededPoints + 2
                            newReward = currentReward!! + 4
                        }
                        "StepByStep" -> {
                            newCondition = neededPoints + 1
                            newReward = currentReward!! + 5
                        }
                        "Halt" -> {
                            newCondition = neededPoints + 3
                            newReward = currentReward!! + 5
                        }
                        "Wealth" -> {
                            newCondition = neededPoints + 3
                            newReward = currentReward!! + 5
                        }
                    }

                    val newLevel = getCurrentAchievementLevel(achName, login)?.plus(1)

                    val upd = db.compileStatement("UPDATE achievements " +
                            "SET current_points = '$newPoints', current_level = '$newLevel', " +
                            "condition = '$newCondition', reward = '$newReward'" +
                            "WHERE user_id = '$userId' AND achievement_name = '$achName'")

                    upd.executeUpdateDelete()

                    val reward = getAchievementReward(achName, login)

                    updateUserLevelInfo(login, reward!!.toInt())

                    val maxLevel = getAchievementMaxLevel(achName, login)
                    if(newLevel == maxLevel){
                        val upd = db.compileStatement("UPDATE achievements SET done = 1 WHERE " +
                                "user_id = '$userId' AND achievement_name = '$achName'")
                        upd.executeUpdateDelete()
                    }
                }
            }
        }
    }
}