package com.ghn.mylibrary.base

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @author 浩楠
 *
 * @date 2023/11/7-11:22.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mItemView: View? = null
    private var mSparse = SparseArray<View>()


    init {
        mItemView = itemView
    }

    /**
     * 设置TextView内容
     */
    fun setText(id: Int, content: String) {
        val textView = findView<TextView>(id)
        textView.text = content
    }

    /**
     * 获取View
     */
    fun <V> findView(id: Int): V {
        var view = mSparse.get(id)
        if (view == null) {
            view = mItemView?.findViewById(id)
            mSparse.put(id, view)
        }
        return view as V
    }

    /**
     * 获取DataBinding
     */
    fun <DB : ViewDataBinding> getDataBinding(): DB? {
        return DataBindingUtil.bind(itemView)
    }

    fun <DB : ViewDataBinding> getDataBinding(view: View): DB? {
        return DataBindingUtil.bind(view)
    }

    /**
     * 获取上下文
     */
    fun getContext(): Context {
        return mItemView?.context!!
    }

}