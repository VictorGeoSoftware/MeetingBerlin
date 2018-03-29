package com.victor.test.meetingberlin.network

import com.victor.test.meetingberlin.network.responses.ReviewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
interface ReviewsRepository {
    @Headers("Content-Type: application/json;charset=UTF-8")

    @GET("/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json?page=0&rating=0&type=&sortBy=date_of_review&direction=DESC")
    fun getReviews(@Query("count") reviewsQuantity: Long): Observable<ReviewsResponse>
}