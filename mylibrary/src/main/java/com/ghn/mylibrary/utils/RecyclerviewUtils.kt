package com.ghn.mylibrary.utils

import android.graphics.Color
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ghn.mylibrary.adapter.BAdapter
import com.ghn.mylibrary.adapter.BMultipleAdapter
import com.ghn.mylibrary.adapter.SideslipAdapter
import com.ghn.mylibrary.base.BaseAdapter
import com.ghn.mylibrary.divider.ItemDivider
import com.ghn.mylibrary.divider.StickHeaderDecoration

/**
 * @author 浩楠
 *
 * @date 2023/11/7-14:47.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO RecyclerView相关配置
 */

/**
 * 垂直或者横向布局
 */
fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
): RecyclerView {
    clearListValue()
    val manager = LinearLayoutManager(context)
    manager.orientation = orientation
    layoutManager = manager
    return this
}

/**
 * 设置分割线
 */
fun RecyclerView.divider(
    color: Int = Color.parseColor("#cccccc"),
    itemType: Int = 0,
    orientation: Int = RecyclerView.VERTICAL,
    hideLast: Boolean = false,
    lineWidth: Int = 1,
    margin: Int = 0,
): RecyclerView {
    val dividerItemDecoration = ItemDivider(color, orientation, itemType)
    dividerItemDecoration.apply {
        setLineWidth(lineWidth)//设置分割线
        setMargin(margin)//设置边距
        if (hideLast) {
            hideLast()
        }
    }

    addItemDecoration(dividerItemDecoration)
    return this
}

/**
 * 网格
 */
fun RecyclerView.grid(
    spanCount: Int = 1,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
): RecyclerView {
    clearListValue()
    val manager = GridLayoutManager(context, spanCount)
    manager.orientation = orientation
    layoutManager = manager
    return this
}

fun RecyclerView.staggered(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
): RecyclerView {
    clearListValue()
    val manager = StaggeredGridLayoutManager(spanCount, orientation)
    layoutManager = manager
    return this
}

/**
 * 支持拖拽 默认就是上下左右
 */
private var mIsDrag = false
private var mDragDirection = -1
fun RecyclerView.drag(direction: Int = -1): RecyclerView {
    mIsDrag = true
    mDragDirection = if (direction == 0) {
        ItemTouchHelper.UP or ItemTouchHelper.DOWN
    } else {
        direction
    }
    return this
}

/**
 * 哪些不能拖拽
 */
private var mNoDragArray: IntArray? = null
fun RecyclerView.noDrag(vararg position: Int): RecyclerView {
    mNoDragArray = position
    return this
}

/**
 * 是否支持滑动删除
 */
private var mIsSlide = false
private var mSlideDirection = -1
fun RecyclerView.slideDelete(direction: Int = -1): RecyclerView {
    mIsSlide = true
    mSlideDirection = when (direction) {
        0 -> {
            ItemTouchHelper.RIGHT
        }

        1 -> {
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }

        else -> {
            ItemTouchHelper.LEFT
        }
    }
    return this
}

/**
 * 设置适配器
 */
fun <T> RecyclerView.set(block: BAdapter<T>.() -> Unit): BAdapter<T> {
    val bAdapter = BAdapter<T>()
    if (mIsDrag) {
        bAdapter.isDrag()
        if (mDragDirection != -1) {
            bAdapter.setDragDirection(mDragDirection)
        }
    }
    //是否滑动
    if (mIsSlide) {
        bAdapter.isSlideDelete()
        if (mSlideDirection != -1) {
            bAdapter.setSlideDirection(mSlideDirection)
        }
    }
    //哪些不能拖拽
    if (mNoDragArray != null) {
        bAdapter.setRecyclerNoDrag(mNoDragArray!!)
    }
    adapter = bAdapter
    bAdapter.block()
    return bAdapter
}

/**
 * 设置侧滑
 */

fun <T> RecyclerView.setSlide(block: SideslipAdapter<T>.() -> Unit): SideslipAdapter<T> {
    val bAdapter = SideslipAdapter<T>()
    adapter = bAdapter
    bAdapter.block()
    return bAdapter
}

/**
 * 获取多条目适配器
 */

fun RecyclerView.setMore(block: BMultipleAdapter.() -> Unit): BMultipleAdapter {
    val bAdapter = BMultipleAdapter()
    adapter = bAdapter
    bAdapter.block()
    return bAdapter
}


/**
 * 吸顶效果
 */
fun RecyclerView.stick(
    height: Float = 40f,
    bgColor: Int = 0,
    textColor: Int = 0,
    textSize: Float = 14f,
    textPaddingLeft: Float = 0f,
    isTextCenter: Boolean = false,
    lineColor: Int = Color.parseColor("#cccccc"),
    isShowLine: Boolean = true
): RecyclerView {
    addItemDecoration(
        StickHeaderDecoration(
            context, height, bgColor,
            textColor, textSize, textPaddingLeft, isTextCenter, lineColor, isShowLine
        )
    )
    return this
}

fun clearListValue() {
    mIsSlide = false
    mIsDrag = false
    mDragDirection = -1
    mSlideDirection = -1
}


/**
 * 获取适配器
 */
fun <T> RecyclerView.get(): BAdapter<T>? {
    if (adapter != null) {
        return adapter as BAdapter<T>
    }
    return null
}

/**
 * 获取BaseAdapter
 */
fun <T> RecyclerView.getAdapter(): BaseAdapter<T>? {
    if (adapter != null) {
        return adapter as BaseAdapter<T>
    }
    return null
}