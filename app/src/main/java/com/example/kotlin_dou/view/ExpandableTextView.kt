package com.example.kotlin_dou.view


import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView


class ExpandableTextView : AppCompatTextView {

    constructor(mContext: Context) : this(mContext, null)

    constructor(mContext: Context, mAttributeSet: AttributeSet?) : this(mContext, mAttributeSet, 0)


    constructor(mContext: Context, mAttributeSet: AttributeSet?, defStyle: Int) : super(
        mContext,
        mAttributeSet,
        defStyle
    )

    private var maxText = 100

    fun setMaxText(max: Int){
        maxText = max
    }

    open fun setExpandableText(str: String){
        if (str.length <= maxText) {
            this.text = str
        } else {
            val sExpand = "...展开"
            val sContent = str.substring(0, maxText)
            val text = sContent + sExpand

            val spannableStringBuilder = SpannableStringBuilder(text)
            spannableStringBuilder.setSpan(
                ForegroundColorSpan(Color.WHITE),
                0,
                sContent.length,
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
            )
            spannableStringBuilder.setSpan(object : ClickableSpan() {
                override fun onClick(p0: View) {
                    this@ExpandableTextView.text = str
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }, text.length - 3, text.length, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableStringBuilder.setSpan(
                ForegroundColorSpan(Color.argb(88, 255, 255, 255)),
                sContent.length,
                text.length,
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
            )
            this.movementMethod = LinkMovementMethod.getInstance()
            this.text = spannableStringBuilder
        }
    }
}

