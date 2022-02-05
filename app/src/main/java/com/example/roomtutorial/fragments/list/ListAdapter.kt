package com.example.roomtutorial.fragments.list

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtutorial.R
import com.example.roomtutorial.model.User
import kotlinx.android.synthetic.main.custom_row.view.*


class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]

        val i: Int = 0


        val shape = GradientDrawable()
        shape.cornerRadius = 8f

        // add some color
        // You can add your random color generator here
        // and set color

        // add some color
        // You can add your random color generator here
        // and set color
        if (i % 2 == 0) {
            shape.setColor(Color.RED)
        } else {
            shape.setColor(Color.BLUE)
        }

        holder.itemView.rowLayout.setBackgroundResource(R.drawable.bg_rounded_corner)


        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.firstName_txt.text = currentItem.firstName
        holder.itemView.lastName_txt.text = currentItem.lastName
        holder.itemView.age_txt.text = currentItem.age.toString()

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = userList.size
}
