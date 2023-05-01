package com.route.chatc37.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.route.chatc37.R
import com.route.chatc37.UserProvider
import com.route.chatc37.database.FireStoreUtils
import com.route.chatc37.database.models.User
import com.route.chatc37.ui.home.HomeActivity
import com.route.chatc37.ui.login.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper())
            .postDelayed({
                navigate();
            },2000);
    }
    fun navigate(){
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser ==null){
            gotoLogin();
            return
        }
        FireStoreUtils()
            .getUserFromDatabase(auth.currentUser?.uid!!)
            .addOnCompleteListener{
                if(!it.isSuccessful){
                    gotoLogin()
                    return@addOnCompleteListener
                }
                val user = it.result.toObject(User::class.java)
                UserProvider.user = user
                gotoHome()
            }
    }

    fun gotoHome(){
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun gotoLogin(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}