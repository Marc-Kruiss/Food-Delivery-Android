package com.example.fooddelivery.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelivery.Domain.FoodDomain
import com.example.fooddelivery.Helper.ManagementCart
import com.example.fooddelivery.Interface.ChangeNumberItemsListener
import com.example.fooddelivery.R
import com.example.fooddelivery.SharedPreferences.UserSharedPreferencesService
import java.util.ArrayList


class CartListAdapter(
    FoodDomains: ArrayList<FoodDomain>,
    context: Context?,
    changeNumberItemsListener: ChangeNumberItemsListener
) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    private val foodDomains: ArrayList<FoodDomain>
    private val managementCart: ManagementCart
    private val changeNumberItemsListener: ChangeNumberItemsListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_card, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = foodDomains[position].title
        holder.feeEachItem.text = (foodDomains[position].fee).toString()
        holder.totalEachItem.text =
            (Math.round(foodDomains[position].numberInCart * foodDomains[position].fee * 100.0) / 100.0).toString()
        holder.num.setText((foodDomains[position].numberInCart).toString())
        val drawableResourceId = holder.itemView.context.resources.getIdentifier(
            foodDomains[position].pic,
            "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .into(holder.pic)
        holder.plusItem.setOnClickListener {
            managementCart.plusNumberFood(
                foodDomains,
                position,
                object : ChangeNumberItemsListener {
                    override fun changed() {
                        notifyDataSetChanged()
                        changeNumberItemsListener.changed()
                    }
                })
        }
        holder.minusItem.setOnClickListener {
            managementCart.MinusNumerFood(
                foodDomains,
                position,
                object : ChangeNumberItemsListener {
                    override fun changed() {
                        notifyDataSetChanged()
                        changeNumberItemsListener.changed()
                    }
                })
        }
    }

    override fun getItemCount(): Int {
        return foodDomains.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var feeEachItem: TextView
        var pic: ImageView
        var plusItem: ImageView
        var minusItem: ImageView
        var totalEachItem: TextView
        var num: TextView

        init {
            title = itemView.findViewById(R.id.title2Txt)
            feeEachItem = itemView.findViewById(R.id.feeEachItem)
            pic = itemView.findViewById(R.id.picCard)
            totalEachItem = itemView.findViewById(R.id.totalEachItem)
            num = itemView.findViewById(R.id.numberItemTxt)
            plusItem = itemView.findViewById(R.id.plusCardBtn)
            minusItem = itemView.findViewById(R.id.minusCardBtn)
        }
    }

    init {
        foodDomains = FoodDomains
        managementCart = context?.let { ManagementCart(it) }!!
        this.changeNumberItemsListener = changeNumberItemsListener
    }
}
