package com.route.chatc37.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.route.chatc37.Constants
import com.route.chatc37.R
import com.route.chatc37.base.BaseActivity
import com.route.chatc37.database.models.Room
import com.route.chatc37.databinding.ActivityHomeBinding
import com.route.chatc37.ui.addRoom.AddRoomActivity
import com.route.chatc37.ui.chat.ChatRoom
import com.route.chatc37.ui.chat.ChatRoomViewModel

class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>(),
    Navigator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.vm = viewModel
        viewModel.navigator = this
        initializeAdapter()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.roomsLiveData.observe(this){
            adapter.changeData(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRooms()
    }
    val adapter=RoomsAdapter()
    private fun initializeAdapter() {
        viewBinding.content.roomsRecycler.adapter = adapter
        adapter.onItemClickListener = object:RoomsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, room: Room) {
                val intent = Intent(this@HomeActivity,ChatRoom::class.java);
                intent.putExtra(Constants.EXTRA_ROOM,room)
                startActivity(intent)
            }
        }
    }

    override fun generateViewModel(): HomeViewModel {
        return ViewModelProvider(this)
            .get(HomeViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun openAddRoom() {
        val intent = Intent(this,AddRoomActivity::class.java)
        startActivity(intent)
    }
}