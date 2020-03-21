package com.example.kotlin_dou.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.utils.Utils
import kotlinx.android.synthetic.main.activity_movie_detail.view.*
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*


class TestActivity : BaseActivity() {
    val n = 5
    val height = Utils.dip2px(20f)
    val horizontalMoveDist = Utils.dip2px(110f).toFloat()
    val animations = mutableListOf<Animator>()
    var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initViews()
    }


    private fun initViews() {

        reset()

        btn_reset.setOnClickListener {
            reset()
        }

        btn_move.setOnClickListener {
            if(isPlaying)
                return@setOnClickListener

            isPlaying = true
            val firstPile = mutableListOf<Int>()
            for (i in n downTo 1) {
                firstPile.add(i)
            }
            val status = listOf(
                firstPile,
                mutableListOf(),
                mutableListOf()
            )
            dfs(status, n, 0, 1)
        }
    }

    private fun reset(){
        ll_container.removeAllViews()

        val fromWidth = 100
        val random = Random()
        for (i in n downTo 1) {
            val width = Utils.dip2px(fromWidth - (i - 1) * 10f)
            val view = TextView(this)

            val factor = 0.5
            val red = random.nextInt(255) * factor
            val green = random.nextInt(255) * factor
            val blue = random.nextInt(255) * factor
            val bgColor = Color.rgb(red.toInt(), green.toInt(), blue.toInt())

            view.gravity = Gravity.CENTER
            view.setTextSize(12f)
            view.text = (n - i + 1).toString()
            view.setTextColor(Color.WHITE)
            view.setBackgroundResource(R.drawable.shape_test)

            val drawable = view.background as GradientDrawable
            drawable.setColor(bgColor)

            view.tag = (n - i + 1)


            val layoutParams = LinearLayout.LayoutParams(width, height)
            layoutParams.leftMargin = Utils.dip2px((i - 1) * 5f)
            ll_container.addView(view, layoutParams)
        }
    }

    private fun getViewByTag(number: Int): View? {
        for (i in 0 until ll_container.childCount) {
            val child = ll_container.getChildAt(i)
            if (child.tag as Int == number)
                return child
        }
        return null
    }

    private fun dfs(currStatus: List<MutableList<Int>>, topK: Int, fromPile: Int, toPile: Int) {
        if (topK == 1) {
            val number = currStatus[fromPile].last()
            val fromIndex = currStatus[fromPile].lastIndex
            val toIndex = currStatus[toPile].lastIndex + 1
            currStatus[toPile].add(number)
            currStatus[fromPile].remove(number)

            val view = getViewByTag(number)
            view?.let {
                val xStart = horizontalMoveDist * fromPile + (n - number) * Utils.dip2px(5f)
                val xEnd = horizontalMoveDist * toPile + (n - number) * Utils.dip2px(5f)
                val xAnim = ObjectAnimator.ofFloat(it, "x", xStart, xEnd)

                val yStart = ((n - fromIndex - 1) * height).toFloat()
                val yMid = 0f
                val yEnd = ((n - toIndex - 1) * height).toFloat()
                val yAnim = ObjectAnimator.ofFloat(it, "y", yStart, yMid, yEnd)

                val animatorSet = AnimatorSet().apply { playTogether(xAnim, yAnim) }
                animations.add(animatorSet)
            }

            if (currStatus[toPile].size == n) {
                val animatorSet = AnimatorSet()
//                animatorSet.playSequentially(animations.subList(0, 2))
                animatorSet.playSequentially(animations)
                animatorSet.addListener(object: Animator.AnimatorListener{
                    override fun onAnimationRepeat(p0: Animator?) {}
                    override fun onAnimationCancel(p0: Animator?) {}
                    override fun onAnimationStart(p0: Animator?) {}

                    override fun onAnimationEnd(p0: Animator?) {
                        isPlaying = false
                    }


                })
                animatorSet.start()
            }
        } else {
            val remainPile =
                arrayListOf(0, 1, 2).subtract(arrayListOf(fromPile, toPile)).iterator().next()
            dfs(currStatus, topK - 1, fromPile, remainPile)
            dfs(currStatus, 1, fromPile, toPile)
            dfs(currStatus, topK - 1, remainPile, toPile)
        }
    }

}