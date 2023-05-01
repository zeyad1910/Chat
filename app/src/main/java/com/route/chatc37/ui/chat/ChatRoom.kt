package com.route.chatc37.ui.chat

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.route.chatc37.Constants
import com.route.chatc37.R
import com.route.chatc37.base.BaseActivity
import com.route.chatc37.database.FireStoreUtils
import com.route.chatc37.database.models.Message
import com.route.chatc37.database.models.Room
import com.route.chatc37.databinding.ActivityChatRoomBinding
import java.util.*

class ChatRoom : BaseActivity<ActivityChatRoomBinding,ChatRoomViewModel>(),
    Navigator{
    var room:Room?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeRoom()
        initializeMessagesAdapter()
        viewBinding.vm = viewModel
        viewModel.navigator = this
        subscribeToMessagesChange()
    }
    val messagesAdapter = MessagesAdapter(mutableListOf())
    lateinit var layoutManager : LinearLayoutManager
    fun initializeMessagesAdapter(){
        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        viewBinding.messagesRecycler.adapter = messagesAdapter
        viewBinding.messagesRecycler.layoutManager = layoutManager
    }
    var listener:ListenerRegistration?=null
    override fun onStart() {
        super.onStart()
        subscribeToMessagesChange()
    }
    fun subscribeToMessagesChange(){
        if(listener==null){
            listener = FireStoreUtils()
                .getRoomMessages(room?.id?:"")
                .addSnapshotListener(
                    EventListener { value, error ->
                        if(error!=null){
                            // we have exception
                            showMessage(
                                error.localizedMessage,
                                posActionTitle = "Try Again",
                                posAction = { subscribeToMessagesChange() }
                            )
                            return@EventListener
                        }
                        value?.documentChanges?.forEach {
                            val message = it.document.toObject(Message::class.java)
                            messagesAdapter.addMessage(message)
                            viewBinding.messagesRecycler.smoothScrollToPosition(messagesAdapter.itemCount)
                        }
                    }
                )
        }
    }

    override fun onStop() {
        super.onStop()
        listener?.remove()
        listener = null
    }
    fun initializeRoom(){

      room = if (Build.VERSION.SDK_INT >= 33) {
          intent.getParcelableExtra(Constants.EXTRA_ROOM,Room::class.java)
        }else{
            intent.getParcelableExtra(Constants.EXTRA_ROOM)
        }
        viewModel.room = room
        viewBinding.invalidateAll()
    }

    override fun getLayoutId(): Int = R.layout.activity_chat_room

    override fun generateViewModel(): ChatRoomViewModel {
        return ViewModelProvider(this).get(ChatRoomViewModel::class.java)
    }
}