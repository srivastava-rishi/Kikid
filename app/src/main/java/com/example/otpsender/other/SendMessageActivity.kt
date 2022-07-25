package com.example.otpsender.other

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.otpsender.R
import com.example.otpsender.base.BaseActivity
import com.example.otpsender.constant.Constant
import com.example.otpsender.data.database.MessageDatabase
import com.example.otpsender.data.entity.MessageEntity
import com.example.otpsender.databinding.ActivitySendMessageBinding
import com.example.otpsender.main.viewModel.MainViewModel
import com.example.otpsender.retrofit.UserNetworkEntity
import com.example.otpsender.util.DataReceiveEvent
import com.example.otpsender.util.EventTagType
import com.example.otpsender.util.RandomUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import javax.inject.Inject


@AndroidEntryPoint
class SendMessageActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivitySendMessageBinding

    val ACCOUNT_SID = Constant.ACCOUNT_SID
    val AUTH_TOKEN = Constant.AUTH_TOKEN

    val logTag = "@SendMessageActivity"

    var userDetailModel: UserNetworkEntity? = null

    var message: String? = null
    var fullMessage: String? = null

    @Inject
    lateinit var savedMessageDatabase: MessageDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_message)
        // set title
        binding.iCustomAppBar.tvTitle.text = "Message"
        message =  RandomUtil.generateRandomNumbers()
        fullMessage = "Hii Your OTP is: $message"
        // calling
        initViews()
        initData()
        initTheme()
        initAction()
    }

    private fun initViews(){
        binding.tvOtpMessage.text = "Hii Your OTP is: $message"
    }

    private fun initData() {
        userDetailModel = intent.getSerializableExtra(Constant.PARAM_USER_DETAIL) as? UserNetworkEntity
    }

    private fun initTheme() {
        window.statusBarColor = resources.getColor(R.color.Black)
        window.navigationBarColor = resources.getColor(R.color.lightBlack)
    }

    private fun initAction() {
        binding.btnHitSend.setOnClickListener(this)
        binding.iCustomAppBar.ivClose.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnHitSend -> {
                sendMessage(fullMessage!!,userDetailModel!!.phonenumber)
            }
            R.id.ivClose -> {
                finish()
            }
        }
    }

    private fun sendMessage(fullMessage : String, userNumber: String) {
        val from = ""

        val base64EncodedCredentials = "Basic " + Base64.encodeToString(
            "$ACCOUNT_SID:$AUTH_TOKEN".toByteArray(), Base64.NO_WRAP
        )
        val data: MutableMap<String, String> = HashMap()
        data["From"] = from
        data["To"] = userNumber
        data["Body"] = fullMessage

        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .build()
        val api = retrofit.create(TwilioApi::class.java)
        api.sendMessage(ACCOUNT_SID, base64EncodedCredentials, data)
            ?.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>?,
                    response: Response<ResponseBody?>
                ) {
                    if (response.isSuccessful) {
                        Log.d(logTag, "onResponse->success")
                        //calling it
                        buildSavedMessageEntity()
                        finish()
                    }else{
                        testToast("Number Not Registered")
                    }
                }
                override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                    Log.d(logTag, "onFailure")
                    testToast("Something wrong")
                }
            })
    }

    private fun saveMessage(item:MessageEntity){
        val savedMessageDao = savedMessageDatabase.savedMessageDao()
        CoroutineScope(Dispatchers.IO).launch {
            savedMessageDao.insertNewMessage(item)
            Log.d(logTag, "messageSaved ${item.id}")
        }
        EventBus.getDefault().post(DataReceiveEvent(EventTagType.EVENT_MESSAGE_ITEM_ADDED,item))
    }

    private fun buildSavedMessageEntity(){

        var name = userDetailModel!!.name + " " + userDetailModel!!.title
        var messageEntity = MessageEntity(
            id = 0,
            name = name,
            message = fullMessage!!,
            image = userDetailModel!!.image,
            savedTimestamp = System.currentTimeMillis()
        )
        saveMessage(messageEntity)

    }

    private fun testToast(message:String){
        val myToast = Toast.makeText(applicationContext,message,
            Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.LEFT,200,200)
        myToast.show()
    }

    internal interface TwilioApi {
        @FormUrlEncoded
        @POST("Accounts/{ACCOUNT_SID}/SMS/Messages")
        fun sendMessage(
            @Path("ACCOUNT_SID") accountSId: String?,
            @Header("Authorization") signature: String?,
            @FieldMap metadata: Map<String, String>?
        ): Call<ResponseBody?>?
    }


}