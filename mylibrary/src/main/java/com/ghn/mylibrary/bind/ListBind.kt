package com.ghn.mylibrary.bind

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ghn.mylibrary.data.BaseMultipleItem
import com.ghn.mylibrary.listener.OnAdapterListener
import com.ghn.mylibrary.listener.OnAdapterMultipleListener
import com.ghn.mylibrary.utils.divider
import com.ghn.mylibrary.utils.grid
import com.ghn.mylibrary.utils.linear
import com.ghn.mylibrary.utils.set
import com.ghn.mylibrary.utils.setMore
import com.ghn.mylibrary.utils.staggered

/**
 * @author 浩楠
 *
 * @date 2023/11/7-14:41.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
object ListBind {
    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:列表绑定
     */
    @BindingAdapter(
        value = ["listManager", "listOrientation", "listSpanCount",
            "isDivider", "dividerDrawable", "listLayout", "listData",
            "listVariableName", "listAdapter", "isMultiple", "multipleAdapter", "multipleData",
            "multipleLayout", "multipleLayoutBindData", "multipleVariableName"],
        requireAll = false
    )
    @JvmStatic
    fun <T> loadList(
        recyclerView: RecyclerView,
        listManager: Int = 0,
        listOrientation: Int? = null,
        listSpanCount: Int = 2,
        isDivider: Boolean = false,
        dividerDrawable: Int = 0,
        listLayout: Int = 0,//xml布局
        listData: MutableList<T>? = null,
        listVariableName: Int? = null,
        listAdapter: OnAdapterListener<T>? = null,
        isMultiple: Boolean? = null,
        multipleAdapter: OnAdapterMultipleListener? = null,
        multipleData: MutableList<BaseMultipleItem>? = null,
        multipleLayout: MutableList<Int>? = null,
        multipleLayoutBindData: MutableList<Class<*>>? = null,//layout绑定的数据对象
        multipleVariableName: MutableList<Int>? = null,//xml绑定的对应的VariableName
    ) {
        try {
            var recycler: RecyclerView? = null
            when (listManager) {
                0 -> {
                    recycler = if (listOrientation == null) {
                        recyclerView.linear()
                    } else {
                        recyclerView.linear(RecyclerView.HORIZONTAL)
                    }
                }
                1 -> {
                    recycler = if (listOrientation == null) {
                        recyclerView.grid(listSpanCount)
                    } else {
                        recyclerView.grid(listSpanCount, listOrientation)
                    }
                }
                2 -> {
                    recycler = if (listOrientation == null) {
                        recyclerView.staggered(listSpanCount)
                    } else {
                        recyclerView.staggered(listSpanCount, listOrientation)
                    }
                }
            }

            //设置分割线
            if (isDivider) {
                if (dividerDrawable != 0) {
                    recycler?.divider(dividerDrawable)
                } else {
                    recycler?.divider()
                }
            }
            //设置数据
            if (isMultiple != null && isMultiple) {
                //多条目
                recycler?.setMore {
                    if (multipleLayout != null &&
                        multipleLayoutBindData != null
                        && multipleVariableName != null
                    ) {
                        multipleLayout.forEachIndexed { index, i ->
                            val multiple = multipleLayoutBindData[index]
                            val variableName = multipleVariableName[index]
                            addLayoutBindData(multiple.newInstance(), i, variableName)
                        }
                    }

                    //添加数据
                    multipleData?.let {
                        setList(it)
                    }

                    multipleAdapter?.adapter(this)
                }

            } else {
                //普通条目
                val adapter = recycler?.set<T> {
                    if (listVariableName != null) {
                        addLayout(listLayout, listVariableName)
                    } else {
                        addLayout(listLayout)
                    }

                    listData?.let {
                        setList(it)
                    }
                }
                //设置适配器
                listAdapter?.adapter(adapter!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}