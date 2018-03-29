package com.victor.test.meetingberlin.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.victor.test.meetingberlin.R
import com.victor.test.meetingberlin.data.ReviewDto
import com.victor.test.meetingberlin.network.responses.ReviewsResponse
import com.victor.test.meetingberlin.presenters.reviews.ReviewsPresenter
import com.victor.test.meetingberlin.ui.ReviewsAdapter
import com.victor.test.meetingberlin.ui.SpaceDecorator
import com.victor.test.meetingberlin.ui.dialogs.NewReviewDialog
import com.victor.test.meetingberlin.utils.app
import com.victor.test.meetingberlin.utils.getDpFromValue
import com.victor.test.meetingberlin.utils.trace
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


        setSupportActionBar(toolBar)
        toolBar.inflateMenu(R.menu.main_menu)

        val linearLayoutManager = LinearLayoutManager(this)
        lstReviews.layoutManager = linearLayoutManager
        lstReviews.addItemDecoration(SpaceDecorator(getDpFromValue(this, 10)))
        reviewsAdapter = ReviewsAdapter(reviewsList)
        lstReviews.adapter = reviewsAdapter


        reviewsPresenter.view = this
        reviewsPresenter.getReviewList(10)
    }

    override fun onDestroy() {
        super.onDestroy()
        reviewsPresenter.destroy()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> {

            }

            R.id.action_add_review -> { shoNewReviewDialog() }

            R.id.action_show_all -> { reviewsPresenter.getAllReviews() }
            else -> {}
        }

        return true
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
        val response = getAlternativeResponse()
        reviewsList.clear()
        reviewsList.addAll(response.data)

        reviewsAdapter.notifyDataSetChanged()
    }

    override fun onReviewSubmitted(reviewDto: ReviewDto) {
        trace("MainActiviy - onReviewSubmitted :: $reviewDto")
        reviewsList.add(0, reviewDto)
        reviewsAdapter.notifyItemInserted(0)
        reviewsAdapter.notifyDataSetChanged()
    }



    // -----------------------------------------------------------------------------------------------------------------
    // --------------------------------------------- METHODS AND RUNNABLES ---------------------------------------------
    private fun shoNewReviewDialog() {
        val newReviewDialog = NewReviewDialog.newInstance(object: NewReviewDialog.ReviewSubmittedListener{
            override fun onReviewSubmitted(reviewDto: ReviewDto) {
                reviewsPresenter.submitNewReview(reviewDto)
            }
        })
        newReviewDialog.show(supportFragmentManager, "NewReviewDialog")
    }

    private fun getAlternativeResponse(): ReviewsResponse {
        val source = "{\"status\":true,\"total_reviews_comments\":461,\"data\":[{\"review_id\":2169729,\"rating\":\"5.0\",\"title\":\"Excellent tour\",\"message\":\"This was a great tour and a highlight of my trip to Berlin. The meeting point was easily accessible, tour guide engaging and informative. The tour took us through parts of the building (it\\u0026#039;s so huge it\\u0026#039;s impossible to do it all) and uncovered its history, architecture and usage during the Nazi period, WWII and the Cold War, taking in main areas of the airport and added extras such as the roof and basements and bunkers. A great tour for anyone who appreciates architecture and the history of this incredible city. Highly recommend.\",\"author\":\"Joe \\u2013 United Kingdom\",\"foreignLanguage\":false,\"date\":\"March 28, 2018\",\"date_unformatted\":{},\"languageCode\":\"en\",\"traveler_type\":\"friends\",\"reviewerName\":\"Joe\",\"reviewerCountry\":\"United Kingdom\"},{\"review_id\":2165685,\"rating\":\"5.0\",\"title\":null,\"message\":\"sehr interessant und empfehlenswert\",\"author\":\"Claudia \\u2013 Austria\",\"foreignLanguage\":true,\"date\":\"March 27, 2018\",\"date_unformatted\":{},\"languageCode\":\"de\",\"traveler_type\":null,\"reviewerName\":\"Claudia\",\"reviewerCountry\":\"Austria\"},{\"review_id\":2157972,\"rating\":\"5.0\",\"title\":\"\",\"message\":\"Die Reise zum ehrw\\u00fcrdigen Flughafen war super , besonders Frau Knap hat es super gemacht.\\nWeiter so !!\",\"author\":\"Michael \\u2013 Germany\",\"foreignLanguage\":true,\"date\":\"March 26, 2018\",\"date_unformatted\":{},\"languageCode\":\"de\",\"traveler_type\":\"couple\",\"reviewerName\":\"Michael\",\"reviewerCountry\":\"Germany\"},{\"review_id\":2152548,\"rating\":\"5.0\",\"title\":\"Sehr interessant\",\"message\":\"Ganz tolle F\\u00fchrerin mit viel Wissen und Infos\",\"author\":\"Daniela \\u2013 Germany\",\"foreignLanguage\":true,\"date\":\"March 26, 2018\",\"date_unformatted\":{},\"languageCode\":\"de\",\"traveler_type\":\"friends\",\"reviewerName\":\"Daniela\",\"reviewerCountry\":\"Germany\"},{\"review_id\":2151459,\"rating\":\"5.0\",\"title\":\"Great guide, stories helped understand,entrance hard to find\",\"message\":\"\",\"author\":\"Cecile \\u2013 Canada\",\"foreignLanguage\":false,\"date\":\"March 25, 2018\",\"date_unformatted\":{},\"languageCode\":\"en\",\"traveler_type\":\"couple\",\"reviewerName\":\"Cecile\",\"reviewerCountry\":\"Canada\"},{\"review_id\":2149263,\"rating\":\"5.0\",\"title\":\"Tolle F\\u00fchrung - sehr empfehlenswert!\",\"message\":\"Wir haben viele interessante Sachen erfahren und gesehen. Es hat sich in jedem Fall gelohnt. Die F\\u00fchrung durch Ellen war gut verst\\u00e4ndlich und einfach nur toll! Vielen Dank!\",\"author\":\"Ulrike \\u2013 Germany\",\"foreignLanguage\":true,\"date\":\"March 25, 2018\",\"date_unformatted\":{},\"languageCode\":\"de\",\"traveler_type\":\"family_old\",\"reviewerName\":\"Ulrike\",\"reviewerCountry\":\"Germany\"},{\"review_id\":2137348,\"rating\":\"5.0\",\"title\":\"Tr\\u00e8s int\\u00e9ressant!\",\"message\":\"Belle visite pleine de surprises avec un guide tr\\u00e8s int\\u00e9ressant! Dommage que les visites ne soient qu\\u2019en allemand et en anglais!\\nAttention beaucoup de marche mais aussi beaucoup d\\u2019escaliers!!!\",\"author\":\"Catherine \\u2013 Switzerland\",\"foreignLanguage\":true,\"date\":\"March 23, 2018\",\"date_unformatted\":{},\"languageCode\":\"fr\",\"traveler_type\":\"couple\",\"reviewerName\":\"Catherine\",\"reviewerCountry\":\"Switzerland\"},{\"review_id\":2131337,\"rating\":\"5.0\",\"title\":\"\",\"message\":\"Super F\\u00fchrung, der jung und alt mitgenommen hat. Gro\\u00dfes Lob auch hier nochmal f\\u00fcr unsere F\\u00fchrerin !\\nEinzig, dass man fit sein sollte im Treppensteigen w\\u00e4re noch einen kleinen Hinweis wert :)\",\"author\":\"a GetYourGuide Customer \\u2013 Germany\",\"foreignLanguage\":true,\"date\":\"March 21, 2018\",\"date_unformatted\":{},\"languageCode\":\"de\",\"traveler_type\":\"family_old\",\"reviewerName\":\"Martina\",\"reviewerCountry\":\"Germany\"},{\"review_id\":2129411,\"rating\":\"5.0\",\"title\":\"Es war eine sehr sch\\u00f6ne Tour\",\"message\":\"Der Guide war sehr bem\\u00fcht Fragen zu beantworten. In einigen Bereichen der Geschichte sollten Die Recherchen noch etwas gr\\u00fcndlicher sein.\",\"author\":\"Thomas \\u2013 Germany\",\"foreignLanguage\":true,\"date\":\"March 21, 2018\",\"date_unformatted\":{},\"languageCode\":\"de\",\"traveler_type\":\"solo\",\"reviewerName\":\"Thomas\",\"reviewerCountry\":\"Germany\"},{\"review_id\":2119709,\"rating\":\"5.0\",\"title\":\"sehr guter Guide, kompetent, freundlich, ist zu empfehlen\",\"message\":\"\",\"author\":\"Volker \\u2013 Germany\",\"foreignLanguage\":true,\"date\":\"March 19, 2018\",\"date_unformatted\":{},\"languageCode\":\"de\",\"traveler_type\":\"friends\",\"reviewerName\":\"Volker\",\"reviewerCountry\":\"Germany\"}]}"
        val gson = Gson()
        return gson.fromJson(source, ReviewsResponse::class.java)
    }


}
