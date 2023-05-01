package com.route.chatc37.ui.login

import androidx.databinding.ObservableField
import com.google.firebase.auth.FirebaseAuth
import com.route.chatc37.UserProvider
import com.route.chatc37.base.BaseViewModel
import com.route.chatc37.database.FireStoreUtils
import com.route.chatc37.database.models.User

class LoginViewModel :BaseViewModel<LoginNavigator>() {

    val email = ObservableField<String>()
    val emailError = ObservableField<String?>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<String?>()
    val auth = FirebaseAuth.getInstance()
    fun login(){
        if(!validForm())return;
        
        navigator?.showLoading("Loading...")
        auth.signInWithEmailAndPassword(
            email.get()!!,
            password.get()!!
        ).addOnCompleteListener{task->
            if(task.isSuccessful){
                getUserFromDataBase(task.result.user?.uid?:"")
//                navigator?.showMessage(task.result.user?.uid?:"")
                return@addOnCompleteListener
            }
            navigator?.hideDialoge()
            navigator?.showMessage(task.exception?.localizedMessage?:"")
        }
    }
    fun getUserFromDataBase(uid:String){
        FireStoreUtils()
            .getUserFromDatabase(uid)
            .addOnCompleteListener{
                navigator?.hideDialoge()
                if(it.isSuccessful){
                    val user = it.result.toObject(User::class.java)
                    UserProvider.user = user
                    navigator?.gotoHome();
                }else {
                    navigator?.showMessage(it.exception?.localizedMessage?:"")
                }

            }
    }
    fun validForm():Boolean{
        var isValid = true;
        if(email.get().isNullOrBlank()){
            emailError.set("Please enter your email")
            isValid=false;
        }else {
            isValid=true;
            emailError.set(null)
        }
        if(password.get().isNullOrBlank()){
            isValid = false
            passwordError.set("please enter password")
        }else {
            isValid = true
            passwordError.set(null)
        }

         return isValid;

    }
    fun gotoRegister(){
        navigator?.gotoRegister()
    }
}