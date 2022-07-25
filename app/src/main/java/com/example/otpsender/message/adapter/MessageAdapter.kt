package com.example.otpsender.message.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.otpsender.R
import com.example.otpsender.base.BaseActivity.Companion.binding
import com.example.otpsender.data.entity.MessageEntity
import com.example.otpsender.explore.adapter.ExploreAdapter
import com.example.otpsender.retrofit.UserNetworkEntity
import com.example.otpsender.util.TimeUtil
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter(
    private var context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {


    private var list: MutableList<MessageEntity> = mutableListOf()
    var logTag: String ="@MessageAdapter"

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.findViewById(R.id.tvName)
        var tvMessage : TextView = view.findViewById(R.id.tvMessage)
        var tvTime : TextView = view.findViewById(R.id.tvTime)
        var civUserPicture : CircleImageView = view.findViewById(R.id.civUserPicture)

        fun onBind(item : MessageEntity) {

            // time
            val t: Long = item.savedTimestamp
            val temp = TimeUtil().getTimeAgo(t, context)
            tvTime.text = temp

            //
            tvName.text = item.name
            tvMessage.text = item.message

            // setImage
            // not the best way to do
            Glide
                .with(context)
                .load(item.image)
                .thumbnail(0.7f)
                .centerCrop()
                .placeholder(R.drawable.img_default_profile_light)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(civUserPicture)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).
        inflate(R.layout.view_message_detail, parent, false)
        return  ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = list[position]
        if (holder is ItemViewHolder) {
            holder.onBind(item)
        }
    }

    fun submitList(newList: List<MessageEntity>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun submitAtTop(item : MessageEntity){
        list.add(item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}