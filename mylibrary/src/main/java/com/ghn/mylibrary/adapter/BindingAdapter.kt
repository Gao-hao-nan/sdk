package com.ghn.mylibrary.adapter

import androidx.databinding.ViewDataBinding
import com.ghn.mylibrary.base.BaseAdapter
import com.ghn.mylibrary.base.BaseViewHolder

/**
 * @author 浩楠
 *
 * @date 2023/11/7-11:19.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
abstract class BindingAdapter<T, VB : ViewDataBinding> : BaseAdapter<T>() {
    /**
     * 设置需要绑定的xml布局
     */
    private var mVariableName = -1
    fun setModelId(variableId: Int) {
        mVariableName = variableId
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:数据操作
     */
    override fun dataOperation(holder: BaseViewHolder, t: T?, position: Int) {
        if (mVariableName != -1) {
            val dataBinding = holder.getDataBinding<VB>()
            dataBinding?.setVariable(mVariableName, t)
            dataBinding?.executePendingBindings()
        }
        bindOperation(holder, t, position)
    }

    abstract fun bindOperation(holder: BaseViewHolder, item: T?, position: Int)
}