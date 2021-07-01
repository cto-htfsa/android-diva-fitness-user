package com.htf.diva.dashboard.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.models.DietPlan
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.homeDietPlan.DietPlanActivity
import com.htf.diva.models.UserDietPlans
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.AppUtils
import kotlinx.android.synthetic.main.row_diet_plan.view.*
import kotlinx.android.synthetic.main.row_workout_days.view.*
import kotlinx.android.synthetic.main.row_workout_weekdays.view.*
import okhttp3.internal.userAgent
import java.util.*
import kotlin.collections.ArrayList

class DietPlanAdapter(private var currActivity: Activity,
                      private var arrDietWeekDays:ArrayList<DietPlan>,
                      private var iListItemClickListener: IListItemClickListener<Any>):
          RecyclerView.Adapter<DietPlanAdapter.MyViewHolder>(){
    private var timer:Timer?=null
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrDietWeekDays[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietPlanAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.row_diet_plan,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrDietWeekDays.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DietPlanAdapter.MyViewHolder, position: Int) {
        val model=arrDietWeekDays[position]


       holder.itemView.tvDietName.text=model.name

        Glide.with(currActivity).load(
                Constants.Urls.MEAL_IMAGE_URL +
                        model.image)
                .placeholder(R.drawable.user).into(holder.itemView.ivDiet)

        if (model.userDietPlans!=null){
            holder.itemView.llAdd_diet_plan.visibility=View.GONE
            holder.itemView.llQuantity.visibility=View.VISIBLE
            holder.itemView.tvUnits.text=model.unit.toString()
            AppUtils.setText(holder.itemView.etValue,model.userDietPlans!!.quantity.toString(),"")

         }else{
            if (model.unit!=null){
                holder.itemView.llAdd_diet_plan.visibility=View.VISIBLE
                holder.itemView.llQuantity.visibility=View.GONE
                holder.itemView.tvUnits.text=model.unit.toString()
                /*AppUtils.setText(  holder.itemView.etValue,model.userDietPlans!!.quantity.toString(),"")*/
            }else{
                holder.itemView.llQuantity.visibility=View.GONE
                holder.itemView.llAdd_diet_plan.visibility=View.VISIBLE
            }
         }


        holder.itemView.llAdd_diet_plan.setOnClickListener {
            if (model.userDietPlans==null){
                val userModel= UserDietPlans()
                userModel.quantity=1
                userModel.calories="0.0"
                userModel.carbs="0.0"
                userModel.fats="0.0"
                userModel.unit=model.unit
                model.userDietPlans=userModel
               /* (currActivity as DietPlanActivity).dietPlanSelect(model,position)*/

                holder.itemView.llAdd_diet_plan.visibility=View.GONE
                holder.itemView.llQuantity.visibility=View.VISIBLE
                holder.itemView.tvUserDiet.visibility=View.VISIBLE
                holder.itemView.tvUserDiet.text=model.carbs+" "+currActivity.getString(R.string.carbs)+
                        ", "+model.proteins+" "+currActivity.getString(R.string.proteins)+", "+
                        model.fats+" "+currActivity.getString(R.string.fats)+", "+model.calories+
                        " "+currActivity.getString(R.string.calories)

             }
        }

        holder.itemView.etValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
               val units=s.toString()
             /*   if(units!="") {
                    timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            currActivity.runOnUiThread {
                                if (model.userDietPlans!!.quantity!! >= 1) {
                                    model.userDietPlans!!.quantity = units.toInt()
                              *//*      model.quantity= units.toInt()*//*
                                  *//*  arrDietWeekDays[position].quantity=units
                                    (currActivity as DietPlanActivity).dietPlanUnit(model, position)*//*
                                }
                            }
                        }

                    }, 3000)

                }*/
                if (model.userDietPlans!!.quantity!! >= 1) {
                    model.userDietPlans!!.quantity = units.toInt()
                    /*      model.quantity= units.toInt()*/
                    /*  arrDietWeekDays[position].quantity=units
                      (currActivity as DietPlanActivity).dietPlanUnit(model, position)*/
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }

        })

     /*   holder.itemView.ivSubtract.setOnClickListener {
            when (currActivity) {
                is DietPlanActivity -> {
                    if (model.userDietPlans!!.quantity!!>1){
                        (currActivity as DietPlanActivity).dietPlanRemoveQty(model,position)
                    }
                }
            }
        }



        holder.itemView.ivAdd.setOnClickListener {
            when (currActivity) {
                is DietPlanActivity -> {
                    if (model.userDietPlans!!.quantity!!>0){
                        (currActivity as DietPlanActivity).dietPlanAddQty(model,position)
                    }
                }
            }
        }*/

    }


}