package com.example.otpsender.message

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otpsender.R
import com.example.otpsender.data.database.MessageDatabase
import com.example.otpsender.data.helper.MessageHelper
import com.example.otpsender.databinding.FragmentMessageBinding
import com.example.otpsender.explore.adapter.ExploreAdapter
import com.example.otpsender.main.viewModel.MainViewModel
import com.example.otpsender.message.adapter.MessageAdapter
import com.example.otpsender.util.DataReceiveEvent
import com.example.otpsender.util.EventTagType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@AndroidEntryPoint
class MessageFragment : Fragment()  {

    lateinit var binding: FragmentMessageBinding

    private lateinit var messageAdapter: MessageAdapter

    val logTag = "@MessageFragment"


    @Inject
    lateinit var messsageDatabase: MessageDatabase

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: DataReceiveEvent) {
        when {
            event.isTagMatchWith(EventTagType.EVENT_MESSAGE_ITEM_ADDED) -> {
                val newMessage = event.getNewMessage()
                if(newMessage !=null){
                  messageAdapter.submitAtTop(newMessage)
                  viewChanges()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false)
        registerEventBus()

        initRecyclerView()
        initData()

        return binding.root
    }

    private fun initRecyclerView() {
        val llm = LinearLayoutManager(requireContext())
        binding.rvMessage.setHasFixedSize(true)
        binding.rvMessage.layoutManager = llm
        messageAdapter = MessageAdapter(requireContext())
        binding.rvMessage.adapter = messageAdapter
    }

    private fun initData(){
        binding.pbProgress.visibility=View.VISIBLE
        binding.tvNoMessage.visibility=View.GONE
        val savedMessageDao = messsageDatabase.savedMessageDao()

        CoroutineScope(Dispatchers.IO).launch {
            val data  = savedMessageDao.getAllSavedMessageByOrder()
            Log.d(logTag, "messageSaved $data")
            try {
                requireActivity().runOnUiThread {

                    if(data.isEmpty()){
                        binding.tvNoMessage.visibility=View.VISIBLE
                    }
                    messageAdapter.submitList(data)
                    binding.pbProgress.visibility = View.GONE
                }
            }catch (e:Exception){
                Log.d(logTag, "initData: " + e.message + "line no 92" )
            }
        }
    }

    private fun viewChanges(){
        binding.tvNoMessage.visibility = View.GONE
    }

    override fun onDetach() {
        unregisterEventBus()
        super.onDetach()
    }

    private fun registerEventBus() {
        EventBus.getDefault().register(this)
    }

    private fun unregisterEventBus() {
        EventBus.getDefault().unregister(this)
    }


}