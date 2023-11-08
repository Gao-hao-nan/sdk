package com.ghn.mylibrary.adapter

import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import com.ghn.mylibrary.R
import com.ghn.mylibrary.base.BaseViewHolder

/**
 * @author 浩楠
 *
 * @date 2023/11/7-14:36.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 滑动删除
 */
abstract class SlideDeleteAdapter<T> : BindingAdapter<T, ViewDataBinding>() {

    //条目点击
    private var mSlideItemClick: ((Int) -> Unit?)? = null


    override fun getLayoutId(): Int {
        return getViewLayout()
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:
     */
    abstract fun getViewLayout(): Int

    override fun bindOperation(holder: BaseViewHolder, item: T?, position: Int) {
        holder.findView<LinearLayout>(R.id.ll_slide).setOnClickListener {
            mSlideItemClick?.invoke(position)
        }
        slideOperation(holder, item, position)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:数据绑定
     */
    abstract fun slideOperation(holder: BaseViewHolder, item: T?, position: Int)

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:侧滑条目点击
     */
    fun setOnSlideClickListener(slideItemClick: (position: Int) -> Unit) {
        mSlideItemClick = slideItemClick
    }

    override fun getIsSlideDelete(): Boolean {
        return true
    }
}