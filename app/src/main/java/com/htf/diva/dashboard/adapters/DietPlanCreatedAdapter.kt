package com.htf.diva.dashboard.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.models.UserDietPlan
import com.htf.diva.netUtils.Constants
import kotlinx.android.synthetic.main.row_diet_plan_created.view.*
import kotlinx.android.synthetic.main.row_home_diet_plan.view.*
import kotlinx.android.synthetic.main.row_home_diet_plan.view.ivMealType
import kotlinx.android.synthetic.main.row_home_diet_plan.view.tvDietName
import kotlinx.android.synthetic.main.row_home_diet_plan.view.tvSetDietPlan

class DietPlanCreatedAdapter(private var currActivity: Activity,
                             private var arrUserDietPlan:ArrayList<UserDietPlan>): RecyclerView.Adapter<DietPlanCreatedAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietPlanCreatedAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_diet_plan_created,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrUserDietPlan.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DietPlanCreatedAdapter.MyViewHolder, position: Int) {
        val model=arrUserDietPlan[position]
        Glide.with(currActivity).load(Constants.Urls.MEAL_IMAGE_URL + model.image)
            .placeholder(R.drawable.user).into(holder.itemView.ivMealType)

        holder.itemView.tvDietName.text=model.name
        holder.itemView.tvItemQty.text=model.quantity.toString()

            holder.itemView.tvSetDietPlan.text= model.carbs+" "+currActivity.getString(
                R.string.carbs)+ ", "+model.proteins+" "+currActivity.getString(R.string.proteins)+", "+
                    model.fats+" "+currActivity.getString(R.string.fats)+", "+model.calories+
                    " "+currActivity.getString(R.string.calories)

    }

}