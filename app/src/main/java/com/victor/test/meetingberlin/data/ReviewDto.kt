package com.victor.test.meetingberlin.data

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */

/*
    I omit date_unformatted field due to optimize developing time.
 */
data class ReviewDto(val review_id: Long,
                     val rating: String,
                     val title: String?,
                     val message: String?,
                     val author: String,
                     val foreignLanguage: Boolean,
                     val date: String,
                     val languageCode: String,
                     val traveler_type: String,
                     val reviewerName: String,
                     val reviewerCountry: String)