package com.pajokka.githubuser.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pajokka.githubuser.R
import com.pajokka.githubuser.ui.detail.DetailActivity.Companion.DATA_USER
import com.pajokka.githubuser.data.source.local.entity.UserModel
import com.pajokka.githubuser.ui.detail.DetailActivity

class UserAdapter(private val listener: (UserModel) -> Unit) :RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private var listUserData = ArrayList<UserModel>()

    fun setDataUser(userData: List<UserModel>?) {
        if (userData == null) {
            return
        }
        this.listUserData.clear()
        this.listUserData.addAll(userData)
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_popular, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(listUserData[position])

    override fun getItemCount(): Int = listUserData.size.coerceAtMost(20)

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        private var tvLocation: TextView = itemView.findViewById(R.id.tv_location)
        private var tvRepository: TextView = itemView.findViewById(R.id.tv_repo)
        private var imgPhoto: ImageView = itemView.findViewById(R.id.iv_profile)

        fun bind(userData: UserModel) {
            tvName.text = userData.name
            tvRepository.text = userData.repository
            if (userData.location.length > 15 )
                tvLocation.text = userData.location.subSequence(0, 15)
            else
                tvLocation.text = userData.location

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