package com.pajokka.githubuser.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pajokka.githubuser.R
import com.pajokka.githubuser.data.source.local.entity.UserModel

class FollowAdapter(private val listener: (UserModel) -> Unit) :
    RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    private var listUserData = ArrayList<UserModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setDataUser(userData: List<UserModel>?) {
        if (userData == null) {
            return
        }
        this.listUserData.clear()
        notifyDataSetChanged()
        this.listUserData.addAll(userData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_follow, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(listUserData[position])

    override fun getItemCount(): Int = listUserData.size.coerceAtMost(8)

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        private var tvLocation: TextView = itemView.findViewById(R.id.tv_location)
        private var tvRepository: TextView = itemView.findViewById(R.id.tv_repo)
        private var imgPhoto: ImageView = itemView.findViewById(R.id.iv_profile)

        fun bind(userData: UserModel) {
            tvRepository.text = userData.repository

            if (userData.name.length > 25) tvName.text = userData.name.subSequence(0, 25)
            else tvName.text = userData.name

            if (userData.location.length > 12) tvLocation.text =
                userData.location.subSequence(0, 12)
            else tvLocation.text = userData.location

            Glide.with(itemView.context)
                .load(userData.avatar)
                .centerCrop()
                .into(imgPhoto)

            itemView.setOnClickListener {
                listener(userData)
            }
        }
    }
}