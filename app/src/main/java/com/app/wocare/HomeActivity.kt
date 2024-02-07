package com.app.wocare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.app.wocare.homeFragments.InsightFragment
import com.app.wocare.homeFragments.MonthFragment
import com.app.wocare.homeFragments.ProfileFragment
import com.app.wocare.homeFragments.TodayFragment
import com.app.wocare.homeFragments.WoAskFragment
import com.app.wocare.homeFragments.YearFragment
import com.app.wocare.models.UserDetails
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var navbar: BottomNavigationView
    private lateinit var judulFragment: TextView
    private lateinit var profilePengguna: RelativeLayout
    private lateinit var bgJudul: RelativeLayout
    private lateinit var btnSwitchCalender: RelativeLayout
    private lateinit var btnBulan: TextView
    private lateinit var showMonth: TextView
    private lateinit var showYear: TextView
    private lateinit var namaProfile: TextView
    private lateinit var uid: String
    private val mAuth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //  define id
        profilePengguna = findViewById(R.id.rl1)
        judulFragment = findViewById(R.id.tv)
        bgJudul = findViewById(R.id.rl)
        navbar = findViewById(R.id.botnavbar)
        btnBulan = findViewById(R.id.btnMonth)
        btnSwitchCalender = findViewById(R.id.btnDate)
        showMonth = findViewById(R.id.showMonth)
        showYear = findViewById(R.id.showYear)
        namaProfile = findViewById(R.id.tvNama)

        //  get Data current User
        val fUser = mAuth.currentUser
        uid = fUser!!.uid
        getDataProfileFromFirebase(uid)
        setNameMonth()

        btnBulan.setOnClickListener {
            hideProfileShowDate()
            setBackgroundbtn(showMonth, showYear)
            loadFragments(MonthFragment())
        }

        showMonth.setOnClickListener {
            setBackgroundbtn(showMonth, showYear)
            loadFragments(MonthFragment())
        }

        showYear.setOnClickListener {
            setBackgroundbtn(showYear, showMonth)
            loadFragments(YearFragment())
        }

        //  value
        val woask = "WoAsk"
        val profile = "Profile"
        val camera = "Camera"
        val insight = "Insight"

        //  navbar
        navbar.itemIconSize = 90
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

    private fun setNameMonth() {
        val calendar = Calendar.getInstance()
        val simpleFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val monthName = simpleFormat.format(calendar.time)
        btnBulan.text = monthName
    }

    private fun setBackgroundbtn(btnFirst: TextView, btnSecond: TextView) {
        btnFirst.setBackgroundResource(R.drawable.bg_white_rounded_2)
        btnFirst.setTextColor(ContextCompat.getColor(this, R.color.main))
        btnSecond.setBackgroundResource(R.color.main)
        btnSecond.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun hideProfileShowDate() {
        profilePengguna.visibility = View.GONE
        btnSwitchCalender.visibility = View.VISIBLE
    }

    private fun showProfile() {
        profilePengguna.visibility = View.VISIBLE
        bgJudul.visibility = View.GONE
        btnSwitchCalender.visibility = View.GONE
    }

    private fun hideProfile(data: String) {
        profilePengguna.visibility = View.GONE
        bgJudul.visibility = View.VISIBLE
        btnSwitchCalender.visibility = View.GONE
        judulFragment.text = data
    }

    private fun loadFragments(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    private fun getDataProfileFromFirebase(uid: String) {
        val db = FirebaseDatabase.getInstance().getReference("Users")

        db.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val userData = snapshot.getValue(UserDetails::class.java)
                    if (userData != null){
                        val nama = userData.username
                        fetchingData(nama)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun fetchingData(nama: String?) {
        namaProfile.text = nama
    }

}