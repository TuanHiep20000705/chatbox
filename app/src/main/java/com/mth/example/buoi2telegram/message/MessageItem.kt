package com.mth.example.buoi2telegram.message

sealed class MessageItem(val id: String) {
    class MyMessage(id: String, var timeMessage: String = "", val contentMessage: String) :
        MessageItem(id)
    class FriendMessage(
        id: String,
        var avatID: Int = 0,
        var nameFriend: String = "",
        var timeMessage: String = "",
        val contentMessage: String
    ) : MessageItem(id)
    class LoadMore(id: String) : MessageItem(id)
}
