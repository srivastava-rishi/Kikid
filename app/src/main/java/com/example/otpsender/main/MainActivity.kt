package com.example.otpsender.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.otpsender.R
import com.example.otpsender.base.BaseActivity
import com.example.otpsender.databinding.ActivityMainBinding
import com.example.otpsender.explore.ExploreFragment
import com.example.otpsender.message.MessageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() , View.OnClickListener  {

    lateinit var binding: ActivityMainBinding

     val logTag = "@MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initTheme()
        initAction()
        changeFragment(MainActivityFragmentTag.EXPLORE)
    }

    private fun initTheme(){
        window.statusBarColor = resources.getColor(R.color.Black)
        window.navigationBarColor = resources.getColor(R.color.lightBlack)
    }

    private fun initAction() {
        binding.iBottomNav.rlExplore.setOnClickListener(this)
        binding.iBottomNav.rlMessage.setOnClickListener(this)
    }

    private fun changeFragment(mainActivityFragmentTag: MainActivityFragmentTag) {

        when (mainActivityFragmentTag) {
            MainActivityFragmentTag.EXPLORE -> {
                setBottomNavView(mainActivityFragmentTag)
                attachFragment(
                    R.id.flFragmentContainer,
                    ExploreFragment(),
                    MainActivityFragmentTag.EXPLORE.name
                )
            }
            MainActivityFragmentTag.MESSAGE -> {
                setBottomNavView(mainActivityFragmentTag)
                attachFragment(
                    R.id.flFragmentContainer,
                    MessageFragment(),
                    MainActivityFragmentTag.MESSAGE.name
                )
            }
        }
    }

    private fun setBottomNavView(mainActivityFragmentTag: MainActivityFragmentTag) {

        when (mainActivityFragmentTag) {

            MainActivityFragmentTag.EXPLORE -> {
                binding.iBottomNav.ivExplore.setColorFilter(resources.getColor(R.color.coloredWhite))
                binding.iBottomNav.ivMessage.setColorFilter(resources.getColor(R.color.lightWhite))
            }
            MainActivityFragmentTag.MESSAGE -> {
                binding.iBottomNav.ivExplore.setColorFilter(resources.getColor(R.color.lightWhite))
                binding.iBottomNav.ivMessage.setColorFilter(resources.getColor(R.color.coloredWhite))
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rlExplore -> {
                changeFragment(MainActivityFragmentTag.EXPLORE)
            }
            R.id.rlMessage -> {
                changeFragment(MainActivityFragmentTag.MESSAGE)
            }
        }
    }



}