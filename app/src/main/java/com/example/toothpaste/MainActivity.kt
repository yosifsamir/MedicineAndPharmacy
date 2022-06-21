package com.example.toothpaste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val navController = findNavController(R.id.myNavHostFragment)
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/events")
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val intent=intent
        if (intent!=null){
            if (intent.getBooleanExtra("server",false)){
//                navController.navigate(R.id.homeFragment)
                if (navController.currentDestination!!.id !=R.id.eventFragment)
                    navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }

    }
}