package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowMealTypesBinding
import com.htf.diva.models.DietWeekdayModel
import com.htf.diva.models.MealDietType
import com.htf.diva.models.MealType
import com.htf.eyenakhr.callBack.IListItemClickListener

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

    override fun onBindViewHolder(holder: MealTypesAdapter.MyViewHolder, position: Int) {
        val model=arrDietWeekDays[position]
        rowDietWeekDayBinding!!.mealTypes =model
    }

}