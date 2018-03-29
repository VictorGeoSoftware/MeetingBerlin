package com.victor.test.meetingberlin

import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.timeout
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.victor.test.meetingberlin.network.ReviewsRepository
import com.victor.test.meetingberlin.network.responses.ReviewsResponse
import com.victor.test.meetingberlin.presenters.reviews.ReviewsPresenter
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ReviewsPresenterTest {
    @Mock lateinit var mockReviewsView: ReviewsPresenter.ReviewsView
    @Mock lateinit var reviewsRepository: ReviewsRepository
    private lateinit var reviewsPresenter: ReviewsPresenter
    private lateinit var testScheduler: TestScheduler

    private val mockedReviewsResponseJson = "{\"status\":true,\"total_reviews_comments\":461,\"data\":[{\"review_id\":2169729,\"rating\":\"5.0\",\"title\":\"Excellent tour\",\"message\":\"This was a great tour and a highlight of my trip to Berlin. The meeting point was easily accessible, tour guide engaging and informative. The tour took us through parts of the building (it\\u0026#039;s so huge it\\u0026#039;s impossible to do it all) and uncovered its history, architecture and usage during the Nazi period, WWII and the Cold War, taking in main areas of the airport and added extras such as the roof and basements and bunkers. A great tour for anyone who appreciates architecture and the history of this incredible city. Highly recommend.\",\"author\":\"Joe \\u2013 United Kingdom\",\"foreignLanguage\":false,\"date\":\"March 28, 2018\",\"date_unformatted\":{},\"languageCode\":\"en\",\"traveler_type\":\"friends\",\"reviewerName\":\"Joe\",\"reviewerCountry\":\"United Kingdom\"}]}"
    private lateinit var mockedReviewsResponse: ReviewsResponse



    private fun createMockedPresenter(): ReviewsPresenter {
        testScheduler = TestScheduler()
        val reviewsPresenter = ReviewsPresenter(testScheduler, testScheduler, reviewsRepository)
        reviewsPresenter.view = mockReviewsView

        return reviewsPresenter
    }

    private fun createMockedResponse(): ReviewsResponse {
        val gson = Gson()
        return gson.fromJson(mockedReviewsResponseJson, ReviewsResponse::class.java)
    }


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        reviewsPresenter = createMockedPresenter()
        mockedReviewsResponse = createMockedResponse()
    }


    @Test
    fun `should make a request a retrieve some response`() {
        val neededReviews: Long = 1
        whenever(reviewsRepository.getReviews(neededReviews)).thenReturn(Observable.just(mockedReviewsResponse))

        reviewsPresenter.getReviewList(neededReviews)
        testScheduler.triggerActions()

        verify(reviewsRepository, times(1)).getReviews(neededReviews)
    }

    @Test
    fun `should make a request and communicate to view the list of received reviews`() {
        val neededReviews: Long = 1
        whenever(reviewsRepository.getReviews(neededReviews)).thenReturn(Observable.just(mockedReviewsResponse))

        reviewsPresenter.getReviewList(neededReviews)
        testScheduler.triggerActions()

        verify(mockReviewsView).onReviewsListReceived(mockedReviewsResponse.data)
    }


    @Test
    fun `should make a request and handle a time out error`() {
        val timeOutException = SocketTimeoutException("TIME_OUT_ERROR")
        val neededReviews: Long = 1
        whenever(reviewsRepository.getReviews(neededReviews)).thenReturn(Observable.error(timeOutException))

        reviewsPresenter.getReviewList(neededReviews)
        testScheduler.triggerActions()

        verify(mockReviewsView).onReviewsListError()
    }
}
