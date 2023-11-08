package com.ghn.mylibrary.adapter

import com.ghn.mylibrary.base.BaseMultipleItemAdapter
import com.ghn.mylibrary.base.BaseViewHolder
import com.ghn.mylibrary.data.BaseMultipleItem
import com.ghn.mylibrary.listener.OnHeadOrFootConvertListener
import com.ghn.mylibrary.listener.OnMultipleConvertListener

/**
 * @author 浩楠
 *
 * @date 2023/11/7-14:32.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 多布局
 */
open class BMultipleAdapter: BaseMultipleItemAdapter() {

    /**
     * 数据操作
     */
    override fun bindOperation(holder: BaseViewHolder, t: BaseMultipleItem?, position: Int) {
        if (mOnMultipleConvertListener != null) {
            mOnMultipleConvertListener?.bind(holder, t!!)
        }
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
     * 绑定头部或者尾部
     */
    fun bindHeadOrFoot(block: BaseViewHolder.(position: Int) -> Unit) {
        setHeadOrFootConvert(object : OnHeadOrFootConvertListener {
            override fun bind(holder: BaseViewHolder, position: Int) {
                block(holder, position)
            }
        })
    }

    /**
     * 绑定数据
     */
    fun bindData(block: BaseViewHolder.(t: BaseMultipleItem) -> Unit) {
        setConvert(object : OnMultipleConvertListener {
            override fun bind(holder: BaseViewHolder, t: BaseMultipleItem) {
                block(holder, t)
            }
        })
    }

    /**
     * 错误信息
     */
    private var mException: ((Exception) -> Unit?)? = null
    fun bindError(exception: (e: Exception) -> Unit) {
        mException = exception
    }

    override fun bindViewHolderError(e: Exception) {
        super.bindViewHolderError(e)
        mException?.invoke(e)
    }


    private var mOnMultipleConvertListener: OnMultipleConvertListener? = null
    private fun setConvert(mOnMultipleConvertListener: OnMultipleConvertListener) {
        this.mOnMultipleConvertListener = mOnMultipleConvertListener
    }


    private var mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener? = null
    private fun setHeadOrFootConvert(mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener) {
        this.mOnHeadOrFootConvertListener = mOnHeadOrFootConvertListener
    }

}