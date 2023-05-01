package com.route.chatc37.ui.addRoom

import androidx.databinding.ObservableField
import com.route.chatc37.UserProvider
import com.route.chatc37.base.BaseNavigator
import com.route.chatc37.base.BaseViewModel
import com.route.chatc37.base.OnDialogClickListener
import com.route.chatc37.database.FireStoreUtils
import com.route.chatc37.database.models.Room

interface Navigator:BaseNavigator{
    fun back()

}
class AddRoomViewModel :BaseViewModel<Navigator>(){
    var selectedCategory: RoomCategory = RoomCategory.getCategories()[0]
    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val titleError = ObservableField<String>()
    val descriptionError = ObservableField<String>()
    fun createRoom(){
        if(!validateForm())return
        val room = Room(
            title = title.get(),
            description = description.get(),
            categoryId = selectedCategory.id,
            createdBy = UserProvider.user?.id)
        navigator?.showLoading("loading...")
        FireStoreUtils()
            .insertRoom(room)
            .addOnCompleteListener{
                navigator?.hideDialoge()
                if(!it.isSuccessful){
                    navigator?.showMessage(it.exception?.localizedMessage?:"")
                    return@addOnCompleteListener
                }
                navigator?.showMessage("Room Created Successfully",
                posActionTitle = "Ok",
                posAction = { navigator?.back() }
                )
            }
    }
    fun validateForm():Boolean{
        var isValid = true
        if(title.get().isNullOrBlank()){
            isValid=false
            titleError.set("Please enter room title")
        }else {
            titleError.set(null)
        }

        if(description.get().isNullOrBlank()){
            isValid=false
            descriptionError.set("Please enter room title")
        }else {
            descriptionError.set(null)
        }
        return isValid
    }
}