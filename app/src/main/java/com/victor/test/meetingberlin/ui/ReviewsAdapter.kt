package com.victor.test.meetingberlin.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.victor.test.meetingberlin.R
import com.victor.test.meetingberlin.data.ReviewDto
import com.victor.test.meetingberlin.utils.inflate
import com.victor.test.meetingberlin.utils.trace
import kotlinx.android.synthetic.main.adapter_reviews.view.*

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
class ReviewsAdapter(private val reviewsList: ArrayList<ReviewDto>): RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {


    override fun getItemCount(): Int = reviewsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder = ReviewViewHolder(parent.inflate(R.layout.adapter_reviews))

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewsList[position])
    }

    class ReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(review:ReviewDto) = with(itemView) {

            txtAuthor.text = review.author
            txtDate.text = review.date

            if (review.title != null && review.title.isNotEmpty()) {
                txtTitle.text = review.title
            } else {
                txtTitle.visibility = View.GONE
            }

            if (review.message != null && review.message.trim().isNotEmpty()) {
                txtMessage.text = review.message
            } else {
                layoutMessage.visibility = View.GONE
            }

        }
    }
}