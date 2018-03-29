package com.victor.test.meetingberlin.di

import com.victor.test.meetingberlin.network.ReviewsRepository
import com.victor.test.meetingberlin.presenters.reviews.ReviewsPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */

@Module(includes = [AppModule::class])
class PresenterModule {

    @Provides
    fun provideReviewsPresenter(reviewsRepository: ReviewsRepository): ReviewsPresenter = ReviewsPresenter(AndroidSchedulers.mainThread(), Schedulers.newThread(), reviewsRepository)
}