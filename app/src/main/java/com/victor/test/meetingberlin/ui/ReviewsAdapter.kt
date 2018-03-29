package com.victor.test.meetingberlin.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.victor.test.meetingberlin.R
import com.victor.test.meetingberlin.data.ReviewDto
import com.victor.test.meetingberlin.utils.inflate
import com.victor.test.meetingberlin.utils.trace
import kotlinx.android.synthetic.main.adapter_reviews.view.*

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
class ReviewsAdapter(private val reviewsList: ArrayList<ReviewDto>): RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>(), Filterable {
    private var nameFilter: NameFilter

    init {
        nameFilter = NameFilter(this, reviewsList)
    }

    override fun getItemCount(): Int = reviewsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder = ReviewViewHolder(parent.inflate(R.layout.adapter_reviews))

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewsList[position])
    }

    override fun getFilter(): Filter {
        return nameFilter
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

    open class NameFilter(private val reviewsAdapter: ReviewsAdapter, private val reviewsList: ArrayList<ReviewDto>): Filter() {
        private val filteredReviewList = ArrayList<ReviewDto>()

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            filteredReviewList.clear()
            val filterResults = FilterResults()

            if (constraint?.length == 0) {
                filteredReviewList.addAll(reviewsList)
            } else {
                val filterPattern = constraint?.toString()?.toLowerCase()?.trim()

                for (review in reviewsList) {
                    if (review.reviewerName.toLowerCase().startsWith(filterPattern!!)) {
                        filteredReviewList.add(review)
                    }
                }
            }

            trace("NameFilter - results ${filterResults.count} + | ${filterResults.values}")
            filterResults.values = filteredReviewList
            filterResults.count = filteredReviewList.size
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            this.reviewsAdapter.notifyDataSetChanged()
        }
    }
}