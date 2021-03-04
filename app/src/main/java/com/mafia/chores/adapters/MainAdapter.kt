package com.mafia.chores.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mafia.chores.R
import com.mafia.chores.data.User
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_item.view.*

class MainAdapter(private val userList: ArrayList<User>, private val ctx: Context): RecyclerView.Adapter<MainAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(userList[position],context = ctx)
    }

    override fun getItemCount(): Int = userList.size

    fun addData(list: List<User>) {
        userList.clear()
        userList.addAll(list)
    }

    inner class DataViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(user: User, context: Context) {
            containerView.name.text = user.username
            containerView.pass.text = user.password
            containerView.sesionId.text = user.id
            containerView.picture.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_foreground))
        }
    }
}