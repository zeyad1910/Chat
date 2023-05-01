package com.route.chatc37.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.route.chatc37.R
import com.route.chatc37.UserProvider
import com.route.chatc37.database.models.Message
import com.route.chatc37.databinding.ItemMessageBinding
import com.route.chatc37.databinding.ItemRecievedMessageBinding
import com.route.chatc37.databinding.ItemSentMessageBinding

enum class MessageType(val value:Int){
    Received(1),
    Sent(2)
}
class MessagesAdapter (var messages:MutableList<Message>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        if(message.senderId == UserProvider.user?.id){
            return MessageType.Sent.value;
        }
        return MessageType.Received.value
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder{
        if(viewType == MessageType.Sent.value){
            val viewBinding:ItemSentMessageBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_sent_message,parent,false)
            return SentMessageViewHolder(viewBinding)
        }
        val viewBinding:ItemRecievedMessageBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_recieved_message,parent,false)
        return ReceivedMessageViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
                                  position: Int) {
        if(holder is SentMessageViewHolder){
            holder.bind(messages[position])
        }
        if(holder is ReceivedMessageViewHolder){
            holder.bind(messages[position])
        }

    }

    override fun getItemCount(): Int =messages.size

    fun addMessage(message: Message ){
        messages.add(message)
        notifyItemInserted(messages.size)
    }
    class SentMessageViewHolder(val viewBinding:ItemSentMessageBinding)
        :RecyclerView.ViewHolder(viewBinding.root){
            fun bind(message:Message){
                viewBinding.message = message
                viewBinding.invalidateAll()
            }
        }
    class ReceivedMessageViewHolder(val viewBinding:ItemRecievedMessageBinding)
        :RecyclerView.ViewHolder(viewBinding.root){
            fun bind(message:Message){
                viewBinding.message = message
                viewBinding.invalidateAll()
            }
        }
}