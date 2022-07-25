package com.example.otpsender.other

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.otpsender.R
import com.example.otpsender.base.BaseActivity
import com.example.otpsender.constant.Constant
import com.example.otpsender.databinding.ActivityUserDetailBinding
import com.example.otpsender.retrofit.UserNetworkEntity
import java.io.Serializable

class UserDetailActivity : BaseActivity() , View.OnClickListener {

    lateinit var binding: ActivityUserDetailBinding
    var userDetailModel: UserNetworkEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)

        // set title
        binding.iCustomAppBar.tvTitle.text = "Info"
        initTheme()
        initData()
        initAction()
    }
    private fun initTheme(){
        window.statusBarColor = resources.getColor(R.color.Black)
        window.navigationBarColor = resources.getColor(R.color.lightBlack)
    }

    private fun initAction() {
        binding.btnSendMessage.setOnClickListener(this)
        binding.iCustomAppBar.ivClose.setOnClickListener(this)
    }

    private fun initData() {
        userDetailModel = intent.getSerializableExtra(Constant.PARAM_USER_DETAIL) as? UserNetworkEntity
        initViews(userDetailModel!!)
    }

    private fun initViews(userInfoModel : UserNetworkEntity) {

        binding.tvActualName.text = userInfoModel.name + " "  +userInfoModel.title
        binding.tvActualPhoneNumber.text = userInfoModel.phonenumber

        Glide
            .with(this)
            .load(userInfoModel.image)
            .thumbnail(0.7f)
            .centerCrop()
            .placeholder(R.drawable.img_default_profile_light)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .into(binding.civUserPicture)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSendMessage -> {
                if(!userDetailModel!!.eligible){
                    val myToast = Toast.makeText(applicationContext,"your number is not register yet please request for otp",
                        Toast.LENGTH_SHORT)
                    myToast.setGravity(Gravity.LEFT,200,200)
                    myToast.show()
                }else{
                    val intent = Intent(this@UserDetailActivity , SendMessageActivity::class.java)
                    intent.putExtra(Constant.PARAM_USER_DETAIL, userDetailModel as Serializable)
                    startActivity(intent)
                }

            }
            R.id.ivClose -> {
                finish()
            }
        }
    }

}