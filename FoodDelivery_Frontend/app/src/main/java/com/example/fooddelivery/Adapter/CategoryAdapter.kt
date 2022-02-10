package com.example.fooddelivery.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelivery.Activity.ListCategoryFood
import com.example.fooddelivery.Domain.CategoryDomain
import com.example.fooddelivery.R
import java.util.ArrayList


class CategoryAdapter(categoryDomains: ArrayList<CategoryDomain>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var categoryDomains: ArrayList<CategoryDomain>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cat, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryDomains[position]
        holder.categoryName.setText(categoryDomains[position].title)

        holder.mainLayout.setOnClickListener {
            val intent = Intent(holder.itemView.context, ListCategoryFood::class.java)
            intent.putExtra("category", category)
            intent.putExtra("categoryId", position)
            holder.itemView.context.startActivity(intent)
        }
        var picUrl = ""
        when (position) {
            0 -> {
                picUrl = "cat_1"
                holder.mainLayout.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.category_background1
                )
            }
            1 -> {
                picUrl = "cat_2"
                holder.mainLayout.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.category_background2
                )
            }
            2 -> {
                picUrl = "cat_3"
                holder.mainLayout.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.category_background3
                )
            }
            3 -> {
                picUrl = "cat_4"
                holder.mainLayout.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.category_background4
                )
            }
            4 -> {
                picUrl = "cat_5"
                holder.mainLayout.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.category_background5
                )
            }
        }
        val drawableResourceId = holder.itemView.context.resources.getIdentifier(
            picUrl,
            "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .into(holder.categoryPic)
    }

    override fun getItemCount(): Int {
        return categoryDomains.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView
        var categoryPic: ImageView
        var mainLayout: ConstraintLayout

        init {
            categoryName = itemView.findViewById(R.id.categoryName)
            categoryPic = itemView.findViewById(R.id.categoryPic)
            mainLayout = itemView.findViewById(R.id.mainLayout)
        }
    }

    init {
        this.categoryDomains = categoryDomains
    }
}
