package com.app.wocare

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var navbar: BottomNavigationView
    lateinit var tv: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tv = findViewById(R.id.tv)
        navbar = findViewById(R.id.botnavbar)
        navbar.itemIconTintList = null
        navbar.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.woask -> {
                    loadFragments(WoAskFragment())
                    tv.text = "WoAsk"
                    true
                }
                R.id.today -> {
                    tv.text = "Today"
                    loadFragments(TodayFragment())
                    true
                }
                R.id.profile -> {
                    tv.text = "Profile"
                    loadFragments(ProfileFragment())
                    true
                }
                R.id.insight -> {
                    tv.text = "Insight"
                    loadFragments(InsightFragment())
                    true
                }
                R.id.camera -> {
                    loadFragments(CameraFragment())
                    tv.text = "Camera"
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragments(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
    }

}