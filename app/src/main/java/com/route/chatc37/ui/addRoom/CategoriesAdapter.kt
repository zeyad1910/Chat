package com.route.chatc37.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.route.chatc37.R
import com.route.chatc37.databinding.ItemRoomCategoryBinding

class CategoriesAdapter(val items:List<RoomCategory>) :BaseAdapter (){
    override fun getItem(position: Int): Any {
        return items[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?):
            View {
        var currentItemView = view
        var viewHolder :ViewHolder
        if(currentItemView == null){
         val viewBinding :ItemRoomCategoryBinding=
              DataBindingUtil.inflate(
                  LayoutInflater.from(parent?.context),
                  R.layout.item_room_category,
                  parent,false
              )
            viewHolder = ViewHolder(viewBinding)
            currentItemView = viewHolder.viewBinding.root
            currentItemView.tag = viewHolder
        }else{
            viewHolder = currentItemView.tag as ViewHolder
        }
        // data binding
//        val item = getItem(position) as RoomCategory
        val item = items[position]
        viewHolder.viewBinding.item = item
        viewHolder.viewBinding.invalidateAll()

        return currentItemView
    }
    class ViewHolder(val viewBinding :ItemRoomCategoryBinding)

    override fun getCount(): Int =items.size

}