package com.app.wocare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var navbar: BottomNavigationView
    private lateinit var judulFragment: TextView
    private lateinit var profilePengguna: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //  define id
        profilePengguna = findViewById(R.id.rl1)
        judulFragment = findViewById(R.id.tv)
        navbar = findViewById(R.id.botnavbar)

        //  value
        val woask = "WoAsk"
        val profile = "Profile"
        val camera = "Camera"
        val insight = "Insight"

        navbar.itemIconSize = 80
        navbar.itemIconTintList = null
        navbar.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.woask -> {
                    hideProfile(woask)
                    loadFragments(WoAskFragment())
                    true
                }
                R.id.today -> {
                    showProfile()
                    loadFragments(TodayFragment())
                    true
                }
                R.id.profile -> {
                    hideProfile(profile)
                    loadFragments(ProfileFragment())
                    true
                }
                R.id.insight -> {
                    hideProfile(insight)
                    loadFragments(InsightFragment())
                    true
                }
                R.id.camera -> {
                    hideProfile(camera)
                    loadFragments(CameraFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
        navbar.selectedItemId = R.id.today
    }

    private fun showProfile() {
        profilePengguna.visibility = View.VISIBLE
        judulFragment.visibility = View.GONE
    }

    private fun hideProfile(data: String) {
        profilePengguna.visibility = View.GONE
        judulFragment.visibility = View.VISIBLE
        judulFragment.text = data
    }

    private fun loadFragments(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
    }

}