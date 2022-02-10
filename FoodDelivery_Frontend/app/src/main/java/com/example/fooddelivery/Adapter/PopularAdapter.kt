package com.example.fooddelivery.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelivery.Activity.ShowDetailActivity
import com.example.fooddelivery.Domain.FoodDomain
import com.example.fooddelivery.R
import java.lang.String
import java.util.ArrayList


class PopularAdapter(FoodDomains: ArrayList<FoodDomain>) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    var foodDomains: ArrayList<FoodDomain>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_popular, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodDomain = foodDomains[position]
        holder.title.text = foodDomain.title
        holder.fee.text = String.valueOf(foodDomain.fee)
        val drawableResourceId = holder.itemView.context.resources.getIdentifier(
            foodDomain.pic,
            "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .into(holder.pic)
        holder.addBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context, ShowDetailActivity::class.java)
            intent.putExtra("food", foodDomain)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return foodDomains.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var fee: TextView
        var pic: ImageView
        var addBtn: TextView

        init {
            title = itemView.findViewById(R.id.title)
            fee = itemView.findViewById(R.id.fee)
            pic = itemView.findViewById(R.id.pic)
            addBtn = itemView.findViewById(R.id.addBtn)
        }
    }

    init {
        foodDomains = FoodDomains
    }
}
