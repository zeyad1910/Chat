package com.route.chatc37.ui.addRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.route.chatc37.R
import com.route.chatc37.base.BaseActivity
import com.route.chatc37.databinding.ActivityAddRoomBinding
import com.route.chatc37.ui.addRoom.RoomCategory.Companion.getCategories

class AddRoomActivity : BaseActivity<ActivityAddRoomBinding,AddRoomViewModel>(),
    Navigator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.vm = viewModel
        viewModel.navigator = this
        initializeSpinner()
        viewBinding.toolBar.backImage.setOnClickListener {
            finish()
        }
    }

    override fun back() {
        finish()
    }
    lateinit var adapter:CategoriesAdapter
    fun initializeSpinner(){
        adapter = CategoriesAdapter(getCategories())
        viewBinding.categorySpinner.adapter = adapter
        viewBinding.categorySpinner.onItemSelectedListener=
            object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parentView: AdapterView<*>?,
                                            itemView: View?,
                                            position: Int, itemId: Long) {
                    val selectedCategory = adapter.getItem(position) as RoomCategory
                    viewModel.selectedCategory = selectedCategory;
//                    val selectedCategory = getCategories().get(position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    override fun generateViewModel(): AddRoomViewModel {
        return ViewModelProvider(this).get(AddRoomViewModel::class.java)
    }

    override fun getLayoutId(): Int =R.layout.activity_add_room

}