package com.mth.example.buoi2telegram.ui

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mth.example.buoi2telegram.R
import com.mth.example.buoi2telegram.message.ItemType
import com.mth.example.buoi2telegram.message.MessageAdapter
import com.mth.example.buoi2telegram.message.MessageItem
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val messageAdapter by lazy {
        MessageAdapter()
    }
    private var messageList: MutableList<MessageItem> = mutableListOf()
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage = 1
    private val totalPage = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initControls()
    }

    private fun initControls() {
        rcv_chat.adapter = messageAdapter
        setFistData()
        rcv_chat.addOnScrollListener(object : PaginationScrollListener(LinearLayoutManager(this)) {
            override fun loadMoreItem() {
                isLoading = true
                Toast.makeText(this@MainActivity, "a", Toast.LENGTH_SHORT).show()
                currentPage += 1
                loadNextPage()
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }
        })
    }

    private fun loadNextPage() {
        Handler().postDelayed({
            val list: List<MessageItem> = getListMessage()
            messageAdapter.removeFooterLoading()
            messageList.addAll(list)
            messageAdapter.setData(messageList)
            isLoading = false
            if (currentPage < totalPage) {
                messageAdapter.addFooterLoading()
            } else {
                isLastPage = true
            }
        }, 2000)
    }

    private fun setFistData() {
        messageList = getListMessage()
        messageAdapter.setData(messageList)
        if (currentPage < totalPage) {
            messageAdapter.addFooterLoading()
        } else {
            isLastPage = true
        }
    }

    private fun getListMessage(): MutableList<MessageItem> {
        val listData = mutableListOf<MessageItem>()
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIENDMESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MYMESS.toString(),
                "21:00",
                "What?"
            )
        )
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIENDMESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MYMESS.toString(),
                "21:00",
                "What?"
            )
        )
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIENDMESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MYMESS.toString(),
                "21:00",
                "What?"
            )
        )
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIENDMESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MYMESS.toString(),
                "21:00",
                "What?"
            )
        )
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIENDMESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MYMESS.toString(),
                "21:00",
                "What?"
            )
        )
        return listData
    }
}