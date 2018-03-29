package com.victor.test.meetingberlin.presenters.reviews

import com.victor.test.meetingberlin.data.ReviewDto
import com.victor.test.meetingberlin.network.ReviewsRepository
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

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- VIEW INTERFACE ---------------------------------------------
    interface ReviewsView {
        fun onReviewsListReceived(data: ArrayList<ReviewDto>) {}
        fun onReviewsListError() { }
    }


    // -------------------------------------------------------------------------------------------------------------
    // --------------------------------------------- PRESENTER METHODS ---------------------------------------------
    fun getReviewList(neededReviews: Long) {
        disposable = reviewsRepository.getReviews(neededReviews)
                .observeOn(androidSchedulers)
                .subscribeOn(subscriberSchedulers)
                .subscribe(
                        {
                            trace("getReviewList - response :: $it")
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

    override fun destroy() {
        if (disposable.isDisposed) {
            disposable.dispose()
        }
    }



}