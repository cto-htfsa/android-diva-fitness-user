package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowMyMealTypeDietBinding
import com.htf.diva.models.MealType
import com.htf.eyenakhr.callBack.IListItemClickListener

class MyMealTypeDietAdapter(private var currActivity: Activity,
                            private var arrMealType:ArrayList<MealType>,
                            private var iListItemClickListener: IListItemClickListener<Any>): RecyclerView.Adapter<MyMealTypeDietAdapter.MyViewHolder>(){
    var rowDietWeekDayBinding: RowMyMealTypeDietBinding?=null

    inner class MyViewHolder(itemView: RowMyMealTypeDietBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowDietWeekDayBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrMealType[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMealTypeDietAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_my_meal_type_diet
            ,parent,false)
        val bindingUtil= RowMyMealTypeDietBinding.bind(itemView);
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrMealType.size
    }

    override fun onBindViewHolder(holder: MyMealTypeDietAdapter.MyViewHolder, position: Int) {
        val model=arrMealType[position]
        rowDietWeekDayBinding!!.myDietModel =model
    }

}