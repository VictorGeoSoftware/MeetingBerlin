package com.victor.test.meetingberlin.presenters.reviews

import com.google.gson.Gson
import com.victor.test.meetingberlin.data.ReviewDto
import com.victor.test.meetingberlin.network.ReviewsRepository
import com.victor.test.meetingberlin.network.responses.ReviewsResponse
import com.victor.test.meetingberlin.presenters.Presenter
import com.victor.test.meetingberlin.utils.trace
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
class ReviewsPresenter(
        val androidSchedulers: Scheduler,
        val subscriberSchedulers: Scheduler,
        val reviewsRepository: ReviewsRepository): Presenter<ReviewsPresenter.ReviewsView>() {

    private lateinit var disposable: Disposable
    private var maxCount:Long = 10

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- VIEW INTERFACE ---------------------------------------------
    interface ReviewsView {
        fun onReviewsListReceived(data: ArrayList<ReviewDto>) {}
        fun onReviewsListError() { }
        fun onReviewSubmitted(reviewDto: ReviewDto) {}
    }


    // -------------------------------------------------------------------------------------------------------------
    // --------------------------------------------- PRESENTER METHODS ---------------------------------------------
    /**
        In this funcion, we call to web service and aside list of Reviews, we also receive the total
        number of available reviews, which we keep on a variable.
        With this value, we can call to getAllReviews methods, and retrieve all availabe reviews.
     */
    fun getReviewList(neededReviews: Long) {
        disposable = reviewsRepository.getReviews(neededReviews)
                .observeOn(androidSchedulers)
                .subscribeOn(subscriberSchedulers)
                .subscribe(
                        {
                            trace("getReviewList - response :: $it")
                            maxCount = it.total_reviews_comments
                            view?.onReviewsListReceived(it.data)
                        },
                        {
                            trace("getReviewList - Error :: ${it.localizedMessage}")
                            it.printStackTrace()
                            view?.onReviewsListError()
                        },
                        {
                            trace("getReviewList - finish!")
                        }
                )
    }

    fun getAllReviews() {
        disposable = reviewsRepository.getReviews(maxCount)
                .observeOn(androidSchedulers)
                .subscribeOn(subscriberSchedulers)
                .subscribe(
                        {
                            trace("getAllReviews - response :: $it")
                            maxCount = it.total_reviews_comments
                            view?.onReviewsListReceived(it.data)
                        },
                        {
                            trace("getAllReviews - Error :: ${it.localizedMessage}")
                            it.printStackTrace()
                            view?.onReviewsListError()
                        },
                        {
                            trace("getAllReviews - finish!")
                        }
                )
    }

    /**
     * For this function, should be necessary a web service whose payload should be our ReviewDto object, and also
     * and object which define a specific and existing user (UserDto in example).
     * As for the response, it should be a simple OK response, so we can keep connection time.
     * Then, we could pass the object to the MainActivity, or, we can call to getReviews web service again.
     */
    fun submitNewReview(reviewDto: ReviewDto) {
        view?.onReviewSubmitted(reviewDto)
    }

    override fun destroy() {
        if (disposable.isDisposed) {
            disposable.dispose()
        }
    }


}