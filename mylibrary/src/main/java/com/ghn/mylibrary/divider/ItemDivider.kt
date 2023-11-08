package com.ghn.mylibrary.divider

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author 浩楠
 *
 * @date 2023/11/7-14:48.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 分割线
 */
class ItemDivider : RecyclerView.ItemDecoration {

    private var mDivider: Drawable? = null

    private var mLineWidth = 1

    /**
     * 默认横向
     */
    private var mOrientation = RecyclerView.HORIZONTAL

    private var mItemType: Int = 0

    constructor(color: Int, orientation: Int, itemType: Int) {
        mDivider = ColorDrawable(color)
        mOrientation = orientation
        mItemType = itemType
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mOrientation == RecyclerView.HORIZONTAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
            if (mItemType != 0) {
                drawVertical(c, parent)
            }

        }
    }


    /**
     * 绘制横向
     */
    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (mIsHideLast && i == childCount - 1) {
                //隐藏最后一个
                break
            }
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.left - params.leftMargin
            val right = (child.right + params.rightMargin
                    + mLineWidth)
            val top = child.bottom + params.bottomMargin
            val bottom = top + mLineWidth
            mDivider!!.setBounds(left + mMargin, top, right - mMargin, bottom)
            mDivider!!.draw(c)
        }
    }

    /**
     * 绘制纵向
     */
    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (mIsHideLast && i == childCount - 1) {
                //隐藏最后一个
                break
            }
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            val right = left + mLineWidth
            mDivider!!.setBounds(left, top + mMargin, right, bottom - mMargin)
            mDivider!!.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (mOrientation == RecyclerView.HORIZONTAL) {
            outRect.set(0, 0, mLineWidth, 0)
        } else {
            outRect.set(0, 0, 0, mLineWidth)
        }
    }

    /**
     * 设置线条，距离左上右下
     */
    private var mMargin: Int = 0
    fun setMargin(margin: Int) {
        mMargin = margin
    }

    /**
     * 分割线的宽度
     */
    fun setLineWidth(width: Int) {
        mLineWidth = width
    }

    /**
     * 最后一条线是否展示
     */
    private var mIsHideLast = false
    fun hideLast() {
        mIsHideLast = true
    }
}