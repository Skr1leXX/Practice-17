package com.example.practice17

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val themeKey = "theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme() // Устанавливаем тему
        setContentView(R.layout.activity_main) // Устанавливаем layout
        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
    }

    private fun setAppTheme() {
        // Получаем текущую тему из SharedPreferences
        val theme = sharedPreferences.getString(themeKey, "light")
        if (theme == "dark") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // Загружаем меню
        updateThemeMenuItem(menu) // Обновляем текст пункта меню для смены темы
        return true
    }

    private fun updateThemeMenuItem(menu: Menu?) {
        val themeMenuItem = menu?.findItem(R.id.action_switch_theme)
        val currentTheme = sharedPreferences.getString(themeKey, "light")
        themeMenuItem?.title = if (currentTheme == "dark") "Сменить на светлую" else "Сменить на тёмную"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_theme -> {
                toggleTheme() // Смена темы
                true
            }
            R.id.action_about -> {
                openAboutActivity() // Открытие AboutActivity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleTheme() {
        val currentTheme = sharedPreferences.getString(themeKey, "light")
        val newTheme = if (currentTheme == "light") "dark" else "light"
        sharedPreferences.edit().putString(themeKey, newTheme).apply()
        recreate() // Перезапускаем активность для применения новой темы
    }

    private fun openAboutActivity() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent) // Переход на AboutActivity
    }
}