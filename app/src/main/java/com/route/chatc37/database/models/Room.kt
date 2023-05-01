package com.route.chatc37.database.models

import android.os.Parcelable
import com.route.chatc37.ui.addRoom.RoomCategory
import kotlinx.parcelize.Parcelize


@Parcelize
data class Room (
    var id:String?=null,
    var title:String?=null,
    var description:String?=null,
    var categoryId:String?=null,
    var createdBy :String?=null
):Parcelable{
   fun getImageId():Int{
        return RoomCategory.getCategoryById(categoryId)
            .imageId
    }
}