package com.ghn.sdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ghn.mylibrary.relist.PageRefreshLayout
import com.ghn.mylibrary.utils.divider
import com.ghn.mylibrary.utils.linear
import com.ghn.mylibrary.utils.set

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mRecyclerview = findViewById<PageRefreshLayout>(R.id.pagesh)

        mRecyclerview.refresh { isRefresh, refreshLayout ->
            addData(getList())
        }.autoRefresh()
        mRecyclerview
            .getRecycler()
            .linear()
            .divider()
            .set<OrdinaryListBean> {
                addLayout(R.layout.layout_ordinary_item)
                bindData { t: OrdinaryListBean ->
                    setText(R.id.tv_title, t.title!!)
                    setText(R.id.tv_desc, t.desc!!)
                }
            }.setList(getList())


//        val adapter = object : BaseAdapter<OrdinaryListBean>(R.layout.layout_ordinary_item) {
//            override fun dataOperation(
//                holder: BaseViewHolder,
//                t: OrdinaryListBean?,
//                position: Int
//            ) {
//                t?.title?.let {
//                    holder.setText(R.id.tv_title, it)
//                }
//
//                t?.desc?.let {
//                    holder.setText(R.id.tv_desc, it)
//                }
//                val ivPic = holder.findView<ImageView>(R.id.iv_pic)
//                t?.icon?.let {
//                    ivPic.setImageResource(it)
//                }
//            }
//
//        }
//
//        adapter.setList(getList())
//
//        adapter.setOnItemClickListener {
//            //条目点击事件
//            Toast.makeText(this, "当前点击条目为：$it", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun getList(): MutableList<OrdinaryListBean> {
        return mutableListOf<OrdinaryListBean>().apply {
            for (a in 0..20) {
                val bean = OrdinaryListBean()
                bean.title = "我是标题$a"
                bean.desc = "我是描述信息$a"
                bean.icon = R.mipmap.ic_launcher
                add(bean)
            }
        }
    }
}