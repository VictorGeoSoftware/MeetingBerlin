package com.victor.test.meetingberlin.network.responses

import com.victor.test.meetingberlin.data.ReviewDto

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
data class ReviewsResponse(val status:Boolean, val total_reviews_comments:Long, val data: ArrayList<ReviewDto>)