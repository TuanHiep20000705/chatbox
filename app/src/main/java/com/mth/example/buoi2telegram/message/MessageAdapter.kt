package com.mth.example.buoi2telegram.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mth.example.buoi2telegram.R

enum class ItemType {
    FRIEND_MESS, MY_MESS, LOAD_MORE
}

class MessageAdapter : ListAdapter<MessageItem, ViewHolder>(MessageDiffUtil) {

    object MessageDiffUtil : DiffUtil.ItemCallback<MessageItem>() {
        override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem::class.java.simpleName == newItem::class.java.simpleName
        }

        override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return when (oldItem) {
                is MessageItem.LoadMore -> {
                    oldItem.id == newItem.id
                }
                is MessageItem.MyMessage -> {
                    oldItem.contentMessage == (newItem as MessageItem.MyMessage).contentMessage
                }
                is MessageItem.FriendMessage -> {
                    oldItem.contentMessage == (newItem as MessageItem.FriendMessage).contentMessage
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MessageItem.FriendMessage -> ItemType.FRIEND_MESS.ordinal
            is MessageItem.MyMessage -> ItemType.MY_MESS.ordinal
            else -> ItemType.LOAD_MORE.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.MY_MESS.ordinal -> {
                MyMessViewHolder.create(parent)
            }
            ItemType.FRIEND_MESS.ordinal -> {
                FriendMessViewHolder.create(parent)
            }
            else -> {
                LoadingViewHolder.create(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.MY_MESS.ordinal -> {
                (holder as MyMessViewHolder).bind(getItem(position) as MessageItem.MyMessage)
            }
            ItemType.FRIEND_MESS.ordinal -> {
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
            txtTime.text = myMessage.timeMessage
            txtContent.text = myMessage.contentMessage
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
            txtNameFriend.text = friendMessage.nameFriend
            txtTime.text = friendMessage.timeMessage
            txtContent.text = friendMessage.contentMessage
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
}
