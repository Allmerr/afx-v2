package com.example.food_xyz_v1

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.food_xyz_v1.menu_activity_fragment.MainMenuActivity
import com.example.food_xyz_v1.menu_activity_fragment.ProfileMenuActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        val idUser = intent?.getStringExtra("id_user").toString()
        val bundle = Bundle()
        bundle.putString("id_user", idUser)

        val mainMenuActivity = MainMenuActivity()
        val profileMenuActivity = ProfileMenuActivity()

        mainMenuActivity.arguments = bundle
        profileMenuActivity.arguments = bundle

        setFragmentActivity(mainMenuActivity)

        findViewById<BottomNavigationView>(R.id.bottom_navigation_view).setOnItemSelectedListener {
            when(it.itemId){
                R.id.mi_cart -> setFragmentActivity(mainMenuActivity)
                R.id.mi_person -> setFragmentActivity(profileMenuActivity)
            }
            true
        }
    }

    private fun setFragmentActivity(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment_menu_activity,fragment)
            commit()
        }
    }
}