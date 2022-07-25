package com.example.otpsender.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.example.otpsender.main.MainActivity
import com.example.otpsender.R
import com.example.otpsender.base.BaseActivity
import com.example.otpsender.databinding.ActivitySplashBinding
import com.example.otpsender.util.UserListHelper

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        // to change the color of status bar and navigation bar.
        initTheme()
        clearList()
        gotoMainActivity()

    }

    private fun initTheme(){
        window.statusBarColor = resources.getColor(R.color.lightBlack)
        window.navigationBarColor = resources.getColor(R.color.lightBlack)
    }

    private fun clearList(){
        UserListHelper.userList.clear()
    }

    private fun gotoMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 500
        )
    }

}