package com.ghn.mylibrary.relist

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.abner.refresh.kernel.SmartRefreshLayout
import com.abner.refresh.kernel.api.RefreshLayout
import com.abner.refresh.kernel.listener.OnRefreshLoadMoreListener
import com.ghn.mylibrary.R
import com.ghn.mylibrary.utils.getAdapter


/**
 * @author 浩楠
 *
 * @date 2023/11/7-16:11.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 下拉刷新列表
 */
class PageRefreshLayout : LinearLayout {

    private var mErrorView: View? = null
    private var mEmptyView: View? = null
    private var mLayoutEmpty: FrameLayout?
    private var mRecyclerView: RecyclerView? = null
    private var mSmartRefreshLayout: SmartRefreshLayout? = null
    private var mPager = 1

    constructor(
        context: Context, attrs: AttributeSet?
    ) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PageRefreshLayout)
        val mEnableRefresh = ta.getBoolean(R.styleable.PageRefreshLayout_srlEnableRefresh, true)
        val mEnableLoadMore = ta.getBoolean(R.styleable.PageRefreshLayout_srlEnableLoadMore, true)
        val view = View.inflate(context, R.layout.layout_refresh_list, null)
        mLayoutEmpty = view.findViewById(R.id.layout_empty)
        mSmartRefreshLayout = view.findViewById(R.id.srl_layout)
        mRecyclerView = view.findViewById(R.id.rv_view)
        setEnableRefresh(mEnableRefresh)
        setEnableLoadMore(mEnableLoadMore)
        addView(view)
    }

    /**
     * I添加空的布局
     */
    fun addEmptyView(layoutId: Int) {
        addEmptyView(View.inflate(context, layoutId, null))
    }


    /**
     * 添加空的View
     */

    fun addEmptyView(view: View) {
        mEmptyView = view
    }

    /**
     * 添加错误的布局
     */
    fun addErrorView(layoutId: Int) {
        addErrorView(View.inflate(context, layoutId, null))
    }

    /**
     * 添加错误View
     */
    fun addErrorView(view: View) {
        mErrorView = view
    }


    /**
     * 显示空布局
     */
    fun showEmptyView() {
        if (mEmptyView != null) {
            mLayoutEmpty?.removeAllViews()
            mLayoutEmpty?.addView(mEmptyView)
            showView(true)
        } else {
            throw NullPointerException("请确定是否执行了addEmptyView方法？")
        }
    }

    /**
     * 显示错误布局
     */
    fun showErrorView() {
        if (mErrorView != null) {
            mLayoutEmpty?.removeAllViews()
            mLayoutEmpty?.addView(mErrorView)
            showView(true)
        } else {
            throw NullPointerException("请确定是否执行了addErrorView方法？")
        }

    }

    fun hintEmptyView() {
        showView(false)
    }

    fun hintErrorView() {
        showView(false)
    }

    private fun showView(isShow: Boolean) {
        if (isShow) {
            mLayoutEmpty?.visibility = View.VISIBLE
            mRecyclerView?.visibility = View.GONE
        } else {
            mLayoutEmpty?.visibility = View.GONE
            mRecyclerView?.visibility = View.VISIBLE
        }
    }


    /**
     * 返回RecyclerView
     */
    fun getRecycler(): RecyclerView {
        return mRecyclerView!!
    }

    /**
     * SmartRefreshLayout
     */
    fun getSmartRefresh(): SmartRefreshLayout {
        return mSmartRefreshLayout!!
    }

    /**
     * 刷新
     */
    fun refresh(block: PageRefreshLayout.(isRefresh: Boolean, refreshLayout: RefreshLayout) -> Unit): PageRefreshLayout {
        mSmartRefreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPager = 1
                block.invoke(this@PageRefreshLayout, true, refreshLayout)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPager++
                block.invoke(this@PageRefreshLayout, false, refreshLayout)
            }
        })
        return this
    }

    /**
     * 获取当前页码
     */
    fun getPager(): Int {
        return mPager
    }

    /**
     * 关闭下拉刷新
     */
    fun finishRefresh() {
        mSmartRefreshLayout?.finishRefresh()
    }

    /**
     * 关闭上拉刷新
     */
    fun finishLoadMore() {
        mSmartRefreshLayout?.finishLoadMore()
    }

    /**
     *禁止上拉
     */
    fun setEnableLoadMore(enabled: Boolean) {
        mSmartRefreshLayout?.setEnableLoadMore(enabled)
    }

    /**
     * 禁止下拉
     */
    fun setEnableRefresh(enabled: Boolean) {
        mSmartRefreshLayout?.setEnableRefresh(enabled)
    }

    /**
     * 自动刷新
     */
    fun autoRefresh() {
        mSmartRefreshLayout?.autoRefresh()
    }

    /**
     * 静默刷新
     */
    fun refresh() {
        mSmartRefreshLayout?.doDefaultRefresh()
    }

    /**
     * 添加数据
     */
    fun <T> addData(@NonNull newData: Collection<T>) {
        val adapter = mRecyclerView?.getAdapter<T>()
        if (getPager() == 1) {
            adapter?.setList(newData as MutableList<T>)
        } else {
            adapter?.addData(newData as MutableList<T>)
        }
        //关闭
        mSmartRefreshLayout?.finishRefresh()
        mSmartRefreshLayout?.finishLoadMore()

    }

    /**
     * 设置高度
     */
    fun setHeightWrapContent() {
        try {
            val layoutParamsRecycler = getRecycler().layoutParams as SmartRefreshLayout.LayoutParams
            layoutParamsRecycler.height = SmartRefreshLayout.LayoutParams.WRAP_CONTENT
            getRecycler().layoutParams = layoutParamsRecycler

            val layoutParamsRefresh = getSmartRefresh().layoutParams as LayoutParams
            layoutParamsRefresh.height = LayoutParams.WRAP_CONTENT
            getSmartRefresh().layoutParams = layoutParamsRefresh
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}