package com.route.chatc37.ui.home

import androidx.lifecycle.MutableLiveData
import com.route.chatc37.base.BaseNavigator
import com.route.chatc37.base.BaseViewModel
import com.route.chatc37.database.FireStoreUtils
import com.route.chatc37.database.models.Room

interface Navigator : BaseNavigator{
    fun openAddRoom()
}
class HomeViewModel :BaseViewModel<Navigator>(){
    val roomsLiveData = MutableLiveData<List<Room>>()
    fun addRoomAction(){
        navigator?.openAddRoom()
    }
    fun loadRooms(){
        FireStoreUtils()
            .getAllRooms()
            .addOnCompleteListener{
                if(!it.isSuccessful){
                    navigator?.showMessage("error loading rooms");
                    return@addOnCompleteListener
                }
               val rooms =  it.result.toObjects(Room::class.java);
                roomsLiveData.value = rooms
            }
    }
}