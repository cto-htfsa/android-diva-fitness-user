package com.htf.diva.dashboard.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowMealTypesBinding
import com.htf.diva.models.MealDietType
import com.htf.diva.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.row_home_diet_plan.view.*

class MealTypesAdapter(private var currActivity: Activity,
                       private var arrDietWeekDays:ArrayList<MealDietType>,
                       private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<MealTypesAdapter.MyViewHolder>(){

    var rowDietWeekDayBinding: RowMealTypesBinding?=null

    inner class MyViewHolder(itemView: RowMealTypesBinding): RecyclerView.ViewHolder(itemView.root){
        init {

            rowDietWeekDayBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrDietWeekDays[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealTypesAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_meal_types
            ,parent,false)
        val bindingUtil= RowMealTypesBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrDietWeekDays.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MealTypesAdapter.MyViewHolder, position: Int) {
        val model=arrDietWeekDays[position]

        if (model.dietPlans!=null){
            holder.itemView.tvSetDietPlan.text= model.dietPlans?.get(position)!!.carbs+" "+currActivity.getString(R.string.carbs)+
                    ", "+model.dietPlans?.get(position)!!.proteins+" "+currActivity.getString(R.string.proteins)+", "+
                    model.dietPlans?.get(position)!!.fats+" "+currActivity.getString(R.string.fats)+", "+model.dietPlans?.get(position)!!.calories+
                    " "+currActivity.getString(R.string.calories)
        }

        rowDietWeekDayBinding!!.mealTypes =model
    }

}