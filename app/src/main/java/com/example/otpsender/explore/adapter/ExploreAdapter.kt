package com.example.otpsender.explore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.otpsender.R
import com.example.otpsender.model.User
import com.example.otpsender.retrofit.UserNetworkEntity

class ExploreAdapter(
    private var context: Context,
    private var listener: ExploreAdapterListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var list: MutableList<UserNetworkEntity> = mutableListOf()
    var logTag: String ="@ExploreAdapter"


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var cvContactInfo: CardView = view.findViewById(R.id.cvContactInfo)
        var tvFirstName: TextView = view.findViewById(R.id.tvFirstName)
        var tvSurname: TextView = view.findViewById(R.id.tvSurname)

        fun onBind(item : UserNetworkEntity) {

            tvFirstName.text = item.name
            tvSurname.text = item.title

         cvContactInfo.setOnClickListener {
             listener.onItemClicked(item)
         }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).
        inflate(R.layout.view_contact_info, parent, false)
        return  ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = list[position]
        if (holder is ItemViewHolder) {
            holder.onBind(item)
        }
    }

    fun submitList(newList: List<UserNetworkEntity>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ExploreAdapterListener {
        fun onItemClicked(item : UserNetworkEntity)
    }

}