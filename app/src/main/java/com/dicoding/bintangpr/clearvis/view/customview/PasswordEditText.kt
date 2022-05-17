package com.dicoding.bintangpr.clearvis.view.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.bintangpr.clearvis.R


class PasswordEditText: AppCompatEditText {


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        hint = "******"
        background = ContextCompat.getDrawable(context, R.drawable.edittext_layout)

        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length<6){
                    error = "Minimum 6 Karakter"
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
}