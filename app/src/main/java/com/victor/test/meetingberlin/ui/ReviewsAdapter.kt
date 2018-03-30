package com.victor.test.meetingberlin.ui

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.victor.test.meetingberlin.R
import com.victor.test.meetingberlin.data.ReviewDto
import com.victor.test.meetingberlin.utils.inflate
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

    /**
     * This custom filter only manage reviewer name.
     * But it's simple to extend to another attributes of ReviewDto.
     * I've selected name filter just in example.
     */
    class NameFilter(private val reviewsAdapter: ReviewsAdapter, private val reviewsList: ArrayList<ReviewDto>): Filter() {
        private val originalReviewList = ArrayList<ReviewDto>()
        private val filteredReviewList = ArrayList<ReviewDto>()


        override fun performFiltering(constraint: CharSequence?): FilterResults {
            originalReviewList.clear()
            originalReviewList.addAll(reviewsList)

            filteredReviewList.clear()
            val filterResults = FilterResults()

            if (constraint?.length == 0) {
                filteredReviewList.addAll(reviewsList)
            } else {
                val filterPattern = constraint?.toString()?.toLowerCase()?.trim()

                for (review in reviewsList) {
                    if (review.reviewerName.toLowerCase().startsWith(filterPattern!!, true)) {
                        filteredReviewList.add(review)
                    }
                }
            }

            filterResults.values = filteredReviewList
            filterResults.count = filteredReviewList.size
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val resultList = results?.values as ArrayList<ReviewDto>

            this.reviewsList.clear()
            this.reviewsList.addAll(resultList)
            this.reviewsAdapter.notifyDataSetChanged()

            Handler().postDelayed({
                this.reviewsList.clear()
                this.reviewsList.addAll(originalReviewList)
            }, 100)
        }
    }
}