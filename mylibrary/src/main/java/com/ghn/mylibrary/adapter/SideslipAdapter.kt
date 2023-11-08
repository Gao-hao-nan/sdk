package com.ghn.mylibrary.adapter

import com.ghn.mylibrary.base.BaseViewHolder
import com.ghn.mylibrary.listener.OnConvertListener
import com.ghn.mylibrary.listener.OnHeadOrFootConvertListener
import java.lang.NullPointerException

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
 * @Description: TODO 侧滑拓展函数适配器
 */
class SideslipAdapter <T> : SlideDeleteAdapter<T>() {
    private var mLayoutId: Int? = null

    private var mSlideLayoutId: Int? = null

    override fun getViewLayout(): Int {
        if (mLayoutId == null) {
            throw NullPointerException("没设置layout，我就问你是不是故意的！！！")
        }
        return mLayoutId!!
    }

    override fun getSlideLayout(): Int {
        if (mSlideLayoutId == null) {
            return super.getSlideLayout()
        }
        return mSlideLayoutId!!
    }

    /**
     * 添加侧滑删除布局
     */
    fun addSlideLayout(layoutId: Int) {
        mSlideLayoutId = layoutId
    }

    /**
     * 添加布局
     */
    fun addLayout(layoutId: Int, variableName: Int = -1) {
        mLayoutId = layoutId
        setModelId(variableName)
    }

    override fun dataOperationHead(holder: BaseViewHolder, position: Int) {
        super.dataOperationHead(holder, position)
        convertListener(holder, position)
    }

    override fun dataOperationFoot(holder: BaseViewHolder, position: Int) {
        super.dataOperationFoot(holder, position)
        convertListener(holder, position)
    }

    private fun convertListener(holder: BaseViewHolder, position: Int) {
        if (mOnHeadOrFootConvertListener != null) {
            mOnHeadOrFootConvertListener?.bind(holder, position)
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绑定头部或者尾部
     */
    fun bindHeadOrFoot(block: BaseViewHolder.(position: Int) -> Unit) {
        setHeadOrFootConvert(object : OnHeadOrFootConvertListener {
            override fun bind(holder: BaseViewHolder, position: Int) {
                block(holder, position)
            }
        })
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绑定数据
     */
    fun bindData(block: BaseViewHolder.(t: T) -> Unit) {
        setConvert(object : OnConvertListener<T> {
            override fun bind(holder: BaseViewHolder, item: T) {
                block(holder, item)
            }
        })
    }

    override fun slideOperation(holder: BaseViewHolder, item: T?, position: Int) {
        if (mOnConvertListener != null) {
            mOnConvertListener?.bind(holder, item!!)
        }
    }

    private var mOnConvertListener: OnConvertListener<T>? = null
    private fun setConvert(mOnConvertListener: OnConvertListener<T>) {
        this.mOnConvertListener = mOnConvertListener
    }

    private var mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener? = null
    private fun setHeadOrFootConvert(mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener) {
        this.mOnHeadOrFootConvertListener = mOnHeadOrFootConvertListener
    }
}