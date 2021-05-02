package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.models.MealType
import com.htf.diva.netUtils.Constants
import kotlinx.android.synthetic.main.row_my_meal_type_diet.view.*
import kotlinx.android.synthetic.main.row_my_workout.view.*
import kotlinx.android.synthetic.main.row_my_workout.view.tvWorkoutName

class MyMealTypeDietAdapter(private var currActivity: Activity,
                            private var arrMealType:ArrayList<MealType>,
                            private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<MyMealTypeDietAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrMealType[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMealTypeDietAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_my_meal_type_diet
            ,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrMealType.size
    }

    override fun onBindViewHolder(holder: MyMealTypeDietAdapter.MyViewHolder, position: Int) {
        val model=arrMealType[position]
        Glide.with(currActivity).load(Constants.Urls.MEALTYPE_IMAGE_URL + model.image)
                .placeholder(R.drawable.user).into(holder.itemView.ivMealType)

        holder.itemView.tvDietName.text=model.name

    }

}