package com.ghn.mylibrary.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ghn.mylibrary.data.BaseMultipleItem

/**
 * @author 浩楠
 *
 * @date 2023/11/7-11:28.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 多条目的适配器
 */
abstract class BaseMultipleItemAdapter : BaseAdapter<BaseMultipleItem>() {

    var mVariableIdMap = HashMap<String, Int>()

    override fun isMultiple(): Boolean {
        return true
    }

    /**
     * 以布局做为多条目
     */
    override fun getMultipleItemViewType(position: Int): Int {
        val bean = getList()[position]
        return bean.itemViewType
    }

    override fun dataOperation(holder: BaseViewHolder, t: BaseMultipleItem?, position: Int) {
        if (mVariableIdMap.isNotEmpty()) {
            val bind = DataBindingUtil.bind<ViewDataBinding>(holder.itemView)
            val mVariableId = mVariableIdMap[t?.javaClass?.name]!!
            bindVariable(bind, mVariableId, t)
        }
        bindOperation(holder, t, position)
    }

    /**
     * 绑定
     */
    private fun bindVariable(bind: ViewDataBinding?, variableName: Int, t: BaseMultipleItem?) {
        bind?.setVariable(variableName, t)
        bind?.executePendingBindings()
    }

    abstract fun bindOperation(holder: BaseViewHolder, t: BaseMultipleItem?, position: Int)

    /**
     * 添加布局
     */

    inline fun <reified T> addLayout(layoutId: Int, variableName: Int = -1) {
        try {
            val t = T::class.java.newInstance()
            val baseMultipleItemBean = t as BaseMultipleItem
            mLayouts.put(baseMultipleItemBean.itemViewType, layoutId)
            if (variableName != -1) {
                mVariableIdMap[t.javaClass.name] = variableName
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addLayoutBindData(t: Any, layoutId: Int, variableName: Int = -1) {
        val baseMultipleItemBean = t as BaseMultipleItem
        mLayouts.put(baseMultipleItemBean.itemViewType, layoutId)
        if (variableName != -1) {
            mVariableIdMap[t.javaClass.name] = variableName
        }
    }

    /**
     * 获取条目对象
     */
    fun <T> getItemBean(position: Int): T {
        return getModel(position) as T
    }

}