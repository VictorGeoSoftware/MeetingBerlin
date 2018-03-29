package com.victor.test.meetingberlin.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.victor.test.meetingberlin.R
import com.victor.test.meetingberlin.data.ReviewDto
import com.victor.test.meetingberlin.presenters.reviews.ReviewsPresenter
import com.victor.test.meetingberlin.ui.ReviewsAdapter
import com.victor.test.meetingberlin.ui.SpaceDecorator
import com.victor.test.meetingberlin.utils.app
import com.victor.test.meetingberlin.utils.getDpFromValue
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ReviewsPresenter.ReviewsView {

    private val component by lazy { app.component.plus(MainActivityModule(this)) }

    @Inject lateinit var reviewsPresenter: ReviewsPresenter
    private val reviewsList = ArrayList<ReviewDto>()
    private lateinit var reviewsAdapter: ReviewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)

        reviewsPresenter.view = this
        reviewsPresenter.getReviewList(10)


        lstReviews.addItemDecoration(SpaceDecorator(getDpFromValue(this, 10)))
        reviewsAdapter = ReviewsAdapter(reviewsList)
        lstReviews.adapter = reviewsAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        reviewsPresenter.destroy()
    }



    // ------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------- REVIEWS VIEW INTERFACE ---------------------------------------------
    override fun onReviewsListReceived(data: ArrayList<ReviewDto>) {
        reviewsList.clear()
        reviewsList.addAll(data)
        reviewsAdapter.notifyDataSetChanged()
    }

    override fun onReviewsListError() {
        Snackbar.make(mainLayout, getString(R.string.network_error), Snackbar.LENGTH_SHORT).show()
    }

}
