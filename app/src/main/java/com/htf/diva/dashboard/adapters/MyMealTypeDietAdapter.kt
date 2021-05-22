package com.htf.diva.dashboard.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.models.MealType
import com.htf.diva.netUtils.Constants
import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.row_diet_plan.view.*
import kotlinx.android.synthetic.main.row_home_diet_plan.view.*
import kotlinx.android.synthetic.main.row_home_diet_plan.view.tvDietName


class MyMealTypeDietAdapter(private var currActivity: Activity,
                            private var arrMealType:ArrayList<MealType>,
                            private var iListItemClickListener: IListItemClickListener<Any>

): RecyclerView.Adapter<MyMealTypeDietAdapter.MyViewHolder>(){
    private lateinit var mUserDietPLanAdapter: DietPlanCreatedAdapter
    var selectedPosition=-1
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrMealType[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMealTypeDietAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_home_diet_plan,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrMealType.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyMealTypeDietAdapter.MyViewHolder, position: Int) {
        val model=arrMealType[position]
        Glide.with(currActivity).load(Constants.Urls.MEALTYPE_IMAGE_URL + model.image)
                .placeholder(R.drawable.user).into(holder.itemView.ivMealType)

        holder.itemView.tvDietName.text=model.name

        if (model.userDietPlans!=null){
            holder.itemView.diet_Created_recylerView.visibility=View.VISIBLE
            holder.itemView.tvSetDietPlan.text= model.userDietPlans?.get(position)!!.carbs+" "+currActivity.getString(R.string.carbs)+
                    ", "+model.userDietPlans?.get(position)!!.proteins+" "+currActivity.getString(R.string.proteins)+", "+
                    model.userDietPlans?.get(position)!!.fats+" "+currActivity.getString(R.string.fats)+", "+model.userDietPlans?.get(position)!!.calories+
                    " "+currActivity.getString(R.string.calories)

            val mLayout= LinearLayoutManager(currActivity)
            holder.itemView.diet_Created_recylerView.layoutManager=mLayout
            mUserDietPLanAdapter= DietPlanCreatedAdapter(currActivity,model.userDietPlans!!)
            holder.itemView.diet_Created_recylerView.adapter=mUserDietPLanAdapter

            holder.itemView.ivDropDown.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }


            holder.itemView.ivDropUp.setOnClickListener {
                holder.itemView.ivDropUp.visibility=View.GONE
                holder.itemView.ivDropDown.visibility=View.VISIBLE
                holder.itemView.diet_Created_recylerView.visibility=View.GONE
            }

            if (selectedPosition==position){
                holder.itemView.ivDropDown.setImageResource(R.drawable.ic_drop_down)
                holder.itemView.ivDropUp.visibility=View.VISIBLE
                holder.itemView.diet_Created_recylerView.visibility=View.VISIBLE

            } else{
                holder.itemView.ivDropDown.setImageResource(R.drawable.ic_drop_down)
                holder.itemView.diet_Created_recylerView.visibility=View.GONE
                holder.itemView.ivDropUp.visibility=View.GONE

            }

        }
    }

}