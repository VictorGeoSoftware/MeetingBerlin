package com.victor.test.meetingberlin.ui.dialogs

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.victor.test.meetingberlin.R
import com.victor.test.meetingberlin.data.ReviewDto
import com.victor.test.meetingberlin.utils.trace
import kotlinx.android.synthetic.main.dialog_new_review.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
class NewReviewDialog: BottomSheetDialogFragment() {
    private var reviewSubmittedListener: ReviewSubmittedListener? = null


    companion object {
        fun newInstance(reviewSubmittedListener: ReviewSubmittedListener): NewReviewDialog {
            val fragment = NewReviewDialog()
            fragment.reviewSubmittedListener = reviewSubmittedListener
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_new_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnAccept.setOnClickListener {
            if (edtName.text.isNotEmpty() && txtMark.text.isNotEmpty()) {

                val date = Date()
                val dateFormat = SimpleDateFormat("MM dd, yyyy", Locale.getDefault())
                val reviewDate = dateFormat.format(date)
                val languageCode = Locale.getDefault().displayLanguage

                val review = ReviewDto(0, txtMark.text.toString(), edtTitle.text.toString(),
                        edtMessage.text.toString(), edtName.text.toString(), false, reviewDate,
                        languageCode,"tourist", edtName.text.toString(), edtCountry.text.toString())

                reviewSubmittedListener?.onReviewSubmitted(review)
                dismiss()
            } else {
                Toast.makeText(context, getString(R.string.complete_fields), Toast.LENGTH_SHORT).show()
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val mark: Double = (progress / 20.0)
                val numberFormat = DecimalFormat("0.0")
                trace("SeekBar - progress :: $progress || mark :: $mark ")
                txtMark.text = numberFormat.format(mark)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
        })
    }


    interface ReviewSubmittedListener {
        fun onReviewSubmitted(reviewDto: ReviewDto)
    }
}