package com.mth.example.buoi2telegram.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mth.example.buoi2telegram.R
import com.mth.example.buoi2telegram.message.ItemType
import com.mth.example.buoi2telegram.message.MessageAdapter
import com.mth.example.buoi2telegram.message.MessageItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val messageAdapter by lazy {
        MessageAdapter()
    }
    private var messageList: MutableList<MessageItem> = mutableListOf()
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rcv_chat.adapter = messageAdapter
        setFistData()
        rcv_chat.addOnScrollListener(
            object : PaginationScrollListener(LinearLayoutManager(this@MainActivity)) {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun loadMoreItem() {
                    loadNextPage()
                }

                override fun isLoading() = isLoading
                override fun isLastPage() = isLastPage
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadNextPage() {
        CoroutineScope(Dispatchers.IO).launch {
            isLoading = true
            messageAdapter.submitList(messageList.plus(MessageItem.LoadMore(ItemType.LOAD_MORE.ordinal.toString())))
            delay(2000)
            messageList.addAll(getListMessage())
            messageAdapter.submitList(messageList)
            messageList.removeIf { item ->
                item.id == ItemType.LOAD_MORE.ordinal.toString()
            }
            isLoading = false
        }
    }

    private fun setFistData() {
        messageList = getListMessage()
        messageAdapter.submitList(messageList)
    }

    private fun getListMessage(): MutableList<MessageItem> {
        val listData = mutableListOf<MessageItem>()
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIEND_MESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MY_MESS.toString(),
                "21:00",
                "What? ${System.currentTimeMillis()}"
            )
        )
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIEND_MESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MY_MESS.toString(),
                "21:00",
                "What?"
            )
        )
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIEND_MESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MY_MESS.toString(),
                "21:00",
                "What?"
            )
        )
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIEND_MESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MY_MESS.toString(),
                "21:00",
                "What?"
            )
        )
        listData.add(
            MessageItem.FriendMessage(
                ItemType.FRIEND_MESS.toString(),
                R.drawable.agri3,
                "Hiep",
                "20:00",
                "Alo!!!"
            )
        )
        listData.add(
            MessageItem.MyMessage(
                ItemType.MY_MESS.toString(),
                "21:00",
                "What?"
            )
        )
        return listData
    }
}
