package com.ghn.mylibrary.listener

import com.ghn.mylibrary.base.BaseViewHolder
import com.ghn.mylibrary.data.BaseMultipleItem

/**
 * @author 浩楠
 *
 * @date 2023/11/7-14:34.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 转换接口
 */
interface OnMultipleConvertListener {
    fun bind(holder: BaseViewHolder, t: BaseMultipleItem)
}