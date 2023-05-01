package com.route.chatc37.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.route.chatc37.R
import com.route.chatc37.base.BaseActivity
import com.route.chatc37.databinding.ActivityRegisterBinding
import com.route.chatc37.ui.home.HomeActivity
import com.route.chatc37.ui.login.LoginActivity

class RegisterActivity
    : BaseActivity<ActivityRegisterBinding,RegisterViewModel>(),
    RegisterNavigator{

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }
    override fun generateViewModel(): RegisterViewModel {
        return ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.vm = viewModel
        viewModel.navigator =this
    }

    override fun gotoHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun gotoLogin() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}