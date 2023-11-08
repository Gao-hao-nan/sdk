package com.ghn.mylibrary.adapter

import androidx.databinding.ViewDataBinding
import com.ghn.mylibrary.base.BaseViewHolder
import com.ghn.mylibrary.data.BaseStickHeaderBean
import com.ghn.mylibrary.listener.OnConvertListener
import com.ghn.mylibrary.listener.OnHeadOrFootConvertListener

/**
 * @author 浩楠
 *
 * @date 2023/11/7-11:17.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 拓展函数适配器
 */
class BAdapter<T> : BindingAdapter<T, ViewDataBinding>() {
    private var mLayoutId: Int? = null

    /**
     * 单选更新
     */
    var mNotifySingleChoice = false

    /**
     * 多选更新
     */
    var mNotifyMultipleChoice = false

    override fun getLayoutId(): Int {
        if (mLayoutId == null) {
            throw NullPointerException("没设置layout，我就问你是不是故意的！！！")
        }
        return mLayoutId!!
    }

    override fun isNotifySingleChoice(): Boolean {
        return mNotifySingleChoice
    }

    override fun isNotifyMultipleChoice(): Boolean {
        return mNotifyMultipleChoice
    }

    /**
     * 添加布局
     */
    fun addLayout(layoutId: Int, variableName: Int = -1) {
        mLayoutId = layoutId
        if (variableName != -1) {
            setModelId(variableName)
        }
    }

    override fun bindOperation(holder: BaseViewHolder, item: T?, position: Int) {
        if (mOnConvertListener != null) {
            mOnConvertListener?.bind(holder, item!!)
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
     *绑定数据
     */
    fun bindData(block: BaseViewHolder.(t: T) -> Unit) {
        setConvert(object : OnConvertListener<T> {
            override fun bind(holder: BaseViewHolder, item: T) {
                block(holder, item)
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


    private var mOnConvertListener: OnConvertListener<T>? = null
    private fun setConvert(mOnConvertListener: OnConvertListener<T>) {
        this.mOnConvertListener = mOnConvertListener
    }

    private var mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener? = null
    private fun setHeadOrFootConvert(mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener) {
        this.mOnHeadOrFootConvertListener = mOnHeadOrFootConvertListener
    }


    /**
     * 是否要吸顶
     */
    fun isItemStick(position: Int): Boolean {
        if (position == 0) {
            return true
        }
        val last = getList()[position - 1]
        val t = getList()[position]
        if (last is BaseStickHeaderBean && t is BaseStickHeaderBean) {
            return last.stickGroup != t.stickGroup
        }
        return false
    }

    fun getGroupName(position: Int): String {
        val t = getList()[position]
        return (t as BaseStickHeaderBean).stickGroup
    }
}