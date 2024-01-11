package kr.hs.dgsw.chatting.chatlist

data class ChatItem(
    val chatId: String,
    val lastMessage: String,
    val otherUserName: String
)