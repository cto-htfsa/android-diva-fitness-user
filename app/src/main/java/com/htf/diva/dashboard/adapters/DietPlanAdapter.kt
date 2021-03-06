package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowDietPlanBinding
import com.htf.diva.databinding.RowMealTypesBinding
import com.htf.diva.models.DietPlan
import com.htf.diva.models.MealDietType
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.row_diet_plan.view.*

class DietPlanAdapter(private var currActivity: Activity,
                      private var arrDietWeekDays:ArrayList<DietPlan>,
                      private var iListItemClickListener: IListItemClickListener<Any>):
          RecyclerView.Adapter<DietPlanAdapter.MyViewHolder>(){

    var rowDietWeekDayBinding: RowDietPlanBinding?=null

    inner class MyViewHolder(itemView: RowDietPlanBinding): RecyclerView.ViewHolder(itemView.root){
        init {

            rowDietWeekDayBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrDietWeekDays[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietPlanAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_diet_plan
            ,parent,false)
        val bindingUtil= RowDietPlanBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrDietWeekDays.size
    }

    override fun onBindViewHolder(holder: DietPlanAdapter.MyViewHolder, position: Int) {
        val model=arrDietWeekDays[position]
        holder.itemView.tvUserDiet.text=model.carbs+" "+currActivity.getString(R.string.carbs)+
                ", "+model.proteins+" "+currActivity.getString(R.string.proteins)+", "+
                model.fats+" "+currActivity.getString(R.string.fats)+", "+model.calories+
                " "+currActivity.getString(R.string.calories)
        rowDietWeekDayBinding!!.dietPlan =model
    }

}