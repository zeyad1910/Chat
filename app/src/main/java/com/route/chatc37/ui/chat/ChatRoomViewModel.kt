package com.route.chatc37.ui.chat

import androidx.databinding.ObservableField
import com.google.firebase.Timestamp
import com.route.chatc37.UserProvider
import com.route.chatc37.base.BaseNavigator
import com.route.chatc37.base.BaseViewModel
import com.route.chatc37.database.FireStoreUtils
import com.route.chatc37.database.models.Message
import com.route.chatc37.database.models.Room

interface Navigator:BaseNavigator{

}
class ChatRoomViewModel :BaseViewModel<Navigator> (){

    var room: Room?=null
    val messageField = ObservableField<String>()

    fun sendMessage(){
        if(messageField.get().isNullOrBlank()) return
        val message = Message(
            content = messageField.get(),
            senderName = UserProvider.user?.userName,
            senderId = UserProvider.user?.id,
            roomId = room?.id,
            dateTime = Timestamp.now()
        )
        FireStoreUtils().
        sendMessage(message)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    messageField.set("")
                    return@addOnCompleteListener
                }
                navigator?.showMessage("error sending your message",
                posActionTitle = "try Again",
                posAction = {
                    sendMessage()
                })
            }
    }
}