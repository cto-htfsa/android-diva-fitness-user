package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.models.ReviewRatingModel
import kotlinx.android.synthetic.main.row_review_rating.view.*

class ReviewRatingAdapter(
    private var currActivity: Activity,
    private var arrReviewRating:ArrayList<ReviewRatingModel>) :
    RecyclerView.Adapter<ReviewRatingAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_review_rating, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewRatingAdapter.MyViewHolder, position: Int) {
        val model = arrReviewRating[position]
        holder.itemView.tvRating.text=model.rating.toString()
        holder.itemView.tvRatingTitle.text=model.title
        holder.itemView.tvRatingMessage.text=model.feedback
        holder.itemView.tvUserName.text=model.name

    }

    override fun getItemCount(): Int {
        return arrReviewRating.size
    }
}