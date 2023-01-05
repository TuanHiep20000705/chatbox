package com.mth.example.buoi2telegram.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mth.example.buoi2telegram.R

enum class ItemType {
    FRIENDMESS, MYMESS, LOADMORE
}

class MessageAdapter : ListAdapter<MessageItem, ViewHolder>(messageDiffUtil) {
    private var isLoadingAdd: Boolean = false

    private var listData = mutableListOf<MessageItem>()

    fun setData(list: MutableList<MessageItem>) {
        this.listData = list
        submitList(listData)
    }

    object messageDiffUtil : DiffUtil.ItemCallback<MessageItem>() {
        override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem::class.java.simpleName == newItem::class.java.simpleName
        }

        override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (listData != null && position == listData.size - 1 && isLoadingAdd) {
            ItemType.LOADMORE.ordinal
        } else if (getItem(position).id == ItemType.MYMESS.toString()) {
            ItemType.MYMESS.ordinal
        } else {
            ItemType.FRIENDMESS.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.MYMESS.ordinal -> {
                MyMessViewHolder.create(parent)
            }
            ItemType.FRIENDMESS.ordinal -> {
                FriendMessViewHolder.create(parent)
            }
            else -> {
                LoadingViewHolder.create(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.MYMESS.ordinal -> {
                (holder as MyMessViewHolder).bind(getItem(position) as MessageItem.MyMessage)
            }
            ItemType.FRIENDMESS.ordinal -> {
                (holder as FriendMessViewHolder).bind(getItem(position) as MessageItem.FriendMessage)
            }
            else -> {
                (holder as LoadingViewHolder).bind()
            }
        }
    }

    class MyMessViewHolder(itemView: View) : ViewHolder(itemView) {
        private val txtTime: TextView = itemView.findViewById(R.id.txt_time_item_mymessage)
        private val txtContent: TextView = itemView.findViewById(R.id.txt_content_item_mymessage)
        fun bind(myMessage: MessageItem.MyMessage) {
            txtTime.setText(myMessage.timeMessage)
            txtContent.setText(myMessage.contentMessage)
        }

        companion object {
            fun create(parent: ViewGroup): MyMessViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_mymessage, parent, false)
                return MyMessViewHolder(view)
            }
        }
    }

    class FriendMessViewHolder(itemView: View) : ViewHolder(itemView) {
        private val imgFriend: ImageView = itemView.findViewById(R.id.img_friend_item_friendmessage)
        private val txtNameFriend: TextView =
            itemView.findViewById(R.id.txt_namefriend_item_friendmessage)
        private val txtTime: TextView = itemView.findViewById(R.id.txt_time_item_friendmessage)
        private val txtContent: TextView =
            itemView.findViewById(R.id.txt_content_item_friendmessage)

        fun bind(friendMessage: MessageItem.FriendMessage) {
            imgFriend.setImageResource(friendMessage.avatID)
            txtNameFriend.setText(friendMessage.nameFriend)
            txtTime.setText(friendMessage.timeMessage)
            txtContent.setText(friendMessage.contentMessage)
        }

        companion object {
            fun create(parent: ViewGroup): FriendMessViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_friendmessage, parent, false)
                return FriendMessViewHolder(view)
            }
        }
    }

    class LoadingViewHolder(itemView: View) : ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        fun bind() {}

        companion object {
            fun create(parent: ViewGroup): LoadingViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loadmore, parent, false)
                return LoadingViewHolder(view)
            }
        }
    }

    fun addFooterLoading() {
        isLoadingAdd = true
        listData.add(MessageItem.MyMessage("", "", ""))
        submitList(listData)
    }

    fun removeFooterLoading() {
        isLoadingAdd = false
        val position = listData.size - 1
        val messageItem = getItem(position)
        if (messageItem != null) {
            currentList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}